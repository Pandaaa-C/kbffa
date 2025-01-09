package com.panda0day.kbffa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDamage implements Listener {
    private static final HashMap<UUID, UUID> lastHitMap = new HashMap<>();

    @EventHandler
    public void onPlayerDamageByFall(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && event.getDamager() instanceof Player damager) {
            if (player.getLocation().getY() >= 63 && damager.getLocation().getY() >= 63) {
                event.setCancelled(true);
                return;
            }

            System.out.println(player.getName());
            System.out.println(damager.getName());
            lastHitMap.put(player.getUniqueId(), damager.getUniqueId());
        }
    }

    public static HashMap<UUID, UUID> getLastHitMap() {
        return lastHitMap;
    }

    public static void removeLastHitMap(UUID uuid) {
        lastHitMap.remove(uuid);
    }
}
