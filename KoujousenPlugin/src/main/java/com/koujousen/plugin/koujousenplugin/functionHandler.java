package com.koujousen.plugin.koujousenplugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.koujousen.plugin.koujousenplugin.Koujousenplugin.blueAlive;
import static com.koujousen.plugin.koujousenplugin.Koujousenplugin.redAlive;

public class functionHandler implements Listener {
    public static boolean playing = false;
    public static int timersecond = 0;
    public static int timerminute = 0;
    public static String playingGame;

    public static void showActionBar(){
        for (Player player : Bukkit.getOnlinePlayers()){
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
                @Override
                public void run() {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("(" + Bukkit.getOnlinePlayers().size() + "人が参加中 " + "赤チーム:" + redAlive + " 青チーム:" + blueAlive + " 経過時間:" + timerminute + "分" + timersecond + "秒" + ")"));
                }
            }, 0L, 0L);
        }
    }

    public static void countdown(Player player){
        Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
            @Override
            public void run() {
                player.sendTitle("5", "", 1, 7, 1);
            }
        }, 20L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
            @Override
            public void run() {
                player.sendTitle("4", "", 1, 7, 1);
            }
        }, 40L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
            @Override
            public void run() {
                player.sendTitle("3", "", 1, 7, 1);
            }
        }, 60L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
            @Override
            public void run() {
                player.sendTitle("2", "", 1, 7, 1);
            }
        }, 80L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
            @Override
            public void run() {
                player.sendTitle("1", "", 1, 7, 1);
            }
        }, 100L);
    }

    public static void start(String name, CommandSender sender){
        ItemStack defenderitem1 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot1");
        ItemStack defenderitem2 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot2");
        ItemStack defenderitem3 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot3");
        ItemStack defenderitem4 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot4");
        ItemStack defenderitem5 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot5");
        ItemStack defenderitem6 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot6");
        ItemStack defenderitem7 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot7");
        ItemStack defenderitem8 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot8");
        ItemStack defenderitem9 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".defenceslot9");
        ItemStack attackeritem1 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot1");
        ItemStack attackeritem2 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot2");
        ItemStack attackeritem3 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot3");
        ItemStack attackeritem4 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot4");
        ItemStack attackeritem5 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot5");
        ItemStack attackeritem6 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot6");
        ItemStack attackeritem7 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot7");
        ItemStack attackeritem8 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot8");
        ItemStack attackeritem9 = ConfigHandler.get().getItemStack("攻城戦." + name + ".settings" + ".attackslot9");
        ItemStack bluechestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bluechestplatemeta = (LeatherArmorMeta) bluechestplate.getItemMeta();
        bluechestplatemeta.setColor(Color.BLUE);
        bluechestplatemeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        bluechestplatemeta.setUnbreakable(true);
        bluechestplate.setItemMeta(bluechestplatemeta);
        ItemStack redchestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta redchestplatemeta = (LeatherArmorMeta) redchestplate.getItemMeta();
        redchestplatemeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
        redchestplatemeta.setUnbreakable(true);
        redchestplatemeta.setColor(Color.RED);
        redchestplate.setItemMeta(redchestplatemeta);
        int timersecond = 0;
        int timerminute = 0;

        try{
            if (ConfigHandler.get().contains("攻城戦." + name)){
                playingGame = name;
                if (ConfigHandler.get().get("攻城戦." + name + ".red" + ".leader") == "" || ConfigHandler.get().get("攻城戦." + name + ".blue" + ".leader") == ""){
                sender.sendMessage(ChatColor.RED + "大将を設定してください");
                }else if(!(Team.redTeam.contains(ConfigHandler.get().getString("攻城戦." + name + ".red" + ".leader")) || Team.blueTeam.contains(ConfigHandler.get().getString("攻城戦." + name + ".blue" + ".leader")))){
                    sender.sendMessage(ChatColor.RED + "大将が自身のチームにいません");
                }
                else {
                    World world = Bukkit.getWorld("world");
                    double redX = ConfigHandler.get().getDouble("攻城戦." + name + ".red" + ".X");
                    double redY = ConfigHandler.get().getDouble("攻城戦." + name + ".red" + ".Y");
                    double redZ = ConfigHandler.get().getDouble("攻城戦." + name + ".red" + ".Z");
                    Location redlocation = new Location(world, redX, redY, redZ);
                    double blueX = ConfigHandler.get().getDouble("攻城戦." + name + ".blue" + ".X");
                    double blueY = ConfigHandler.get().getDouble("攻城戦." + name + ".blue" + ".Y");
                    double blueZ = ConfigHandler.get().getDouble("攻城戦." + name + ".blue" + ".Z");
                    Location bluelocation = new Location(world, blueX, blueY, blueZ);
                    if (ConfigHandler.get().get("攻城戦." + name + ".red" + ".side") == "attack") {
                        //Red Attack
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (Team.redTeam.contains(player.getName())) {
                                countdown(player);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
                                    @Override
                                    public void run() {
                                        player.sendTitle("開始！", "", 1, 7, 1);
                                        player.setHealth(20);
                                        player.setFoodLevel(20);
                                        player.teleport(redlocation);
                                        player.getInventory().clear();
                                        player.setGameMode(GameMode.ADVENTURE);
                                        player.getInventory().setItem(0, attackeritem1);
                                        player.getInventory().setItem(1, attackeritem2);
                                        player.getInventory().setItem(2, attackeritem3);
                                        player.getInventory().setItem(3, attackeritem4);
                                        player.getInventory().setItem(4, attackeritem5);
                                        player.getInventory().setItem(5, attackeritem6);
                                        player.getInventory().setItem(6, attackeritem7);
                                        player.getInventory().setItem(7, attackeritem8);
                                        player.getInventory().setItem(8, attackeritem9);
                                        player.getInventory().setChestplate(redchestplate);
                                        playingGame = name;
                                        playing = true;

                                    }
                                            }, 120L);
                            }else{
                                //Blue Defend
                                countdown(player);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
                                    @Override
                                    public void run() {
                                        player.sendTitle("開始！", "", 1, 7, 1);
                                        player.getInventory().clear();
                                        player.setGameMode(GameMode.ADVENTURE);
                                        player.setHealth(20);
                                        player.setFoodLevel(20);
                                        player.getInventory().clear();
                                        player.getInventory().setItem(0, defenderitem1);
                                        player.getInventory().setItem(1, defenderitem2);
                                        player.getInventory().setItem(2, defenderitem3);
                                        player.getInventory().setItem(3, defenderitem4);
                                        player.getInventory().setItem(4, defenderitem5);
                                        player.getInventory().setItem(5, defenderitem6);
                                        player.getInventory().setItem(6, defenderitem7);
                                        player.getInventory().setItem(7, defenderitem8);
                                        player.getInventory().setItem(8, defenderitem9);
                                        player.getInventory().setChestplate(bluechestplate);
                                        playingGame = name;
                                        playing = true;

                                    }
                                }, 120L);
                            }
                        }
                    } else {
                        //Blue Attack
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (Team.blueTeam.contains(player.getName())) {
                                countdown(player);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
                                    @Override
                                    public void run() {
                                        player.sendTitle("開始！", "", 1, 7, 1);
                                        player.teleport(bluelocation);
                                        player.setHealth(20);
                                        player.setFoodLevel(20);
                                        player.getInventory().clear();
                                        player.setGameMode(GameMode.ADVENTURE);
                                        player.getInventory().setItem(0, attackeritem1);
                                        player.getInventory().setItem(1, attackeritem2);
                                        player.getInventory().setItem(2, attackeritem3);
                                        player.getInventory().setItem(3, attackeritem4);
                                        player.getInventory().setItem(4, attackeritem5);
                                        player.getInventory().setItem(5, attackeritem6);
                                        player.getInventory().setItem(6, attackeritem7);
                                        player.getInventory().setItem(7, attackeritem8);
                                        player.getInventory().setItem(8, attackeritem9);
                                        player.getInventory().setChestplate(bluechestplate);
                                        playingGame = name;
                                        playing = true;

                                    }
                                }, 120L);
                            }else{
                                //Red Defend
                                countdown(player);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Koujousenplugin.getPlugin(Koujousenplugin.class), new Runnable() {
                                    @Override
                                    public void run() {
                                        player.sendTitle("開始！", "", 1, 7, 1);
                                        player.setGameMode(GameMode.ADVENTURE);
                                        player.setFoodLevel(20);
                                        player.setHealth(20);
                                        player.getInventory().clear();
                                        player.getInventory().setItem(0, defenderitem1);
                                        player.getInventory().setItem(1, defenderitem2);
                                        player.getInventory().setItem(2, defenderitem3);
                                        player.getInventory().setItem(3, defenderitem4);
                                        player.getInventory().setItem(4, defenderitem5);
                                        player.getInventory().setItem(5, defenderitem6);
                                        player.getInventory().setItem(6, defenderitem7);
                                        player.getInventory().setItem(7, defenderitem8);
                                        player.getInventory().setItem(8, defenderitem9);
                                        player.getInventory().setChestplate(redchestplate);
                                        playingGame = name;
                                        playing = true;
                                    }
                                }, 120L);
                            }
                        }
                    }

                }
            }else{
                sender.sendMessage(ChatColor.RED + "その攻城戦は存在しません");
            }
        }catch (Exception e){
            sender.sendMessage(ChatColor.RED + "エラーが発生しましたもう一度試してください");
        }
    }
}
