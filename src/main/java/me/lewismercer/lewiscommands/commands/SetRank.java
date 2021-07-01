package me.lewismercer.lewiscommands.commands;

import me.lewismercer.lewiscommands.api.PermissionsAPI;
import me.lewismercer.lewiscommands.permissions.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRank implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        PermissionsAPI api = new PermissionsAPI();

        if(sender instanceof Player){
            sender.sendMessage("This command must be ran from console!");
            return false;
        }

        if(args.length == 2){
            Player target = Bukkit.getPlayerExact(args[0]);
            Rank rank = Rank.valueOf(args[1]);

            api.setRank(target.getUniqueId(), rank);
        }else{
            sender.sendMessage("/setrank <player> <rank>");
        }


        return false;
    }
}
