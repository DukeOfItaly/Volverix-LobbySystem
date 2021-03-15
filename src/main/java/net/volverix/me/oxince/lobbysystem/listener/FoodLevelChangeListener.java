package net.volverix.me.oxince.lobbysystem.listener;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 22:00
 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
