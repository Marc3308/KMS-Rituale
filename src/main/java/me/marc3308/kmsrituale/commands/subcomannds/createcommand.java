package me.marc3308.kmsrituale.commands.subcomannds;

import me.marc3308.kmsrituale.commands.subcommand;
import me.marc3308.kmsrituale.utility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.marc3308.kmsrituale.utility.savecon2;
import static me.marc3308.kmsrituale.utility.savecons;

public class createcommand extends subcommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "create a new Ritual";
    }

    @Override
    public String getSyntax() {
        return "/KMSRituale create <Ritual> <RitualName> <Material>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<3){
            p.sendMessage(ChatColor.RED+"Try: "+getSyntax());
            return;
        }

        if(args[1].equals("RitualPartikel")){
            int k=1;
            while (utility.getcon(2).get(String.valueOf(k))!=null)k++;
            savecon2.put(k+".Name",args[2]);
            p.sendMessage(ChatColor.GREEN+"Du hast Den Ritual Partikel Effect "+args[2]+" erfolgreich erstellt");
            return;
        }

        if(args.length<4){
            p.sendMessage(ChatColor.RED+"Try: "+getSyntax());
            return;
        }


        ArrayList<String> rituale= utility.alltheritual(args[1]);
        for(String s : rituale){
            String ss=s.substring(0, s.length() - 2);
            if(s.contains("Gruppe") && utility.getcon(1).getString(ss+".Material"+".1"+".Material").equals(args[3])){

                int i=1;
                //test if works
                while (utility.getcon(1).get(ss+"."+i)!=null)i++;
                savecons.put(ss+"."+i+".Name",args[2]);

                p.sendMessage(ChatColor.GREEN+"Du hast Das Ritual "+args[2]+" erfolgreich erstellt");

                return;

            } else if (!s.contains("Gruppe") && utility.getcon(1).getString(s+".Material"+".1"+".Material").equals(args[3])) {

                int i=1;
                while (utility.getcon(1).get(args[1]+".Gruppe"+"."+i)!=null)i++;
                String pfad=args[1]+".Gruppe"+"."+i;
                //create the groupe
                savecons.put(pfad+".Name",args[3]+"Groupe");
                savecons.put(pfad+".Material"+".1"+".Material",args[3]);

                //set the rituale
                savecons.put(pfad+".1"+".Name",utility.getcon(1).getString(s+".Name"));
                savecons.put(pfad+".1"+".Art",utility.getcon(1).getString(s+".Art"));
                savecons.put(pfad+".1"+".Auswirkungen",utility.getcon(1).getString(s+".Auswirkungen"));
                if(utility.getcon(1).getString(s+".Beschreibung")!=null) savecons.put(pfad+".1"+".Beschreibung",utility.getcon(1).getString(s+".Beschreibung"));
                if(utility.getcon(1).get(s+".Auswirkungestärke")!=null) savecons.put(pfad+".1"+".Auswirkungestärke",utility.getcon(1).getInt(s+".Auswirkungestärke"));
                if(utility.getcon(1).get(s+".Auswirkungsdauer")!=null) savecons.put(pfad+".1"+".Auswirkungsdauer",utility.getcon(1).getString(s+".Auswirkungsdauer"));
                if(utility.getcon(1).getString(s+".FailsSound")!=null) savecons.put(pfad+".1"+".FailsSound",utility.getcon(1).getString(s+".FailsSound"));
                if(utility.getcon(1).getString(s+".SucsessSound")!=null) savecons.put(pfad+".1"+".Beschreibung",utility.getcon(1).getString(s+".SucsessSound"));

                if(utility.getcon(1).getString(s+".Sound")!=null) savecons.put(pfad+".1"+".Sound",utility.getcon(1).getString(s+".Sound"));
                if(utility.getcon(1).getString(s+".Nebenwirkung")!=null) savecons.put(pfad+".1"+".Nebenwirkung",utility.getcon(1).getString(s+".Nebenwirkung"));
                if(utility.getcon(1).get(s+".Nebenwirkungstärke")!=null) savecons.put(pfad+".1"+".Nebenwirkungstärke",utility.getcon(1).getInt(s+".Nebenwirkungstärke"));
                if(utility.getcon(1).get(s+".Nebenwirkungsdauer")!=null) savecons.put(pfad+".1"+".Nebenwirkungsdauer",utility.getcon(1).getInt(s+".Nebenwirkungsdauer"));
                if(utility.getcon(1).get(s+".Zeit")!=null) savecons.put(pfad+".1"+".Zeit",utility.getcon(1).getInt(s+".Zeit"));
                if(utility.getcon(1).get(s+".Kosten")!=null) savecons.put(pfad+".1"+".Kosten",utility.getcon(1).getInt(s+".Kosten"));

                i=2;
                while (utility.getcon(1).get(s+".Material"+"."+i+".Material")!=null){
                    savecons.put(pfad+".1"+".Material"+"."+(i-1)+".Material",utility.getcon(1).getString(s+".Material"+"."+i+".Material"));
                    if(utility.getcon(1).get(s+".Material"+"."+i+".AnzeigeName")!=null)savecons.put(pfad+".1"+".Material"+"."+(i-1)+".AnzeigeName",utility.getcon(1).getString(s+".Material"+"."+i+".AnzeigeName"));
                    if(utility.getcon(1).get(s+".Material"+"."+i+".NamensFarbe")!=null)savecons.put(pfad+".1"+".Material"+"."+(i-1)+".NamensFarbe",utility.getcon(1).getString(s+".Material"+"."+i+".NamensFarbe"));
                    if(utility.getcon(1).get(s+".Material"+"."+i+".Beschreibung")!=null)savecons.put(pfad+".1"+".Material"+"."+(i-1)+".Beschreibung",utility.getcon(1).getString(s+".Material"+"."+i+".Beschreibung"));
                    if(utility.getcon(1).get(s+".Material"+"."+i+".Custemmoddeldata")!=null)savecons.put(pfad+".1"+".Material"+"."+(i-1)+".Custemmoddeldata",utility.getcon(1).getInt(s+".Material"+"."+i+".Custemmoddeldata"));
                    i++;
                }

                //remove this test
                savecons.put(s,null);
                //add the ritual
                savecons.put(pfad+".2"+".Name",args[2]);
                //savecons.put(pfad+".2"+".Material"+".1",args[3]);
                p.sendMessage(ChatColor.GREEN+"Du hast Das Ritual "+args[2]+" erfolgreich erstellt, dadurch wurde die Gruppe "+args[3]+"Groupe"+" erstellt und das ritual "+utility.getcon(1).getString(s+".Name")+" Hineinbewegt");

                return;
            }
        }

        int i=1;
        for (String s : rituale)if(!s.contains("Gruppe"))i++;
        savecons.put(args[1]+"."+i+".Name",args[2]);
        savecons.put(args[1]+"."+i+".Material"+".1"+".Material",args[3]);

        p.sendMessage(ChatColor.GREEN+"Du hast Das Ritual "+args[2]+" erfolgreich erstellt");

    }
}
