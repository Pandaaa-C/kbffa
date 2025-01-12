package com.panda0day.kbffa.addons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.*;

public class AddonManager {
    private final List<Addon> addons = new ArrayList<>();
    public Map<Player, Addon> playerAddons = new HashMap<>();

    private final int ADDON_SHOP_INVENTORY_COLUMNS = 9;
    private final int ADDON_SHOP_INVENTORY_ROWS = 5;

    public AddonManager() {
        this.addons.add(new DoubleJumpAddon());
    }

    public void addPlayer(Player player, Addon addon) {
        this.playerAddons.put(player, addon);
    }

    public void removePlayer(Player player) {
        this.playerAddons.remove(player);
    }

    public boolean hasActiveAddon(Player player) {
        return this.playerAddons.containsKey(player);
    }

    public boolean isAddonExpired(Player player) {
        Addon addon = this.playerAddons.get(player);
        if (addon == null) {
            return true;
        }
        return new Date().after(addon.getAddonEnd());
    }

    public void openAddonShopInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, ADDON_SHOP_INVENTORY_COLUMNS * ADDON_SHOP_INVENTORY_ROWS, "Addon Shop");

        addons.forEach(addon -> {
            inventory.addItem(addon.getAddonMaterial());
        });

        player.openInventory(inventory);
    }

    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView inventoryView = event.getView();
        if (!inventoryView.getTitle().equalsIgnoreCase("Addon Shop")) return;

        event.setCancelled(true);
    }
}
