package me.airtheon.airtheonsessentials.commands;

import me.airtheon.airtheonsessentials.AirtheonsEssentials;
import me.airtheon.airtheonsessentials.persistent.LocationPersistence;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerTeleport {
    // Use this class to teleport players, to make sure the previous location is saved for using /back.

    private final LocationPersistence backLocationPersistence;

    public PlayerTeleport(AirtheonsEssentials ase){
        this.backLocationPersistence = new LocationPersistence(ase, "back");
    }

    public void teleport(Player player, Location location){
        // Set the current location as the location to return to using /back.
        this.backLocationPersistence.set(player, player.getLocation());
        // Teleport the player to the new location.
        player.teleport(location);
    }
}
