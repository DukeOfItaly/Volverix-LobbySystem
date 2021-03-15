package net.volverix.me.oxince.lobbysystem.util;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 23:48
 */

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.SneakyThrows;
import net.volverix.me.oxince.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class LocationPattern {
    @Getter
    private ArrayList<Location> spawnLocation;

    @Getter
    private ArrayList<Location> bedWarsLocation;

    @Getter
    private ArrayList<Location> tacticBedWarsLocation;

    @Getter
    private ArrayList<Location> mlgRushLocation;

    @Getter
    private ArrayList<Location> skyWarsLocation;

    @Getter
    private ArrayList<Location> cityBuildLocation;

    @Getter
    private ArrayList<Location> skyBlockLocation;

    @Getter
    private ArrayList<Location> gunGameLocation;

    @Getter
    private ArrayList<Location> skyPvpLocation;

    @Getter
    private ArrayList<Location> kitPvpLocation;

    @Getter
    private ArrayList<Location> knockFfaLocation;

    @Getter
    private ArrayList<Location> rushFfaLocation;

    @Getter
    private ArrayList<Location> hardcoreFfaLocation;

    public void load() {
        if (locationIsExisting("spawn")) {
            spawnLocation = Lists.newArrayList();
            spawnLocation.add(0, getLocation("spawn"));
        }

        if (locationIsExisting("bedwars")) {
            bedWarsLocation = Lists.newArrayList();
            bedWarsLocation.add(0, getLocation("bedwars"));
        }

        if (locationIsExisting("tacticbedwars")) {
            tacticBedWarsLocation = Lists.newArrayList();
            tacticBedWarsLocation.add(0, getLocation("tacticbedwars"));
        }

        if (locationIsExisting("mlgrush")) {
            mlgRushLocation = Lists.newArrayList();
            mlgRushLocation.add(0, getLocation("mlgrush"));
        }

        if (locationIsExisting("skywars")) {
            skyWarsLocation = Lists.newArrayList();
            skyWarsLocation.add(0, getLocation("SKYWARS"));
        }

        if (locationIsExisting("citybuild")) {
            cityBuildLocation = Lists.newArrayList();
            cityBuildLocation.add(0, getLocation("CITYBUILD"));
        }

        if (locationIsExisting("skyblock")) {
            skyBlockLocation = Lists.newArrayList();
            skyBlockLocation.add(0, getLocation("skyblock"));
        }

        if (locationIsExisting("gungame")) {
            gunGameLocation = Lists.newArrayList();
            gunGameLocation.add(0, getLocation("skyblock"));
        }

        if (locationIsExisting("skypvp")) {
            skyPvpLocation = Lists.newArrayList();
            skyPvpLocation.add(0, getLocation("skypvp"));
        }

        if (locationIsExisting("kitpvp")) {
            kitPvpLocation = Lists.newArrayList();
            kitPvpLocation.add(0, getLocation("kitpvp"));
        }

        if (locationIsExisting("knockffa")) {
            knockFfaLocation = Lists.newArrayList();
            knockFfaLocation.add(0, getLocation("knockffa"));
        }

        if (locationIsExisting("rushffa")) {
            rushFfaLocation = Lists.newArrayList();
            rushFfaLocation.add(0, getLocation("rushffa"));
        }

        if (locationIsExisting("hardcoreffa")) {
            hardcoreFfaLocation = Lists.newArrayList();
            hardcoreFfaLocation.add(0, getLocation("hardcoreffa"));
        }
    }

    public void setLocation(Location location, String locationName) {
        if (!locationIsExisting(locationName))
            LobbySystem.getLobbySystem().getDriverManager().update("INSERT INTO Lobby_Locations(NAME, WORLD, X, Y, Z, YAW, PITCH) VALUES('"
                    + locationName.toLowerCase()
                    + "', '" + location.getWorld().getName()
                    + "', '" + round(location.getX(), 4)
                    + "', '" + round(location.getY(), 4)
                    + "', '" + round(location.getZ(), 4)
                    + "', '" + location.getYaw()
                    + "', '" + location.getPitch() + "')");
    }

    @SneakyThrows
    private Location getLocation(String locationName) {
        if (locationIsExisting(locationName)) {
            ResultSet resultSet = LobbySystem.getLobbySystem().getDriverManager().resultSet("SELECT * FROM Lobby_Locations WHERE NAME='" + locationName.toLowerCase() + "'");
            if (resultSet.next()) {
                World world = Bukkit.getWorld(resultSet.getString("WORLD"));

                double x = resultSet.getDouble("X");
                double y = resultSet.getDouble("Y");
                double z = resultSet.getDouble("Z");

                float yaw = resultSet.getFloat("YAW");
                float pitch = resultSet.getFloat("PITCH");

                return new Location(world, x, y, z, yaw, pitch);
            }
        }
        return null;
    }

    @SneakyThrows
    public boolean locationIsExisting(String locationName) {
        ResultSet resultSet = LobbySystem.getLobbySystem().getDriverManager().resultSet("SELECT * FROM Lobby_Locations WHERE NAME='" + locationName.toUpperCase() + "'");
        if (resultSet.next()) {
            return resultSet.getString(locationName) != null;
        }
        return false;
    }

    private double round(double number, int digits) {
        return number + Math.round(Math.pow(10, digits) * (0.0)) / (Math.pow(10, digits));
    }
}
