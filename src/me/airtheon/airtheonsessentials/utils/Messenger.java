package me.airtheon.airtheonsessentials.utils;

import org.bukkit.entity.Player;

import java.util.List;

public class Messenger {

    // Send the same message to a list of players.
    public void sendMessageToAll(List<Player> players, String msg){
        for (Player player : players) {
            player.sendMessage(msg);
        }
    }
}
