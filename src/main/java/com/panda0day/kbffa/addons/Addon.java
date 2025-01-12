package com.panda0day.kbffa.addons;

import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class Addon {
    private final String addonName;
    private final ItemStack addonMaterial;
    private final int addonPrice;
    private Date addonStart;
    private Date addonEnd;

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

    public void setAddonStart(Date addonStart) {
        this.addonStart = addonStart;
    }

    public void setAddonEnd(Date addonEnd) {
        this.addonEnd = addonEnd;
    }

    public Date getAddonEnd() {
        return addonEnd;
    }

    public Date getAddonStart() {
        return addonStart;
    }
}
