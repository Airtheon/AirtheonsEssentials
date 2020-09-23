package me.airtheon.airtheonsessentials.persistent;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationPersistence {

    private final NamespacedKey locationKey;
    private final LocationTagType locationTagType;

    public LocationPersistence(JavaPlugin javaPlugin, String locationName){
        this.locationKey = new NamespacedKey(javaPlugin, locationName);
        this.locationTagType = new LocationTagType(javaPlugin);
    }

    public void set(Player player, Location location){
        PersistentDataContainer customPlayerTagContainer = player.getPersistentDataContainer();
        customPlayerTagContainer.set(locationKey, locationTagType, player.getLocation());
    }

    public Location get(Player player) {
        PersistentDataContainer customPlayerTagContainer = player.getPersistentDataContainer();
        return customPlayerTagContainer.get(locationKey, locationTagType);
    }
}
