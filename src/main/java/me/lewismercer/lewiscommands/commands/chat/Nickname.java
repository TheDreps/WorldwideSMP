package me.lewismercer.lewiscommands.commands.chat;

import me.lewismercer.lewiscommands.api.NicknameAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Nickname implements CommandExecutor {

    NicknameAPI api = new NicknameAPI();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        String prefix = ChatColor.DARK_PURPLE + "Nickname" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;

        if(!(sender instanceof Player)){
            sender.sendMessage("Players only");
            return false;
        }

        Player p = (Player) sender;

        if(!(p.hasPermission("lewiscommands.nickname"))){
            p.sendMessage(ChatColor.DARK_RED + "Sorry, you must be a " + ChatColor.BOLD + "SUPPORTER" + ChatColor.RESET + ChatColor.DARK_RED + " rank to use this!");
            return false;
        }

        if(args.length == 0){
            if(p.hasPermission("lewiscommands.nickname.others")){
                p.sendMessage(prefix + "/nick <nickname> [player]");
            }else{
                p.sendMessage(prefix + "/nick <nickname>");
            }

            return false;
        }else if (args.length == 1 || !(p.hasPermission("lewiscommands.nickname.others"))){
            if(args[0].length() > 16){
                p.sendMessage(prefix + "Can not have nickname over 16 characters");
                return false;
            }
            setNick(args[0], p);

            if(args[0].equals("reset")){
                p.sendMessage(prefix + "Cleared your nickname!");
                return true;
            }

            p.sendMessage(prefix + "Set your nickname to: " + ChatColor.translateAlternateColorCodes('&', args[0]));
            return true;
        }else{
            if(!(Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline())){
                p.sendMessage(prefix + "Couldn't find player \"" + args[1] + "\"");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);


            if(p.hasPermission("lewiscommands.nickname.others")){
                if(args[0].length() > 16){
                    p.sendMessage(prefix + "Can not have nickname over 16 characters");
                    return false;
                }
                setNick(args[0], target);
                if(args[0].equals("reset")){
                    p.sendMessage(prefix + "Cleared " + target.getName() + "'s nickname!");
                    return true;
                }
                p.sendMessage(prefix + "Set " + target.getName() + "'s nickname to: " + ChatColor.translateAlternateColorCodes('&', args[0]));
                return true;
            }
        }



        return false;
    }

    public void setNick(String nick, Player p){
        api.setNick(p.getUniqueId(), nick);
    }
}
