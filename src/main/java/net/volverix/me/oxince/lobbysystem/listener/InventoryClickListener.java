package net.volverix.me.oxince.lobbysystem.listener;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 00:05
 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClickListener(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
