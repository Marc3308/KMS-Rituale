package me.marc3308.kmsrituale.commands.subcomannds;

import me.marc3308.kmsrituale.commands.subcommand;
import me.marc3308.kmsrituale.utility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class listcommand extends subcommand {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "lists all rituals";
    }

    @Override
    public String getSyntax() {
        return "/KMSRituale list <Ritual>";
    }

    @Override
    public void perform(Player p, String[] args) {

        if(args.length<2){
            p.sendMessage(ChatColor.RED+"Try: "+getSyntax());
            return;
        }


        if(args[1].equals("RitualPartikel")){
            p.sendMessage("------------"+args[1]+"------------");
            for (String s : utility.alltheeffects())p.sendMessage(utility.getcon(2).getString(s+".Name"));
            p.sendMessage("-----------------------------");
            return;
        }

        ArrayList<String> Namen= utility.alltheritual(args[1]);
        p.sendMessage("------------"+args[1]+"------------");
        for (String s : Namen){
            p.sendMessage(utility.getcon(1).getString(s+".Name"));
        }
        p.sendMessage("-----------------------------");

    }
}
