package net.volverix.me.dukeofitaly.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemPattern {

    private Player player;

    public ItemPattern(Player player) {
        this.player = player;
    }

    public void setItem(Material itemStack, Integer slot, String displayName, List lore) {
        Inventory inv = player.getInventory();
        ItemStack is = new ItemStack(itemStack);
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(displayName);
        m.setLore(lore);
        is.setItemMeta(m);
        inv.setItem(slot, is);
    }

    public void setJoinItems() {
    }

}
