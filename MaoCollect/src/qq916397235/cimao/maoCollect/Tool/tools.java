package qq916397235.cimao.maoCollect.Tool;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tools {
    //判断是否为整数
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    public static String rlore(String lore)
    {
        while (lore.indexOf("§") != -1) {
            lore = lore.substring(0, lore.indexOf("§")) + lore.substring(lore.indexOf("§") + 2, lore.length());
        }
        return lore;
    }

    public static List<String> rlores(List<String> lores)
    {
        for (int i = 0; i < lores.size(); i++)
        {
            String lore = (String)lores.get(i);
            while (lore.indexOf("§") != -1) {
                lore = lore.substring(0, lore.indexOf("§")) + lore.substring(lore.indexOf("§") + 2, lore.length());
            }
            lores.set(i, lore);
        }
        return lores;
    }

    public static String clore(String lore)
    {
        lore = lore.replaceAll("§","&");
        return lore;
    }

    public static List<String> clores(List<String> lores)
    {
        for (int i = 0; i < lores.size(); i++)
        {
            String lore = (String)lores.get(i);
            lore = lore.replaceAll("§","&");
            lores.set(i, lore);
        }
        return lores;
    }

    public static List<String> tlores(List<String> lores)
    {
        for (int i = 0; i < lores.size(); i++)
        {
            String lore = (String)lores.get(i);
            lore = lore.replaceAll("&","§");
            lores.set(i, lore);
        }
        return lores;
    }

    public static String alore(String lore)
    {
        if (lore.indexOf("<")!=-1&&lore.indexOf("-")!=-1&&lore.indexOf(">")!=-1) {
            int start = Integer.parseInt(lore.substring(lore.indexOf("<")+1,lore.indexOf("-")));
            int end = Integer.parseInt(lore.substring(lore.indexOf("-")+1,lore.indexOf(">")));
            String r1 = lore.substring(lore.indexOf("<"),lore.indexOf(">")+1);
            String r2 = getRandom(start,end)+"";
            lore = lore.replace(r1,r2);
        }
        return lore;
    }

    public static int getRandom(int start, int end){
        return (int)(Math.random() * (end-start+1) + start);
    }

    public static int getInt(String lore)
    {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(lore);
        lore = m.replaceAll("").trim();
        int num = Integer.parseInt(lore);
        return num;
    }
    public static void giveItem(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty()!=-1) {
            player.getInventory().addItem(itemStack);
        }else {
            player.getWorld().dropItem(player.getLocation(),itemStack);
        }
    }
}
