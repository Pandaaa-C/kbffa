package com.panda0day.kbffa.listeners;

import com.panda0day.kbffa.managers.ItemManager;
import com.panda0day.kbffa.managers.LocationManager;
import com.panda0day.kbffa.managers.WorldManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerRespawn implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        Location location = LocationManager.getLocation("Spawn");
        if (location != null) {
            event.setRespawnLocation(location);

            player.teleport(location);
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);

            Map<Integer, Enchantment> enchantments = new HashMap<>();
            enchantments.put(10, Enchantment.UNBREAKING);
            enchantments.put(2, Enchantment.KNOCKBACK);

            player.getInventory().addItem(ItemManager.createItemWithEnchantments(Material.STICK, 1, "OP-Stick", null, enchantments));
        }
    }
}
