package com.panda0day.kbffa.addons;

import com.panda0day.kbffa.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

public class DoubleJumpAddon extends Addon {
    public DoubleJumpAddon() {
        super(
                "Double Jump",
                new ItemManager(Material.DIAMOND_BOOTS)
                        .addEnchantment(Enchantment.UNBREAKING, 10)
                        .setDisplayName("§6§lDouble Jump")
                        .setLore(List.of(
                                "§aGet a Double Jump Effect",
                                "§afor §6§l5 minutes",
                                "§bFor 5000 Coins"
                        ))
                        .create(),
                5000
        );
    }
}
