package com.worldwidesmp.worldwidesmp;

import com.worldwidesmp.worldwidesmp.gui.Crafting;
import com.worldwidesmp.worldwidesmp.gui.GuiEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldwideSMP extends JavaPlugin {

    public static WorldwideSMP plugin;

    PluginManager pm = Bukkit.getPluginManager();

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
    }
}
