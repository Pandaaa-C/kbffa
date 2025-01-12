package com.panda0day.kbffa.addons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class AddonManager {
    private List<Addon> addons = new ArrayList<>();

    private final int ADDON_SHOP_INVENTORY_COLUMNS = 9;
    private final int ADDON_SHOP_INVENTORY_ROWS = 5;

    public AddonManager() {
        this.addons.add(new DoubleJumpAddon());
    }

    public void openAddonShopInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, ADDON_SHOP_INVENTORY_COLUMNS * ADDON_SHOP_INVENTORY_ROWS, "Addon Shop");

        addons.forEach(addon -> {
            inventory.addItem(addon.getAddonMaterial());
        });

        player.openInventory(inventory);
    }
}
