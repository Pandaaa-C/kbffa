package com.panda0day.kbffa.managers;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;

public class GameRuleManager {
    public static void setDefaultGameRules(World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setDifficulty(Difficulty.PEACEFUL);
    }
}
