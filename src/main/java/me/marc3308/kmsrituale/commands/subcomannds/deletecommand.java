package me.marc3308.kmsrituale.commands.subcomannds;

import me.marc3308.kmsrituale.commands.subcommand;
import me.marc3308.kmsrituale.utility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.marc3308.kmsrituale.utility.*;

public class deletecommand extends subcommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "delets a ritual";
    }

    @Override
    public String getSyntax() {
        return "/KMSRituale delete <Ritual> <RitualName>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<2){
            p.sendMessage(ChatColor.RED+"Try: "+getSyntax());
            return;
        }

        if(args[1].equals("RitualPartikel")){
            for (String s : utility.alltheeffects()){
                if(utility.getcon(2).getString(s+".Name").equals(args[2])){
                    savecons.put(s,null);
                    p.sendMessage(ChatColor.GREEN+args[2]+" wurde erfolgreich gelöscht!");
                    return;
                }
            }
        }

        for(String s : alltheritual(args[1])){
            if(utility.getcon(1).getString(s+".Name").equals(args[2]) || (s.contains("Gruppe") && utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name").equals(args[2]))){
                if(s.contains("Gruppe") && utility.getcon(1).getString(s.substring(0, s.length() - 2)+".Name").equals(args[2]))s=s.substring(0, s.length() - 2);
                savecons.put(s,null);
                p.sendMessage(ChatColor.GREEN+args[2]+" wurde erfolgreich gelöscht!");
                return;
            }
        }
        p.sendMessage(ChatColor.RED+args[2]+" scheint nicht zu existieren");

    }
}
