package me.airtheon.airtheonsessentials.listeners;

import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SimpleSleepListener implements Listener {

    public void onPlayerBedEnter(PlayerBedEnterEvent event){
        // Get the current world of the player that is sleeping.
        World world = event.getPlayer().getWorld();
        // Make it day again.
        world.setWeatherDuration(0);
        world.setTime(0L);
    }

}
