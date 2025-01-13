package com.panda0day.kbffa.addons;

import com.panda0day.kbffa.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KnockbackTenAddon extends Addon {
    public KnockbackTenAddon() {
        super(
                AddonNames.KNOCKBACK_TEN.getDisplayName(),
                new ItemManager(Material.STICK)
                        .addEnchantment(Enchantment.KNOCKBACK, 10)
                        .setDisplayName("§6§lKnockback 10")
                        .setLore(List.of(
                                "§aGet a Knockback 10 Stick",
                                "§afor §6§l2 minutes",
                                "§bFor 10000 Coins"
                        ))
                        .create(),
                10000
        );

        setAddonStart(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getAddonStart());
        calendar.add(Calendar.MINUTE, 2);
        setAddonEnd(calendar.getTime());
    }

    public void giveKnockBackTen(Player player) {
        player.getInventory().clear();

        player.getInventory().addItem(
                new ItemManager(Material.STICK)
                        .setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "OP Stick X")
                        .addEnchantment(Enchantment.UNBREAKING, 10)
                        .addEnchantment(Enchantment.KNOCKBACK, 10)
                        .setAmount(1)
                        .create()
        );
    }
}
