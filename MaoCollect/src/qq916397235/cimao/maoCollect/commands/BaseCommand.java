package qq916397235.cimao.maoCollect.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length>0) {
            switch (args[0]) {
                case "s":
                    CMDSet.parseCommand(sender, cmd, args);
                    return true;
                case "list":
                    Player player = (Player)sender;
                    List<String> c = new ArrayList<>();
                    c.addAll(ConfigLoad.getInstance().getCollects().getKeys(false));
                    for (int i=0;i<c.size();i++) {
                        player.sendMessage(c.get(i));
                    }
                    return true;
                case "info":
                    if (ConfigLoad.getInstance().getCollects().get(args[1])==null) {
                        sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[1]+"§c不存在");
                        return true;
                    }
                    sender.sendMessage("采集区: "+args[1]);
                    sender.sendMessage("区域位置: "+ConfigLoad.getInstance().getCollects().getInt(args[1]+".X")+","+ConfigLoad.getInstance().getCollects().getInt(args[1]+".Z")+"-"+ConfigLoad.getInstance().getCollects().getInt(args[1]+".x")+","+ConfigLoad.getInstance().getCollects().getInt(args[1]+".z"));
                    if (ConfigLoad.getInstance().getCollects().getInt(args[1]+".Type")==1) {
                        sender.sendMessage("采集模式: 1-随意采集");
                    }
                    if (ConfigLoad.getInstance().getCollects().getInt(args[1]+".Type")==2) {
                        sender.sendMessage("采集模式: 2-阈值采集");
                        sender.sendMessage("阈值: "+ConfigLoad.getInstance().getCollects().getInt(args[1]+".Power"));
                    }
                    if (ConfigLoad.getInstance().getCollects().getInt(args[1]+".Type")==3) {
                        sender.sendMessage("采集模式: 3-特定采集");
                        sender.sendMessage("采集工具名: "+ConfigLoad.getInstance().getCollects().getInt(args[1]+".Lore"));
                    }
                    sender.sendMessage("采集方块: "+ConfigLoad.getInstance().getCollects().getString(args[1]+".Collect"));
                    sender.sendMessage("采集时间: "+ConfigLoad.getInstance().getCollects().getInt(args[1]+".PickTime")+"秒");
                    sender.sendMessage("恢复时间: "+ConfigLoad.getInstance().getCollects().getInt(args[1]+".Cooldown")+"秒");
                    return true;
                case "reload":
                    ConfigLoad.getInstance().reloadAll();
                    sender.sendMessage("§c[§fMaoCollect§c]§d配置文件重载成功!");
                    return true;
                case "test":
                    Player p = (Player)sender;
                    p.getLocation().getWorld().dropItem(p.getLocation(),p.getInventory().getItemInMainHand());
                    return true;
            }
        }
        if (sender.isOp()) {
            sender.sendMessage("§b===================MaoCollect===================");
            sender.sendMessage("§a/mc s 指令设置相关[op]");
            sender.sendMessage("§c/mc list 列出所有采集区");
            sender.sendMessage("§a/mc info [采集区] 检索采集区的信息");
            sender.sendMessage("§c/mc reload  重载插件");
            sender.sendMessage("§b================================================");
        }
        return true;
    }
}
