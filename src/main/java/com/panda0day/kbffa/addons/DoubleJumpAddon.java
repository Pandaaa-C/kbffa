package com.panda0day.kbffa.addons;

import com.panda0day.kbffa.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoubleJumpAddon extends Addon {
    public Map<Player, Date> activeList;

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
        this.activeList = new HashMap<>();
    }

    public void addPlayer(Player player) {
        this.activeList.put(player, new Date());
    }

    public void removePlayer(Player player) {
        this.activeList.remove(player);
    }

    public boolean doubleJumpExpired(Player player) {
        Date date = this.activeList.get(player);
        if (date == null) {
            return false;
        }

        return (new Date().getTime() - date.getTime()) > 1000 * 60 * 5;
    }

    public boolean doubleJumpActive(Player player) {
        return this.activeList.containsKey(player);
    }

    public boolean canDoubleJump(Player player) {
        return !this.doubleJumpExpired(player) && this.doubleJumpActive(player);
    }

    public Map<Player, Date> getActiveList() {
        return activeList;
    }
}
