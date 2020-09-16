package me.airtheon.airtheonsessentials.commands;

import me.airtheon.airtheonsessentials.AirtheonsEssentials;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements ICommand {

    private World main_world;
    private final Boolean use_main;

    public SpawnCommand(AirtheonsEssentials airtheonsEssentials) {
        // Set the correct usage of the spawn command according to config.
        this.use_main = airtheonsEssentials.getConfig().getBoolean("use-main");

        String world = airtheonsEssentials.getConfig().getString("main-world");
        if(world != null) {
            try {
                this.main_world = airtheonsEssentials.getServer().getWorld(world);
            } catch (Exception e) {
                airtheonsEssentials.getLogger().warning("Main world in config could not be found, using world 0 instead");
                this.main_world = airtheonsEssentials.getServer().getWorlds().get(0);
            }
        }
        else {
            this.main_world = airtheonsEssentials.getServer().getWorlds().get(0);
        }
    }

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player){
            // player
            Player player = (Player) sender;
            if (player.hasPermission("ase.spawn")){
                if(this.use_main){
                    player.teleport(this.main_world.getSpawnLocation());
                }
                else {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                return true;
            }
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        else {
            // console
            sender.sendMessage("This command is player-only.");
        }
        return true;
    }
}
