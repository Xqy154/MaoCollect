package qq916397235.cimao.maoCollect.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

public class OnCloseInv implements Listener {
    @EventHandler
    public void PlayerCloseInv (InventoryCloseEvent e) {
        if (e.getView().getTitle().indexOf("MaoCollect-")!=-1) {
            String key = e.getView().getTitle().replaceAll("MaoCollect-","");
            Inventory inv = e.getView().getTopInventory();
            ConfigLoad.getInstance().getLoots().set(key,null);
            for (int i=0;i<inv.getSize();i++) {
                if (inv.getItem(i)!=null&&!inv.getItem(i).getType().equals(Material.AIR)) {
                    ConfigLoad.getInstance().getLoots().set(key+"."+i,inv.getItem(i));
                }
            }
            ConfigLoad.getInstance().saveLoots();
            e.getPlayer().sendMessage("§f[§eMaoCollect§f]§d采集区§f"+key+"§d采集奖励品设置成功");
        }
    }
}
