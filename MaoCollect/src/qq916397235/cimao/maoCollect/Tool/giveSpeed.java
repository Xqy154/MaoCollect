package qq916397235.cimao.maoCollect.Tool;

import org.bukkit.inventory.ItemStack;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.List;

public class giveSpeed {
    public static int giveSpeed(ItemStack item) {
        if (item.getAmount()<1) {
            return 1;
        }
        if (item.getAmount()<1||item.getItemMeta().getDisplayName()==null) {
            return 1;
        }
        List<String> lores = item.getItemMeta().getLore();
        for (int i=0;i<lores.size();i++) {
            if (lores.get(i).indexOf(ConfigLoad.getInstance().getConfig().getString("Speed"))!=-1) {
                return tools.getInt(tools.rlore(lores.get(i)));
            }
        }
        return 1;
    }

}
