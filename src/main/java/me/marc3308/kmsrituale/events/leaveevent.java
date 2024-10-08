package me.marc3308.kmsrituale.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.marc3308.kmsrituale.ritual.Ritual.removerit;

public class leaveevent implements Listener {

    @EventHandler
    public void onleav(PlayerQuitEvent e){
        removerit(e.getPlayer(),"playerleft");
    }
}
