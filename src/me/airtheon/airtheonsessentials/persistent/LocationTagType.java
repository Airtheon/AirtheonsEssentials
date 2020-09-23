package me.airtheon.airtheonsessentials.persistent;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("NullableProblems")
public class LocationTagType implements PersistentDataType<PersistentDataContainer, Location> {
    // Implement a custom persistent datatype for locations, to store homes on player entities.

    // this is the one from the Spigot JavaDoc examples
    private static final UUIDTagType UUID_TAG_TYPE = new UUIDTagType();

    private final JavaPlugin javaPlugin;

    public LocationTagType(JavaPlugin javaPlugin){
        this.javaPlugin = javaPlugin;
    }

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public Class<Location> getComplexType() {
        return Location.class;
    }

    @Override
    public PersistentDataContainer toPrimitive(Location location, PersistentDataAdapterContext persistentDataAdapterContext) {
        // Create data container
        PersistentDataContainer persistentDataContainer = persistentDataAdapterContext.newPersistentDataContainer();

        // Convert location to the persistent data container
        persistentDataContainer.set(key("world"), UUID_TAG_TYPE, location.getWorld().getUID());
        persistentDataContainer.set(key("x"), PersistentDataType.DOUBLE, location.getX());
        persistentDataContainer.set(key("y"), PersistentDataType.DOUBLE, location.getY());
        persistentDataContainer.set(key("z"), PersistentDataType.DOUBLE, location.getZ());
        persistentDataContainer.set(key("yaw"), PersistentDataType.FLOAT, location.getYaw());
        persistentDataContainer.set(key("pitch"), PersistentDataType.FLOAT, location.getPitch());

        return persistentDataContainer;
    }

    @Override
    public Location fromPrimitive(PersistentDataContainer persistentDataContainer, PersistentDataAdapterContext persistentDataAdapterContext) {
        // Get variable needed to create location from the persistent data container.
        World world = this.javaPlugin.getServer().getWorld(persistentDataContainer.get(key("world"), UUID_TAG_TYPE));
        double x = persistentDataContainer.get(key("x"), PersistentDataType.DOUBLE);
        double y = persistentDataContainer.get(key("y"), PersistentDataType.DOUBLE);
        double z = persistentDataContainer.get(key("z"), PersistentDataType.DOUBLE);
        float yaw = persistentDataContainer.get(key("yaw"), PersistentDataType.FLOAT);
        float pitch = persistentDataContainer.get(key("pitch"), PersistentDataType.FLOAT);
        //TODO how to handle NPEs?
        return new Location(world, x, y, z, yaw, pitch);
    }

    private NamespacedKey key(String key){
        return new NamespacedKey(this.javaPlugin, key);
    }

}
