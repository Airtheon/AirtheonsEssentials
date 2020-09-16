package me.airtheon.airtheonsessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommand {

    // Each command should have a method that runs the command.
    boolean run(CommandSender sender, Command command, String[] args);
}
