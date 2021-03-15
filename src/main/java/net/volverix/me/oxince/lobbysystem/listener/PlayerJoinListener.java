package net.volverix.me.oxince.lobbysystem.listener;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 17:33
 */

import net.volverix.me.oxince.lobbysystem.LobbyPlayer;
import net.volverix.me.oxince.lobbysystem.LobbySystem;
import net.volverix.me.oxince.me.dukeofitaly.core.CorePlayer;
import net.volverix.me.oxince.me.dukeofitaly.core.util.StatisticsPattern;
import net.volverix.me.oxince.me.dukeofitaly.core.util.StatisticsType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LobbyPlayer lobbyPlayer = new LobbyPlayer(player);
        CorePlayer corePlayer = new CorePlayer(player.getUniqueId());

        event.setJoinMessage(null);

        lobbyPlayer.teleportToSpawn();
        lobbyPlayer.setDefaultItems();
        lobbyPlayer.setDefaultDurations();
        lobbyPlayer.setScoreboard();
        lobbyPlayer.startScoreboardAnimation();

        corePlayer.registerToDatabase(LobbySystem.getLobbySystem().getRegisterPattern());

        new StatisticsPattern(LobbySystem.getLobbySystem().getDriverManager(), "teststats", player.getUniqueId()).increaseStatistics(StatisticsType.KILLS);
    }
}
