package net.volverix.me.oxince.lobbysystem;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 07:39
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import net.volverix.me.oxince.lobbysystem.commands.SetCommand;
import net.volverix.me.oxince.lobbysystem.listener.*;
import net.volverix.me.oxince.lobbysystem.util.LocationPattern;
import net.volverix.me.oxince.me.dukeofitaly.core.sql.DriverManager;
import net.volverix.me.oxince.me.dukeofitaly.core.util.CoinPattern;
import net.volverix.me.oxince.me.dukeofitaly.core.util.RegisterPattern;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class LobbySystem extends JavaPlugin {
    @Getter
    private static LobbySystem lobbySystem;

    @Getter
    private static String prefix;

    @Getter
    private DriverManager driverManager;

    @Getter
    private LocationPattern locationPattern;

    @Getter
    private RegisterPattern registerPattern;

    @Getter
    private CoinPattern coinPattern;

    @Getter
    private HashMap<Player, Integer> hideMap;

    @Override
    public void onEnable() {
        prefix = "§8•§7● §bVolverix§3NET §8» §7";
        lobbySystem = this;

        driverManager = new DriverManager("localhost", "root", "test", "");
        driverManager.startConnection();

        driverManager.update("CREATE TABLE IF NOT EXISTS PlayerData(UUID VARCHAR(128), COINS int, CLAN VARCHAR(16), CLAN_ROLE int, ELO int)");
        driverManager.update("CREATE TABLE IF NOT EXISTS LobbyPlayer(UUID VARCHAR(128), HIDE int)");
        driverManager.update("CREATE TABLE IF NOT EXISTS Lobby_Locations(NAME VARCHAR(16),WORLD VARCHAR(64), X double, Y double, Z double, YAW float, PITCH float)");
        driverManager.update("CREATE TABLE IF NOT EXISTS Lobby_LatestLocationOfPlayer(UUID VARCHAR(128),WORLD VARCHAR(16), X double, Y double, Z double, YAW float, PITCH float)");

        locationPattern = new LocationPattern();
        registerPattern = new RegisterPattern(driverManager);
        coinPattern = new CoinPattern(driverManager);

        locationPattern.load();

        hideMap = Maps.newHashMap();

        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);

        getCommand("set").setExecutor(new SetCommand());
    }
}
