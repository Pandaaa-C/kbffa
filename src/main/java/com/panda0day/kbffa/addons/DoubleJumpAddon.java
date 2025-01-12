package com.panda0day.kbffa.addons;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoubleJumpAddon {
    public Map<Player, Date> activeList;

    public DoubleJumpAddon() {
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
