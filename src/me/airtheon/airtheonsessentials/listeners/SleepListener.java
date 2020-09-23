package me.airtheon.airtheonsessentials.listeners;

import me.airtheon.airtheonsessentials.AirtheonsEssentials;
import me.airtheon.airtheonsessentials.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class SleepListener implements Listener {
    AirtheonsEssentials ase;
    Hashtable<String, List<Player>> sleepers;
    Float sleep_percentage;
    Messenger messenger;

    public SleepListener(AirtheonsEssentials ase) {
        // Initialize plugin
        this.ase = ase;
        // Initialize hashtable with 0s for each world as there are no sleepers on a server restart.
        this.sleepers = new Hashtable<>();
        List<World> worlds = this.ase.getServer().getWorlds();
        for (World w : worlds) {
            this.sleepers.put(w.getName(), new ArrayList<>());
        }
        // Initialize sleep-percentage variable from the config file.
        this.sleep_percentage = this.ase.getConfig().getInt("sleep-percentile") / 100f;
        // Initialize messenger
        this.messenger = new Messenger();
    }

    // Executes first, to give players some leeway whilst going to bed.
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerBedEnter(PlayerBedEnterEvent event){
        // Only count a sleeping player if they are allowed to sleep.
        if(event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK){
            event.isCancelled();
            return;
        }
        // Get the current world of the player that is sleeping.
        World world = event.getPlayer().getWorld();
        // Add player to the list of sleepers for this world.
        sleepers.get(world.getName()).add(event.getPlayer());

        // Wait 5 seconds before proceeding to make sure the player sleeps.
        Bukkit.getServer().getScheduler().runTaskLater(this.ase, () -> {
            if (ase.getConfig().getBoolean("use-percentile-sleep")){
                checkSleepers(world);
            } else {
                // One person sleeps so it becomes day again.
                makeDay(world);
                // Reset the sleepers counter for this world.
                sleepers.put(world.getName(), new ArrayList<>());
            }
        }, 100L);
    }

    // Check if there are enough people asleep to make it day again.
    private void checkSleepers(World world){
        int sleepersNeeded = (int) Math.ceil(world.getPlayers().size() * this.sleep_percentage);
        /*event.getPlayer().sendMessage(ChatColor.BLUE + "Currently " +
                ChatColor.GREEN + world.getPlayers().size() +
                ChatColor.BLUE + " players are in this world and " +
                ChatColor.GREEN + sleepersNeeded +
                ChatColor.BLUE + " players are needed to make this world day again.");*/

        List<Player> sleepersCurrent = this.sleepers.get(world.getName());
        if( sleepersCurrent.size() >= sleepersNeeded){
            // If enough people sleep it becomes day again.
            makeDay(world);
            // Reset the sleepers counter for this world.
            this.sleepers.put(world.getName(), new ArrayList<>());
        } else {
            // send a message to all sleeping people of how many people are still needed.
            messenger.sendMessageToAll(sleepersCurrent, ChatColor.BLUE + "Currently " +
                    ChatColor.GREEN + sleepersCurrent.size() +
                    ChatColor.BLUE + " players are sleeping, " +
                    ChatColor.GREEN + (sleepersNeeded - sleepersCurrent.size()) +
                    ChatColor.BLUE + " more are needed.");
        }
    }

    private void makeDay(World world) {
        // Send a nice message to all sleeping players
        messenger.sendMessageToAll(sleepers.get(world.getName()), ChatColor.YELLOW + "Rise and shine!");
        // Set daytime to 0
        world.setTime(0L);
        // Clear weather and make it clear for the next 5 minutes.
        world.setThundering(false);
        world.setStorm(false);
        // Clear weather duration between 12000 and 180000 ticks
        Random random = new Random();
        int min = 12000;
        int max = 180000;
        world.setWeatherDuration(random.nextInt(max - min) + min);
    }

    // Leaving should be higher priority as it should immediately update the state.
    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerBedLeave(PlayerBedLeaveEvent event){
        // Get the current world of the player that is sleeping.
        Player player = event.getPlayer();
        this.sleepers.get(player.getWorld().getName()).remove(player);
    }

}
