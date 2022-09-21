package qq916397235.cimao.maoCollect.Tool;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.regex.Pattern;

public class removeBlock {
    public static void rb(Location loc) {
        loc.getWorld().setBlockData(loc,Bukkit.getServer().createBlockData(Material.AIR));
    }
}
