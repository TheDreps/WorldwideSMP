package me.lewismercer.lewiscommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Map implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Map" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;

        sender.sendMessage(prefix + "http://worldwidesmp.com:8151");

        return false;
    }
}
