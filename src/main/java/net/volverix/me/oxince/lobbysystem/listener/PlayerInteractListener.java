package net.volverix.me.oxince.lobbysystem.listener;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 18:25
 */

import com.google.common.collect.Lists;
import net.volverix.me.oxince.lobbysystem.LobbySystem;
import net.volverix.me.oxince.lobbysystem.util.ItemBuilder;
import net.volverix.me.oxince.me.dukeofitaly.core.util.StatisticsPattern;
import net.volverix.me.oxince.me.dukeofitaly.core.util.StatisticsType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() != Material.AIR && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getAmount() != 0) {
            Player player = event.getPlayer();

            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8•§7● §fNavigator §7(right click) §7●§8•")) {
                Inventory inventory = Bukkit.createInventory(null, 9 * 5, "§8•§7● §fNavigator §7●§8•");

                inventory.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(37, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(43, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(44, new ItemBuilder(Material.STAINED_GLASS_PANE, 7).addEnchant(Enchantment.DURABILITY, 10).setDisplayName(" ").build());
                inventory.setItem(22, new ItemBuilder(Material.MAGMA_CREAM).setDisplayName("§fLobby §8| §fSpawn").build());

                player.playSound(player.getLocation(), Sound.CLICK, 10, 10);

                player.openInventory(inventory);
            }

            ArrayList<Player> hideCoolDown = Lists.newArrayList();

            BukkitRunnable bukkitTask;

            bukkitTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if (player != null) {
                        hideCoolDown.remove(player);
                        Bukkit.getConsoleSender().sendMessage("SHEE");
                    }
                }
            };

            if (!hideCoolDown.contains(player)) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8•§7● §eOnly VIP's visible §7(right click) §7●§8•")) {
                    LobbySystem.getLobbySystem().getHideMap().put(player, 0);
                    player.playSound(player.getLocation(), Sound.BURP, 10, 10);
                    player.getInventory().setItem(1, new ItemBuilder(Material.INK_SACK, 1).setDisplayName("§8•§7● §cNo Players visible §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());
                }

                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8•§7● §cNo players visible §7(right click) §7●§8•")) {
                    LobbySystem.getLobbySystem().getHideMap().put(player, 1);
                    player.playSound(player.getLocation(), Sound.BURP, 10, 10);
                    player.getInventory().setItem(1, new ItemBuilder(Material.INK_SACK, 10).setDisplayName("§8•§7● §aAll players visible §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());
                }

                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8•§7● §aAll players visible §7(right click) §7●§8•")) {
                    LobbySystem.getLobbySystem().getHideMap().put(player, 2);
                    player.playSound(player.getLocation(), Sound.BURP, 10, 10);
                    player.getInventory().setItem(1, new ItemBuilder(Material.INK_SACK, 11).setDisplayName("§8•§7● §eOnly VIP's visible §7(right click) §7●§8•").addEnchant(Enchantment.DURABILITY, 16).build());
                }

                hideCoolDown.add(player);
                bukkitTask.runTaskLaterAsynchronously(LobbySystem.getLobbySystem(), 15);

                player.sendMessage(String.valueOf(new StatisticsPattern(LobbySystem.getLobbySystem().getDriverManager(), "teststats", player.getUniqueId()).getStatistics(StatisticsType.KILLS)));
            } else {
                player.sendMessage(LobbySystem.getPrefix() + "Please wait a moment before using this item again!");
            }
        }
    }
}
