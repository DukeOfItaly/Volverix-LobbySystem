package net.volverix.me.oxince.lobbysystem;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 07:39
 */

import lombok.SneakyThrows;
import net.volverix.me.oxince.lobbysystem.util.GadgetPattern;
import net.volverix.me.oxince.lobbysystem.util.ItemBuilder;
import net.volverix.me.oxince.me.dukeofitaly.core.CorePlayer;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.sql.ResultSet;

public class LobbyPlayer {
    private final Player player;
    private final GadgetPattern gadgetPattern = new GadgetPattern();

    public LobbyPlayer(Player player) {
        this.player = player;
    }

    public void setDefaultItems() {
        Inventory inventory = player.getInventory();

        inventory.clear();

        inventory.setItem(0, new ItemBuilder(Material.COMPASS).setDisplayName("§8•§7● §fNavigator §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());
        inventory.setItem(4, new ItemBuilder(Material.CHEST).setDisplayName("§8•§7● §6Gadgets §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());
        inventory.setItem(7, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§8•§7● §bLobby Switcher §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());
        inventory.setItem(8, new ItemBuilder(Material.SKULL_ITEM, 3).givePlayerSkull(player).setDisplayName("§8•§7● §aYour Profile §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());

        if (gadgetPattern.getHide(player) == 0)
            inventory.setItem(1, new ItemBuilder(Material.INK_SACK, 1).setDisplayName("§8•§7● §cNo Players visible §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());

        if (gadgetPattern.getHide(player) == 1)
            inventory.setItem(1, new ItemBuilder(Material.INK_SACK, 10).setDisplayName("§8•§7● §aAll players visible §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());

        if (gadgetPattern.getHide(player) == 2)
            inventory.setItem(1, new ItemBuilder(Material.INK_SACK, 11).setDisplayName("§8•§7● §eOnly VIP's visible §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());
    }

    public void setDefaultDurations() {
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    public void teleportToSpawn() {
        if (LobbySystem.getLobbySystem().getLocationPattern().getSpawnLocation() != null) {
            player.teleport(LobbySystem.getLobbySystem().getLocationPattern().getSpawnLocation().get(0));
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 10);

            if (latestPlayerLocationIsExisting()) {
                teleportToLatestPlayerLocation();
            }
        } else {
            player.sendMessage(LobbySystem.getPrefix() + "The spawn point isn't set! Please set the spawn point! §8\"§a/set spawn§8\"");
        }
    }

    public void setLatestPlayerLocation(Location location) {
        if (!latestPlayerLocationIsExisting())
            LobbySystem.getLobbySystem().getDriverManager().update("INSERT INTO Lobby_LatestLocationOfPlayer(UUID, WORLD, X, Y, Z, YAW, PITCH) VALUES('"
                    + player.getUniqueId() + "', '"
                    + location.getWorld().getName()
                    + "', '" + location.getX()
                    + "', '" + location.getY()
                    + "', '" + location.getZ()
                    + "', '" + location.getYaw()
                    + "', '" + location.getPitch() + "')");
    }

    @SneakyThrows
    private void teleportToLatestPlayerLocation() {
        if (latestPlayerLocationIsExisting()) {
            ResultSet resultSet = LobbySystem.getLobbySystem().getDriverManager().resultSet("SELECT * FROM Lobby_LatestLocationOfPlayer WHERE UUID='" + player.getUniqueId() + "'");
            if (resultSet.next()) {
                World world = Bukkit.getWorld(resultSet.getString("WORLD"));

                double x = resultSet.getDouble("X");
                double y = resultSet.getDouble("Y");
                double z = resultSet.getDouble("Z");

                float yaw = resultSet.getFloat("YAW");
                //float pitch = resultSet.getFloat("PITCH");

                player.teleport(new Location(world, x, y, z, yaw, 0));
            }
        }
    }

    @SneakyThrows
    private boolean latestPlayerLocationIsExisting() {
        ResultSet resultSet = LobbySystem.getLobbySystem().getDriverManager().resultSet("SELECT * FROM Lobby_LatestLocationOfPlayer WHERE UUID='" + player.getUniqueId() + "'");
        if (resultSet.next()) {
            return resultSet.getString("UUID") != null;
        }
        return false;
    }

    public void setScoreboard() {
        CorePlayer corePlayer = new CorePlayer(player.getUniqueId());

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "bbb");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§bVolverix§3NET");

        objective.getScore("§1").setScore(13);
        objective.getScore("§7Coins§8:").setScore(12);
        objective.getScore("§8➥ §6" + corePlayer.getCoins(LobbySystem.getLobbySystem().getCoinPattern())).setScore(11);
        objective.getScore("§2").setScore(10);
        objective.getScore("§7Clan§8:").setScore(9);
        objective.getScore("§8➥ §bKein Clan").setScore(8);
        objective.getScore("§3").setScore(7);
        objective.getScore("§7Shop§8:").setScore(6);
        objective.getScore("§8➥ §3shop.volverix.net").setScore(5);
        objective.getScore("§4").setScore(4);
        objective.getScore("§7Lobby§8:").setScore(3);
        objective.getScore("§8➥ §c" + player.getServer().getServerName()).setScore(2);
        objective.getScore("§b                                    §b").setScore(1);

        player.setScoreboard(scoreboard);
    }

    private final String[] scoreboardTitle = {
            "§bVolverix§3NET", "§bVolverix§3NET", "§bVolverix§3NET", "§bVolverix§3NET", "§bVolverix§3NET", "§bVolverix§3NET", "§bVolverix§3NET", "§bVolverix§3NET ",
            "§bVolverix§3NET §8➜", "§bVolverix§3NET §8➜ ", "§bVolverix§3NET §8➜ §7L", "§bVolverix§3NET §8➜ §7Lo", "§bVolverix§3NET §8➜ §7Lob", "§bVolverix§3NET §8➜ §7Lobb", "§bVolverix§3NET §8➜ §7Lobby",
            "§bVolverix§3NET §8➜ §7Lobby", "§bVolverix§3NET §8➜ §7Lobby", "§bVolverix§3NET §8➜ §7Lobby", "§bVolverix§3NET §8➜ §7Lobby", "§bVolverix§3NET §8➜ §7Lobby", "§bVolverix§3NET §8➜ §7Lobby", "§bVolverix§3NET §8➜ §7Lobby"
    };

    private int scoreboardAnimationTick = 1;
    private boolean scoreboardAnimationReverse = false;

    public void startScoreboardAnimation() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(LobbySystem.getLobbySystem(), new BukkitRunnable() {
            @Override
            public void run() {
                if (scoreboardAnimationTick == 0) {
                    scoreboardAnimationReverse = true;
                    scoreboardAnimationTick++;
                }

                if (scoreboardAnimationTick == 21 && scoreboardAnimationReverse) {
                    scoreboardAnimationReverse = false;
                    scoreboardAnimationTick--;
                }

                if (scoreboardAnimationTick >= 0 && !scoreboardAnimationReverse) {
                    if (player.getScoreboard().getObjective("aaa") != null) {
                        player.getScoreboard().getObjective("aaa").setDisplayName(scoreboardTitle[scoreboardAnimationTick]);
                    }
                    scoreboardAnimationTick--;
                }

                if (scoreboardAnimationReverse) {
                    if (player.getScoreboard().getObjective("aaa") != null) {
                        player.getScoreboard().getObjective("aaa").setDisplayName(scoreboardTitle[scoreboardAnimationTick]);
                    }
                    scoreboardAnimationTick++;
                }
            }
        }, 0, 3);
    }
}
