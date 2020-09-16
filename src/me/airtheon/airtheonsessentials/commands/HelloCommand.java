package me.airtheon.airtheonsessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelloCommand implements ICommand {
    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player){
            // player
            Player player = (Player) sender;
            if (player.hasPermission("ase.hello")) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "HI! this message is brought to you by Airtheon's Essentials");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1H&2a&3v&4e &5f&6u&7n&8!"));
                return true;
            }
            player.sendMessage(ChatColor.RED + "You do not have permission!");
        }
        else {
            // console
            sender.sendMessage("Hey console!");
        }
        return true;
    }
}
