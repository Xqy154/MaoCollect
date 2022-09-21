package qq916397235.cimao.maoCollect.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import qq916397235.cimao.maoCollect.MainClass;
import qq916397235.cimao.maoCollect.Tool.isIn;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.ArrayList;
import java.util.List;

public class OnPlayerMove implements Listener {
    @EventHandler
    public void onPlayerMoveEvent (PlayerMoveEvent e) {
        Player player = e.getPlayer();
        //玩家正在采集 取消移动
        if (ConfigLoad.getInstance().getDoing().get(player.getName())!=null) {
            if (e.getTo().getX()!=e.getFrom().getX()||e.getTo().getZ()!=e.getFrom().getZ()) {
                e.setCancelled(true);
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                //判断玩家是否正处在一个采集区内
                if (ConfigLoad.getInstance().getData().get(player.getName())!=null) {
                    //在
                    String key = ConfigLoad.getInstance().getData().getString(player.getName());
                    if (!isIn.isin(player.getLocation(),key)) {
                        //退出了这个区域
                        ConfigLoad.getInstance().getData().set(player.getName(),null);
                    }
                }else {
                    //不在 遍历所有区域
                    List<String> keys = new ArrayList<>();
                    keys.addAll(ConfigLoad.getInstance().getCollects().getKeys(false));
                    for (int i=0;i<keys.size();i++) {
                        if (isIn.isin(player.getLocation(),keys.get(i))) {
                            //进入了
                            player.sendTitle(ConfigLoad.getInstance().getCollects().getString(keys.get(i)+".MessageA").replaceAll("&","§"),ConfigLoad.getInstance().getCollects().getString(keys.get(i)+".MessageB").replaceAll("&","§"),10,40,10);
                            ConfigLoad.getInstance().getData().set(player.getName(),keys.get(i));
                            return;
                        }
                    }
                }
            }
        }.runTaskAsynchronously(MainClass.getInstance());
    }

}
