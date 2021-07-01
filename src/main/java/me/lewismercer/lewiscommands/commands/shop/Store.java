package me.lewismercer.lewiscommands.commands.shop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Store implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Inventories invs = new Inventories();


        if(!(sender instanceof Player)){
            sender.sendMessage("This is for players only sorry!");
            return false;
        }

        Player p = (Player) sender;

        if(p.hasPermission("lewiscommands.store")){
            invs.openShop(p);
        }

        return false;
    }


}
