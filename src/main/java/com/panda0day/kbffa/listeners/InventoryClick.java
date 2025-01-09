package com.panda0day.kbffa.listeners;

import com.panda0day.kbffa.managers.TicketManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        TicketManager.onInventoryClick(event);
    }
}
