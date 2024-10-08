package me.marc3308.kmsrituale;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import static me.marc3308.kmsrituale.ritual.Ritual.RitualLocation;

public class utility {

    public static final HashMap<Integer, FileConfiguration> Cons=new HashMap<>();
    public static final HashMap<String, Object> savecons=new HashMap<>();
    public static final HashMap<String, Object> savecon2=new HashMap<>();

    public static FileConfiguration getcon(Integer num){

        if(!Cons.containsKey(num))throw new IllegalArgumentException("Keine gültige FileConfiguration gefunden");

        return Cons.get(num);

    }

    public static ArrayList<String> alltheritual(String ritus){

        ArrayList<String> pfäde=new ArrayList<>();
        ArrayList<String> arten=new ArrayList<>();
        if(ritus.equals("")){
            arten.add("Feuer");
            arten.add("SeeFeuer");
        } else {
            arten.add(ritus);
        }

        for(String s : arten){
            for(int i=1;i<300;i++){
                if(utility.getcon(1).get(s+".Gruppe"+"."+i)!=null){
                    //check if there is a ritual in a grupe left
                    for(int j=1;j<300;j++){
                        if(utility.getcon(1).get(s+".Gruppe"+"."+i+"."+j)!=null){
                            pfäde.add(s+".Gruppe"+"."+i+"."+j);
                        }
                    }
                }
            }

            for(int i=1;i<300;i++){
                if(utility.getcon(1).get(s+"."+i)!=null)pfäde.add(s+"."+i);
            }
        }

        return pfäde;
    }

