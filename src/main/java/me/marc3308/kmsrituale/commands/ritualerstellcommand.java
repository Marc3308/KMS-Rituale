package me.marc3308.kmsrituale.commands;

import me.marc3308.kmsrituale.commands.subcomannds.*;
import me.marc3308.kmsrituale.utility;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.command.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ritualerstellcommand implements CommandExecutor , TabCompleter {

    private static ArrayList<subcommand> subcommands =new ArrayList<>();
    public ritualerstellcommand(){

        subcommands.add(new helpcommand());
        subcommands.add(new listcommand());
        subcommands.add(new infocommand());
        subcommands.add(new createcommand());
        subcommands.add(new deletecommand());
        subcommands.add(new editcommand());

    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(utility.getcon(1).get("Permisions"+".Ritualcommand")!=null && !p.hasPermission(utility.getcon(1).getString("Permisions"+".Ritualcommand")))return false;
        if(!utility.getcon(1).getBoolean("Ritualcommand"))return false;

        try {
            if(args.length>0){

                switch (args[0]){
                    case "list":
                        getSubcommands().get(1).perform(p,args);
                        break;
                    case "info":
                        getSubcommands().get(2).perform(p,args);
                        break;
                    case "create":
                        getSubcommands().get(3).perform(p,args);
                        break;
                    case "edit":
                        getSubcommands().get(5).perform(p,args);
                        break;
                    case "delete":
                        getSubcommands().get(4).perform(p,args);
                        break;
                    default:
                        getSubcommands().get(0).perform(p,args);
                        break;
                }
            } else {
                getSubcommands().get(0).perform(p,args);
            }
        } catch (IllegalArgumentException e){
            System.out.println("There was a Error with: "+args);
            utility.removesavecons();
        }


        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list =new ArrayList<>();

       try {
           if(args.length == 0)return list;
           if(utility.getcon(1).get("Permisions"+".Ritualcommand")!=null && !((Player) sender).hasPermission(utility.getcon(1).getString("Permisions"+".Ritualcommand")))return list;
           if( utility.getcon(1).get("Ritualcommand")==null || !utility.getcon(1).getBoolean("Ritualcommand"))return list;
           if(args.length>2){
               if(args[0].equals("help"))return list;
               if(args[0].equals("list"))return list;
               if(!args[0].equals("edit") && args.length>4)return list;
           }


           if(args.length == 1){
               for(subcommand s : subcommands){
                   list.add(s.getName());
               }
           } else if (args.length == 2) {
               if(args[0].equals("help"))return list;
               list.add("Feuer");
               list.add("SeeFeuer");
               list.add("RitualPartikel");

           } else if (args.length == 3) {

               if(args[0].equals("create")){
                   list.add("<Name>");
                   return list;
               }

               if(args[1].equals("RitualPartikel")){
                   for (String s : utility.alltheeffects())list.add(utility.getcon(2).getString(s+".Name"));
               } else {
                   for (String s : utility.alltheritual(args[1])){
                       if((args[0].equals("edit") || args[0].equals("info") || args[0].equals("delete")) && s.contains("Gruppe") && !list.contains(utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name"))){
                           list.add(utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name"));
                       }
                       list.add(utility.getcon(1).getString(s+".Name"));
                   }
               }

           } else if (args.length == 4) {

               switch (args[0]){
                   case "create" :
                       if(args[1].equals("RitualPartikel"))return list;
                       for (Material m :Material.values())list.add(m.toString());
                       break;
                   case "edit" :
                       if(args[1].equals("RitualPartikel")){
                           list.add("add");
                           list.add("remove");
                           for(String s : utility.alltheeffects()){
                               if(utility.getcon(2).getString(s+".Name").equals(args[2])){
                                   for (int i = 1; i < 100; i++) {
                                       if(utility.getcon(2).get(s+"."+i)!=null){
                                           list.add(String.valueOf(i));
                                       }
                                   }
                               }
                           }
                       } else {
                           list.add("Name");
                           list.add("Beschreibung");
                           list.add("Partikeleffect");
                           list.add("Zeitlimit");
                           list.add("Auswirkung");
                           list.add("XpKosten");
                           list.add("Materialien");
                           list.add("Sounds");
                           list.add("Nebenwirkung");
                           list.add("Vorausetzungen");

                           for (String s : utility.alltheritual(args[1])){
                               if(s.contains("Gruppe") && utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name").equals(args[2])){
                                   list.remove("Auswirkung");
                                   list.remove("XpKosten");
                                   break;
                               }
                           }
                       }
                       break;
                   default:
                       return list;
               }
           } else if (args.length == 5) {

               if(args[1].equals("RitualPartikel")){
                   switch (args[3]){
                       case "remove":
                           for(String s : utility.alltheeffects()){
                               if(utility.getcon(2).getString(s+".Name").equals(args[2])){
                                   for (int i = 1; i < 100; i++) {
                                       if(utility.getcon(2).get(s+"."+String.valueOf(i))!=null){
                                           list.add(String.valueOf(i));
                                       }
                                   }
                               }
                           }
                           break;
                       case "add":
                           for(String s : utility.alltheeffects()){
                               if(utility.getcon(2).getString(s+".Name").equals(args[2])){
                                   for (int i = 1; i < 100; i++) {
                                       if(utility.getcon(2).get(s+"."+String.valueOf(i))!=null){
                                           if(utility.getcon(2).get(s+"."+i+".Name")!=null){
                                               list.add(utility.getcon(2).getString(s+"."+i+".Name"));
                                           }
                                       }
                                   }
                               }
                           }
                       default:
                           list.add("AnzahlFormen");
                           list.add("Größe");
                           list.add("PartikelEffekt");
                           list.add("AnzahlPartikel");
                           break;
                   }
               } else {
                   switch (args[3]){
                       case "Vorausetzungen":
                           list.add("Wetter");
                           list.add("Biom");
                           list.add("AnzahlSpieler");
                           list.add("Tageszeit");
                           break;
                       case "Sounds":
                           list.add("RichtigesMaterialSound");
                           list.add("FalschesMaterialSound");
                           list.add("AbschlussSound");
                           break;
                       case "Materialien" :
                           list.add("add");
                           list.add("remove");
                           for(String s : utility.alltheritual(args[1])){
                               if(utility.getcon(1).getString(s+".Name").equals(args[2])){
                                   int i=1;
                                   while (utility.getcon(1).get(s+".Material"+"."+i)!=null){
                                       list.add(String.valueOf(i));
                                       i++;
                                   }
                               }
                           }
                           break;
                       case "Auswirkung":
                           list.add("Sound");
                           list.add("Beschwörung");
                           list.add("Teleportation");
                           list.add("Effect");
                           list.add("Kreierung");
                           list.add("Message");
                           list.add("Chanche");
                           list.add("Attack");
                           break;
                       case "Nebenwirkung":
                           list.add("Schaden");
                           list.add("Effect");
                           list.add("Teleportation");
                           list.add("Erfahrungsverlust");
                           break;
                       case "Partikeleffect":
                           int i=1;
                           ArrayList<String> partname=new ArrayList<>();
                           while (utility.getcon(2).get(String.valueOf(i))!=null){
                               if(utility.getcon(2).getString(i+".Name")!=null)list.add(utility.getcon(2).getString(i+".Name"));
                               i++;
                           }
                           list.add("<remove>");
                           list.add("TimeItselfIsJustAConstruckt");
                       default:
                           return list;
                   }
               }
           } else if (args.length == 6) {

               if(args[3].equals("Sounds")){
                   list.add("<remove>");
                   for (Sound s : Sound.values())list.add(s.toString());
               }

               switch (args[4]){
                   case "Attack":
                       list.add("Close");
                       list.add("Long");
                       list.add("Homing");
                       list.add("AOE");
                       list.add("ATELARIE");
                       break;
                   case "Chanche":
                       list.add("Wetter");
                       //list.add("Biom"); //maby somday but not today
                       list.add("Tageszeit");
                       break;
                   case "Wetter":
                       list.add("clear");
                       list.add("rain");
                       list.add("thunder");
                       break;
                   case "Biom":
                       for (Biome b : Biome.values())list.add(b.toString());
                       break;
                   case "Tageszeit":
                       list.add("day");
                       list.add("noon");
                       list.add("night");
                       list.add("midnight");
                       break;
                   case "PartikelEffekt":
                       for(Particle pt : Particle.values())list.add(pt.toString());
                       break;
                   case "AnzahlSpieler":
                       return list;
                   case "add":
                       list.add("<opfern>");
                       for (Material m :Material.values())list.add(m.toString());
                       break;
                   case "remove":
                       for(String s : utility.alltheritual(args[1])){
                           if(utility.getcon(1).getString(s+".Name").equals(args[2])){
                               int i=1;
                               while (utility.getcon(1).get(s+".Material"+"."+i)!=null){
                                   list.add(String.valueOf(i));
                                   i++;
                               }
                           }
                       }
                       break;
                   case "Message":
                       list.add("<die Nachricht>");
                       break;
                   case "Sound":
                       for (Sound s : Sound.values())list.add(s.toString());
                       break;
                   case "Beschwörung":
                       for(EntityType et : EntityType.values())list.add(et.toString());
                       break;
                   case "Teleportation":
                       list.add("<range>");
                       break;
                   case "Effect":
                       for (PotionEffectType ef : PotionEffectType.values())list.add(ef.getName().toString());
                       break;
                   case "Kreierung":
                       for (Material m :Material.values())list.add(m.toString());
                       break;
                   case "Schaden":
                       list.add("<Anzahl>");
                       break;
                   case "Erfahrungsverlust":
                       list.add("<Anzahl>");
                       break;
                   default:
                       if(args[1].equals("RitualPartikel"))return list;
                       list.add("Name");
                       list.add("NamenFarbe");
                       list.add("Material");
                       list.add("Partikeleffect");
                       list.add("ReienvolgenNummer");
                       list.add("Beschreibung");
                       list.add("Custemmoddeldata");
                       break;
               }
           } else if (args.length == 7) {

               switch (args[4]){
                   case "Effect" :
                       list.add("<Stufe>");
                       break;
                   case "Beschwörung":
                       list.add("<Anzahl>");
                       break;
                   case "Kreierung":
                       list.add("<Anzahl>");
                       break;
                   case "Attack":
                       list.add("FIREBALL");
                       list.add("DRAGON_FIREBALL");
                       list.add("SMALL_FIREBALL");
                       list.add("WITHER_SKULL");
                       list.add("TRIDENT");
                       list.add("ARROW");
                       list.add("LLAMA_SPIT");
                       list.add("SNOWBALL");
                       list.add("SNOWBALL");
                       list.add("SHULKER_BULLET");
                       list.add("MINECART_TNT");
                       list.add("EVOKER_FANGS");
                       list.add("PRIMED_TNT");
                       break;
               }
               switch (args[5]){
                   case "<opfern>":
                       for(EntityType et : EntityType.values())list.add(et.toString());
                       break;
                   case "Wetter":
                       list.add("clear");
                       list.add("rain");
                       list.add("thunder");
                       break;
                   case "Biom":
                       for (Biome b : Biome.values())list.add(b.toString());
                       break;
                   case "Tageszeit":
                       list.add("<Zeit>");
                       list.add("1000");
                       list.add("6000");
                       list.add("13000");
                       list.add("18000");
                       break;
                   case "Name" :
                       list.add("<NeuerName>");
                       for(EntityType et : EntityType.values())list.add(et.toString());
                       break;
                   case "NamenFarbe" :
                       for (ChatColor ck : ChatColor.values())list.add(ck.toString());
                       break;
                   case "ReienvolgenNummer" :
                       for(String s : utility.alltheritual(args[1])){
                           if(utility.getcon(1).getString(s+".Name").equals(args[2])){
                               int i=1;
                               while (utility.getcon(1).get(s+".Material"+"."+i)!=null){
                                   list.add(String.valueOf(i));
                                   i++;
                               }
                           }
                       }
                       break;
                   case "Beschreibung" :
                       list.add("<Beschreibung>");
                       break;
                   case "Custemmoddeldata" :
                       list.add("<Nummer>");
                       break;
                   case "Material":
                       list.add("<opfern>");
                       for (Material m :Material.values())list.add(m.toString());
                       break;
                   case "Partikeleffect":
                       int i=1;
                       ArrayList<String> partname=new ArrayList<>();
                       while (utility.getcon(2).get(String.valueOf(i))!=null){
                           if(utility.getcon(2).getString(i+".Name")!=null)list.add(utility.getcon(2).getString(i+".Name"));
                           i++;
                       }
                       list.add("<remove>");
                       break;
               }
           } else if (args.length == 8) {
               if(args[5].equals("<opfern>"))for(EntityType et : EntityType.values())list.add(et.toString());
               if(args[4].equals("Effect"))list.add("<Dauer>");
               if(args[4].equals("Effect"))list.add("<Anzahl>");
           }

           //autocompetion
           ArrayList<String> commpleteList = new ArrayList<>();
           String currentarg = args[args.length-1].toLowerCase();
           for (String s : list){
               if(s==null)return list;
               String s1 =s.toLowerCase();
               if(s1.startsWith(currentarg)){
                   commpleteList.add(s);
               }
           }

           return commpleteList;
       } catch (CommandException e){
           return list;
       }
    }

    public static ArrayList<subcommand> getSubcommands(){
        return subcommands;
    }
}
