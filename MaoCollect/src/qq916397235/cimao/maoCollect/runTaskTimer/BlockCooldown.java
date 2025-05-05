package qq916397235.cimao.maoCollect.runTaskTimer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import qq916397235.cimao.maoCollect.MainClass;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;
public class BlockCooldown {
    public static void bc() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ConfigLoad.getInstance().getCooldown().getKeys(false).forEach(key -> {
                    int time = ConfigLoad.getInstance().getCooldown().getInt(key);
                    if (time <= 1) {
                        restoreBlock(key);
                    } else {
                        ConfigLoad.getInstance().getCooldown().set(key, time - 1);
                    }
                });
                ConfigLoad.getInstance().saveCooldown();
            }
        }.runTaskTimer(MainClass.getInstance(), 0, 20);
    }
    private static void restoreBlock(String key) {
        String[] parts = key.split("\\|");
        if (parts.length != 5) {
            MainClass.getInstance().getLogger().warning("非法冷却坐标数据: " + key);
            return;
        }
        World world = Bukkit.getWorld(parts[0]);
        if (world == null) {
            MainClass.getInstance().getLogger().warning("世界未加载: " + parts[0]);
            return;
        }
        try {
            Location loc = new Location(
                world,
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
            );
            Material material = Material.getMaterial(parts[4]);
            if (material == null) {
                MainClass.getInstance().getLogger().warning("非法材质: " + parts[4]);
                return;
            }
            // 确保在主线程操作方块
            Bukkit.getScheduler().runTask(MainClass.getInstance(), () -> {
                Block block = loc.getBlock();
                block.setType(material, false); // 关闭物理更新
                ConfigLoad.getInstance().getCooldown().set(key, null);
            });
        } catch (NumberFormatException e) {
            MainClass.getInstance().getLogger().warning("坐标数值错误: " + key);
        }
    }
}