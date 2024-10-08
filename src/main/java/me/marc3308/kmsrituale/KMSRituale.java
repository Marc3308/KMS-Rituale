package me.marc3308.kmsrituale;

import me.marc3308.kmsrituale.commands.ritualerstellcommand;
import me.marc3308.kmsrituale.events.leaveevent;
import me.marc3308.kmsrituale.events.ritualevents;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static me.marc3308.kmsrituale.utility.savecon2;
import static me.marc3308.kmsrituale.utility.savecons;

public final class KMSRituale extends JavaPlugin {

    public static KMSRituale plugin;
    @Override
    public void onEnable() {

        plugin = this;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                //save the con
                File file = new File("plugins/KMS Plugins/Rituale","rituale.yml");
                FileConfiguration con= YamlConfiguration.loadConfiguration(file);
                File file2 = new File("plugins/KMS Plugins/Rituale","partikeleffecte.yml");
                FileConfiguration con2= YamlConfiguration.loadConfiguration(file2);

                for(String s : savecons.keySet())con.set(s,savecons.get(s));
                for(String s : savecon2.keySet())con2.set(s,savecon2.get(s));

                utility.Cons.put(1,con);
                utility.Cons.put(2,con2);

                utility.removesavecons();


                try {
                    con.save(file);
                } catch (IOException i) {
                    i.printStackTrace();
                }

                try {
                    con2.save(file2);
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }, 0, 20);

        //todo atack vorausschauende angriffe

        Bukkit.getPluginManager().registerEvents(new leaveevent(),this);
        Bukkit.getPluginManager().registerEvents(new ritualevents(),this);

        getCommand("KMSRituale").setExecutor(new ritualerstellcommand());


