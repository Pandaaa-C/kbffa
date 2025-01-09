package com.panda0day.kbffa.managers;

import com.panda0day.kbffa.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldManager {
    public static void loadWorld(String worldName) {
        World world = getOrCreateWorld(worldName);
        if (world != null) {
            GameRuleManager.setDefaultGameRules(world);
            Main.getInstance().getLogger().info("World " + worldName + " created or loaded.");
        }
    }

    public static void unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, false);
            Main.getInstance().getLogger().info("World " + worldName + " removed.");
        }
    }

    public static boolean isWorldLoaded(String worldName) {
        return getWorld(worldName) != null;
    }

    private static World getOrCreateWorld(String worldName) {
        World world = getWorld(worldName);
        if (world == null) {
            world = Bukkit.createWorld(new WorldCreator(worldName).environment(World.Environment.NORMAL));
        }
        return world;
    }

    public static World getWorld(String worldName) {
        return Bukkit.getWorld(worldName);
    }
}
