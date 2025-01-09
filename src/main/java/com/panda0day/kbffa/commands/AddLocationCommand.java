package com.panda0day.kbffa.commands;

import com.panda0day.kbffa.managers.LocationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddLocationCommand implements CommandExecutor {
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
            player.sendMessage(ChatColor.RED + "Usage: /addlocation <player>");
            return true;
        }

        String locationName = args[0];
        if (LocationManager.doesLocationExist(locationName)) {
            player.sendMessage(ChatColor.RED + "Location " + locationName + " exists.");
            return true;
        }

        boolean created = LocationManager.addLocation(
                locationName,
                player.getWorld().getName(),
                player.getLocation().getX(),
                player.getLocation().getY(),
                player.getLocation().getZ(),
                player.getLocation().getYaw(),
                player.getLocation().getPitch()
        );

        if (created) {
            player.sendMessage(ChatColor.GREEN + "Location Created: " + ChatColor.YELLOW + locationName);
        }

        return true;
    }
}
