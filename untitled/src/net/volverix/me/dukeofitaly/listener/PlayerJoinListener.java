package net.volverix.me.dukeofitaly.listener;

import net.volverix.me.dukeofitaly.LobbySystem;
import net.volverix.me.dukeofitaly.utils.ScoreboardPattern;
import net.volverix.me.oxince.me.dukeofitaly.core.util.RegisterPattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {



    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        LobbySystem lobbySystem = new LobbySystem();
        ScoreboardPattern scoreboardPattern = new ScoreboardPattern(player);

        player.sendMessage("JOIN MESSAGE");
        scoreboardPattern.setLobbyScoreBoard();

    }

}
