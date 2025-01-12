package com.panda0day.kbffa.listeners;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.managers.*;
import com.panda0day.kbffa.stats.Stats;
import com.panda0day.kbffa.tickets.Tickets;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerConnection implements Listener {

    @EventHandler
    public void onPlayerConnection(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(Main.getMainConfig().getPrefix() + ChatColor.GREEN + player.getName() + " §7has joined the server.");

        Location location = LocationManager.getLocation("Spawn");
        if (location != null) {
            if(!WorldManager.isWorldLoaded(location.getWorld().getName())) {
                WorldManager.loadWorld(location.getWorld().getName());
            }

            player.getInventory().clear();
            player.teleport(location);
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

            player.getInventory().addItem(
                    new ItemManager(Material.STICK)
                            .setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "OP Stick")
                            .addEnchantment(Enchantment.UNBREAKING, 10)
                            .addEnchantment(Enchantment.KNOCKBACK, 2)
                            .setAmount(1)
                            .create()
            );

            Stats playerStats = StatsManager.getStats(player.getUniqueId());
            if (playerStats != null) {
                StatsManager.createStats(player.getUniqueId());
            }

            int coins = CoinsManager.getCoins(player.getUniqueId());
            if (coins <= -1) {
                CoinsManager.createCoins(player.getUniqueId());
            }

            Tickets tickets = TicketManager.getPlayerTickets(player);
            if (tickets.getTickets() <= -1) {
                TicketManager.createTickets(player.getUniqueId());
            }
        }
    }
}
