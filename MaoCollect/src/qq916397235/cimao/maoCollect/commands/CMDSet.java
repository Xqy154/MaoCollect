package qq916397235.cimao.maoCollect.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import qq916397235.cimao.maoCollect.Tool.tools;
import qq916397235.cimao.maoCollect.configuration.ConfigLoad;

import java.util.ArrayList;
import java.util.List;

public class CMDSet {public static void parseCommand(CommandSender sender, Command cmd, String[] args) {
        if (!sender.isOp()) {
            return;
        }
        //mc S choose
        if (args.length==2&&args[1].equalsIgnoreCase("choose")) {
            if (ConfigLoad.getInstance().getSettings().get(sender.getName()+".type")!=null) {
                if (ConfigLoad.getInstance().getSettings().getString(sender.getName()+".type").equals("choose")) {
                    ConfigLoad.getInstance().getSettings().set(sender.getName()+".type", null);
                    sender.sendMessage("§f[§eMaoCollect§f]§d退出选区模式");
                    return;
                }
            }
            ConfigLoad.getInstance().getSettings().set(sender.getName()+".type","choose");
            sender.sendMessage("§f[§eMaoCollect§f]§d已进入选区模式,请左键右键分别选择1,2点");
            return;
        }
        //mc s create [采集区名]
        if (args.length==3&&args[1].equalsIgnoreCase("create")) {
            System.out.println(sender.getName()+".choose.1");
            if (ConfigLoad.getInstance().getSettings().get(sender.getName()+".choose.1")==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c选区错误-未选择点1,请/mc s choose进入选区模式");
                return;
            }
            if (ConfigLoad.getInstance().getSettings().get(sender.getName()+".choose.2")==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c选区错误-未选择点2,请/mc s choose进入选区模式");
                return;
            }
            if (ConfigLoad.getInstance().getCollects().get(args[2])!=null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c已存在,请更换选区名或先删除");
                return;
            }
            //创建选区
            if (ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.1.x")>ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.2.x")) {
                ConfigLoad.getInstance().getCollects().set(args[2]+".X",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.1.x"));
                ConfigLoad.getInstance().getCollects().set(args[2]+".x",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.2.x"));
            }else {
                ConfigLoad.getInstance().getCollects().set(args[2]+".X",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.2.x"));
                ConfigLoad.getInstance().getCollects().set(args[2]+".x",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.1.x"));
            }
            if (ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.1.z")>ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.2.z")) {
                ConfigLoad.getInstance().getCollects().set(args[2]+".Z",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.1.z"));
                ConfigLoad.getInstance().getCollects().set(args[2]+".z",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.2.z"));
            }else {
                ConfigLoad.getInstance().getCollects().set(args[2]+".Z",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.2.z"));
                ConfigLoad.getInstance().getCollects().set(args[2]+".z",ConfigLoad.getInstance().getSettings().getInt(sender.getName()+".choose.1.z"));
            }
            ConfigLoad.getInstance().getCollects().set(args[2]+".PickTime",5);
            ConfigLoad.getInstance().getCollects().set(args[2]+".Cooldown",30);
            ConfigLoad.getInstance().getCollects().set(args[2]+".Type",1);
            ConfigLoad.getInstance().getCollects().set(args[2]+".Power",5);
            ConfigLoad.getInstance().getCollects().set(args[2]+".Lore","采集工具");
            ConfigLoad.getInstance().getCollects().set(args[2]+".MessageA","&f采集区: "+args[2]);
            ConfigLoad.getInstance().getCollects().set(args[2]+".MessageB","&f请使用指令/mc s message [采集区名] <message> <message>编辑此信息");
            ConfigLoad.getInstance().saveCollects();
            sender.sendMessage("§f[§eMaoCollect§f]§d成功创建采集区§f"+args[2]);
            if (ConfigLoad.getInstance().getSettings().get(sender.getName()+".type")!=null) {
                if (ConfigLoad.getInstance().getSettings().getString(sender.getName()+".type").equals("choose")) {
                    ConfigLoad.getInstance().getSettings().set(sender.getName()+".type", null);
                    sender.sendMessage("§f[§eMaoCollect§f]§d检测到采集区创建,已为您自动退出选区模式");
                    return;
                }
            }
            return;
        }
        //mc s select [采集区名]
        if (args.length==3&&args[1].equalsIgnoreCase("select")) {
            if (ConfigLoad.getInstance().getCollects().get(args[2])==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c不存在,请先创建采集区");
                return;
            }
            ConfigLoad.getInstance().getSettings().set(sender.getName()+".type","select");
            ConfigLoad.getInstance().getSettings().set(sender.getName()+".key",args[2]);
            sender.sendMessage("§f[§eMaoCollect§f]§d已进入选择模式,请左键选择方块为采集方块");
            return;

        }
        //mc s select [采集区名]
        if (args.length==3&&args[1].equalsIgnoreCase("remove")) {
            if (ConfigLoad.getInstance().getCollects().get(args[2])==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c不存在");
                return;
            }
            ConfigLoad.getInstance().getCollects().set(args[2],null);
            ConfigLoad.getInstance().saveCollects();
            sender.sendMessage("§f[§eMaoCollect§f]§d删除成功");
            return;
        }
        //mc s loots [采集区名]
        if (args.length==3&&args[1].equalsIgnoreCase("loots")) {
            Player player = (Player)sender;
            if (ConfigLoad.getInstance().getCollects().get(args[2])==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c不存在");
                return;
            }
            Inventory inv = Bukkit.createInventory(null, 27, "MaoCollect-"+args[2]);
            if (ConfigLoad.getInstance().getLoots().get(args[2])!=null) {
                List<String> l =new ArrayList<>();
                l.addAll(ConfigLoad.getInstance().getLoots().getConfigurationSection(args[2]).getKeys(false));
                for (int i=0;i<l.size();i++) {
                    inv.setItem(i,ConfigLoad.getInstance().getLoots().getItemStack(args[2]+"."+l.get(i)));
                }
            }
            player.openInventory(inv);
            return;
        }
        //mc s message [采集区名] <message> <message>
        if (args.length>3&&args[1].equalsIgnoreCase("message")) {
            Player player = (Player)sender;
            if (ConfigLoad.getInstance().getCollects().get(args[2])==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c不存在");
                return;
            }
            if (args.length==4) {
                ConfigLoad.getInstance().getCollects().set(args[2]+".MessageA",args[3].replaceAll("\"\""," "));
                ConfigLoad.getInstance().getCollects().set(args[2]+".MessageB","");
            }
            if (args.length==5) {
                ConfigLoad.getInstance().getCollects().set(args[2]+".MessageA",args[3].replaceAll("\"\""," "));
                ConfigLoad.getInstance().getCollects().set(args[2]+".MessageB",args[4].replaceAll("\"\""," "));
            }
            ConfigLoad.getInstance().saveCollects();
            sender.sendMessage("§f[§eMaoCollect§f]§d设置成功 现在为您弹出此进入效果以做演示");
            player.sendTitle(ConfigLoad.getInstance().getCollects().getString(args[2]+".MessageA").replaceAll("&","§"),ConfigLoad.getInstance().getCollects().getString(args[2]+".MessageB").replaceAll("&","§"),10,40,10);
            return;
        }
        ///mc s type [采集区名] [1/2/3]
        if (args.length>3&&args[1].equalsIgnoreCase("type")) {
            if (ConfigLoad.getInstance().getCollects().get(args[2])==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c不存在");
                return;
            }
            if (args[3].equalsIgnoreCase("1")) {
                ConfigLoad.getInstance().getCollects().set(args[2]+".Type",args[3]);
                sender.sendMessage("§f[§eMaoCollect§f]§d成功设置采集方式为1-随意采集");
                ConfigLoad.getInstance().saveCollects();
                return;
            }
            if (args.length==4) {
                sender.sendMessage("§f[§eMaoCollect§f]§c你没有输入阈值或采集字符");
                return;
            }
            if (args[3].equalsIgnoreCase("2")) {
                if (tools.isInteger(args[4])) {
                    ConfigLoad.getInstance().getCollects().set(args[2] + ".Type", args[3]);
                    ConfigLoad.getInstance().getCollects().set(args[2] + ".Power", Integer.parseInt(args[4]));
                    sender.sendMessage("§f[§eMaoCollect§f]§d成功设置采集方式为2-阈值采集");
                    ConfigLoad.getInstance().saveCollects();
                    return;
                }else {
                    sender.sendMessage("§f[§eMaoCollect§f]§c阈值错误");
                    return;
                }
            }
            if (args[3].equalsIgnoreCase("3")) {
                ConfigLoad.getInstance().getCollects().set(args[2]+".Type",args[3]);
                ConfigLoad.getInstance().getCollects().set(args[2]+".Lore",args[4]);
                sender.sendMessage("§f[§eMaoCollect§f]§d成功设置采集方式为3-特定采集");
                ConfigLoad.getInstance().saveCollects();
                return;
            }
            if (args.length==5) {
                sender.sendMessage("§f[§eMaoCollect§f]§c你没有输入阈值或采集字符");
                return;
            }
            if (args[3].equalsIgnoreCase("4")) {
                ConfigLoad.getInstance().getCollects().set(args[2]+".Type",args[3]);
                ConfigLoad.getInstance().getCollects().set(args[2] + ".Power", Integer.parseInt(args[4]));
                ConfigLoad.getInstance().getCollects().set(args[2]+".Lore",args[5]);
                sender.sendMessage("§f[§eMaoCollect§f]§d成功设置采集方式为3-特定采集");
                ConfigLoad.getInstance().saveCollects();
                return;
            }
        }
        //mc s cooldown [采集区名] [时间]
        if (args.length==4&&args[1].equalsIgnoreCase("cooldown")) {
            if (ConfigLoad.getInstance().getCollects().get(args[2])==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c不存在");
                return;
            }
            if (tools.isInteger(args[3])) {
                ConfigLoad.getInstance().getCollects().set(args[2] + ".Cooldown", args[3]);
                sender.sendMessage("§f[§eMaoCollect§f]§d成功设置采集物恢复时间");
                ConfigLoad.getInstance().saveCollects();
                return;
            }else {
                sender.sendMessage("§f[§eMaoCollect§f]§c时间不为数字,请检查输入指令");
                return;
            }
        }
        //mc s picktime [采集区名] [时间]
        if (args.length==4&&args[1].equalsIgnoreCase("picktime")) {
            if (ConfigLoad.getInstance().getCollects().get(args[2])==null) {
                sender.sendMessage("§f[§eMaoCollect§f]§c采集区§f"+args[2]+"§c不存在");
                return;
            }
            if (tools.isInteger(args[3])) {
                ConfigLoad.getInstance().getCollects().set(args[2] + ".Picktime", args[3]);
                sender.sendMessage("§f[§eMaoCollect§f]§d成功设置采集物采集时间");
                ConfigLoad.getInstance().saveCollects();
                return;
            }else {
                sender.sendMessage("§f[§eMaoCollect§f]§c时间不为数字,请检查输入指令");
                return;
            }
        }
        if (args[0].equalsIgnoreCase("s")) {
            sender.sendMessage("§b===================MaoCollect===================");
            sender.sendMessage("§a/mc s choose 进入选区模式");
            sender.sendMessage("§c/mc s create [采集区名] 创建一个采集区");
            sender.sendMessage("§a/mc s select [采集区名] 选择一个方块作为采集方块");
            sender.sendMessage("§c/mc s remove [采集区名] 删除采集区");
            sender.sendMessage("§a/mc s loots [采集区名] 为采集区设置采集奖励品");
            sender.sendMessage("§c/mc s cooldown [采集区名] [时间] 设置采集物恢复时间(秒)");
            sender.sendMessage("§a/mc s picktime [采集区名] [时间] 设置采集物采集时间(秒)");
            sender.sendMessage("§c/mc s type [采集区名] [1/2/3] <阈值/字符> 设置采集方式1-随意采集 2-阈值采集 3-特定采集");
            sender.sendMessage("§c/mc s type [采集区名] 4 <阈值> <字符> 设置采集方式阈值采集+特定采集");
            sender.sendMessage("§a/mc s message [采集区名] [message] <message> 设置进入此区域的Title消息");
            sender.sendMessage("§b===============================================");
            return;
        }
    }
}
