package qq916397235.cimao.maoCollect.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import qq916397235.cimao.maoCollect.MainClass;
import java.io.File;
import java.io.IOException;

public class ConfigLoad {
    File file_collects = new File(MainClass.getInstance().getDataFolder(),"Collects.yml"),
            file_loots = new File(MainClass.getInstance().getDataFolder(),"Loots.yml"),
            file_cooldown = new File(MainClass.getInstance().getDataFolder(),"Cooldown.yml"),
            file_config = new File(MainClass.getInstance().getDataFolder(),"config.yml");

    ////
    FileConfiguration data,config,collects,loots,settings,doing,cooldown;
    public static ConfigLoad instance = new ConfigLoad();
    public static ConfigLoad getInstance() {return instance;}
    public void reloadAll() {
        if(!file_config.exists()) {MainClass.getInstance().saveDefaultConfig();}
        if(!file_collects.exists()) {MainClass.getInstance().saveResource("Collects.yml", false);}
        if(!file_loots.exists()) {MainClass.getInstance().saveResource("Loots.yml", false);}
        if(!file_cooldown.exists()) {MainClass.getInstance().saveResource("Cooldown.yml", false);}
        config = YamlConfiguration.loadConfiguration(file_config);
        collects = YamlConfiguration.loadConfiguration(file_collects);
        loots = YamlConfiguration.loadConfiguration(file_loots);
        cooldown = YamlConfiguration.loadConfiguration(file_cooldown);
        data = YamlConfiguration.loadConfiguration(new File(""));
        settings = YamlConfiguration.loadConfiguration(new File(""));
        doing = YamlConfiguration.loadConfiguration(new File(""));
    }
    public FileConfiguration getConfig(){
        return this.config;
    }
    public void saveConfig(){
        try {
            config.save(file_config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getData(){
        return this.data;
    }
    public FileConfiguration getCollects(){
        return this.collects;
    }
    public void saveCollects(){
        try {
            collects.save(file_collects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getLoots(){
        return this.loots;
    }
    public void saveLoots(){
        try {
            loots.save(file_loots);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getCooldown(){
        return this.cooldown;
    }
    public void saveCooldown(){
        try {
            cooldown.save(file_cooldown);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getSettings(){
        return this.settings;
    }
    public FileConfiguration getDoing(){
        return this.doing;
    }
}
