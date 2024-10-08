package me.marc3308.kmsrituale.ritual;
import me.marc3308.kmsrituale.KMSRituale;
import me.marc3308.kmsrituale.utility;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Ritual {

    public static final HashMap<Player, String> Ritualpfad=new HashMap<>();
    public static final HashMap<Player, int[]> Ritualnumber=new HashMap<>();
    public static final HashMap<Player,Location> RitualLocation=new HashMap<>();
    private static final HashMap<Player, Integer> Ritualzeit=new HashMap<>();
    private static final HashMap<Player, Player> RitualSpieler=new HashMap<>();

    public static boolean thebook(ItemStack item, String R, Location loc){

        //pafd amkürtzen
        String pfad="Buch"+".Material";
        if(utility.getcon(1).getString(pfad)==null)throw new IllegalArgumentException("KEIN BUUCH WO IST DAS BUUUCH");


        //build
        ItemStack build =new ItemStack(Material.valueOf(utility.getcon(1).getString(pfad)));
        ItemMeta build_meta = build.getItemMeta();
        ArrayList<String> build_lore=new ArrayList<>();

        //name
        if(utility.getcon(1).get(pfad+".AnzeigeName")!=null){

            build_meta.setDisplayName(utility.getcon(1).getString(pfad+".NamensFarbe")==null
                    ? utility.getcon(1).getString(pfad+".AnzeigeName")
                    : utility.getcon(1).getString(pfad+".NamensFarbe")+utility.getcon(1).getString(pfad+".AnzeigeName"));

        }

        //addons
        if(utility.getcon(1).get(pfad+".Beschreibung")!=null)build_lore.add(utility.getcon(1).getString(pfad+".Beschreibung"));
        if(utility.getcon(1).get(pfad+".Custemmoddeldata")!=null)build_meta.setCustomModelData(utility.getcon(1).getInt(pfad+".Custemmoddeldatataken"));

        build_meta.setLore(build_lore);
        build.setItemMeta(build_meta);

        if(!item.equals(build))return false;

        String pf=randomritual(R);
        //this is so there is a failsafe
        if(pf.equals(""))return false;

        //build buch
        ItemStack ritualbuch =new ItemStack(Material.WRITTEN_BOOK);
        BookMeta ritualbuch_buchmeta = (BookMeta) ritualbuch.getItemMeta();
        ArrayList<String> ritualbuch_lore=new ArrayList<>();
        ritualbuch_buchmeta.setAuthor(R);
        ritualbuch_buchmeta.setTitle("Ritual Buch");

        //kleine Beschreibung
        ritualbuch_lore.add(utility.getcon(1).getString(pf+".Beschreibung"));

        String firstpage="~~~~~~Infos~~~~~~";
        firstpage+="\n"+"Name: "+utility.getcon(1).getString(pf+".Name");
        firstpage+="\n"+"Art: "+utility.getcon(1).getString(pf+".Art");
        firstpage+="\n"+"Auswirkung: "+utility.getcon(1).getString(pf+".Auswirkungen");
        if(utility.getcon(1).getString(pf+".Auswirkungestärke")!=null)firstpage+="\n"+"Auswirkungestärke: "+utility.getcon(1).getString(pf+".Auswirkungestärke");
        if(utility.getcon(1).getString(pf+".Auswirkungsdauer")!=null)firstpage+="\n"+"Auswirkungsdauer: "+utility.getcon(1).getString(pf+".Auswirkungsdauer");
        if(utility.getcon(1).getString(pf+".Nebenwirkung")!=null)firstpage+="\n"+"Nebenwirkung: "+utility.getcon(1).getString(pf+".Nebenwirkung");
        if(utility.getcon(1).getString(pf+".Nebenwirkungstärke")!=null)firstpage+="\n"+"Nebenwirkungstärke: "+utility.getcon(1).getString(pf+".Nebenwirkungstärke");
        if(utility.getcon(1).getString(pf+".Nebenwirkungsdauer")!=null)firstpage+="\n"+"Nebenwirkungsdauer: "+utility.getcon(1).getString(pf+".Nebenwirkungsdauer");
        if(utility.getcon(1).getString(pf+".Zeit")!=null)firstpage+="\n"+"ZeitLimit: "+utility.getcon(1).getString(pf+".Zeit");
        if(utility.getcon(1).getString(pf+".Kosten")!=null)firstpage+="\n"+"LevelKosten: "+utility.getcon(1).getString(pf+".Kosten");
        if(utility.getcon(1).getString(pf+".Vorausetzung")!=null)firstpage+="\n"+"Vorausetzung: "+utility.getcon(1).getString(pf+".Vorausetzung");
        if(utility.getcon(1).getString(pf+".Vorausetzungzustand")!=null)firstpage+="\n"+"Zustand: "+utility.getcon(1).getString(pf+".Vorausetzungzustand");


        ritualbuch_buchmeta.addPage(firstpage);
        ritualbuch_buchmeta.addPage("~~~~Materialien~~~~");

        //materialliste
        int O=0;
        if(pf.contains("G")){
            O++;
            String pfr=pf.substring(0, pf.length() - 2)+".Material";
            while (utility.getcon(1).get(pfr+"."+(O))!=null){
                ritualbuch_buchmeta.setPage(ritualbuch_buchmeta.getPageCount(),utility.getcon(1).get(pfr+"."+O+".AnzeigeName")!=null
                        ? ritualbuch_buchmeta.getPage(ritualbuch_buchmeta.getPageCount())+"\nNR "+O+": 1x "+utility.getcon(1).getString(pfr+"."+O+".AnzeigeName")
                        : ritualbuch_buchmeta.getPage(ritualbuch_buchmeta.getPageCount())+"\nNR "+O+": 1x "+ utility.getcon(1).getString(pfr+"."+O+".Material"));
                O++;
            }
        }

        pf=pf+".Material";
        int i=1;
        while (utility.getcon(1).get(pf+"."+(i))!=null){
            ritualbuch_buchmeta.setPage(ritualbuch_buchmeta.getPageCount(),utility.getcon(1).get(pf+"."+i+".AnzeigeName")!=null
                    ? ritualbuch_buchmeta.getPage(ritualbuch_buchmeta.getPageCount())+"\nNR "+(i+O)+": 1x "+utility.getcon(1).getString(pf+"."+i+".AnzeigeName")
                    : ritualbuch_buchmeta.getPage(ritualbuch_buchmeta.getPageCount())+"\nNR "+(i+O)+": 1x "+ utility.getcon(1).getString(pf+"."+i+".Material"));
            i++;
        }

        loc.getWorld().getBlockAt(loc).setType(Material.AIR);
        ritualbuch_buchmeta.setLore(ritualbuch_lore);
        ritualbuch.setItemMeta(ritualbuch_buchmeta);
        loc.getWorld().dropItemNaturally(loc,ritualbuch);

        return true;
    }

    public static String randomritual(String art){
        ArrayList<String> arten=new ArrayList<>();
        if(art.equals("")){
            arten.add("Feuer");
            arten.add("SeeFeuer");
        } else {
            arten.add(art);
        }
        ArrayList<String> pfäde=new ArrayList<>();

        for(String s : arten){
            int i=1;
            while (utility.getcon(1).get(s+".Gruppe"+"."+i)!=null){
                int ig=1;
                //check if there is a ritual in a grupe left
                while(utility.getcon(1).get(s+".Gruppe"+"."+i+"."+ig)!=null){
                    pfäde.add(s+".Gruppe"+"."+i+"."+ig);
                    ig++;
                }
                i++;
            }
            i=1;
            while(utility.getcon(1).get(s+"."+i)!=null){
                pfäde.add(s+"."+i);
                i++;
            }
        }

        Random rn = new Random();
        return pfäde.get(rn.nextInt(pfäde.size()));
    }

    public static void removerit(Player p,String s){
        Ritualnumber.remove(p);
        RitualLocation.remove(p);
        Ritualpfad.remove(p);
        Ritualzeit.remove(p);
        RitualSpieler.remove(p);
    }

    public static boolean additemplus (Player p, String R){
        int[] next =Ritualnumber.get(p);
        next[next.length-1]+=1;
        Ritualnumber.put(p,next);

        //System.out.println(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Material"+"."+(Ritualnumber.get(p)[Ritualnumber.get(p).length-1]));
        //Check if ritual ist fertig
        if(utility.getcon(1).get(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Material"+"."+(Ritualnumber.get(p)[Ritualnumber.get(p).length-1]))==null){
            if(Ritualpfad.get(p).contains(".") && Ritualpfad.get(p).matches("\\D+")){
                Ritualpfad.put(p,Ritualpfad.get(p)+"."+next.length);
                Ritualnumber.remove(p);
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean nextitem(Player p, ItemStack item){

        //pafd amkürtzen
        String pfad=Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Material"+"."+(Ritualnumber.get(p)[Ritualnumber.get(p).length-1]);
        if(utility.getcon(1).getString(pfad+".Material")==null)throw new IllegalArgumentException("Kein Material Angegeben");
        if(utility.getcon(1).getString(pfad+".Material").equals("<opfern>"))return false;

        //build
        ItemStack build =new ItemStack(Material.valueOf(utility.getcon(1).getString(pfad+".Material")));
        ItemMeta build_meta = build.getItemMeta();
        ArrayList<String> build_lore=new ArrayList<>();

        //name
        if(utility.getcon(1).get(pfad+".AnzeigeName")!=null){

            build_meta.displayName(Component.text(utility.getcon(1).getString(pfad+".NamensFarbe")==null
                    ? utility.getcon(1).getString(pfad+".AnzeigeName")
                    : utility.getcon(1).getString(pfad+".NamensFarbe")+utility.getcon(1).getString(pfad+".AnzeigeName")));

        }

        //check if player ritual
        if(item.hasItemMeta() && item.getItemMeta().hasLore() && Bukkit.getPlayer(UUID.fromString(item.getItemMeta().getLore().get(0)))!=null){
            RitualSpieler.put(p,Bukkit.getPlayer(UUID.fromString(item.getItemMeta().getLore().get(0))));
            build_lore.add(item.getItemMeta().getLore().get(0));
        }

        //addons
        if(utility.getcon(1).get(pfad+".Beschreibung")!=null)build_lore.add(utility.getcon(1).getString(pfad+".Beschreibung"));
        if(utility.getcon(1).get(pfad+".Custemmoddeldata")!=null)build_meta.setCustomModelData(utility.getcon(1).getInt(pfad+".Custemmoddeldatataken"));

        build_meta.setLore(build_lore);
        build.setItemMeta(build_meta);

        //check if sound
        if(item.equals(build)
                && utility.getcon(1).get(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Sound")!=null){
            p.getWorld().playSound(RitualLocation.get(p), Sound.valueOf(utility.getcon(1).getString(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Sound")), 10, 1);
        }

        return item.equals(build);

    }

    public static void wrong(Player p, String wrongg){
        String pfad=Ritualpfad.get(p)+"."+Ritualnumber.get(p).length;
        p.playSound(p.getLocation(),utility.getcon(1).getString(pfad+".FailsSound")==null
                ? Sound.BLOCK_LAVA_EXTINGUISH
                : Sound.valueOf(utility.getcon(1).getString(pfad+".FailsSound")),5,1);



        p.sendTitle(org.bukkit.ChatColor.RED+"Das Ritual ist gescheitert",  wrongg.equals("Falsches Item")
                ? org.bukkit.ChatColor.RED+"Du hast ein Falsches Item Benutzt!"
                : wrongg.equals("Zeit Überschritten")
                ? org.bukkit.ChatColor.RED+"Du hast zu lange gebraucht!"
                : wrongg.equals("Keineverbindung")
                ? org.bukkit.ChatColor.RED+"Du scheinst keine verbindung zu dem spieler zu bekommen"
                : wrongg.equals("falsche vorausetzung")
                ? org.bukkit.ChatColor.RED+"Es scheinen die Vorausetzung für dieses Ritual sind nicht gegeben"
                : org.bukkit.ChatColor.RED+"Du hast nicht genug Level!");


        try {
            //pafd amkürtzen
            switch (utility.getcon(1).getString(pfad+".Nebenwirkung")!=null ? utility.getcon(1).getString(pfad+".Nebenwirkung"):"a"){
                case "Damage":
                    p.setHealth(p.getHealth()-utility.getcon(1).getInt(pfad+".Nebenwirkungstärke")<=0 ? 1.0 : p.getHealth()-utility.getcon(1).getInt(pfad+".Nebenwirkungstärke"));
                    break;
                case "Teleport":
                    Random rn = new Random();
                    Location loc=p.getLocation().add(rn.nextInt(0,utility.getcon(1).getInt(pfad+".Nebenwirkungstärke"))
                            ,rn.nextInt(0,utility.getcon(1).getInt(pfad+".Nebenwirkungstärke")),rn.nextInt(utility.getcon(1).getInt(pfad+".Nebenwirkungstärke")));
                    loc.setY(p.getWorld().getHighestBlockYAt(loc)+1);
                    loc.setYaw(rn.nextFloat(0,380));
                    loc.setPitch(rn.nextFloat(-90,90));
                    p.teleport(loc);
                    break;
                case "Effect":
                    p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(utility.getcon(1).getString(pfad+".Nebenwirkungseffect"))
                            ,utility.getcon(1).getInt(pfad+".Nebenwirkungsdauer")*20
                            ,utility.getcon(1).getInt(pfad+".Nebenwirkungstärke")-1,false,false));
                    break;
                case "Xp":
                    p.setLevel(p.getLevel()-utility.getcon(1).getInt(pfad+".Nebenwirkungstärke")>=0 ? p.getLevel()-utility.getcon(1).getInt(pfad+".Nebenwirkungstärke") : 0);
                    break;
                case "Spaß":
                    break;
            }


        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Falsche Auswirkungen");
        }
        removerit(p,"wrong");
    }

    public static boolean vorausetzungen(Player p){

        if(utility.getcon(1).get(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Vorausetzung")!=null){


            switch (utility.getcon(1).getString(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Vorausetzung")){
                case "Wetter":
                    String wetter=utility.getcon(1).getString(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Vorausetzungzustand");
                    if(wetter.equals("clear") && !p.getWorld().isClearWeather()){
                        return false;
                    } else if (wetter.equals("rain") && !p.getWorld().hasStorm()) {
                        return false;
                    } else if (wetter.equals("thunder") && !p.getWorld().isThundering()) {
                        return false;
                    }
                    break;
                case "Biom":
                    String biom=utility.getcon(1).getString(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Vorausetzungzustand");
                    if(!p.getWorld().getBiome(RitualLocation.get(p)).toString().equals(biom))return false;
                    break;
                case "AnzahlSpieler":
                    Integer anzsp=utility.getcon(1).getInt(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Vorausetzungzustand");
                    if(RitualLocation.get(p).getNearbyEntities(10,10,10).size()<anzsp)return false;
                    break;
                case "Tageszeit":
                    String tageszeit=utility.getcon(1).getString(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Vorausetzungzustand");
                    if(tageszeit.equals("day") && (p.getWorld().getTime()<1000 || p.getWorld().getTime()>5999)){
                        return false;
                    } else if (tageszeit.equals("noon") && (p.getWorld().getTime()<6000 || p.getWorld().getTime()>12999)) {
                        return false;
                    } else if (tageszeit.equals("night") && (p.getWorld().getTime()<13000 || p.getWorld().getTime()>17999)) {
                        return false;
                    } else if (tageszeit.equals("midnight") && p.getWorld().getTime()<18000 && p.getWorld().getTime()>1000) {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Wrong vorausetzung");
            }

        }
        return true;
    }

    public static void activateritual(Player p,Location loc){

        p.getWorld().strikeLightningEffect(RitualLocation.get(p));
        p.getWorld().strikeLightningEffect(RitualLocation.get(p));
        p.getWorld().getBlockAt(RitualLocation.get(p)).setType(Material.AIR);

        String pfad=Ritualpfad.get(p)+"."+Ritualnumber.get(p).length;

        //add kosten
        try {
            if(utility.getcon(1).get(pfad+".Kosten")!=null){
                if(utility.getcon(1).getInt(pfad+".Kosten")>p.getLevel()){
                    Ritual.wrong(p,"KeineLevel");
                    return;
                } else {
                    p.setLevel(p.getLevel()-utility.getcon(1).getInt(pfad+".Kosten"));
                }
            }
        } catch (IllegalArgumentException e){
            Ritual.wrong(p,"KeineLv Illegal Argument");
            throw new IllegalArgumentException("Flasche Lv Kosten wurden angegeben");
        }

        switch (utility.getcon(1).getString(pfad+".Art")){
            case "Summon":
                try {
                    p.getWorld().spawnEntity(RitualSpieler.containsKey(p) ? RitualSpieler.get(p).getLocation() : RitualLocation.get(p), EntityType.valueOf(utility.getcon(1).getString(pfad+".Auswirkungen")));
                    if(utility.getcon(1).get(pfad+".Auswirkungestärke")!=null) {
                        for(int i=1;i<utility.getcon(1).getInt(pfad+".Auswirkungestärke");i++){
                            p.getWorld().spawnEntity(RitualSpieler.containsKey(p) ? RitualSpieler.get(p).getLocation() : RitualLocation.get(p), EntityType.valueOf(utility.getcon(1).getString(pfad+".Auswirkungen")));
                        }
                    }
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Falsche Auswirkungen");
                }
                break;
            case "Effect":
                Player pp= RitualSpieler.containsKey(p) ? RitualSpieler.get(p) : p;
                pp.addPotionEffect(new PotionEffect(PotionEffectType.getByName(utility.getcon(1).getString(pfad+".Auswirkungen"))
                        ,utility.getcon(1).getInt(pfad+".Auswirkungsdauer")*20,utility.getcon(1).getInt(pfad+".Auswirkungestärke")-1,false,false));
                break;
            case "Create":
                if(Material.valueOf(utility.getcon(1).getString(pfad+".Auswirkungen")).isBlock()){
                    p.getWorld().getBlockAt(RitualLocation.get(p)).setType(Material.valueOf(utility.getcon(1).getString(pfad+".Auswirkungen")));
                    if(utility.getcon(1).get(pfad+".Auswirkungestärke")!=null){
                        ItemStack item=new ItemStack(Material.valueOf((utility.getcon(1).getString(pfad+".Auswirkungen"))));
                        item.setAmount(utility.getcon(1).getInt(pfad+".Auswirkungestärke")-1);
                        p.getWorld().dropItemNaturally(RitualLocation.get(p),item);
                    }
                } else {
                    ItemStack item=new ItemStack(Material.valueOf((utility.getcon(1).getString(pfad+".Auswirkungen"))));
                    item.setAmount(utility.getcon(1).get(pfad+".Auswirkungestärke")!=null ? utility.getcon(1).getInt(pfad+".Auswirkungestärke") : 1);
                    p.getWorld().dropItemNaturally(RitualLocation.get(p),item);
                }
                break;
            case "Message":
                if(RitualSpieler.containsKey(p) && RitualSpieler.get(p).isOnline()){
                    RitualSpieler.get(p).sendTitle(utility.getcon(1).getString(pfad+".Auswirkungen"),"");
                } else {
                    wrong(p,"Keineverbindung");
                }
                break;
            case "Sound":
                if(RitualSpieler.containsKey(p) && RitualSpieler.get(p).isOnline()){
                    RitualSpieler.get(p).playSound(RitualSpieler.get(p),Sound.valueOf(utility.getcon(1).getString(pfad+".Auswirkungen")),10,1);
                } else {
                    RitualLocation.get(p).getWorld().playSound(RitualLocation.get(p),Sound.valueOf(utility.getcon(1).getString(pfad+".Auswirkungen")),10,1);
                }
                break;
            case "Teleport":
                if(RitualSpieler.containsKey(p) && RitualSpieler.get(p).isOnline() && RitualLocation.get(p).distance(RitualSpieler.get(p).getLocation())<=utility.getcon(1).getInt(pfad+".Auswirkungen")){
                    RitualSpieler.get(p).teleport(RitualLocation.get(p));
                } else {
                    wrong(p,"Keineverbindung");
                }
                break;
            case "Chanche":
                String au=utility.getcon(1).getString(pfad+".Auswirkungen");
                switch (au){
                    case "Wetter":
                        switch (utility.getcon(1).getString(pfad+".Auswirkungestärke")){
                            case "rain":
                                p.getWorld().setStorm(true);
                                break;
                            case "clear":
                                p.getWorld().setClearWeatherDuration(120);
                                break;
                            default:
                                p.getWorld().setStorm(true);
                                p.getWorld().setThundering(true);
                                break;
                        }
                        break;
                    case "Biom":
                        p.getWorld().setBiome(RitualLocation.get(p), Biome.valueOf(utility.getcon(1).getString(pfad+".Auswirkungen")));
                        break;
                    case "Time":
                        int newtime=utility.getcon(1).getInt(pfad+".Auswirkungestärke");
                        int time=30;
                        if(p.getWorld().getTime()>newtime)time*=-1;
                        final int timesone=time;
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                if((timesone<0 && p.getWorld().getTime()<newtime) || (timesone>0 && p.getWorld().getTime()>newtime))cancel();
                                p.getWorld().setTime(p.getWorld().getTime()+timesone);
                            }
                        }.runTaskTimer(KMSRituale.getPlugin(),0,1);
                        break;
                }
                break;
            case "Attack":

                if(utility.getcon(1).getString(pfad+".Auswirkungen").equals("Long") || utility.getcon(1).getString(pfad+".Auswirkungen").equals("ATELARIE")){

                    Player popfer= RitualSpieler.containsKey(p) ? RitualSpieler.get(p) : p;
                    final int[] j = {0};
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (j[0] >= utility.getcon(1).getInt(pfad + ".Auswirkungsdauer")-1) cancel();
                            if(popfer.isDead() || !popfer.isOnline())cancel();
                            if(utility.getcon(1).getString(pfad+".Auswirkungen").equals("ATELARIE")){
                                for (int i = 0; i < 8; i++) {
                                    for (int k = 0; k < 8; k++) {
                                        Entity et2=p.getWorld().spawnEntity(popfer.getLocation().add(k-3,30,i-3), EntityType.valueOf(utility.getcon(1).getString(pfad+".Auswirkungestärke")));
                                        utility.shotshit(et2,utility.getcon(1).getString(pfad+".Auswirkungen"),popfer,popfer.getLocation().add(k-3,0,i-3));
                                    }
                                }
                            } else {
                                p.getWorld().spawnEntity(popfer.getLocation(), EntityType.valueOf(utility.getcon(1).getString(pfad+".Auswirkungestärke")));
                            }
                            j[0]++;
                        }
                    }.runTaskTimer(KMSRituale.getPlugin(),0,20);

                } else {

                    ArrayList<LivingEntity> et =(ArrayList<LivingEntity>) loc.getNearbyLivingEntities(20);
                    if(et.size()>1)et.remove(p);
                    int ran=new Random().nextInt(et.size());

                    LivingEntity opfer= RitualSpieler.containsKey(p) ? RitualSpieler.get(p) : p;

                    for (LivingEntity li : et)if(et.indexOf(li)==ran && opfer==p)opfer=li;

                    final LivingEntity oppfer=opfer;
                    final int[] j = {0};
                    new BukkitRunnable(){
                        @Override
                        public void run() {

                            if(j[0]>=utility.getcon(1).getInt(pfad+".Auswirkungsdauer")-1)cancel();
                            Entity et=p.getWorld().spawnEntity( loc.clone().add(0,1,0), EntityType.valueOf(utility.getcon(1).getString(pfad+".Auswirkungestärke")));
                            utility.shotshit(et,utility.getcon(1).getString(pfad+".Auswirkungen"),oppfer,oppfer.getLocation());
                            if(utility.getcon(1).getString(pfad+".Auswirkungen").equals("AOE")){
                                for (int i = 0; i < 5; i++) {
                                    for (int k = 0; k < 5; k++) {
                                        Entity et2=p.getWorld().spawnEntity( loc.clone().add(k-2.5,i+1,-k+2.5), EntityType.valueOf(utility.getcon(1).getString(pfad+".Auswirkungestärke")));
                                        utility.shotshit(et2,utility.getcon(1).getString(pfad+".Auswirkungen"),oppfer,oppfer.getLocation().add(k-2.5,i+1,-k+2.5));
                                    }
                                }
                            }
                            if(oppfer.getLocation().distance(et.getLocation())>=100)cancel();
                            j[0]++;
                        }
                    }.runTaskTimer(KMSRituale.getPlugin(),0,40);
                    break;
                }
                break;
            default:
                throw new IllegalArgumentException("Falsches Art wurde angegeben");
        }
        removerit(p,"activ");
    }

    public static void startritual(Player p, Item it, String R,Location loc){

        try{
            //check if the book
            if(thebook(it.getItemStack(),R,loc)){
                it.remove();
                return;
            }

            //check with ritual the player trys to summon
            if(!Ritualnumber.containsKey(p)){

                //check witch ritual
                String ritual=Ritualpfad.containsKey(p) ? Ritualpfad.get(p) : R;
                int igruppe=1;
                int i=1;
                while (true){
                    //check if there is a ritual left
                    if(utility.getcon(1).get(ritual+".Gruppe"+"."+igruppe+".Material"+".1")==null && utility.getcon(1).get(ritual+"."+i+".Material"+".1")==null)return;
                    int[] a= new int[i];

                    a[i-1]=1;
                    Ritualnumber.put(p,a);
                    Ritualpfad.put(p,utility.getcon(1).get(ritual+".Gruppe"+"."+igruppe+".Material"+".1")!=null ? ritual+".Gruppe" : ritual);

                    if(nextitem(p,it.getItemStack())) {
                        if(!vorausetzungen(p)){
                            wrong(p,"falsche vorausetzung");
                            return;
                        }
                        if(utility.getcon(1).getString(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Partikeleffect")!=null)utility.playeffecht(utility.getcon(1).getString(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Partikeleffect"),loc);
                        break;
                    }

                    removerit(p,"witch");
                    if(utility.getcon(1).get(ritual+".Gruppe"+"."+igruppe+".Material"+".1")!=null){
                        igruppe++;
                    } else {
                        i++;
                    }
                }
                RitualLocation.put(p,loc);
            }

            //check if right lockation
            if(!RitualLocation.containsKey(p) ||!RitualLocation.get(p).equals(loc))return;

            //check if the player throws the correckt item
            if(!nextitem(p,it.getItemStack())){
                it.remove();
                wrong(p,"Falsches Item");
                return;
            }

            //add a time limit
            ritualtilelimit(p,utility.getcon(1).get(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Zeit")!=null ? utility.getcon(1).getInt(Ritualpfad.get(p)+"."+Ritualnumber.get(p).length+".Zeit") : 30);

            //destroy the item
            it.remove();

            if(!vorausetzungen(p)){
                it.remove();
                wrong(p,"falsche vorausetzung");
                return;
            }

            //activate effect if
            if(!additemplus(p,R))activateritual(p,loc);


        } catch (IllegalArgumentException e){
            String pfad =Ritualpfad.containsKey(p) ? Ritualpfad.get(p) : " Categorie "+R;
            String number =Ritualnumber.containsKey(p) ? String.valueOf(Ritualnumber.get(p).length)+"."+String.valueOf((Ritualnumber.get(p)[Ritualnumber.get(p).length-1]+1)) : "Item "+it;

            removerit(p,"IllegalARguments");
            Ritualzeit.remove(p);

            System.out.println(ChatColor.RED+"There was a error in: "+pfad+" "+number);
            System.out.println(org.bukkit.ChatColor.RED+"Nachicht: "+e.getMessage());
        }

    }

    public static void ritualtilelimit(Player p,Integer i){

        if(Ritualzeit.containsKey(p) || i.equals(-10)){
            Ritualzeit.put(p,i);
            return;
        }


        Ritualzeit.put(p,i);
        if(!Ritualpfad.containsKey(p) || !Ritualnumber.containsKey(p))return;
        String pfad=Ritualpfad.get(p)+"."+Ritualnumber.get(p).length;
        org.bukkit.boss.BossBar bar = Bukkit.createBossBar(utility.getcon(1).getString(pfad+".Beschreibung"), BarColor.GREEN, i>10 ? BarStyle.SEGMENTED_20 : BarStyle.SEGMENTED_10);
        bar.addPlayer(p);

        new BukkitRunnable(){
            @Override
            public void run() {

                if(!Ritualzeit.containsKey(p) || Ritualzeit.get(p)<=-10){
                    Ritualzeit.remove(p);
                    bar.setVisible(false);
                    bar.removePlayer(p);
                    cancel();
                    return;
                }

                double present=Double.valueOf(String.format("%.1f",(Ritualzeit.get(p)/(i*1.0))));

                bar.setProgress(present);
                bar.setColor(present>=0.8 ? BarColor.GREEN : present>=0.4 ? BarColor.YELLOW : BarColor.RED);

                Ritualzeit.put(p,(Ritualzeit.get(p)-1));
                if(Ritualzeit.get(p)<=0){
                    wrong(p,"Zeit Überschritten");
                    Ritualzeit.remove(p);
                }
            }
        }.runTaskTimer(KMSRituale.getPlugin(),0,20);



    }
}
