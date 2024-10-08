package me.marc3308.kmsrituale.commands.subcomannds;

import me.marc3308.kmsrituale.commands.ritualerstellcommand;
import me.marc3308.kmsrituale.commands.subcommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class helpcommand extends subcommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "gives all commands";
    }

    @Override
    public String getSyntax() {
        return "/KMSRituale help";
    }

    @Override
    public void perform(Player p, String[] args) {

        p.sendMessage(ChatColor.DARK_GREEN+"----------------------------------------------------");
        for (subcommand sub : ritualerstellcommand.getSubcommands()){
            p.sendMessage(ChatColor.DARK_GREEN+sub.getSyntax()+" "+sub.getDescription());
        }
        p.sendMessage(ChatColor.DARK_GREEN+"----------------------------------------------------");

    }
}
