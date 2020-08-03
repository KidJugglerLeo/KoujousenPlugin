package com.koujousen.plugin.koujousenplugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigHandler {

    private static File file;
    private static FileConfiguration config;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Koujousenplugin").getDataFolder(), "config.yml");
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch(Exception e){
                System.out.println("failed to create file");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return config;
    }

    public static void save(){
        try{
            config.save(file);
        }catch(Exception e){
            System.out.println("Error while saving file");
        }
    }

    public static void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }
}
