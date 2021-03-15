package net.volverix.me.oxince.lobbysystem.util;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 18:54
 */

import lombok.SneakyThrows;
import net.volverix.me.oxince.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.UUID;

public class GadgetPattern {
    public void setHide(int hide, Player player) {
        if (playerIsRegistered(player.getUniqueId())) {
            LobbySystem.getLobbySystem().getDriverManager().update("UPDATE LobbyPlayer SET HIDE='" + hide + "' WHERE UUID='" + player.getServer() + "'");
        } else {
            registerPlayer(player.getUniqueId());
            setHide(hide, player);
        }
    }

    @SneakyThrows
    public Integer getHide(Player player) {
        if (playerIsRegistered(player.getUniqueId())) {
            ResultSet resultSet = LobbySystem.getLobbySystem().getDriverManager().resultSet("SELECT * FROM LobbyPlayer WHERE UUID='" + player.getUniqueId() + "'");
            if (resultSet.next()) {
                return Integer.parseInt(resultSet.getString("HIDE"));
            }
        } else {
            registerPlayer(player.getUniqueId());
            getHide(player);
        }
        return 1;
    }

    @SneakyThrows
    public boolean playerIsRegistered(UUID uuid) {
        ResultSet resultSet = LobbySystem.getLobbySystem().getDriverManager().resultSet("SELECT * FROM LobbyPlayer WHERE UUID='" + uuid + "'");

        if (resultSet.next()) {
            return resultSet.getString("UUID") != null;
        }
        return false;
    }

    public void registerPlayer(UUID uuid) {
        if (!playerIsRegistered(uuid)) {
            LobbySystem.getLobbySystem().getDriverManager().update("INSERT INTO LobbyPlayer(UUID, HIDE) VALUES('" + uuid + "', '1')");
        }
    }
}
