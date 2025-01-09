package com.panda0day.kbffa.managers;

import com.panda0day.kbffa.Main;

import java.sql.ResultSet;
import java.util.UUID;

public class CoinsManager {
    public static int getCoins(UUID uuid) {
        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM player_coins WHERE uid = '" + uuid.toString() + "'");
            while (resultSet.next()) {
                return resultSet.getInt("coins");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public static void setCoins(UUID uuid, int coins) {
        Main.getDatabase().executeQuery("UPDATE player_coins SET coins = " + coins + " WHERE uid = '" + uuid.toString() + "'");
    }

    public static void addCoins(UUID uuid, int coins) {
        int currentCoins = getCoins(uuid);

        setCoins(uuid, currentCoins + coins);
    }

    public static void removeCoins(UUID uuid, int coins) {
        int currentCoins = getCoins(uuid);

        setCoins(uuid, currentCoins - coins);
    }

    public static void createCoins(UUID uuid) {
        Main.getDatabase().executeQuery("INSERT INTO player_coins (uid, coins) VALUES ('" + uuid.toString() + "', 0);");
    }

    public static void createDefaultTable() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS player_coins (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     uid VARCHAR(255) NOT NULL UNIQUE,
                     coins INT(32) NOT NULL
                 )
                """);
    }
}
