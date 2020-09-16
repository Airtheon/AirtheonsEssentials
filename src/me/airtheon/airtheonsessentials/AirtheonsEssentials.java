package me.airtheon.airtheonsessentials;

import me.airtheon.airtheonsessentials.commands.*;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AirtheonsEssentials extends JavaPlugin{

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
    }

    @Override
    public void onDisable() {
        // shutdown
        // reloads
        // plugin reloads
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