        File file = new File("plugins/KMS Plugins/Rituale","rituale.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);
        File file2 = new File("plugins/KMS Plugins/Rituale","partikeleffecte.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(file2);

        List<String> infos =new ArrayList<>();
        infos.add("Diese yml ist für das Ritual-Plugin, es ermöglicht das einfache erstellen von Ritualen");
        infos.add("--------------------Zusatzinfo--------------------");
        infos.add("Buch: Das Ritual Item das man ins feuer schmeisen muss um ein ritual Rezept zu bekommen");
        infos.add("Ritualcommand: true/false ob man den Ritualcommand benutzen können soll");
        infos.add("Permision Rituale: Die Permision die der spieler haben muss um rituale benutzen zu können");
        infos.add("Permision Ritualcommand: Die Permision die der Spieler haben muss um den Ritualcommand benutzen zu können");
        infos.add("--------------------CreirungSachen--------------------");
        infos.add("Beschreibung: Beschreibung des Rituals");
        infos.add("Partikeleffect: Der Partikeleffect der gespielt wird wenn das ritual begonnen wird (optional)");
        infos.add("Zeit: Zeit in der Man das nächste Item hinzufügen muss (optional)");
        infos.add("Sound: Der Beim einwerfen abgespielte sound (optional)");
        infos.add("FailSound: Der Beim Berkaken eingespielte sound (optional)");
        infos.add("SucsessSound: Der Beim Gelingen eingespielte sound (optional)");
        infos.add("Material: Das nächste Material");
        infos.add("Vorausetzungen: Eine vorausetzung das das ritual klappt (optional");
        infos.add("--------------------Vorausetzungen (Optional)-----------------------");
        infos.add("Vorausetzung:          Wetter | Biom | AnzahlSpieler | Tageszeit");
        infos.add("Vorausetzungzustand:   Wetter | Biom | AnzahlSpieler | Tageszeit");
        infos.add("--------------------Material Custem (Optional)-----------------------");
        infos.add("AnzeigeName: Name des Items");
        infos.add("Partikeleffect: Der Partikeleffect der gespielt wird wenn das material eingegeben wird");
        infos.add("NamensFarbe: Farbe vom Namen des Items");
        infos.add("Beschreibung: Die Beschreibung des Items");
        infos.add("Custemmoddeldata: Die Custem Moddel Data des Items");
        infos.add("----------------------------Ritual Arten------------------------------");
        infos.add("              Art:    Create       | Summon | Teleport |   Effect | Message | Sound |   Chanche    |     Attack");
        infos.add("     Auswirkungen: Block / item    | Entity |   Range  |   Type   | Message | Sound | Wetter/Time  | Close/Long/Homing/AOE/ATELARIE");
        infos.add("Auswirkungestärke:      Anzahl     | Anzahl |     -    |   Stärke |    -    |   -   | Wetter/Time  |       Art");
        infos.add(" Auswirkungsdauer:      -          |   -    |     -    |   Länge                                   |      Anzahl");
        infos.add("----------------------------Ritual Bestrafungen-----------------------");
        infos.add("Nebenwirkung:       Damage | Teleport | Effect |   Xp   ");
        infos.add("Nebenwirkungstärke: damage |   Range  | Stärke | Anzahl ");
        infos.add("Nebenwirkungsdauer:   -    |    -     | Dauer  |   -    ");

        if(con.get("Feuer")==null){

            //erklärbuch
            con.set("Buch"+".Material","BOOK");
            con.set("Ritualcommand",true);
            con.set("Permisions"+".Rituale","Hexe");
            List<String> inf2 =new ArrayList<>();
            inf2.add("Permisions können auch lehrgelassen werden falls es keine geben soll");
            con.setComments("Feuer",inf2);
            con.set("Permisions"+".Ritualcommand","Admin");


            con.setComments("Feuer",infos);

            con.set("Feuer"+".1"+".Name","DiamandBlock");
            con.set("Feuer"+".1"+".Beschreibung","Beschwöre einen Diamand Block");
            con.set("Feuer"+".1"+".Art","Create");
            con.set("Feuer"+".1"+".Auswirkungen","DIAMOND_BLOCK");
            con.set("Feuer"+".1"+".FailsSound","ENTITY_CHICKEN_HURT");
            con.set("Feuer"+".1"+".SucsessSound","ENTITY_CHICKEN_HURT");
            con.set("Feuer"+".1"+".Sound","ENTITY_CHICKEN_HURT");
            con.set("Feuer"+".1"+".Nebenwirkung","Teleport");
            con.set("Feuer"+".1"+".Nebenwirkungstärke",20);
            con.set("Feuer"+".1"+".Material"+".1"+".Material","DIAMOND");
            con.set("Feuer"+".1"+".Material"+".2"+".Material","DIAMOND");
            con.set("Feuer"+".1"+".Material"+".3"+".Material","DIAMOND");
            con.set("Feuer"+".1"+".Material"+".4"+".Material","DIAMOND");
            con.set("Feuer"+".1"+".Material"+".5"+".Material","DIAMOND");
            con.set("Feuer"+".1"+".Material"+".6"+".Material","DIAMOND");
            con.set("Feuer"+".1"+".Material"+".7"+".Material","DIAMOND");
            con.set("Feuer"+".1"+".Material"+".8"+".Material","DIAMOND");

            con.set("Feuer"+".2"+".Name","GeschwindikeitsRitual");
            con.set("Feuer"+".2"+".Beschreibung","Erhalte den Speed Effect");
            con.set("Feuer"+".2"+".Art","Effect");
            con.set("Feuer"+".2"+".Auswirkungen","SPEED");
            con.set("Feuer"+".2"+".Auswirkungestärke",2);
            con.set("Feuer"+".2"+".Auswirkungsdauer",20);
            con.set("Feuer"+".2"+".FailsSound","ENTITY_CHICKEN_HURT");
            con.set("Feuer"+".2"+".SucsessSound","ENTITY_CHICKEN_HURT");
            con.set("Feuer"+".2"+".Sound","ENTITY_CHICKEN_HURT");
            con.set("Feuer"+".2"+".Nebenwirkung","Effect");
            con.set("Feuer"+".2"+".Nebenwirkungseffect","DARKNESS");
            con.set("Feuer"+".2"+".Nebenwirkungstärke",2);
            con.set("Feuer"+".2"+".Nebenwirkungsdauer",20);
            con.set("Feuer"+".2"+".Material"+".1"+".Material","FEATHER");
            con.set("Feuer"+".2"+".Material"+".2"+".Material","FEATHER");
            con.set("Feuer"+".2"+".Material"+".3"+".Material","FEATHER");
            con.set("Feuer"+".2"+".Material"+".4"+".Material","FEATHER");


        }

        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

        infos.clear();
        infos.add("Mögliche zusatzinhalte: ecken, partikelart, größe");
        if(con2.get("Effecte")==null){
            con2.set("Effecte","");
            con2.set("1"+".Name","GroßeGreiße");
            con2.set("1"+".1"+".Ecken",0);
            con2.set("1"+".1"+".Größe",3);
            con2.set("1"+".1"+".Partikelart","DRIP_LAVA");
            con2.set("1"+".1"+".Partikelanzahl",10);

            con2.setComments("1",infos);
            con2.set("1"+".2"+".Ecken",0);
            con2.set("1"+".2"+".Größe",4);
            con2.set("1"+".2"+".Partikelart","DRIP_LAVA");
            con2.set("1"+".2"+".Partikelanzahl",10);
            con2.set("1"+".3"+".Ecken",0);
            con2.set("1"+".3"+".Größe",6);
            con2.set("1"+".3"+".Partikelart","DRIP_LAVA");
            con2.set("1"+".3"+".Partikelanzahl",10);
            con2.set("1"+".4"+".Name","Viereck");

            con2.set("2"+".Name","Poligon4");
            con2.set("2"+".1"+".Ecken",4);
            con2.set("2"+".1"+".Größe",5);
            con2.set("2"+".1"+".Partikelart","DRIP_LAVA");
            con2.set("2"+".1"+".Partikelanzahl",10);
        }

        try {
            con2.save(file2);
        } catch (IOException i){
            i.printStackTrace();
        }

    }

    public static KMSRituale getPlugin() {
        return plugin;
    }
}
