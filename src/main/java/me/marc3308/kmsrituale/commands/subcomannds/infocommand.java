package me.marc3308.kmsrituale.commands.subcomannds;

import me.marc3308.kmsrituale.commands.subcommand;
import me.marc3308.kmsrituale.utility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class infocommand extends subcommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "info about a special ritual";
    }

    @Override
    public String getSyntax() {
        return "/KMSRituale info <Ritual> <RitualName>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<3) {
            p.sendMessage(ChatColor.RED+"Try: "+getSyntax());
            return;
        }

        if(args[1].equals("RitualPartikel")){
            for(String s : utility.alltheeffects()){
                if(utility.getcon(2).getString(s+".Name").equals(args[2])){
                    p.sendMessage(ChatColor.GREEN+"Name: "+utility.getcon(2).getString(s+".Name"));
                    for (int i = 1; i < 100; i++) {
                        if(utility.getcon(2).get(s+"."+String.valueOf(i))!=null){
                            if(utility.getcon(2).get(s+"."+i+".Name")!=null){
                                p.sendMessage(ChatColor.GREEN+""+i+": "+utility.getcon(2).getString(s+"."+i+".Name"));
                            } else {
                                p.sendMessage(ChatColor.GREEN+""+i+". Formen: "+utility.getcon(2).getInt(s+"."+i+".Ecken"));
                                p.sendMessage(ChatColor.GREEN+""+i+". Größe: "+utility.getcon(2).getInt(s+"."+i+".Größe"));
                                p.sendMessage(ChatColor.GREEN+""+i+". Partikel: "+utility.getcon(2).getString(s+"."+i+".Partikelart"));
                                p.sendMessage(ChatColor.GREEN+""+i+". Partikelanzahl: "+utility.getcon(2).getString(s+"."+i+".Partikelanzahl"));
                            }
                        }
                    }
                    return;
                }
            }
        }

        for (String s : utility.alltheritual(args[1])){
            if(utility.getcon(1).getString(s+".Name").equals(args[2]) || (s.contains("Gruppe") && utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name").equals(args[2]))){

                if(s.contains("Gruppe") && utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name").equals(args[2]))s=s.substring(0, s.length() - 2);

                p.sendMessage(ChatColor.GREEN+"Name: "+utility.getcon(1).getString(s+".Name"));
                if(utility.getcon(1).getString(s+".Beschreibung")!=null)p.sendMessage(ChatColor.GREEN+"Beschreibung: "+utility.getcon(1).getString(s+".Beschreibung"));
                p.sendMessage(ChatColor.GREEN+"Auswirkung: "+utility.getcon(1).getString(s+".Art")+" "+utility.getcon(1).getString(s+".Auswirkungen"));
                if(utility.getcon(1).getString(s+".Auswirkungestärke")!=null)p.sendMessage(ChatColor.GREEN+"Auswirkungestärke: "+utility.getcon(1).getString(s+".Auswirkungestärke"));
                if(utility.getcon(1).getString(s+".Partikeleffect")!=null)p.sendMessage(ChatColor.GREEN+"Partikeleffect: "+utility.getcon(1).getString(s+".Partikeleffect"));

                if(utility.getcon(1).getString(s+".Auswirkungsdauer")!=null)p.sendMessage(ChatColor.GREEN+"Auswirkungsdauer: "+utility.getcon(1).getString(s+".Auswirkungsdauer"));
                if(utility.getcon(1).getString(s+".FailsSound")!=null)p.sendMessage(ChatColor.GREEN+"FailsSound: "+utility.getcon(1).getString(s+".FailsSound"));
                if(utility.getcon(1).getString(s+".SucsessSound")!=null)p.sendMessage(ChatColor.GREEN+"SucsessSound: "+utility.getcon(1).getString(s+".SucsessSound"));
                if(utility.getcon(1).getString(s+".Sound")!=null)p.sendMessage(ChatColor.GREEN+"RichtigesItemSound: "+utility.getcon(1).getString(s+".Sound"));
                if(utility.getcon(1).getString(s+".Nebenwirkung")!=null)p.sendMessage(ChatColor.GREEN+"Nebenwirkung: "+utility.getcon(1).getString(s+".Nebenwirkung"));
                if(utility.getcon(1).getString(s+".Nebenwirkungstärke")!=null)p.sendMessage(ChatColor.GREEN+"Nebenwirkungstärke: "+utility.getcon(1).getString(s+".Nebenwirkungstärke"));
                if(utility.getcon(1).getString(s+".Nebenwirkungsdauer")!=null)p.sendMessage(ChatColor.GREEN+"Nebenwirkungsdauer: "+utility.getcon(1).getString(s+".Nebenwirkungsdauer"));
                if(utility.getcon(1).getString(s+".Zeit")!=null)p.sendMessage(ChatColor.GREEN+"Zeit: "+utility.getcon(1).getString(s+".Zeit"));
                if(utility.getcon(1).getString(s+".Kosten")!=null)p.sendMessage(ChatColor.GREEN+"Kosten: "+utility.getcon(1).getString(s+".Kosten"));
                if(utility.getcon(1).getString(s+".Vorausetzung")!=null)p.sendMessage(ChatColor.GREEN+"Vorausetzung: "+utility.getcon(1).getString(s+".Vorausetzung"));
                if(utility.getcon(1).getString(s+".Vorausetzungzustand")!=null)p.sendMessage(ChatColor.GREEN+"Vorausetzungzustand: "+utility.getcon(1).getString(s+".Vorausetzungzustand"));


                p.sendMessage(ChatColor.GREEN+"Materialien: ");
                int i=1;
                int g=1;
                while (utility.getcon(1).get(s+".Material"+"."+i)!=null){
                    String ss=s.substring(0, s.length() - 2);
                    if(s.contains("Gruppe") && utility.getcon(1).get(ss+".Material"+"."+g)!=null){
                        p.sendMessage(ChatColor.GREEN+"Nr"+g+"."+utility.getcon(1).getString(ss+".Material"+"."+g+".Material"));
                        if(utility.getcon(1).get(ss+".Material"+"."+g+".AnzeigeName")!=null)p.sendMessage(utility.getcon(1).get(ss+".Material"+"."+g+".NamensFarbe")!=null
                                ? ChatColor.GREEN+"Nr"+g+"."+": "+utility.getcon(1).get(ss+".Material"+"."+g+".NamensFarbe")+utility.getcon(1).get(ss+".Material"+"."+g+".AnzeigeName")
                                : ChatColor.GREEN+"Nr"+g+"."+": "+utility.getcon(1).get(ss+".Material"+"."+g+".AnzeigeName"));
                        if(utility.getcon(1).get(ss+".Material"+"."+g+".Beschreibung")!=null)p.sendMessage(ChatColor.GREEN+"Nr"+(i+(g-1))+"."+utility.getcon(1).getString(ss+".Material"+"."+g+".Beschreibung"));
                        if(utility.getcon(1).get(ss+".Material"+"."+g+".Custemmoddeldata")!=null)p.sendMessage(ChatColor.GREEN+"Nr"+(i+(g-1))+"."+String.valueOf(utility.getcon(1).getInt(ss+".Material"+"."+g+".Custemmoddeldatataken")));
                        if(utility.getcon(1).get(ss+".Material"+"."+g+".Partikeleffect")!=null)p.sendMessage(ChatColor.GREEN+"Nr"+(i+(g-1))+"."+String.valueOf(utility.getcon(1).getInt(ss+".Material"+"."+g+".Partikeleffect")));
                        g++;
                    } else {
                        p.sendMessage(ChatColor.GREEN+"Nr"+(i+(g-1))+"."+utility.getcon(1).getString(s+".Material"+"."+i+".Material"));
                        if(utility.getcon(1).get(s+".Material"+"."+i+".AnzeigeName")!=null)p.sendMessage(utility.getcon(1).get(s+".Material"+"."+i+".NamensFarbe")!=null
                                ? ChatColor.GREEN+"Nr"+(i+(g-1))+"."+": "+utility.getcon(1).get(s+".Material"+"."+i+".NamensFarbe")+utility.getcon(1).get(s+".Material"+"."+i+".AnzeigeName")
                                : ChatColor.GREEN+"Nr"+(i+(g-1))+"."+": "+utility.getcon(1).get(s+".Material"+"."+i+".AnzeigeName"));
                        if(utility.getcon(1).get(s+".Material"+"."+i+".Beschreibung")!=null)p.sendMessage(ChatColor.GREEN+"Nr"+(i+(g-1))+"."+utility.getcon(1).getString(s+".Material"+"."+i+".Beschreibung"));
                        if(utility.getcon(1).get(s+".Material"+"."+i+".Custemmoddeldata")!=null)p.sendMessage(ChatColor.GREEN+"Nr"+(i+(g-1))+"."+String.valueOf(utility.getcon(1).getInt(s+".Material"+"."+i+".Custemmoddeldatataken")));
                        if(utility.getcon(1).get(s+".Material"+"."+i+".Partikeleffect")!=null)p.sendMessage(ChatColor.GREEN+"Nr"+(i+(g-1))+"."+String.valueOf(utility.getcon(1).getInt(s+".Material"+"."+i+".Partikeleffect")));
                        i++;
                    }
                }
                return;

            }
        }
    }
}
