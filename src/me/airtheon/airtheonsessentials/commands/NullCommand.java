package me.airtheon.airtheonsessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class NullCommand implements ICommand {

    // Always returns false to enable switch statement in main class.

    @Override
    public boolean run(CommandSender sender, Command command, String[] args) {
        return false;
    }
}
