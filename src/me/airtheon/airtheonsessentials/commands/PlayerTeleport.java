package me.airtheon.airtheonsessentials.commands;

import com.sun.istack.internal.NotNull;
import me.airtheon.airtheonsessentials.AirtheonsEssentials;
import me.airtheon.airtheonsessentials.persistent.LocationPersistence;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerTeleport {
    // Use this class to teleport players, to make sure the previous location is saved for using /back.

    private final LocationPersistence backLocationPersistence;

    public PlayerTeleport(AirtheonsEssentials ase){
        this.backLocationPersistence = new LocationPersistence(ase, "back");
    }

    public void teleport(@NotNull Player player, @NotNull Location location){
        // Remember the current location as the location to return to using /back.
        Location prevLoc = player.getLocation();
        // Only update the new /back location when teleporting the player is successful.
        // It should always be successful but nevertheless a check like this can't hurt.
        if (player.teleport(location)) {
            this.backLocationPersistence.set(player, prevLoc);
        } else {
            player.sendMessage(ChatColor.RED + "Invalid teleport location: " + location.toString());
        }
    }
}
