package com.panda0day.kbffa.managers;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.tickets.Tickets;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TicketManager {
    private static final int TICKET_SHOP_INVENTORY_COLUMNS = 9;
    private static final int TICKET_SHOP_INVENTORY_ROWS = 5;
    private static final int TICKET_SHOP_PRICE = 100;

    private static final int TICKET_INVENTORY_COLUMNS = 9;
    private static final int TICKET_INVENTORY_ROWS = 6;

    private static final HashMap<UUID, Integer> TICKET_OPENED_ENDER_CHESTS = new HashMap<>();

    public static void openTicketShopInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, TICKET_SHOP_INVENTORY_COLUMNS * TICKET_SHOP_INVENTORY_ROWS, "Ticket Shop");
        // TODO: If the other slots should be filled with an item, uncomment this
        /*for (int i = 0; i < (TICKET_SHOP_INVENTORY_COLUMNS * TICKET_SHOP_INVENTORY_ROWS); i++) {
            inventory.setItem(i,
                    new ItemManager(Material.WHITE_STAINED_GLASS_PANE)
                            .setDisplayName(ChatColor.RESET + "")
                            .setAmount(1)
                            .create()
            );
        }*/

        Tickets playerTickets = getPlayerTickets(player);
        String ticketsTitle = playerTickets.getTickets() <= 0 ? "§6Tickets (0)" : "§6Tickets (" + playerTickets.getTickets() + ")";
        inventory.setItem(21,
                new ItemManager(Material.PAPER)
                        .setDisplayName(ChatColor.GOLD + "Buy Ticket")
                        .setAmount(1)
                        .create()
        );
        inventory.setItem(23,
                new ItemManager(Material.CHEST_MINECART)
                        .setDisplayName(ticketsTitle)
                        .setAmount(1)
                        .create()
        );

        player.openInventory(inventory);
    }

    public static void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView inventoryView = event.getView();

        if (inventoryView.getTitle().equalsIgnoreCase("Ticket Shop")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) {
                return;
            }
            ItemMeta meta;

            switch (clickedItem.getType()){
                case PAPER:
                    meta = clickedItem.getItemMeta();
                    if (meta.getDisplayName().equalsIgnoreCase("§6Buy Ticket")) {
                        buyTicket(player);
                    }
                    break;
                case CHEST_MINECART:
                    meta = clickedItem.getItemMeta();
                    if (meta.getDisplayName().startsWith("§6Tickets")) {
                        useTicket(player);
                    }
                    break;
                default: break;
            }
        }

        if (inventoryView.getTitle().equalsIgnoreCase("§6§lTicket")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) {
                return;
            }

            ItemMeta meta;

            switch (clickedItem.getType()) {
                case ENDER_CHEST:
                    meta = clickedItem.getItemMeta();
                    if (meta.getDisplayName().equalsIgnoreCase("§cOpen")) {
                        openEnderChest(player, event.getSlot());
                    }
                    break;
                default: break;
            }

            int opened = TICKET_OPENED_ENDER_CHESTS.getOrDefault(player.getUniqueId(), 0);

            if (opened >= 5) {
                TICKET_OPENED_ENDER_CHESTS.remove(player.getUniqueId());
                player.closeInventory();
            }
        }
    }

    public static Tickets getPlayerTickets(Player player) {
        return new Tickets(player.getUniqueId());
    }

    public static void buyTicket(Player player) {
        Tickets playerTickets = getPlayerTickets(player);

        if (CoinsManager.getCoins(player.getUniqueId()) < TICKET_SHOP_PRICE) {
            player.sendMessage(Main.getMainConfig().getPrefix() + "§cYou dont have enough Coins to buy a Ticket!");
            return;
        }

        CoinsManager.removeCoins(player.getUniqueId(), TICKET_SHOP_PRICE);

        playerTickets.addTicket();
        playerTickets.update();

        player.sendMessage(Main.getMainConfig().getPrefix() + "§aYou have bought a Ticket!");

        String ticketsTitle = playerTickets.getTickets() <= 0 ? "§6Tickets (0)" : "§6Tickets (" + playerTickets.getTickets() + ")";
        player.getOpenInventory().setItem(23,
                new ItemManager(Material.CHEST_MINECART)
                        .setDisplayName(ticketsTitle)
                        .setAmount(1)
                        .create()
        );
    }

    public static void useTicket(Player player) {
        Tickets playerTickets = getPlayerTickets(player);
        if (playerTickets.getTickets() <= 0) {
            player.sendMessage(Main.getMainConfig().getPrefix() + "§cYou dont have any Tickets!");
            player.closeInventory();
            return;
        }

        Inventory inventory = Bukkit.createInventory(player, TICKET_INVENTORY_COLUMNS * TICKET_INVENTORY_ROWS, "§6§lTicket");

        for (int i = 0; i < (TICKET_INVENTORY_COLUMNS * TICKET_INVENTORY_ROWS); i++) {
            inventory.setItem(i,
                    new ItemManager(Material.ENDER_CHEST)
                            .setDisplayName("§cOpen")
                            .setAmount(1)
                            .create()
            );
        }

        playerTickets.removeTicket();
        playerTickets.update();

        player.closeInventory();
        player.openInventory(inventory);
    }

    public static void openEnderChest(Player player, int slot) {
        InventoryView inventoryView = player.getOpenInventory();

        if (inventoryView.getTitle().equalsIgnoreCase("§6§lTicket")) {
            int coins = getRarityValue();
            if (coins > 500) {
                inventoryView.setItem(slot,
                        new ItemManager(Material.GOLD_INGOT)
                                .setDisplayName(ChatColor.GOLD + "" + coins + " Coins")
                                .setAmount(1)
                                .addEnchantment(Enchantment.UNBREAKING, 10)
                                .create()
                );
                player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f, 1f);
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

                if (coins >= 5000) {
                    player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                    player.playSound(player, Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
                }
            } else {
                inventoryView.setItem(slot,
                        new ItemManager(Material.GOLD_INGOT)
                                .setDisplayName(ChatColor.GOLD + "" + coins + " Coins")
                                .create()
                );
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            }
            player.sendMessage(Main.getMainConfig().getPrefix() + "You opened a Ticket! " + ChatColor.GOLD + "+" + coins + " Coins");
            CoinsManager.addCoins(player.getUniqueId(), coins);
            int opened = TICKET_OPENED_ENDER_CHESTS.getOrDefault(player.getUniqueId(), 0);
            TICKET_OPENED_ENDER_CHESTS.put(player.getUniqueId(), opened + 1);
        }
    }

    public static int getRarityValue() {
        Random random = new Random();

        int randomValue = random.nextInt(500);
        if (randomValue < 495) {
            return new Random().nextInt(1, 500);
        } else if (randomValue < 499) {
            return new Random().nextInt(1000, 2000);
        } else {
            return new Random().nextInt(5000, 1000000);
        }
    }

    public static void createTickets(UUID uuid) {
        Main.getDatabase().executeQuery("INSERT INTO tickets (uid, tickets) VALUES ('" + uuid + "', 0)");
    }

    public static void createDefaultTable() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS tickets (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     uid VARCHAR(255) NOT NULL UNIQUE,
                     tickets INT(32) NOT NULL
                 )
                """);
    }
}
