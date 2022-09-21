package qq916397235.cimao.maoCollect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import qq916397235.cimao.maoCollect.commands.BaseCommand;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;
import qq916397235.cimao.maoCollect.listeners.OnCloseInv;
import qq916397235.cimao.maoCollect.listeners.OnPlayerInteract;
import qq916397235.cimao.maoCollect.listeners.OnPlayerMove;
import qq916397235.cimao.maoCollect.runTaskTimer.BlockCooldown;

public class MainClass extends JavaPlugin {
    static MainClass mainclass;
    static ConfigLoad configload;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(" §b╔══  §e╦  §a╔╦╗  §4╔═╗  §2╔═╗ ");
        Bukkit.getConsoleSender().sendMessage(" §b║    §e║  §a║║║  §4║ ║  §2║ ║ ");
        Bukkit.getConsoleSender().sendMessage(" §b╚══  §e╩  §a╩ ╩  §4╚═╚  §2╚═╝ ");
        Bukkit.getConsoleSender().sendMessage("§cMaoCollect插件加载成功！");
        mainclass=this;
        configload = ConfigLoad.getInstance();
        configload.reloadAll();
        this.getCommand("mc").setExecutor(new BaseCommand());
        getServer().getPluginManager().registerEvents(new OnPlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new OnCloseInv(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerMove(), this);
        BlockCooldown.bc();
    }


    public static MainClass getInstance() {
        return mainclass;
    }

    public static ConfigLoad getConfigManager() {
        return configload;
    }
}
