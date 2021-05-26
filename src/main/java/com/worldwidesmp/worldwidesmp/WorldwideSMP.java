package com.worldwidesmp.worldwidesmp;

import com.worldwidesmp.worldwidesmp.gui.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Logger;

public final class WorldwideSMP extends JavaPlugin {
    public static WorldwideSMP plugin;
    public static Logger logger = Bukkit.getLogger();
    PluginManager pm = Bukkit.getPluginManager();
    public FileConfiguration config = this.getConfig();
    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
        plugin = this;
        NamespacedKey key = new NamespacedKey(this, "backpack");

        ShapedRecipe recipe = new ShapedRecipe(key, Items.backpack);

        recipe.shape("LLL", "L L", "LLL");

        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);
    }

    @Override
    public void onDisable() {
        //
    }

    private void registerCommands(){
        //
    }

    private void registerEvents(){
        pm.registerEvents(new GuiEvents(), this);
        pm.registerEvents(new Crafting(), this);
        pm.registerEvents(new Backpack(), this);
    }
}
