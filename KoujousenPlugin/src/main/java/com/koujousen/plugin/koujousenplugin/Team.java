package com.koujousen.plugin.koujousenplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    public static List<String> redTeam = new ArrayList<String>();
    public static List<String> blueTeam = new ArrayList<String>();

    public static void addToTeam(TeamType type, Player player){
        switch (type){
            case RED:
                redTeam.add(player.getName());
                player.sendMessage(ChatColor.GREEN + "あなたは" + ChatColor.RED + "赤" + ChatColor.GREEN + "チームです");
                blueTeam.remove(player.getName());
                break;
            case BLUE:
                blueTeam.add(player.getName());
                player.sendMessage(ChatColor.GREEN + "あなたは" + ChatColor.BLUE + "青" + ChatColor.GREEN + "チームです");
                redTeam.remove(player.getName());
                break;
        }
    }
    public static void removeFromTeam(Player player){
        redTeam.remove(player.getName());
        blueTeam.remove(player.getName());
    }
}
