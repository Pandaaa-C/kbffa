package com.panda0day.kbffa.commands;

import com.panda0day.kbffa.managers.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportWorldCommand implements CommandExecutor {
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
            player.sendMessage(ChatColor.RED + "Usage: /teleportworld <world>");
            return true;
        }

        String worldName = args[0];
        if (!WorldManager.isWorldLoaded(worldName)) {
            WorldManager.loadWorld(worldName);
        }

        World world = WorldManager.getWorld(worldName);
        if (world == null) {
            player.sendMessage(ChatColor.RED + "World not found: " + worldName);
        }

        player.teleport(new Location(world, 0, 60, 0));

        return true;
    }
}
