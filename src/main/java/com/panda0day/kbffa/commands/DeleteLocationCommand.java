package com.panda0day.kbffa.commands;

import com.panda0day.kbffa.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteLocationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return false;
        }

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /deletelocation <name>");
            return true;
        }

        String locationName = args[0];
        if (!LocationManager.doesLocationExist(locationName)) {
            player.sendMessage(ChatColor.RED + "The location '" + locationName + "' does not exist.");
            return true;
        }

        boolean deleted = LocationManager.deleteLocation(locationName);
        if (deleted) {
            player.sendMessage(ChatColor.GREEN + "Location '" + locationName + "' deleted.");
        }

        return true;
    }
}
