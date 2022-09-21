package qq916397235.cimao.maoCollect.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import qq916397235.cimao.maoCollect.MainClass;
import qq916397235.cimao.maoCollect.Tool.giveSpeed;
import qq916397235.cimao.maoCollect.Tool.lootItem;
import qq916397235.cimao.maoCollect.Tool.removeBlock;
import qq916397235.cimao.maoCollect.Tool.tools;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.List;

public class OnPlayerInteract implements Listener {
    @EventHandler
    public void PlayerInteract (PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (ConfigLoad.getInstance().getSettings().get(player.getName()+".type")!=null) {
            //玩家正在进行settings操作
            //choose---选区
            if (ConfigLoad.getInstance().getSettings().getString(player.getName()+".type").equalsIgnoreCase("choose")) {
                if (e.getHand().name().equals("HAND")) {
                    if (e.getAction().name().equalsIgnoreCase("LEFT_CLICK_BLOCK")) {
                        ConfigLoad.getInstance().getSettings().set(player.getName()+".choose.1.x",e.getClickedBlock().getLocation().getX());
                        ConfigLoad.getInstance().getSettings().set(player.getName()+".choose.1.z",e.getClickedBlock().getLocation().getZ());
                        player.sendMessage("§f[§eMaoCollect§f]§d已选择 1点 X "+e.getClickedBlock().getLocation().getX()+"  Z "+e.getClickedBlock().getLocation().getZ());
                        player.sendMessage("§f[§eMaoCollect§f]§a退出选区模式请再次输入/mc s choose");
                        e.setCancelled(true);
                        return;
                    }
                    if (e.getAction().name().equalsIgnoreCase("RIGHT_CLICK_BLOCK")) {
                        ConfigLoad.getInstance().getSettings().set(player.getName()+".choose.2.x",e.getClickedBlock().getLocation().getX());
                        ConfigLoad.getInstance().getSettings().set(player.getName()+".choose.2.z",e.getClickedBlock().getLocation().getZ());
                        player.sendMessage("§f[§eMaoCollect§f]§d已选择 2点 X "+e.getClickedBlock().getLocation().getX()+"  Z "+e.getClickedBlock().getLocation().getZ());
                        player.sendMessage("§f[§eMaoCollect§f]§a退出选区模式请再次输入/mc s choose");
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            //select---选采集方块
            if (ConfigLoad.getInstance().getSettings().getString(player.getName()+".type").equalsIgnoreCase("select")) {
                if (e.getHand().name().equals("HAND")) {
                    if (e.getAction().name().equalsIgnoreCase("LEFT_CLICK_BLOCK")) {
                        ConfigLoad.getInstance().getCollects().set(ConfigLoad.getInstance().getSettings().getString(player.getName()+".key")+".Collect",e.getClickedBlock().getBlockData().getMaterial().name());
                        player.sendMessage("§f[§eMaoCollect§f]§d已选择方块§f"+e.getClickedBlock().getBlockData().getMaterial().name()+"§d作为采集方块");
                        ConfigLoad.getInstance().saveCollects();
                        ConfigLoad.getInstance().getSettings().set(player.getName()+".type", null);
                        e.setCancelled(true);
                    }
                }
            }
            return;
        }
        if (ConfigLoad.getInstance().getData().get(player.getName())!=null) {
            //玩家正在读条 不能进行下次采集操作
            if (ConfigLoad.getInstance().getDoing().get(player.getName())!=null) {
                return;
            }
            String key = ConfigLoad.getInstance().getData().getString(player.getName());
            //判断玩家是否为左键点击
            if (e.getAction().name().equals("LEFT_CLICK_BLOCK")) {
                //判断点击BLOCK是否为COLLECT BLOCK
                if (e.getClickedBlock().getType().equals(Material.getMaterial(ConfigLoad.getInstance().getCollects().getString(key+".Collect")))) {
                    //判断Type 1-自由采集 2-阈值采集 3-特定采集
                    if (ConfigLoad.getInstance().getCollects().getString(key+".Type").equalsIgnoreCase("2")) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item.getAmount()<1||item.getItemMeta().getLore()==null) {
                            return;
                        }
                        List<String> lores = item.getItemMeta().getLore();
                        boolean go = false;
                        for (int i=0;i<lores.size();i++) {
                            if (lores.get(i).indexOf(ConfigLoad.getInstance().getConfig().getString("Power"))!=-1) {
                                int power = tools.getInt(tools.rlore(lores.get(i)));
                                if (power>=ConfigLoad.getInstance().getCollects().getInt(key+".Power")) {
                                    go = true;
                                }
                            }
                        }
                        if (!go) {
                            return;
                        }
                    }
                    if (ConfigLoad.getInstance().getCollects().getString(key+".Type").equalsIgnoreCase("3")) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item.getAmount()<1||item.getItemMeta().getDisplayName()==null) {
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().indexOf(ConfigLoad.getInstance().getCollects().getString(key+".Lore"))==-1) {
                            return;
                        }
                    }
                    if (ConfigLoad.getInstance().getCollects().getString(key+".Type").equalsIgnoreCase("4")) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item.getAmount()<1||item.getItemMeta().getLore()==null||item.getItemMeta().getDisplayName()==null) {
                            return;
                        }
                        List<String> lores = item.getItemMeta().getLore();
                        boolean go = false;
                        for (int i=0;i<lores.size();i++) {
                            if (lores.get(i).indexOf(ConfigLoad.getInstance().getConfig().getString("Power"))!=-1) {
                                int power = tools.getInt(tools.rlore(lores.get(i)));
                                if (power>=ConfigLoad.getInstance().getCollects().getInt(key+".Power")) {
                                    go = true;
                                }
                            }
                        }
                        if (!go) {
                            return;
                        }
                        if (item.getItemMeta().getDisplayName().indexOf(ConfigLoad.getInstance().getCollects().getString(key+".Lore"))==-1) {
                            return;
                        }
                    }
                    int speed = giveSpeed.giveSpeed(e.getPlayer().getInventory().getItemInMainHand());
                    int time = ConfigLoad.getInstance().getCollects().getInt(key+".PickTime")/speed;
                    if (ConfigLoad.getInstance().getCollects().getInt(key+".PickTime")%speed!=0) {
                        time = time+1;
                    }
                    int t = time;
                    ConfigLoad.getInstance().getDoing().set(player.getName(),true);
                    //条件允许 开始采集 - 读条
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 10; i++) {
                                player.sendTitle(ConfigLoad.getInstance().getConfig().getString("Message.ti").replaceAll("ITEM",key).replaceAll("&","§"), ConfigLoad.getInstance().getConfig().getString("Message.s" + i).replaceAll("&","§"), 0, (int) (t * 2 + 5), 0);
                                try {
                                    Thread.sleep(t * 100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (i == 9) {
                                    //消除doing
                                    Location l = e.getClickedBlock().getLocation();
                                    ConfigLoad.getInstance().getDoing().set(player.getName(),null);
                                    ConfigLoad.getInstance().getCooldown().set(l.getWorld().getName()+"|"+l.getBlockX()+"|"+l.getBlockY()+"|"+l.getBlockZ()+"|"+e.getClickedBlock().getType(),ConfigLoad.getInstance().getCollects().getInt(key+".Cooldown"));
                                    //给与物品
                                    lootItem.li(key,player,player.getInventory().getItemInMainHand(),l);
                                }
                            }
                        }
                    }.runTaskAsynchronously(MainClass.getInstance());
                }
            }
        }
    }
}
