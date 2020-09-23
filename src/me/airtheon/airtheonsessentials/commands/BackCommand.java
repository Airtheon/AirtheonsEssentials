package me.airtheon.airtheonsessentials.commands;

import me.airtheon.airtheonsessentials.AirtheonsEssentials;
import me.airtheon.airtheonsessentials.persistent.LocationPersistence;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements ICommand {

    private final LocationPersistence backPersistence;
    private final PlayerTeleport playerTeleport;

    public BackCommand(AirtheonsEssentials ase){
        this.backPersistence = new LocationPersistence(ase, "back");
        this.playerTeleport = new PlayerTeleport(ase);
    }

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player){
            // player
            Player player = (Player) sender;
            if (player.hasPermission("ase.back")){
                // Make sure to get the location before the teleport.
                Location location  = this.backPersistence.get(player);
                if(location == null){
                    player.sendMessage(ChatColor.RED + "No location to teleport back to.");
                    return false;
                }
                this.playerTeleport.teleport(player, location);
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
