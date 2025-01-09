package com.panda0day.kbffa.managers;

import com.panda0day.kbffa.stats.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerBoardManager implements Runnable {
    private static final PlayerBoardManager instance = new PlayerBoardManager();

    private PlayerBoardManager() {}

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getScoreboard() != null && player.getScoreboard().getObjective("KBFFA") != null) {
                updateScoreboard(player);
            } else {
                createNewScoreboard(player);
            }
        }
    }

    private void createNewScoreboard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("KBFFA", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW +"" + ChatColor.BOLD + "KnockbackFFA");

        Stats stats = StatsManager.getStats(player.getUniqueId());

        objective.getScore(ChatColor.RESET + " ").setScore(9);
        objective.getScore(ChatColor.RED + " Coins").setScore(8);
        //objective.getScore(ChatColor.GOLD + " > " + CoinsManager.getCoins(player.getUniqueId())).setScore(7);
        objective.getScore(ChatColor.RED + " ").setScore(6);
        objective.getScore(ChatColor.RED + " Kills").setScore(5);
        //objective.getScore(ChatColor.GOLD + " > " + stats.getKills()).setScore(4);
        objective.getScore(ChatColor.GREEN + " ").setScore(3);
        objective.getScore(ChatColor.RED + " Deaths").setScore(2);
        //objective.getScore(ChatColor.GOLD + " > " + stats.getDeaths()).setScore(1);
        objective.getScore(ChatColor.BLUE + " ").setScore(0);

        Team coinTeam = scoreboard.registerNewTeam("Coins");
        String coinTeamKey = ChatColor.GOLD.toString();
        coinTeam.addEntry(coinTeamKey);
        coinTeam.setPrefix("> ");
        coinTeam.setSuffix("" + CoinsManager.getCoins(player.getUniqueId()));

        objective.getScore(coinTeamKey).setScore(7);

        Team killsTeam = scoreboard.registerNewTeam("Kills");
        String killsTeamKey = ChatColor.RED.toString();
        killsTeam.addEntry(killsTeamKey);
        killsTeam.setPrefix("> ");
        killsTeam.setSuffix("" + stats.getKills());

        objective.getScore(killsTeamKey).setScore(4);

        Team deathsTeam = scoreboard.registerNewTeam("Deaths");
        String deathsTeamKey = ChatColor.YELLOW.toString();
        deathsTeam.addEntry(deathsTeamKey);
        deathsTeam.setPrefix("> ");
        deathsTeam.setSuffix("" + stats.getDeaths());

        objective.getScore(deathsTeamKey).setScore(1);

        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        Stats stats = StatsManager.getStats(player.getUniqueId());
        Team coinTeam = scoreboard.getTeam("Coins");
        coinTeam.setSuffix(ChatColor.GOLD + "" + CoinsManager.getCoins(player.getUniqueId()));

        Team killsTeam = scoreboard.getTeam("Kills");
        killsTeam.setSuffix(ChatColor.GOLD + "" + stats.getKills());

        Team deathsTeam = scoreboard.getTeam("Deaths");
        deathsTeam.setSuffix(ChatColor.GOLD + "" + stats.getDeaths());
    }

    public static PlayerBoardManager getInstance() {
        return instance;
    }
}
