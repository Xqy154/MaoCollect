package qq916397235.cimao.maoCollect.Tool;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import qq916397235.cimao.maoCollect.MainClass;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class lootItem {
    public static void li(String str, Player player, ItemStack item , Location loc) {
        List<String> keys = new ArrayList<>();
        keys.addAll(ConfigLoad.getInstance().getLoots().getConfigurationSection(str).getKeys(false));
        int num = 0;
        for (int i=0;i<keys.size();i++) {
            ItemStack gitem = ConfigLoad.getInstance().getLoots().getItemStack(str+"."+keys.get(i));
            //获取总数
            num+=gitem.getAmount();
        }
        Random rd = new Random();
        int sj = rd.nextInt(num);
        int now = 0;
        int gnum = 1;
        if (ConfigLoad.getInstance().getConfig().getBoolean("MoreCollect")) {
            List<String> lores = item.getItemMeta().getLore();
            for (int i = 0; i < lores.size(); i++) {
                if (lores.get(i).indexOf(ConfigLoad.getInstance().getConfig().getString("Power")) != -1) {
                    int power = tools.getInt(tools.rlore(lores.get(i)));
                    if (power >= ConfigLoad.getInstance().getCollects().getInt(str + ".Power")) {
                        gnum = power / ConfigLoad.getInstance().getCollects().getInt(str + ".Power");
                    }
                }
            }
        }
        Sound b = loc.getWorld().getBlockAt(loc).getBlockData().getSoundGroup().getBreakSound();
        Sound s = loc.getWorld().getBlockAt(loc).getBlockData().getSoundGroup().getHitSound();
        label:for (int a=0 ; a<gnum ; a++) {
            for (int n = 0; n < keys.size(); n++) {
                ItemStack gitem = ConfigLoad.getInstance().getLoots().getItemStack(str + "." + keys.get(n));
                now += gitem.getAmount();
                if (sj < now) {
                    ItemStack gi = gitem.clone();
                    //发放物品 成功则扣除手中物品
                    gi.setAmount(1);
                    Bukkit.getScheduler().runTask(MainClass.getInstance(),new Runnable() {
                        @Override
                        public void run() {
                            loc.getWorld().dropItem(loc,gi);
                        }
                    });
                    player.playSound(loc,s,5,1);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue label;
                }
            }
        }
        Bukkit.getScheduler().runTask(MainClass.getInstance(),new Runnable() {
            @Override
            public void run() {
                removeBlock.rb(loc);
            }
        });
        player.playSound(loc,b,5,1);
        return ;
    }
}
