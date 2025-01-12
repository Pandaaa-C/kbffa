package com.panda0day.kbffa.listeners;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.managers.TicketManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteract implements Listener {
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            Villager villager = (Villager) event.getRightClicked();
            if (villager.getName().equalsIgnoreCase("Ticket Shop")) {
                TicketManager.openTicketShopInventory(player);
            } else if (villager.getName().equalsIgnoreCase("Addon Shop")) {
                Main.getAddonManager().openAddonShopInventory(player);
            }
        }
    }
}
