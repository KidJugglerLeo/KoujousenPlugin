package com.koujousen.plugin.koujousenplugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;

import java.io.File;

import static com.koujousen.plugin.koujousenplugin.functionHandler.*;

public final class Koujousenplugin extends JavaPlugin implements Listener {

    public static int redAlive;
    public static int blueAlive;


    @Override
    public void onEnable() {
        ConfigHandler.setup();
        ConfigHandler.save();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (playing){
                    timersecond += 1;
                    if (timersecond == 60){
                        timerminute += 1;
                        timersecond -= 60;
                    }
                }else{
                    timerminute = 0;
                    timersecond = 0;
                }
            }
        }, 0L, 20L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        showActionBar();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("kjs")){
            if (!sender.isOp()){
                sender.sendMessage(ChatColor.RED + "あなたはop権限がありません");
                return true;
            }
            if (!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーしか使えません");
                return true;
            }
            try{
                if (args[0].equalsIgnoreCase("create")){
                    try{
                        if (!args[1].isEmpty()){
                            Player player = (Player) sender;
                            ItemStack Stone_Sword = new ItemStack(Material.STONE_SWORD);
                            ItemStack Bow = new ItemStack(Material.BOW);
                            ItemStack Arrow = new ItemStack(Material.ARROW, 64);
                            ItemStack Steak = new ItemStack(Material.COOKED_BEEF, 64);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".X", 0);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".Y", 0);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".Z", 0);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".leader", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".side", "attack");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".X", 0);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".Y", 0);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".Z", 0);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".side", "defend");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".leader", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".world", player.getWorld().getName());
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot1", Stone_Sword);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot2", Arrow);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot3", Bow);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot4", Steak);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot5", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot6", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot7", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot8", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot9", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot1", Stone_Sword);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot2", Arrow);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot3", Bow);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot4", Steak);
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot5", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot6", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot7", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot8", "");
                            ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot9", "");
                            ConfigHandler.save();
                            sender.sendMessage(ChatColor.GREEN + "攻城戦が作られました");
                            return true;
                        }else{
                            sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("setredspawn")){
                    if (!(sender instanceof Player)){
                        sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーしか使えません");
                        return true;
                    }
                    Player player = (Player) sender;
                    double X = player.getLocation().getX();
                    double Y = player.getLocation().getY();
                    double Z = player.getLocation().getZ();
                    try{
                        if (!args[1].isEmpty()){
                            try{
                                if (ConfigHandler.get().contains("攻城戦." + args[1] + ".red" + ".X")){
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".X", X);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".Y", Y);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".red" + ".Z", Z);
                                    ConfigHandler.save();
                                    player.sendMessage(ChatColor.GREEN + "赤のリス地が設定されました");
                                }else{
                                    player.sendMessage(ChatColor.RED + "先に攻城戦を作ってください");
                                }
                                return true;
                            }catch(Exception e){
                                player.sendMessage(ChatColor.RED + "先に攻城戦を作ってください");
                                return true;
                            }
                        }else{
                            player.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }
                    }catch (Exception e){
                        player.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("setbluespawn")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーしか使えません");
                        return true;
                    }
                    Player player = (Player) sender;
                    double X = player.getLocation().getX();
                    double Y = player.getLocation().getY();
                    double Z = player.getLocation().getZ();
                    try {
                        if (!args[1].isEmpty()) {
                            try {
                                if (ConfigHandler.get().contains("攻城戦." + args[1] + ".blue" + ".X")) {
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".X", X);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".Y", Y);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".blue" + ".Z", Z);
                                    ConfigHandler.save();
                                    player.sendMessage(ChatColor.GREEN + "青のリス地が設定されました");
                                } else {
                                    player.sendMessage(ChatColor.RED + "先に攻城戦を作ってください");
                                }
                                return true;
                            } catch (Exception e) {
                                player.sendMessage(ChatColor.RED + "先に攻城戦を作ってください");
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("setblueleader")){
                    try{
                        if (args[1].isEmpty()){
                            sender.sendMessage(ChatColor.RED + "ユーザーネームを入力してください");
                            return true;
                        }else if(args[2].isEmpty() || !(ConfigHandler.get().contains("攻城戦." + args[2] + ".blue" + ".leader"))){
                            sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }else{ ;
                            ConfigHandler.get().set("攻城戦." + args[2] + ".blue" + ".leader", args[1]);
                            ConfigHandler.save();
                            sender.sendMessage(ChatColor.GREEN + "青チームの大将が設定されました");
                            return true;
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("setredleader")){
                    try{
                        if (args[1].isEmpty()){
                            sender.sendMessage(ChatColor.RED + "ユーザーネームを入力してください");
                            return true;
                        }else if(args[2].isEmpty() || !(ConfigHandler.get().contains("攻城戦." + args[2] + ".red" + ".leader"))){
                            sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }else{
                            String player = Bukkit.getPlayer(args[1]).getName();
                            ConfigHandler.get().set("攻城戦." + args[2] + ".red" + ".leader", args[1]);
                            ConfigHandler.save();
                            sender.sendMessage(ChatColor.GREEN + "赤チームの大将が設定されました");
                            return true;
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("setredside")){
                    try{
                        if (args[1].isEmpty()){
                            sender.sendMessage(ChatColor.RED + "defend または attackを選択してください");
                            return true;
                        }else if(args[2].isEmpty() || !(ConfigHandler.get().contains("攻城戦." + args[2] + ".red" + ".side"))){
                            sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }else if(args[1].equalsIgnoreCase("defend")){
                            ConfigHandler.get().set("攻城戦." + args[2] + ".red" + ".side", "defend");
                            ConfigHandler.get().set("攻城戦." + args[2] + ".blue" + ".side", "attack");
                            ConfigHandler.save();
                            sender.sendMessage(ChatColor.GREEN + "赤チームのサイドが設定されました");
                            return true;
                        }else if(args[1].equalsIgnoreCase("attack")){
                            ConfigHandler.get().set("攻城戦." + args[2] + ".red" + ".side", "attack");
                            ConfigHandler.get().set("攻城戦." + args[2] + ".blue" + ".side", "defend");
                            ConfigHandler.save();
                            sender.sendMessage(ChatColor.GREEN + "赤チームのサイドが設定されました");
                            return true;
                        }else{
                            sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                            return true;
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("setblueside")){
                    try{
                        if (args[1].isEmpty()){
                            sender.sendMessage(ChatColor.RED + "defend または attackを選択してください");
                            return true;
                        }else if(args[2].isEmpty() || !(ConfigHandler.get().contains("攻城戦." + args[2] + ".blue" + ".side"))){
                            sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }else if(args[1].equalsIgnoreCase("defend")){
                            ConfigHandler.get().set("攻城戦." + args[2] + ".blue" + ".side", "defend");
                            ConfigHandler.get().set("攻城戦." + args[2] + ".red" + ".side", "attack");
                            ConfigHandler.save();
                            sender.sendMessage(ChatColor.GREEN + "青チームのサイドが設定されました");
                            return true;
                        }else if(args[1].equalsIgnoreCase("attack")){
                            ConfigHandler.get().set("攻城戦." + args[2] + ".blue" + ".side", "attack");
                            ConfigHandler.get().set("攻城戦." + args[2] + ".red" + ".side", "defend");
                            ConfigHandler.save();
                            sender.sendMessage(ChatColor.GREEN + "青チームのサイドが設定されました");
                            return true;
                        }else{
                            sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                            return true;
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                        return true;
                    }
                }else if(args[0].equals("movered")){
                    try{
                        if (!(args[1].isEmpty())){
                            Player player = Bukkit.getPlayer(args[1]);
                            Team.removeFromTeam(player);
                            Team.addToTeam(TeamType.RED, player);
                            player.setPlayerListName(ChatColor.RED + player.getName());
                            player.setCustomName(ChatColor.RED + player.getName() + ChatColor.WHITE + "");
                            player.setCustomNameVisible(true);
                            player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE + "");
                        }
                        else{
                            sender.sendMessage(ChatColor.RED + "ユーザーネームを入力してください");
                            return true;
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                        return true;
                    }
                }else if(args[0].equals("moveblue")){
                    try{
                        if (!(args[1].isEmpty())){
                            Player player = Bukkit.getPlayer(args[1]);
                            Team.removeFromTeam(player);
                            Team.addToTeam(TeamType.BLUE, player);
                            player.setPlayerListName(ChatColor.BLUE + player.getName());
                            player.setCustomName(ChatColor.BLUE + player.getName() + ChatColor.WHITE + "");
                            player.setCustomNameVisible(true);
                            player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE + "");
                        }
                        else{
                            sender.sendMessage(ChatColor.RED + "ユーザーネームを入力してください");
                            return true;
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("setattackerinv")){
                    try{
                        if (!(args[1].isEmpty())){
                            try{
                                if(ConfigHandler.get().contains("攻城戦." + args[1])){
                                    Player player = (Player) sender;
                                    Inventory inv = player.getInventory();
                                    ItemStack item1 = inv.getItem(0);
                                    ItemStack item2 = inv.getItem(1);
                                    ItemStack item3 = inv.getItem(2);
                                    ItemStack item4 = inv.getItem(3);
                                    ItemStack item5 = inv.getItem(4);
                                    ItemStack item6 = inv.getItem(5);
                                    ItemStack item7 = inv.getItem(6);
                                    ItemStack item8 = inv.getItem(7);
                                    ItemStack item9 = inv.getItem(8);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot1", item1);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot2", item2);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot3", item3);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot4", item4);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot5", item5);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot6", item6);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot7", item7);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot8", item8);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".attackslot9", item9);
                                    ConfigHandler.save();
                                    player.sendMessage(ChatColor.GREEN + "守り側のインベントリーを設定しました");
                                    return true;
                                }else{
                                    sender.sendMessage(ChatColor.RED + "正しい攻城戦名を入力してください");
                                    return true;
                                }
                            }catch(Exception e){
                                sender.sendMessage(ChatColor.RED + "正しい攻城戦名を入力してください");
                                return true;
                            }
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                    }

                }else if(args[0].equalsIgnoreCase("setdefenderinv")){
                    try{
                        if (!(args[1].isEmpty())){
                            try{
                                if(ConfigHandler.get().contains("攻城戦." + args[1])){
                                    Player player = (Player) sender;
                                    Inventory inv = player.getInventory();
                                    ItemStack item1 = inv.getItem(0);
                                    ItemStack item2 = inv.getItem(1);
                                    ItemStack item3 = inv.getItem(2);
                                    ItemStack item4 = inv.getItem(3);
                                    ItemStack item5 = inv.getItem(4);
                                    ItemStack item6 = inv.getItem(5);
                                    ItemStack item7 = inv.getItem(6);
                                    ItemStack item8 = inv.getItem(7);
                                    ItemStack item9 = inv.getItem(8);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot1", item1);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot2", item2);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot3", item3);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot4", item4);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot5", item5);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot6", item6);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot7", item7);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot8", item8);
                                    ConfigHandler.get().set("攻城戦." + args[1] + ".settings" + ".defenceslot9", item9);
                                    ConfigHandler.save();
                                    player.sendMessage(ChatColor.GREEN + "守り側のインベントリーを設定しました");
                                    return true;
                                }else{
                                    sender.sendMessage(ChatColor.RED + "正しい攻城戦名を入力してください");
                                    return true;
                                }
                            }catch(Exception e){
                                sender.sendMessage(ChatColor.RED + "正しい攻城戦名を入力してください");
                                return true;
                            }
                        }
                    }catch(Exception e){
                        sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                    }

                }else if(args[0].equalsIgnoreCase("start")){
                    try{
                        if (!(args[1].isEmpty())){
                            if (functionHandler.playing){
                                sender.sendMessage(ChatColor.RED + "攻城戦をいま遊んでいます");
                                return true;
                            }
                            redAlive = Team.redTeam.size();
                            blueAlive = Team.blueTeam.size();
                            functionHandler.start(args[1], sender);
                            return true;
                        }else {
                            sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                            return true;
                        }
                    }catch (Exception e){
                        sender.sendMessage(ChatColor.RED + "攻城戦名を入力してください");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("stop")){
                    functionHandler.playing = false;
                    timersecond = 0;
                    timerminute = 0;
                    return true;
                }else if(args[0].equalsIgnoreCase("divide")){
                    int i = 0;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (i < Bukkit.getOnlinePlayers().size() / 2){
                            Team.removeFromTeam(player);
                            Team.addToTeam(TeamType.RED, player);
                            player.setPlayerListName(ChatColor.RED + player.getName());
                            player.setCustomName(ChatColor.RED + player.getName() + ChatColor.WHITE + "");
                            player.setCustomNameVisible(true);
                            player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE + "");
                        }else{
                            Team.removeFromTeam(player);
                            Team.addToTeam(TeamType.BLUE, player);
                            player.setPlayerListName(ChatColor.BLUE + player.getName());
                            player.setCustomName(ChatColor.BLUE + player.getName() + ChatColor.WHITE + "");
                            player.setCustomNameVisible(true);
                            player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE + "");
                        }
                        i++;
                    }
                }else if(args[0].equalsIgnoreCase("reload")) {
                    ConfigHandler.reload();
                    sender.sendMessage(ChatColor.GREEN + "プラグインがリロードされました");
                    return true;
                }else if(args[0].equalsIgnoreCase("help")){
                    sender.sendMessage(ChatColor.GREEN + "/kjs create [攻城戦名]: 新しい攻城戦を作る");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setbluespawn [攻城戦名]: 青チームのリス地を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setredspawn [攻城戦名]: 赤チームのリス地を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setblueleader [ユーザーネーム] [攻城戦名]: 青チームの大将を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setredleader [ユーザーネーム] [攻城戦名]: 赤チームの大将を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setredside [defend/attack] [攻城戦名]: 赤チームのサイドを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setblueside [defend/attack] [攻城戦名]: 青チームのサイドを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setdefenderinv [攻城戦名]: 守る側のホットバーを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setattackerinv [攻城戦名]: 攻める側のホットバーを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs reload: 攻城戦 pluginをリロードする");
                    sender.sendMessage(ChatColor.GREEN + "/kjs help: このhelpメッセージを表示する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs movred [ユーザーネーム]: プレイヤーを赤チームに移動する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs moveblue [ユーザーネーム]: プレイヤーを青チームに移動する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs start [攻城戦名]: このhelpメッセージを表示する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs stop: 今行われている攻城戦を止める");
                    sender.sendMessage(ChatColor.GREEN + "/kjs divide: チーム分けをする");
                    return true;
                }else{
                    sender.sendMessage(ChatColor.GREEN + "/kjs create [攻城戦名]: 新しい攻城戦を作る");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setbluespawn [攻城戦名]: 青チームのリス地を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setredspawn [攻城戦名]: 赤チームのリス地を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setblueleader [ユーザーネーム] [攻城戦名]: 青チームの大将を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setredleader [ユーザーネーム] [攻城戦名]: 赤チームの大将を設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setredside [defend/attack] [攻城戦名]: 赤チームの一回戦目のサイドを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setblueside [defend/attack] [攻城戦名]: 青チームの一回戦目のサイドを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setdefenderinv [攻城戦名]: 守る側のホットバーを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs setattackerinv [攻城戦名]: 攻める側のホットバーを設定する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs reload: 攻城戦 pluginをリロードする");
                    sender.sendMessage(ChatColor.GREEN + "/kjs help: このhelpメッセージを表示する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs movred [ユーザーネーム]: プレイヤーを赤チームに移動する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs moveblue [ユーザーネーム]: プレイヤーを青チームに移動する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs start [攻城戦名]: このhelpメッセージを表示する");
                    sender.sendMessage(ChatColor.GREEN + "/kjs stop: 今行われている攻城戦を止める");
                    sender.sendMessage(ChatColor.GREEN + "/kjs divide: チーム分けをする");
                    return true;
                }
            }catch (Exception e){
                sender.sendMessage(ChatColor.GREEN + "/kjs create [攻城戦名]: 新しい攻城戦を作る");
                sender.sendMessage(ChatColor.GREEN + "/kjs setbluespawn [攻城戦名]: 青チームのリス地を設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs setredspawn [攻城戦名]: 赤チームのリス地を設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs setblueleader [ユーザーネーム] [攻城戦名]: 青チームの大将を設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs setredleader [ユーザーネーム] [攻城戦名]: 赤チームの大将を設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs setredside [defend/attack] [攻城戦名]: 赤チームの一回戦目のサイドを設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs setblueside [defend/attack] [攻城戦名]: 青チームの一回戦目のサイドを設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs setdefenderinv [攻城戦名]: 守る側のホットバーを設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs setattackerinv [攻城戦名]: 攻める側のホットバーを設定する");
                sender.sendMessage(ChatColor.GREEN + "/kjs reload: 攻城戦 pluginをリロードする");
                sender.sendMessage(ChatColor.GREEN + "/kjs help: このhelpメッセージを表示する");
                sender.sendMessage(ChatColor.GREEN + "/kjs movred [ユーザーネーム]: プレイヤーを赤チームに移動する");
                sender.sendMessage(ChatColor.GREEN + "/kjs moveblue [ユーザーネーム]: プレイヤーを青チームに移動する");
                sender.sendMessage(ChatColor.GREEN + "/kjs start [攻城戦名]: このhelpメッセージを表示する");
                sender.sendMessage(ChatColor.GREEN + "/kjs stop: 今行われている攻城戦を止める");
                sender.sendMessage(ChatColor.GREEN + "/kjs divide: チーム分けをする");
                return true;
            }
        }
        return true;
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if ((e.getDamager() instanceof Player) && (e.getEntity() instanceof Player)){
            Player damager = (Player) e.getDamager();
            Player player = (Player) e.getEntity();
            if ((Team.blueTeam.contains(damager.getName())) && (Team.blueTeam.contains(player.getName()))){
                e.setCancelled(true);
            }else if((Team.redTeam.contains(damager.getName())) && (Team.redTeam.contains(player.getName()))){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerdeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        if (functionHandler.playing){
            if (Team.redTeam.contains(player.getName())){
                if (ConfigHandler.get().get("攻城戦." + playingGame + ".red" + ".side").equals("defend")){
                    player.setGameMode(GameMode.SPECTATOR);
                    redAlive -= 1;
                    String p = ConfigHandler.get().getString("攻城戦." + functionHandler.playingGame + ".red" + ".leader");
                    if (player.getName().equals(p)){
                        int resultSecond = timersecond;
                        int resultMinute = timerminute;
                        functionHandler.playing = false;
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()){
                            String subTitle = p + "が倒された";
                            onlinePlayers.setGameMode(GameMode.ADVENTURE);
                            onlinePlayers.sendTitle(ChatColor.BLUE + "青チームの勝利！", subTitle, 10, 10, 10);
                            onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.BLOCK_ANVIL_HIT, 10, 1);
                            onlinePlayers.sendMessage("青チームの勝利");
                            onlinePlayers.sendMessage(ChatColor.GRAY + "[result]" + ChatColor.WHITE + "経過時間は" + resultMinute + "分" + resultSecond + "秒でした。");
                        }
                    }
                }
            }else if(Team.blueTeam.contains(player.getName())){
                if (ConfigHandler.get().get("攻城戦." + functionHandler.playingGame + ".blue" + ".side").equals("defend")){
                    player.setGameMode(GameMode.SPECTATOR);
                    blueAlive -= 1;
                    String p = ConfigHandler.get().getString("攻城戦." + functionHandler.playingGame + ".blue" + ".leader");
                    if (player.getName().equals(p)){
                        int resultSecond = timersecond;
                        int resultMinute = timerminute;
                        functionHandler.playing = false;
                        for (Player onlineplayers : Bukkit.getOnlinePlayers()){
                            String subTitle = p + "が倒された";
                            onlineplayers.setGameMode(GameMode.ADVENTURE);
                            onlineplayers.sendTitle(ChatColor.RED + "赤チームの勝利！", subTitle, 10, 10, 10);
                            onlineplayers.playSound(onlineplayers.getLocation(), Sound.BLOCK_ANVIL_HIT, 10, 1);
                            onlineplayers.sendMessage("赤チームの勝利");
                            onlineplayers.sendMessage(ChatColor.GRAY + "[result]" + ChatColor.WHITE + "経過時間は" + resultMinute + "分" + resultSecond + "秒でした。");
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public static void onLogOut(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (playing){
            if (Team.redTeam.contains(player.getName())){
                if (ConfigHandler.get().get("攻城戦." + playingGame + ".red" + ".side").equals("defend")){
                    redAlive -= 1;
                    String p = ConfigHandler.get().getString("攻城戦." + functionHandler.playingGame + ".red" + ".leader");
                    if (player.getName().equals(p)){
                        int resultSecond = timersecond;
                        int resultMinute = timerminute;
                        functionHandler.playing = false;
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()){
                            String subTitle = p + "が倒された";
                            onlinePlayers.sendTitle(ChatColor.BLUE + "青チームの勝利！", subTitle, 10, 10, 10);
                            onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.BLOCK_ANVIL_HIT, 10, 1);
                            onlinePlayers.sendMessage("青チームの勝利");
                            onlinePlayers.sendMessage(ChatColor.GRAY + "[result]" + ChatColor.WHITE + "経過時間は" + resultMinute + "分" + resultSecond + "秒でした。");
                            onlinePlayers.setGameMode(GameMode.ADVENTURE);
                        }
                    }
                }
            }else if(Team.blueTeam.contains(player.getName())){
                if (ConfigHandler.get().get("攻城戦." + functionHandler.playingGame + ".blue" + ".side").equals("defend")){
                    blueAlive -= 1;
                    String p = ConfigHandler.get().getString("攻城戦." + functionHandler.playingGame + ".blue" + ".leader");
                    if (player.getName().equals(p)){
                        int resultSecond = timersecond;
                        int resultMinute = timerminute;
                        functionHandler.playing = false;
                        for (Player onlinePlayers : Bukkit.getOnlinePlayers()){
                            String subTitle = p + "が倒された";
                            onlinePlayers.sendTitle(ChatColor.RED + "赤チームの勝利！", subTitle, 10, 10, 10);
                            onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.BLOCK_ANVIL_HIT, 10, 1);
                            onlinePlayers.setGameMode(GameMode.ADVENTURE);
                            onlinePlayers.sendMessage("赤チームの勝利");
                            onlinePlayers.sendMessage(ChatColor.GRAY + "[result]" + ChatColor.WHITE + "経過時間は" + resultMinute + "分" + resultSecond + "秒でした。");
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void respawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        if (playing){
            if (Team.redTeam.contains(player.getName())){
                String worldName = ConfigHandler.get().getString("攻城戦." + playingGame + ".settings" + ".world");
                World world = Bukkit.getWorld(worldName);
                player.sendMessage(worldName);
                double redX = ConfigHandler.get().getDouble("攻城戦." + playingGame + ".red" + ".X");
                double redY = ConfigHandler.get().getDouble("攻城戦." + playingGame + ".red" + ".Y");
                double redZ = ConfigHandler.get().getDouble("攻城戦." + playingGame + ".red" + ".Z");
                Location redLocation = new Location(world, redX, redY, redZ);
                player.teleport(redLocation);
                e.setRespawnLocation(redLocation);
            }else if(Team.blueTeam.contains(player.getName())){
                String worldName = ConfigHandler.get().getString("攻城戦." + playingGame + ".settings" + ".world");
                World world = Bukkit.getWorld(worldName);
                player.sendMessage(worldName);
                double blueX = ConfigHandler.get().getDouble("攻城戦." + playingGame + ".blue" + ".X");
                double blueY = ConfigHandler.get().getDouble("攻城戦." + playingGame + ".blue" + ".Y");
                double blueZ = ConfigHandler.get().getDouble("攻城戦." + playingGame + ".blue" + ".Z");
                Location blueLocation = new Location(world, blueX, blueY, blueZ);
                player.teleport(blueLocation);
                e.setRespawnLocation(blueLocation);
            }
        }
    }
}
