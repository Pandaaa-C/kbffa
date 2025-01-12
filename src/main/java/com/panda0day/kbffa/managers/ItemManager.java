package com.panda0day.kbffa.managers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemManager {
    private final ItemMeta itemMeta;
    private final ItemStack itemStack;

    public ItemManager(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemManager setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        this.setItemMeta(itemMeta);

        return this;
    }

    public ItemManager setLore(List<String> lore) {
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemManager addEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        this.setItemMeta(itemMeta);

        return this;
    }

    public ItemManager addEnchantments(List<Enchantment> enchantments) {
        for (Enchantment enchantment : enchantments) {
            this.itemMeta.addEnchant(enchantment, 1, true);
        }

        this.setItemMeta(itemMeta);

        return this;
    }

    public void setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemManager setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    };

    public ItemStack create() {
        return this.itemStack;
    }
}
