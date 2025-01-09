package com.panda0day.kbffa.commands;

import com.panda0day.kbffa.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class LocationsCommand implements CommandExecutor {
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

        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /locations");
            return true;
        }

        player.sendMessage(ChatColor.GREEN + "Available locations:");

        var locations = LocationManager.getLocations();
        locations.forEach((s, location) -> {
            player.sendMessage(ChatColor.GREEN + "- " + ChatColor.WHITE + s);
        });

        return true;
    }
}
