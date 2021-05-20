package com.worldwidesmp.worldwidesmp.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Items {

    static ItemStack placeholder = createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    static ItemStack air = new ItemStack(Material.AIR);
    static ItemStack backpack = createGuiItem(Material.LEATHER,"Backpack");



    static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    static ItemStack createGuiItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }

}
