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
    private final PlayerTeleport playerTeleport;

    public SpawnCommand(AirtheonsEssentials ase) {
        // This is a player that is likely going to teleport so use the PlayerTeleport class to do it.
        this.playerTeleport = new PlayerTeleport(ase);

        // Set the correct usage of the spawn command according to config.
        this.use_main = ase.getConfig().getBoolean("use-main");

        String world = ase.getConfig().getString("main-world");
        if(world != null) {
            try {
                this.main_world = ase.getServer().getWorld(world);
            } catch (Exception e) {
                ase.getLogger().warning("Main world in config could not be found, using world 0 instead");
                this.main_world = ase.getServer().getWorlds().get(0);
            }
        }
        else {
            this.main_world = ase.getServer().getWorlds().get(0);
        }
    }

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player){
            // player
            Player player = (Player) sender;
            if (player.hasPermission("ase.spawn")){
                if(this.use_main){
                    this.playerTeleport.teleport(player, this.main_world.getSpawnLocation());
                }
                else {
                    this.playerTeleport.teleport(player, player.getWorld().getSpawnLocation());
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