    public static ArrayList<String> alltheeffects(){
        ArrayList<String> names=new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            if(utility.getcon(2).get(String.valueOf(i))!=null)names.add(String.valueOf(i));
        }
        return names;
    }

    public static void removesavecons(){
        savecons.clear();
        savecon2.clear();
    }

    public static void playeffecht(String name, Location loc){

        ArrayList<String> names=new ArrayList<>();

        int k=1;
        while (utility.getcon(2).get(String.valueOf(k))!=null){
            names.add(utility.getcon(2).getString(k+".Name"));
            k++;
        }


        if(name.equals("TimeItselfIsJustAConstruckt")){

            new BukkitRunnable(){
                @Override
                public void run() {
                    if(!RitualLocation.containsValue(loc) || (!loc.getBlock().getType().equals(Material.FIRE) && !loc.getBlock().getType().equals(Material.SOUL_FIRE)))cancel();
                    loc.getWorld().setTime(loc.getWorld().getTime()+90);
                }
            }.runTaskTimer(KMSRituale.getPlugin(),0,1);

            return;
        }

        for(String s : names){
            if(s.equals(name)){
                for (int j = 1; j < 100; j++) {
                    if(utility.getcon(2).get((names.indexOf(s)+1)+"."+j)!=null){
                        if(utility.getcon(2).get((names.indexOf(s)+1)+"."+j+".Name")!=null){

                            playeffecht(utility.getcon(2).getString((names.indexOf(s)+1)+"."+j+".Name"),loc);

                        } else {

                            int eckanzahl=utility.getcon(2).get((names.indexOf(s)+1)+"."+j+".Ecken")!=null ? utility.getcon(2).getInt((names.indexOf(s)+1)+"."+j+".Ecken") : 0;
                            int radius=utility.getcon(2).get((names.indexOf(s)+1)+"."+j+".Größe")!=null ? utility.getcon(2).getInt((names.indexOf(s)+1)+"."+j+".Größe") : 1;
                            int anzahl=utility.getcon(2).get((names.indexOf(s)+1)+"."+j+".Partikelanzahl")!=null ? utility.getcon(2).getInt((names.indexOf(s)+1)+"."+j+".Partikelanzahl") : 1;
                            Particle pt=Particle.valueOf(utility.getcon(2).getString((names.indexOf(s)+1)+"."+j+".Partikelart")!=null ? utility.getcon(2).getString((names.indexOf(s)+1)+"."+j+".Partikelart") : "HEART");

                            if(eckanzahl==0){

                                final double[] y={0};

                                new BukkitRunnable(){
                                    @Override
                                    public void run() {

                                        if(!RitualLocation.containsValue(loc) || (!loc.getBlock().getType().equals(Material.FIRE) && !loc.getBlock().getType().equals(Material.SOUL_FIRE)))cancel();
                                        double x=0.5 * Math.cos(y[0]);
                                        double z=0.5 * Math.sin(y[0]);


                                        loc.getWorld().spawnParticle(pt, loc.clone().add((x*radius)+0.5,0,(z*radius)+0.5),anzahl);
                                        loc.getWorld().spawnParticle(pt, loc.clone().add((-x*radius)+0.5,0,(-z*radius)+0.5),anzahl);

                                        y[0]+=0.1;
                                    }
                                }.runTaskTimer(KMSRituale.getPlugin(),0,1);

                            } else if (eckanzahl<3) {

                                final double[] y={0};

                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        if(!RitualLocation.containsValue(loc) || (!loc.getBlock().getType().equals(Material.FIRE) && !loc.getBlock().getType().equals(Material.SOUL_FIRE)))cancel();
                                        double x=0.5 * Math.cos(y[0]);
                                        double z=0.5 * Math.sin(y[0]);

                                        loc.getWorld().spawnParticle(pt,loc.clone().add(x*radius+0.5,0,x*radius+1.5),anzahl);

                                        y[0]+=0.1;
                                    }
                                }.runTaskTimer(KMSRituale.getPlugin(),0,1);

                            } else {



                                final double[] y={0};
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {

                                        if(!RitualLocation.containsValue(loc) || (!loc.getBlock().getType().equals(Material.FIRE) && !loc.getBlock().getType().equals(Material.SOUL_FIRE)))cancel();
                                        double angleStep = 2 * Math.PI / eckanzahl;

                                        for (int i = 0; i < eckanzahl; i++) {
                                            double angle = i * angleStep;
                                            double x = loc.clone().getX() + radius * Math.cos(angle) * Math.cos(y[0]);
                                            double z = loc.clone().getZ() + radius * Math.sin(angle) * Math.sin(y[0]);
                                            Location particleLocation = new Location(loc.clone().getWorld(), x+0.5, loc.clone().getY(), z+0.5);
                                            loc.clone().getWorld().spawnParticle(pt, particleLocation, anzahl, 0, 0, 0, 0);
                                        }

                                        y[0]+=0.1;
                                    }
                                }.runTaskTimer(KMSRituale.getPlugin(),0,1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void shotshit(Entity et, String name, Entity opfer, Location loc){

        et.setGravity(false);

        final Location opp=loc;
        Location oplocation = opfer.getLocation();
        Location etloc = et.getLocation();
        @NotNull Vector direction = oplocation.toVector().subtract(etloc.toVector()).normalize();
        double distance = etloc.distance(oplocation);
        double pullStrength = 1.0; // Adjust as needed
        @NotNull Vector pullVector = direction.multiply(pullStrength);
        et.setVelocity(pullVector);

        new BukkitRunnable(){
            @Override
            public void run() {

                if(et.isDead() || !et.isValid() || !opfer.isValid() || opfer.isDead()){
                    et.remove();
                    cancel();
                }

                Location oplocation = opp;
                if(name.equals("Homing"))oplocation = opfer.getLocation();

                Location etloc = et.getLocation();
                @NotNull Vector direction = oplocation.clone().add(0,1,0).toVector().subtract(etloc.toVector()).normalize();
                double distance = etloc.distance(oplocation);
                double pullStrength = name.equals("Homing") ? 0.5 : 1.0; // Adjust as needed
                @NotNull Vector pullVector = direction.multiply(pullStrength);
                et.setVelocity(pullVector);

                if(oplocation.distance(etloc)>=100)et.remove();
                if(oplocation.distance(etloc)<=2){
                    et.setGravity(true);
                    cancel();
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            et.remove();
                            cancel();
                        }
                    }.runTaskTimer(KMSRituale.getPlugin(),20*3,1);

                }

            }
        }.runTaskTimer(KMSRituale.getPlugin(),0,1);
    }
}

