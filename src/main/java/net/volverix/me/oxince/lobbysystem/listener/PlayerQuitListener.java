package net.volverix.me.oxince.lobbysystem.listener;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 20:32
 */

import net.volverix.me.oxince.lobbysystem.LobbyPlayer;
import net.volverix.me.oxince.lobbysystem.LobbySystem;
import net.volverix.me.oxince.lobbysystem.util.GadgetPattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        LobbyPlayer lobbyPlayer = new LobbyPlayer(player);
        GadgetPattern gadgetPattern = new GadgetPattern();

        lobbyPlayer.setLatestPlayerLocation(player.getLocation());
        gadgetPattern.setHide(LobbySystem.getLobbySystem().getHideMap().get(event.getPlayer()), event.getPlayer());
    }
}
