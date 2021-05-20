package com.worldwidesmp.worldwidesmp;

import com.worldwidesmp.worldwidesmp.gui.Backpack;
import com.worldwidesmp.worldwidesmp.gui.Crafting;
import com.worldwidesmp.worldwidesmp.gui.GuiEvents;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
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
        pm.registerEvents(new Backpack(45), this);
    }
}
