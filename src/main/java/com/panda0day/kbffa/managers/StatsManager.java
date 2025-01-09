package com.panda0day.kbffa.managers;

import com.panda0day.kbffa.Main;
import com.panda0day.kbffa.stats.Stats;

import java.util.UUID;

public class StatsManager {
    public static Stats getStats(UUID playerUid) {
        return new Stats(playerUid);
    }

    public static void createStats(UUID playerUid) {
        Main.getDatabase().executeQuery("INSERT INTO player_stats (uid, kills, deaths) VALUES ('" + playerUid.toString() + "', 0, 0);");
    }

    public static void clearStats(UUID playerUid) {
        Main.getDatabase().executeQuery("UPDATE player_stats SET kills = 0, deaths = 0 WHERE uid = ?;", playerUid.toString());
    }

    public static void createDefaultTable() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS player_stats (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     uid VARCHAR(255) NOT NULL UNIQUE,
                     kills INT(32) NOT NULL,
                     deaths INT(32) NOT NULL
                 )
                """);
    }
}
