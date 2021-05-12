package com.worldwidesmp.worldwidesmp.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Items {

    static ItemStack placeholder = createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " ");





    static ItemStack createGuiItem(final Material material, final String name, final Component... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));

        meta.lore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    static ItemStack createGuiItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));

        item.setItemMeta(meta);

        return item;
    }

}
