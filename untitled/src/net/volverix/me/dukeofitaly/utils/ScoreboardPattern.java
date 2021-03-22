package net.volverix.me.dukeofitaly.utils;

import net.volverix.me.dukeofitaly.LobbySystem;
import net.volverix.me.oxince.me.dukeofitaly.core.CorePlayer;
import net.volverix.me.oxince.me.dukeofitaly.core.sql.DriverManager;
import net.volverix.me.oxince.me.dukeofitaly.core.util.ClanPattern;
import net.volverix.me.oxince.me.dukeofitaly.core.util.CoinPattern;
import net.volverix.me.oxince.me.dukeofitaly.core.util.StatisticsPattern;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import javax.persistence.Lob;
import java.util.UUID;

public class ScoreboardPattern {

    private Player player;

    public ScoreboardPattern(Player player) {
        this.player = player;
    }

    public void setLobbyScoreBoard() {

        UUID playerUUID = player.getUniqueId();
        LobbySystem lobbySystem = LobbySystem.getLobbySystem();
        DriverManager driverManager = lobbySystem.getDriverManager();
        CorePlayer corePlayer = new CorePlayer(player.getUniqueId());
        CoinPattern coinPattern = new CoinPattern(driverManager);
        ClanPattern clanPattern = new ClanPattern(playerUUID, driverManager);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "bbb");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§cLobby");

        objective.getScore("§7Rank §8:").setScore(11);
        objective.getScore("§8» §6RANK").setScore(10);
        objective.getScore("§a").setScore(9);
        objective.getScore("§7Clan §8:").setScore(8);
        objective.getScore("§8» §6" + clanPattern.getClanName()).setScore(7);
        objective.getScore("§d").setScore(6);
        objective.getScore("§7Coins §8:").setScore(5);
        objective.getScore("§8» §6" + coinPattern.getCoins(playerUUID)).setScore(4);
        objective.getScore("§f").setScore(3);
        objective.getScore("§7Server §8:").setScore(2);
        objective.getScore("§8» §6" + Bukkit.getServer().getName()).setScore(1);
        objective.getScore("§b                       §b").setScore(0);

    }

}
