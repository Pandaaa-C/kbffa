package com.panda0day.kbffa.addons;

import com.panda0day.kbffa.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DoubleJumpAddon extends Addon {
    public DoubleJumpAddon() {
        super(
                AddonNames.DOUBLE_JUMP.getDisplayName(),
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

        setAddonStart(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getAddonStart());
        calendar.add(Calendar.MINUTE, 5);
        setAddonEnd(calendar.getTime());
    }

    public void allowFlight(Player player) {
        player.setAllowFlight(true);
    }
}
