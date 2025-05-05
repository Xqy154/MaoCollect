package qq916397235.cimao.maoCollect.configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import qq916397235.cimao.maoCollect.MainClass;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
public class ConfigLoad {
    // 使用线程安全的 ConcurrentHashMap 替代内存配置
    private final ConcurrentHashMap<String, FileConfiguration> memoryConfigs = new ConcurrentHashMap<>();
    
    // 所有配置文件路径统一管理
    private enum ConfigType {
        CONFIG("config.yml"),
        COLLECTS("Collects.yml"),
        LOOTS("Loots.yml"),
        COOLDOWN("Cooldown.yml"),
        DATA("Data.yml"),
        SETTINGS("Settings.yml"),
        DOING("Doing.yml");
        final String fileName;
        ConfigType(String fileName) {
            this.fileName = fileName;
        }
    }
    private static ConfigLoad instance = new ConfigLoad();
    public static ConfigLoad getInstance() { return instance; }
    // 获取配置文件对象（线程安全）
    private FileConfiguration getConfig(ConfigType type) {
        File file = new File(MainClass.getInstance().getDataFolder(), type.fileName);
        if (!file.exists()) {
            try {
                if (type == ConfigType.CONFIG) {
                    MainClass.getInstance().saveDefaultConfig();
                } else {
                    file.createNewFile();
                }
            } catch (IOException e) {
                MainClass.getInstance().getLogger().severe("创建配置文件失败: " + type.fileName);
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
    // 初始化所有配置
    public void reloadAll() {
        memoryConfigs.clear();
        
        // 磁盘配置文件
        for (ConfigType type : new ConfigType[]{ConfigType.COLLECTS, ConfigType.LOOTS, ConfigType.COOLDOWN}) {
            memoryConfigs.put(type.name(), getConfig(type));
        }
        // 内存型配置文件
        memoryConfigs.put(ConfigType.DATA.name(), new YamlConfiguration());
        memoryConfigs.put(ConfigType.SETTINGS.name(), new YamlConfiguration()); 
        memoryConfigs.put(ConfigType.DOING.name(), new YamlConfiguration());
    }
    // 统一保存方法（线程安全）
    private void saveConfig(ConfigType type) {
        if (type == ConfigType.DATA || type == ConfigType.SETTINGS || type == ConfigType.DOING) {
            return; // 内存型配置无需保存
        }
        File file = new File(MainClass.getInstance().getDataFolder(), type.fileName);
        try {
            memoryConfigs.get(type.name()).save(file);
        } catch (IOException e) {
            MainClass.getInstance().getLogger().severe("保存配置文件失败: " + type.fileName);
        }
    }
    //====================== 公共访问方法 (保持原有API不变) ======================
    // 主配置
    public FileConfiguration getConfig() {
        return memoryConfigs.get(ConfigType.CONFIG.name());
    }
    public void saveConfig() {
        saveConfig(ConfigType.CONFIG);
    }
    // 数据配置
    public FileConfiguration getData() {
        return memoryConfigs.get(ConfigType.DATA.name());
    }
    // 采集区配置
    public FileConfiguration getCollects() {
        return memoryConfigs.get(ConfigType.COLLECTS.name());
    }
    public void saveCollects() {
        saveConfig(ConfigType.COLLECTS);
    }
    // 掉落物配置
    public FileConfiguration getLoots() {
        return memoryConfigs.get(ConfigType.LOOTS.name());
    }
    public void saveLoots() {
        saveConfig(ConfigType.LOOTS);
    }
    // 冷却配置
    public FileConfiguration getCooldown() { 
        return memoryConfigs.get(ConfigType.COOLDOWN.name());
    }
    public void saveCooldown() {
        saveConfig(ConfigType.COOLDOWN);
    }
    // 设置配置（内存型）
    public FileConfiguration getSettings() {
        return memoryConfigs.get(ConfigType.SETTINGS.name());
    }
    // 操作状态配置（内存型） 
    public FileConfiguration getDoing() {
        return memoryConfigs.get(ConfigType.DOING.name());
    }
}