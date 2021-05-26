package com.worldwidesmp.worldwidesmp.gui;

import com.sun.tools.javac.jvm.ClassWriter;
import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.logger;
import static com.worldwidesmp.worldwidesmp.WorldwideSMP.plugin;

public class Items implements Listener{

    public static FileConfiguration configuration = WorldwideSMP.plugin.getConfig();
    public static ItemStack placeholder = createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    static ItemStack air = new ItemStack(Material.AIR);
    public static ItemStack backpack = createGuiItem(Material.WOODEN_SWORD,ChatColor.RESET+"Backpack"," ");

    static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray())
            hidden += ChatColor.COLOR_CHAR+""+c;
        return hidden;
    }
    static ItemStack createGuiItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }

}
