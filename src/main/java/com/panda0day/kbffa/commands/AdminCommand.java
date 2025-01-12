package com.panda0day.kbffa.commands;

import com.panda0day.kbffa.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!player.isOp()) return false;

        Main.getDoubleJumpAddon().addPlayer(player);
        player.setAllowFlight(true);
        return true;
    }
}
