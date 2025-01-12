package com.panda0day.kbffa.addons;

import org.bukkit.inventory.ItemStack;

public class Addon {
    private final String addonName;
    private final ItemStack addonMaterial;
    private final int addonPrice;

    public Addon(String addonName, ItemStack addonMaterial, int addonPrice) {
        this.addonName = addonName;
        this.addonMaterial = addonMaterial;
        this.addonPrice = addonPrice;
    }

    public String getAddonName() {
        return addonName;
    }

    public ItemStack getAddonMaterial() {
        return addonMaterial;
    }

    public int getAddonPrice() {
        return addonPrice;
    }
}
