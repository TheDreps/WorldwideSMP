package me.lewismercer.lewiscommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Tpa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Double cost = 1500.00;

        //  /tpa <player>


        if(!(sender instanceof Player)){
            sender.sendMessage("This is only for diamonds!");
            return false;
        }

        Player p = (Player) sender;

        if(args.length == 0){
            p.sendMessage(ChatColor.RED + "Usage: /tpa <player>");
        }

        if(!(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1])))){
            p.sendMessage("Can not find player!");
        }else{ //correct syntax and the player is online

            Inventory pInv = p.getInventory();



            return true;
        }


        return false;
    }
}
