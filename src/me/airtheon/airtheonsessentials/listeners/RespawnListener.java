package me.airtheon.airtheonsessentials.listeners;

import me.airtheon.airtheonsessentials.AirtheonsEssentials;
import me.airtheon.airtheonsessentials.persistent.LocationPersistence;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    private final LocationPersistence backPersistence;

    public RespawnListener(AirtheonsEssentials ase){
        this.backPersistence = new LocationPersistence(ase, "back");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerRespawnEvent(PlayerRespawnEvent event){
        this.backPersistence.set(event.getPlayer(), event.getPlayer().getLocation());
    }
}
