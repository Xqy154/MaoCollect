// removeBlock.java（1.12兼容）
package qq916397235.cimao.maoCollect.Tool;
import org.bukkit.Location;
import org.bukkit.Material;
public class removeBlock {
    public static void rb(Location loc) {
        loc.getBlock().setType(Material.AIR, false);
    }
}
