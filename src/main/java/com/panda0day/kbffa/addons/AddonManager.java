package com.panda0day.kbffa.addons;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.managers.CoinsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.util.*;

public class AddonManager {
    private final List<Addon> addons = new ArrayList<>();
    public Map<Player, Addon> playerAddons = new HashMap<>();

    private final int ADDON_SHOP_INVENTORY_COLUMNS = 9;
    private final int ADDON_SHOP_INVENTORY_ROWS = 5;

    public AddonManager() {
        this.addons.add(new DoubleJumpAddon());

        Main.getInstance().getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (hasActiveAddon(player)) {
                        if (isAddonExpired(player)) {
                            removePlayer(player);

                            Addon addon = getAddon(player);
                            if (addon == null) return;

                            player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "Your Addon " + ChatColor.GOLD + addon.getAddonName() + " has expired!");
                        }
                    }
                });
            }
        }, 0L, 20L * 5);
    }

    public void addPlayer(Player player, Addon addon) {
        this.playerAddons.put(player, addon);
    }

    public void removePlayer(Player player) {
        this.playerAddons.remove(player);
    }

    public boolean addonActive(Player player, String addonName) {
        return this.playerAddons.containsKey(player) && this.playerAddons.get(player).getAddonName().equals(addonName);
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

    public Addon getAddon(Player player) {
        return this.playerAddons.get(player);
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

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return;

        ItemMeta clickedItemMeta = clickedItem.getItemMeta();
        if (clickedItemMeta == null) return;

        switch (clickedItemMeta.getDisplayName()) {
            case "§6§lDouble Jump":
                if (hasActiveAddon(player)) {
                    player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "You already have an Addon active!");
                    return;
                }

                if (isAddonExpired(player)) {
                    removePlayer(player);
                }

                DoubleJumpAddon addon = new DoubleJumpAddon();
                int coins = CoinsManager.getCoins(player.getUniqueId());
                if (coins >= addon.getAddonPrice()) {
                    CoinsManager.removeCoins(player.getUniqueId(), addon.getAddonPrice());
                    player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.GREEN + "You have bought the Addon " + ChatColor.GOLD + addon.getAddonName());
                    addPlayer(player, addon);
                    addon.allowFlight(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                }
                break;
            default: break;
        }
    }
}
