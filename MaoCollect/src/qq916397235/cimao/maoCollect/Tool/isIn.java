package qq916397235.cimao.maoCollect.Tool;

import org.bukkit.Location;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.regex.Pattern;

public class isIn {
    public static boolean isin(Location loc,String str) {
        double x = loc.getX();
        double z = loc.getZ();
        if (x> ConfigLoad.getInstance().getCollects().getInt(str+".X")) {
            return false;
        }
        if (x< ConfigLoad.getInstance().getCollects().getInt(str+".x")) {
            return false;
        }
        if (z> ConfigLoad.getInstance().getCollects().getInt(str+".Z")) {
            return false;
        }
        if (z< ConfigLoad.getInstance().getCollects().getInt(str+".z")) {
            return false;
        }
        return true;
    }
}
