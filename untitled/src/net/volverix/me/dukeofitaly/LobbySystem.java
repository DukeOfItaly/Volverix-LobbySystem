package net.volverix.me.dukeofitaly;

import net.volverix.me.dukeofitaly.listener.PlayerJoinListener;
import net.volverix.me.oxince.me.dukeofitaly.core.sql.DriverManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Driver;

public class LobbySystem extends JavaPlugin {

    private DriverManager driverManager;

    private static LobbySystem lobbySystem;

    @Override
    public void onEnable() {
        registerListeners();
        connect();
        lobbySystem = this;
    }

    @Override
    public void onDisable() {
    }


    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
    }


    private void connect() {
            driverManager = new DriverManager("localhost", "root", "volverix", "");
            if (!driverManager.isConnected()) {
                try {
                    System.out.println("[LobbySystem] Trying to connect to MySQL database via VolverixCore...");
                    driverManager = new DriverManager("localhost", "root", "volverix", "");
                    driverManager.startConnection();
                    driverManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PlayerData (UUID VARCHAR(128) PRIMARY KEY, COINS INT(16), CLAN VARCHAR(16), CLAN_RANK VARCHAR(16), ELO INT(16))").executeUpdate();
                    driverManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PlayerSettings (UUID VARCHAR(128) PRIMARY KEY, PARTY_REQUESTS INT(16), FRIEND_REQUESTS INT(16))").executeUpdate();
                    driverManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS hardcoreffa (UUID VARCHAR(128) PRIMARY KEY, KILLS INT(16), DEATHS INT(16), POINTS INT(16), WINS INT(16), EXTRAS INT(16), ELO INT(16), ELO_RANK VARCHAR(64))").executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    public static LobbySystem getLobbySystem() {
        return lobbySystem;
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

}
