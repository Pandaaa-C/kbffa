package com.panda0day.kbffa.commands;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.managers.StatsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearStatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player player)) {
            System.out.println("You must be a player to execute this command!");
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Main.getMainConfig().getPrefix() + "Usage: /clearstats <player>");
            return false;
        }

        StatsManager.clearStats(player.getUniqueId());
        player.sendMessage(Main.getMainConfig().getPrefix() + "Your stats have been reset");

        return true;
    }
}
