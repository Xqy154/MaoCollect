package qq916397235.cimao.maoCollect.runTaskTimer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import qq916397235.cimao.maoCollect.MainClass;
import qq916397235.cimao.maoCollect.Tool.removeBlock;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.ArrayList;
import java.util.List;

public class BlockCooldown {
    public static void bc () {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            //要(延迟/循环)执行的内容
            public void run() {
                List<String> keys = new ArrayList<>();
                keys.addAll(ConfigLoad.getInstance().getCooldown().getKeys(false));
                for (int i=0;i<keys.size();i++) {
                    if (ConfigLoad.getInstance().getCooldown().getInt(keys.get(i))!=1) {
                        ConfigLoad.getInstance().getCooldown().set(keys.get(i),ConfigLoad.getInstance().getCooldown().getInt(keys.get(i))-1);
                    }else {
                        //需要替换回来
                        t(keys.get(i));
                        ConfigLoad.getInstance().getCooldown().set(keys.get(i),null);
                    }
                }
                if (keys.size()>0) {
                    ConfigLoad.getInstance().saveCooldown();
                }
            }
        };
        runnable.runTaskTimerAsynchronously(MainClass.getInstance(),0,20);
    }
    public static void t (String str) {
        String world = str.substring(0,str.indexOf("|"));
        str = str.substring(world.length()+1,str.length());
        String x = str.substring(0,str.indexOf("|"));
        str = str.substring(x.length()+1,str.length());
        String y = str.substring(0,str.indexOf("|"));
        str = str.substring(y.length()+1,str.length());
        String z = str.substring(0,str.indexOf("|"));
        String m = str.replace(z+"|","");
        Location loc = new Location(Bukkit.getWorld(world),Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(z));
        Bukkit.getScheduler().runTask(MainClass.getInstance(),new Runnable() {
            @Override
            public void run() {
                loc.getWorld().setBlockData(loc,Bukkit.getServer().createBlockData(Material.getMaterial(m)));
            }
        });
    }
//    public static void all () {
//        List<String> keys = new ArrayList<>();
//        keys.addAll(ConfigLoad.getInstance().getCooldown().getKeys(false));
//        for (int i=0;i<keys.size();i++) {
//            tS(keys.get(i));
//        }
//    }
//    public static void tS (String str) {
//        String world = str.substring(0,str.indexOf("|"));
//        str = str.substring(world.length()+1,str.length());
//        String x = str.substring(0,str.indexOf("|"));
//        str = str.substring(x.length()+1,str.length());
//        String y = str.substring(0,str.indexOf("|"));
//        str = str.substring(y.length()+1,str.length());
//        String z = str.substring(0,str.indexOf("|"));
//        String m = str.replace(z+"|","");
//        Location loc = new Location(Bukkit.getWorld(world),Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(z));
//        loc.getWorld().setBlockData(loc,Bukkit.getServer().createBlockData(Material.getMaterial(m)));
//    }
}
