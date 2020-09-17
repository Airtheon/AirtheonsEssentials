package me.airtheon.airtheonsessentials;

import me.airtheon.airtheonsessentials.commands.*;
import me.airtheon.airtheonsessentials.listeners.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class AirtheonsEssentials extends JavaPlugin{

    // reloads can happen with players already joined and doing things!

    @Override
    public void onEnable() {
        // startup
        // reloads
        // plugin reloads
        getLogger().info("Thank you for using Airtheon's Essentials version " +
                this.getDescription().getVersion() + "!");
        getLogger().info("I hope you find it useful!");

        this.saveDefaultConfig();
        this.reloadConfig();

        // Only add the sleep listener if it is going to be used.
        if (this.getConfig().getBoolean("use-sleep-checker")){
            this.getServer().getPluginManager().registerEvents(new SleepListener(this), this);
        }

    }

    @Override
    public void onDisable() {
        // shutdown
        // reloads
        // plugin reloads
        // Unregister the listeners on disabling the plugin, just in case.
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Create variable to be filled by the switch, NullCommand always returns False.
        ICommand cmd = new NullCommand();
        switch (label.toLowerCase()){
            case "hello":
                cmd = new HelloCommand();
                break;
            case "spawn":
                cmd = new SpawnCommand(this);
                break;
        }
        return cmd.run(sender, command, args);
    }
}
