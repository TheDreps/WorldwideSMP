package me.lewismercer.lewiscommands;

import me.lewismercer.lewiscommands.commands.Map;
import me.lewismercer.lewiscommands.commands.Streamers;
import me.lewismercer.lewiscommands.commands.Sumo;
import me.lewismercer.lewiscommands.commands.eco.*;
import me.lewismercer.lewiscommands.events.PlayerDeath;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LewisCommands extends JavaPlugin {

    public static LewisCommands plugin;

    PluginManager pm = getServer().getPluginManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerEvents();

        plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(){
        //eco
        getCommand("balance").setExecutor(new Balance());
        getCommand("balancetop").setExecutor(new BalanceTop());
        getCommand("addbalance").setExecutor(new AddBalance());
        getCommand("takebalance").setExecutor(new TakeBalance());
        getCommand("setbalance").setExecutor(new SetBalance());
        getCommand("pay").setExecutor(new Pay());
        getCommand("sell").setExecutor(new Sell());



        getCommand("streamers").setExecutor(new Streamers());
        getCommand("sumo").setExecutor(new Sumo());
        getCommand("map").setExecutor(new Map());
        //getCommand("tpa").setExecutor(new Tpa());
    }

    private void registerEvents(){
        pm.registerEvents(new PlayerDeath(), this);
        //pm.registerEvents(new InventoryMenus(), this);
    }

}
