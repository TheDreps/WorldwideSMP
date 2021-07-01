package me.lewismercer.lewiscommands.commands.chat;

import me.lewismercer.lewiscommands.api.PermissionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Tell implements CommandExecutor{

    PermissionsAPI api = new PermissionsAPI();

    public static HashMap<UUID, UUID> lastReceived = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Messaging" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;

        if(!(sender instanceof Player)){
            sender.sendMessage("Players only :/");
            return false;
        }

        Player p = (Player) sender;

        if(args.length < 2){
            p.sendMessage(prefix + "/t <username> <message>");
            return false;
        }

        if(!(Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline())){
            p.sendMessage(prefix + "Couldn't find player \"" + args[0] + "\"");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i<args.length; i++){
            sb.append(args[i]).append(" ");
        }

        target.sendMessage(api.getChatName(p) + ChatColor.GRAY + ChatColor.BOLD + " -> " + ChatColor.RESET  + api.getColour(api.getRank(target.getUniqueId())) + "You " + ChatColor.RESET + sb);
        p.sendMessage(api.getColour(api.getRank(p.getUniqueId())) + "You" + ChatColor.GRAY + ChatColor.BOLD + " -> " + api.getChatName(target) + ChatColor.RESET + " " + sb);

        lastReceived.put(target.getUniqueId(), p.getUniqueId());

        return true;
    }
}
