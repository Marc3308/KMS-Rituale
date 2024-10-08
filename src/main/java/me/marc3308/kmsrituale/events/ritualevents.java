package me.marc3308.kmsrituale.events;

import me.marc3308.kmsrituale.ritual.Ritual;
import me.marc3308.kmsrituale.utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class ritualevents implements Listener {

    @EventHandler
    public void onFire(EntityDamageEvent e){
        try {


            if(!(e.getEntity() instanceof Item))return;
            Item item=(Item) e.getEntity();
            if(!e.getEntity().getLocation().getBlock().getType().equals(Material.FIRE)
                && !e.getEntity().getLocation().getBlock().getType().equals(Material.SOUL_FIRE))return;

            Player p= Bukkit.getPlayer(item.getThrower());
            //if nesesary
            if(utility.getcon(1).get("Permisions"+".Rituale")!=null && !p.hasPermission(utility.getcon(1).getString("Permisions"+".Rituale")))return;
            //so the plugin dont explode
            if(item.getItemStack().getAmount()!=1)return;

            Ritual.startritual(p,item, e.getEntity().getLocation().getBlock().getType().equals(Material.FIRE) ? "Feuer" : "SeeFeuer",e.getEntity().getLocation().getBlock().getLocation());
        } catch (IllegalArgumentException f){
            e.getEntity().getWorld().getBlockAt(e.getEntity().getLocation()).setType(Material.AIR);
            return;
        } catch (NullPointerException ef){
            if(((Item) e.getEntity()).getThrower()!=null) Ritual.removerit(Bukkit.getPlayer(((Item) e.getEntity()).getThrower()),"");
            return;
        }
    }

    @EventHandler
    public void onkill(EntityDeathEvent e){

        if(!(e.getEntity().getKiller() instanceof Player))return;
        Player p= (Player) e.getEntity().getKiller();
        if(!Ritual.Ritualpfad.containsKey(p))return;
        String pfad=Ritual.Ritualpfad.get(p)+"."+Ritual.Ritualnumber.get(p).length+".Material"+"."+(Ritual.Ritualnumber.get(p)[Ritual.Ritualnumber.get(p).length-1]);
        if(utility.getcon(1).getString(pfad+".Material")==null || !utility.getcon(1).getString(pfad+".Material").equals("<opfern>"))return;
        if(e.getEntity().getType().toString().equals(utility.getcon(1).getString(pfad+".AnzeigeName")))if(!Ritual.additemplus(p,""))Ritual.activateritual(p,Ritual.RitualLocation.get(p));

    }
}
