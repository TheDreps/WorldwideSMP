package me.lewismercer.lewiscommands.commands.chat;

import me.lewismercer.lewiscommands.LewisCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Mute implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Mute" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;

        if(!(sender instanceof Player)){
            sender.sendMessage("Sorry nope");
            return false;
        }
        Player p = (Player) sender;

        if(!(p.getName().equalsIgnoreCase("TheDreps") || p.getName().equalsIgnoreCase("kittyisuwu"))){
            if(args[0].equalsIgnoreCase("thedreps") || args[0].equalsIgnoreCase("ilykota") || args[0].equalsIgnoreCase("IdaT") || args[0].equalsIgnoreCase("kittyisuwu")){
                Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + sender.getName() + " just tried to mute " + args[0] + ", " + ChatColor.DARK_RED + ChatColor.BOLD + "L");
                return false;
            }
        }

        if(!(sender.hasPermission("lewiscommands.mute"))){
            sender.sendMessage(prefix + "No perms L");
            return false;
        }



        if(args.length == 0){
            p.sendMessage(prefix + "/mute <player>");
            return false;
        }

        if(!(Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline())){
            p.sendMessage(prefix + "Couldn't find player \"" + args[0] + "\"");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        String targetName = target.getName().toLowerCase();
        List<String> players = LewisCommands.plugin.getConfig().getStringList("Muted");

        if(players.contains(targetName)){
            players.remove(targetName);
            p.sendMessage(prefix + args[0] + " is no longer muted");
        }else{
            players.add(targetName);
            p.sendMessage(prefix + args[0] + " is now muted");
        }

        LewisCommands.plugin.getConfig().set("Muted", players);
        LewisCommands.plugin.saveConfig();


        return false;
    }
}
