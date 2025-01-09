package com.panda0day.kbffa.listeners;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.managers.CoinsManager;
import com.panda0day.kbffa.managers.StatsManager;
import com.panda0day.kbffa.stats.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;
import java.util.UUID;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            player.spigot().respawn();
        }, 1L);

        Stats playerStats = StatsManager.getStats(player.getUniqueId());
        playerStats.addDeaths();
        playerStats.update();

        if (PlayerDamage.getLastHitMap().containsKey(player.getUniqueId())) {
            UUID attackerId = PlayerDamage.getLastHitMap().get(player.getUniqueId());
            Player killer = Bukkit.getPlayer(attackerId);
            if (killer != null) {
                Stats killerStats = StatsManager.getStats(killer.getUniqueId());
                killerStats.addKills();
                killerStats.update();

                int coins = new Random().nextInt(25) + 1;

                killer.sendMessage(Main.getMainConfig().getPrefix() + "You have killed " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + "! ["  + ChatColor.GOLD + "+" +  coins + ChatColor.GRAY + "]");
                player.sendMessage(Main.getMainConfig().getPrefix() + "You have been killed by " + ChatColor.GOLD + killer.getName());

                CoinsManager.addCoins(killer.getUniqueId(), coins);

                PlayerDamage.getLastHitMap().remove(player.getUniqueId());
            }
        }
    }
}
