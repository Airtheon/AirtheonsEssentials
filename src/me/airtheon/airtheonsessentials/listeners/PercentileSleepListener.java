package me.airtheon.airtheonsessentials.listeners;

import me.airtheon.airtheonsessentials.AirtheonsEssentials;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.Hashtable;
import java.util.List;

public class PercentileSleepListener implements Listener {
    Hashtable<String, Integer> sleepers;
    Float sleep_percentage;

    public PercentileSleepListener(AirtheonsEssentials airtheonsEssentials) {
        // Initialize hashtable with 0s for each world as there are no sleepers on a server restart.
        this.sleepers = new Hashtable<String, Integer>();
        List<World> worlds = airtheonsEssentials.getServer().getWorlds();
        for (World w : worlds) {
            this.sleepers.put(w.getName(), 0);
        }
        // Initialize sleep-percentage variable from the config file.
        this.sleep_percentage = airtheonsEssentials.getConfig().getInt("sleep-percentage") / 100f;
    }

    // Executes first, to give players some leeway whilst going to bed.
    @EventHandler (priority = EventPriority.LOW)
    public void onPlayerBedEnter(PlayerBedEnterEvent event){
        // Get the current world of the player that is sleeping.
        World world = event.getPlayer().getWorld();
        // Add 1 to the number of sleepers for this world.
        this.sleepers.put(world.getName(), this.sleepers.get(world.getName()) + 1);
        // Check if there are enough people asleep to make it day again.
        int sleepersNeeded = (int) Math.ceil(world.getPlayers().size() * this.sleep_percentage);
        int sleepersCurrent = this.sleepers.get(world.getName());
        if( sleepersCurrent >= sleepersNeeded){
            // If enough people sleep it becomes day again.
            this.makeDay(world);
        } else {
            // give a message of how many people are still needed.
            event.getPlayer().sendMessage(ChatColor.BLUE + "Currently " +
                    ChatColor.GREEN + sleepersCurrent +
                    ChatColor.BLUE + " players are sleeping, " +
                    ChatColor.GREEN + (sleepersNeeded - sleepersCurrent) +
                    ChatColor.BLUE + " more are needed.");
        }
    }

    private void makeDay(World world) {
        world.setWeatherDuration(0);
        world.setTime(0L);
    }

    // Executes after, to give players entering beds some priority.
    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerBedLeave(PlayerBedLeaveEvent event){
        // Get the current world of the player that is sleeping.
        World world = event.getPlayer().getWorld();
        // Subtract 1 to the number of sleepers for this world.
        this.sleepers.put(world.getName(), this.sleepers.get(world.getName()) - 1);
        // Check to make sure that the number of sleepers never goes negative.
        if (this.sleepers.get(world.getName()) > 0) {
            this.sleepers.put(world.getName(), 0);
        }
    }
}
