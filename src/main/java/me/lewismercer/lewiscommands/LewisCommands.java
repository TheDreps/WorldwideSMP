package me.lewismercer.lewiscommands;

import me.lewismercer.lewiscommands.commands.*;
import me.lewismercer.lewiscommands.commands.chat.Mute;
import me.lewismercer.lewiscommands.commands.chat.Nickname;
import me.lewismercer.lewiscommands.commands.chat.Reply;
import me.lewismercer.lewiscommands.commands.chat.Tell;
import me.lewismercer.lewiscommands.commands.eco.*;
import me.lewismercer.lewiscommands.commands.shop.Inventories;
import me.lewismercer.lewiscommands.commands.shop.Sell;
import me.lewismercer.lewiscommands.commands.shop.Store;
import me.lewismercer.lewiscommands.events.*;
import me.lewismercer.lewiscommands.permissions.Permissions;
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
        Permissions.addPermissions();
        this.saveDefaultConfig();


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
        getCommand("setrank").setExecutor(new SetRank());
        getCommand("tell").setExecutor(new Tell());
        getCommand("reply").setExecutor(new Reply());
        getCommand("mute").setExecutor(new Mute());
        getCommand("nickname").setExecutor(new Nickname());
        getCommand("store").setExecutor(new Store());


        //getCommand("map").setExecutor(new Map());
    }

    private void registerEvents(){
        pm.registerEvents(new PlayerDeath(), this);
        pm.registerEvents(new Permissions(), this);
        pm.registerEvents(new PlayerChat(), this);
        pm.registerEvents(new Inventories(), this);
    }
}
