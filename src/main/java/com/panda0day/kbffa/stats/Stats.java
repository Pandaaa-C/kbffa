package com.panda0day.kbffa.stats;

import com.panda0day.kbffa.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Stats {
    private final UUID playerUid;
    private int kills;
    private int deaths;

    public Stats(UUID playerUid) {
        this.playerUid = playerUid;

        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM player_stats WHERE uid=?", playerUid.toString());
            while (resultSet.next()) {
                kills = resultSet.getInt("kills");
                deaths = resultSet.getInt("deaths");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addKills() {
        kills += 1;
    }

    public void addDeaths() {
        deaths += 1;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void update() {
        Main.getDatabase().executeQuery("UPDATE player_stats SET kills = ?, deaths = ? WHERE uid = ?;", this.kills, this.deaths, this.playerUid.toString());
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public UUID getPlayerUid() {
        return playerUid;
    }
}
