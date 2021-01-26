package me.lewismercer.lewiscommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sumo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("nope");
            return false;
        }

        Player p = (Player) sender;
        Location loc = p.getLocation();
        Location sumo = new Location(p.getWorld(), 485.52, 135.00, -215.50, 1250f, 1.50f);

        if(loc.getX() > 424 && loc.getX() < 544 && loc.getY() > 30 && loc.getY() < 144 && loc.getZ() < -138 && loc.getZ() > -261 && loc.getWorld().getName().equalsIgnoreCase("world")){
            if(p.getHealth() == 20){
                p.teleport(sumo);
                return true;
            }else{
                p.sendMessage(ChatColor.RED + "Must be fully healed to teleport!");
            }
        }else{
            p.sendMessage(ChatColor.RED + "Please move closer to the platform to use this command!");
        }

        return false;
    }
}
