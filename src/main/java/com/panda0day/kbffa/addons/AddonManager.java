package com.panda0day.kbffa.addons;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.managers.CoinsManager;
import com.panda0day.kbffa.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
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
        this.addons.add(new KnockbackTenAddon());

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

                            if (Objects.equals(addon.getAddonName(), AddonNames.KNOCKBACK_TEN.getDisplayName())) {
                                player.getInventory().clear();

                                player.getInventory().addItem(
                                        new ItemManager(Material.STICK)
                                                .setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "OP Stick")
                                                .addEnchantment(Enchantment.UNBREAKING, 10)
                                                .addEnchantment(Enchantment.KNOCKBACK, 2)
                                                .setAmount(1)
                                                .create()
                                );
                            }
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

        int coins = CoinsManager.getCoins(player.getUniqueId());

        switch (clickedItemMeta.getDisplayName()) {
            case "§6§lDouble Jump":
                if (hasActiveAddon(player)) {
                    player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "You already have an Addon active!");
                    return;
                }

                if (isAddonExpired(player)) {
                    removePlayer(player);
                }

                DoubleJumpAddon doubleJumpAddon = new DoubleJumpAddon();
                if (coins >= doubleJumpAddon.getAddonPrice()) {
                    CoinsManager.removeCoins(player.getUniqueId(), doubleJumpAddon.getAddonPrice());
                    player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.GREEN + "You have bought the Addon " + ChatColor.GOLD + doubleJumpAddon.getAddonName());
                    addPlayer(player, doubleJumpAddon);
                    doubleJumpAddon.allowFlight(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                }
                break;
            case "§6§lKnockback 10":
                if (hasActiveAddon(player)) {
                    player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.RED + "You already have an Addon active!");
                    return;
                }

                if (isAddonExpired(player)) {
                    removePlayer(player);
                }

                KnockbackTenAddon kbTenAddon = new KnockbackTenAddon();
                if (coins >= kbTenAddon.getAddonPrice()) {
                    CoinsManager.removeCoins(player.getUniqueId(), kbTenAddon.getAddonPrice());
                    player.sendMessage(Main.getMainConfig().getPrefix() + ChatColor.GREEN + "You have bought the Addon " + ChatColor.GOLD + kbTenAddon.getAddonName());
                    addPlayer(player, kbTenAddon);
                    kbTenAddon.giveKnockBackTen(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                }
                break;
            default: break;
        }
    }
}
