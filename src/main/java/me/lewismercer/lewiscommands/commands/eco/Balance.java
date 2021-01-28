package me.lewismercer.lewiscommands.commands.eco;

import me.lewismercer.lewiscommands.api.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Balance implements CommandExecutor {

    EcoAPI api = new EcoAPI();



    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Eco" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
        String noPerms = prefix + "Sorry you do not have the required permissions.";
        String playerNotFound = prefix + "That player has never joined the server before.";
        String usage = prefix + "Usage: /bal";
        String usage2 = prefix + "Usage: /bal [player]";


        if (args.length == 0){

            if(sender.hasPermission("lewiscommands.balance")){

                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + "This command is for players only without a target player!");
                    return true;
                }

                Player player = (Player) sender;

                player.sendMessage(prefix + "You have $" + api.roundToTwoDecimalPlaces(api.getBal(player.getUniqueId().toString())));
            }else{
                sender.sendMessage(noPerms);
            }


            return true;


        }else if(args.length == 1){



            if(sender.hasPermission("lewiscommands.balance.others")){

                UUID offlineUUID = api.getOfflineUUID(args[0]);
                OfflinePlayer player = Bukkit.getOfflinePlayer(offlineUUID);

                if(!(player.hasPlayedBefore())){
                    sender.sendMessage(playerNotFound);
                    return true;
                }

                sender.sendMessage(prefix + player.getName() + "'s balance: $" + api.roundToTwoDecimalPlaces(api.getBal(offlineUUID.toString())));
            }else{
                sender.sendMessage(noPerms);
            }
            return true;

        }else{

            if(sender.hasPermission("lewiscommands.balance")){
                if(sender.hasPermission("lewiscommands.balance.others")){
                    sender.sendMessage(usage2);
                }else{
                    sender.sendMessage(usage);
                }
            }else{
                sender.sendMessage(noPerms);
            }

            return true;

        }
    }
}
