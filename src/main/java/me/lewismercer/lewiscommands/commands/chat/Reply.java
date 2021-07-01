package me.lewismercer.lewiscommands.commands.chat;

import me.lewismercer.lewiscommands.api.PermissionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reply implements CommandExecutor {

    PermissionsAPI api = new PermissionsAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        String prefix = ChatColor.DARK_PURPLE + "Messaging" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;

        if(!(sender instanceof Player)){
            sender.sendMessage("Players only :/");
            return false;
        }

        Player p = (Player) sender;

        if(args.length < 1){
            p.sendMessage(prefix + "/r <message>");
            return false;
        }

        if(!(Tell.lastReceived.containsKey(p.getUniqueId()))){
            p.sendMessage(prefix + "Not messaged anyone recently!");
            return false;
        }

        if(Bukkit.getPlayer(Tell.lastReceived.get(p.getUniqueId())) == null){
            p.sendMessage(prefix + "Player is no longer online");
            return false;
        }

        Player target = Bukkit.getPlayer(Tell.lastReceived.get(p.getUniqueId()));

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<args.length; i++){
            sb.append(args[i]).append(" ");
        }

        target.sendMessage(api.getChatName(p) + ChatColor.GRAY + ChatColor.BOLD + " -> " + ChatColor.RESET  + api.getColour(api.getRank(target.getUniqueId())) + "You " + ChatColor.RESET + sb);
        p.sendMessage(api.getColour(api.getRank(p.getUniqueId())) + "You" + ChatColor.GRAY + ChatColor.BOLD + " -> " + api.getChatName(target) + ChatColor.RESET + " " + sb);


        Tell.lastReceived.put(target.getUniqueId(), p.getUniqueId());

        return true;
    }
}
