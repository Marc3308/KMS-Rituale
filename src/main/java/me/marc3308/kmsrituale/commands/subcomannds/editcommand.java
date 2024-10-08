package me.marc3308.kmsrituale.commands.subcomannds;

import me.marc3308.kmsrituale.commands.subcommand;
import me.marc3308.kmsrituale.utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static me.marc3308.kmsrituale.utility.*;

public class editcommand extends subcommand {
    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "edit a ritual";
    }

    @Override
    public String getSyntax() {
        return "/KMSRituale edit <Ritual> <RitualName> <Wert> <NeuerWert>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<5){
            p.sendMessage(ChatColor.RED+"Try: "+getSyntax());
            return;
        }

        if(args[1].equals("RitualPartikel")){
            for (String s : utility.alltheeffects()){
                if(utility.getcon(2).getString(s+".Name").equals(args[2])){
                    int k=0;
                    switch (args[3]){
                        case "remove":

                            ArrayList<Object> speicher=new ArrayList<>();

                            for (String ss  : alltheeffects()){
                                if(utility.getcon(2).getString(ss+".Name").equals(args[2])){
                                    k=1;
                                    while (utility.getcon(2).get(s+"."+k)!=null){
                                        speicher.add(utility.getcon(2).getConfigurationSection(s+"."+k));
                                        k++;
                                    }

                                    speicher.remove(Integer.valueOf(args[4])-1);
                                    speicher.add(null);

                                    k=1;
                                    for (Object ob : speicher){
                                        savecon2.put(s+"."+k,ob);
                                        k++;
                                    }
                                    break;
                                }
                            }

                            p.sendMessage(ChatColor.GREEN+"Nr."+args[4]+" von "+args[2]+" wurde gelöscht");
                            break;
                        case "add":
                            k=1;
                            while (utility.getcon(2).get(s+"."+k)!=null)k++;
                            switch (args[4]){
                                case "AnzahlFormen":
                                    savecon2.put(s+"."+k+".Ecken",Integer.valueOf(args[5]));
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                case "Größe":
                                    savecon2.put(s+"."+k+".Größe",Integer.valueOf(args[5]));
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                case "PartikelEffekt":
                                    savecon2.put(s+"."+k+".Partikelart",args[5]);
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                case "AnzahlPartikel":
                                    savecon2.put(s+"."+k+".Partikelanzahl",Integer.valueOf(args[5]));
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                default:
                                    savecon2.put(s+"."+k+".Name",args[5]);
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                            }
                            break;
                        default:
                            k=Integer.valueOf(args[3]);
                            switch (args[4]){
                                case "AnzahlFormen":
                                    savecon2.put(s+"."+k+".Ecken",Integer.valueOf(args[5]));
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                case "Größe":
                                    savecon2.put(s+"."+k+".Größe",Integer.valueOf(args[5]));
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                case "PartikelEffekt":
                                    savecon2.put(s+"."+k+".Partikelart",args[5]);
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                case "AnzahlPartikel":
                                    savecon2.put(s+"."+k+".Partikelanzahl",Integer.valueOf(args[5]));
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                                default:
                                    savecon2.put(s+"."+k+".Name",args[5]);
                                    p.sendMessage(ChatColor.GREEN+"Nr."+k+" wurde erfolgreich erstellt");
                                    break;
                            }
                            break;
                    }
                    return;
                }
            }
        }

        for (String s : utility.alltheritual(args[1])){
            if(utility.getcon(1).getString(s+".Name").equals(args[2]) || (s.contains("Gruppe") && utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name").equals(args[2]))){
                if(s.contains("Gruppe") && utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name").equals(args[2]))s=s.substring(0, s.length() - 2);

                switch (args[3]){
                    case "Name":
                        savecons.put(s+".Name",args[4]);
                        p.sendMessage(ChatColor.GREEN+args[3]+" wurde erfolgreich zu "+args[4]+" geändert");
                        break;
                    case "Beschreibung":
                        savecons.put(s+".Beschreibung",args[4]);
                        p.sendMessage(ChatColor.GREEN+args[3]+" wurde erfolgreich zu "+args[4]+" geändert");
                        break;
                    case "XpKosten":
                        savecons.put(s+".Kosten", Integer.valueOf(args[4]));
                        p.sendMessage(ChatColor.GREEN+args[3]+" wurde erfolgreich zu "+args[4]+" geändert");
                        break;
                    case "Zeitlimit":
                        savecons.put(s+".Zeit", Integer.valueOf(args[4]));
                        p.sendMessage(ChatColor.GREEN+args[3]+" wurde erfolgreich zu "+args[4]+" geändert");
                        break;
                    case "Vorausetzungen":
                        savecons.put(s+".Vorausetzung",args[4]);
                        savecons.put(s+".Vorausetzungzustand",args[4].equals("AnzahlSpieler") ? Integer.valueOf(args[5]) : args[5]);
                        p.sendMessage(ChatColor.GREEN+args[3]+" wurde erfolgreich zu "+args[4]+" geändert");
                        break;
                    case "Sounds":
                        if(args[5].equals("<remove>"))args[5]=null;
                        switch (args[4]){
                            case "RichtigesMaterialSound":
                                savecons.put(s+".Sound",args[5]);
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");

                                break;
                            case "FalschesMaterialSound":
                                savecons.put(s+".FailSound",args[5]);
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "AbschlussSound":
                                savecons.put(s+".SucsessSound",args[5]);
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                        }
                        break;
                    case "Materialien":
                        switch (args[4]){
                            case "add":
                                int i=1;
                                while (utility.getcon(1).get(s+".Material"+"."+i)!=null)i++;
                                savecons.put(s+".Material"+"."+i+".Material",args[5]);
                                p.sendMessage(ChatColor.GREEN+"Das Material "+args[5]+" wurde erfolgreich auf nummer "+i+" gesetzt");
                                break;
                            case "remove":
                                //test if works
                                ArrayList<Object> speicher=new ArrayList<>();
                                int j=1;
                                while (utility.getcon(1).get(s+".Material"+"."+j)!=null){
                                    speicher.add(utility.getcon(1).getConfigurationSection(s+".Material"+"."+j));
                                    j++;
                                }

                                speicher.add(null);
                                speicher.remove(Integer.valueOf(args[5])-1);
                                for (Object ob : speicher){
                                    savecons.put(s+".Material"+"."+(speicher.indexOf(ob)+1),ob);
                                }
                                p.sendMessage(ChatColor.GREEN+"Das Material auf der nummer "+args[5]+" wurde erfolgreich gelöscht");
                                break;
                            default:
                                switch (args[5]){
                                    case "Name":
                                        savecons.put(s+".Material"+"."+Integer.valueOf(args[4])+".AnzeigeName",args[6]);
                                        p.sendMessage(ChatColor.GREEN+args[5]+" wurde erfolgreich zu "+args[6]+" geändert");
                                        break;
                                    case "NamenFarbe":
                                        savecons.put(s+".Material"+"."+Integer.valueOf(args[4])+".NamensFarbe",args[6]);
                                        p.sendMessage(ChatColor.GREEN+args[5]+" wurde erfolgreich zu "+args[6]+" geändert");
                                        break;
                                    case "Custemmoddeldata":
                                        savecons.put(s+".Material"+"."+Integer.valueOf(args[4])+".Custemmoddeldata",args[6]);
                                        p.sendMessage(ChatColor.GREEN+args[5]+" wurde erfolgreich zu "+args[6]+" geändert");
                                        break;
                                    case "Beschreibung":
                                        savecons.put(s+".Material"+"."+Integer.valueOf(args[4])+".Beschreibung",args[6]);
                                        p.sendMessage(ChatColor.GREEN+args[5]+" wurde erfolgreich zu "+args[6]+" geändert");
                                        break;
                                    case "ReienvolgenNummer":
                                        ArrayList<Object> reinspeicher=new ArrayList<>();

                                        int rs=1;
                                        while (utility.getcon(1).get(s+".Material"+"."+rs)!=null){
                                            reinspeicher.add(utility.getcon(1).getConfigurationSection(s+".Material"+"."+rs));
                                            rs++;
                                        }
                                        Object sp=reinspeicher.get(Integer.valueOf(args[4])-1);
                                        reinspeicher.set(Integer.valueOf(args[4])-1,reinspeicher.get(Integer.valueOf(args[6])-1));
                                        reinspeicher.set(Integer.valueOf(args[6])-1,sp);
                                        rs=1;
                                        for (Object ob : reinspeicher){
                                            savecons.put(s+".Material"+"."+rs,ob);
                                            rs++;
                                        }
                                        p.sendMessage(ChatColor.GREEN+args[5]+" wurde erfolgreich zu "+args[6]+" geändert");
                                        break;
                                    case "Material":
                                        savecons.put(s+".Material"+"."+args[4]+".Material",args[6]);
                                        p.sendMessage(ChatColor.GREEN+args[5]+" wurde erfolgreich zu "+args[6]+" geändert");
                                        break;
                                    case "Partikeleffect":
                                        savecons.put(s+".Partikeleffect"+"."+args[4],args[6]);
                                        p.sendMessage(ChatColor.GREEN+args[5]+" wurde erfolgreich zu "+args[6]+" geändert");
                                        break;
                                }
                                break;
                        }
                        break;
                    case "Partikeleffect":
                        if(args[4].equals("<remove>"))args[4]=null;
                        savecons.put(s+".Partikeleffect",args[4]);
                        p.sendMessage(ChatColor.GREEN+args[3]+" wurde erfolgreich zu "+args[4]+" geändert");
                        break;
                    default:
                        switch (args[4]){
                            case "Beschwörung":
                                savecons.put(s+".Art","Summon");
                                savecons.put(s+".Auswirkungen",args[5]);
                                if(args.length>6)savecons.put(s+".Auswirkungestärke",Integer.valueOf(args[6]));
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "Teleportation":
                                savecons.put(args[3].equals("Auswirkung") ? s+".Art" : s+".Nebenwirkung","Teleport");
                                savecons.put(args[3].equals("Auswirkung") ? s+".Auswirkungen" : s+".Nebenwirkungstärke",Integer.valueOf(args[5]));
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "Kreierung":
                                savecons.put(s+".Art","Create");
                                savecons.put(s+".Auswirkungen",args[5]);
                                if(args.length>6)savecons.put(s+".Auswirkungestärke",Integer.valueOf(args[6]));
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "Effect":
                                savecons.put(args[3].equals("Auswirkung") ? s+".Art" : s+".Nebenwirkung","Effect");
                                savecons.put(args[3].equals("Auswirkung") ? s+".Auswirkungen" : s+".Nebenwirkungseffect",args[5]);
                                savecons.put(args[3].equals("Auswirkung") ? s+".Auswirkungestärke" : s+".Nebenwirkungstärke",Integer.valueOf(args[6]));
                                savecons.put(args[3].equals("Auswirkung") ? s+".Auswirkungsdauer" : s+".Nebenwirkungsdauer",Integer.valueOf(args[7]));
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "Erfahrungsverlust":
                                savecons.put(s+".Nebenwirkung","Xp");
                                savecons.put(s+".Nebenwirkungstärke",Integer.valueOf(args[5]));
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "Schaden":
                                savecons.put(s+".Nebenwirkung","Damage");
                                savecons.put(s+".Nebenwirkungstärke",Integer.valueOf(args[5]));
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "Message":
                                savecons.put(s+".Art","Message");
                                String aus="";
                                for (int i = 5; i <args.length ; i++) {
                                    aus+=" "+args[i];
                                }
                                savecons.put(s+".Auswirkungen",aus);
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+aus+" geändert");
                                break;
                            case "Sound":
                                savecons.put(s+".Art","Sound");
                                savecons.put(s+".Auswirkungen",args[5]);
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                            case "Chanche":
                                switch (args[5]){
                                    case "Tageszeit":
                                        savecons.put(s+".Art","Chanche");
                                        savecons.put(s+".Auswirkungen","Time");
                                        savecons.put(s+".Auswirkungestärke",Integer.valueOf(args[6]));
                                        p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                        break;
                                    default:
                                        savecons.put(s+".Art","Chanche");
                                        savecons.put(s+".Auswirkungen",args[5]);
                                        savecons.put(s+".Auswirkungestärke",args[6]);
                                        p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                        break;
                                }
                                break;
                            case "Attack":
                                savecons.put(s+".Art","Attack");
                                savecons.put(s+".Auswirkungen",args[5]);
                                savecons.put(s+".Auswirkungestärke",args[6]);
                                savecons.put(s+".Auswirkungsdauer",args.length>=8 ? Integer.valueOf(args[7]) : 1);
                                p.sendMessage(ChatColor.GREEN+args[4]+" wurde erfolgreich zu "+args[5]+" geändert");
                                break;
                        }
                        break;
                }
                break;
            }
        }
    }
}
