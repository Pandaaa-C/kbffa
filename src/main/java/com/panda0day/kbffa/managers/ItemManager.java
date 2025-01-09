package com.panda0day.kbffa.managers;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Map;

public class ItemManager {
    public static ItemStack createItem(Material material, int amount, String displayName) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItemWithLore(Material material, int amount, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createPlayerSkull(String displayName, String owner) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setDisplayName(displayName);
        skullMeta.setOwner(owner);
        item.setItemMeta(skullMeta);

        return item;
    }

    public static ItemStack createItemWithEnchantment(Material material, int amount, String displayName, List<String> lore, Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItemWithEnchantments(Material material, int amount, String displayName, List<String> lore, Map<Integer, Enchantment> enchantments) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        for (Map.Entry<Integer, Enchantment> entry : enchantments.entrySet()) {
            meta.addEnchant(entry.getValue(), entry.getKey(), true);
        }
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createFireWork(String displayName, int amount, Color color, int power) {
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET, amount);
        FireworkMeta meta = (FireworkMeta) item.getItemMeta();
        meta.setDisplayName(displayName);
        Builder builder = FireworkEffect.builder();
        builder.withColor(color);
        builder.with(FireworkEffect.Type.BALL_LARGE);
        meta.addEffect(builder.build());
        meta.setPower(power);
        item.setItemMeta(meta);

        return item;
    }
}
