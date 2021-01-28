package me.lewismercer.lewiscommands.commands.eco;

import me.lewismercer.lewiscommands.api.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TakeBalance implements CommandExecutor {

    EcoAPI api = new EcoAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        String prefix = ChatColor.DARK_PURPLE + "Eco" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
        String noPerms = prefix + "Sorry you do not have the required permissions.";
        String playerNotFound = prefix + "That player has never joined the server before.";
        String usage = prefix + "Usage: /takebal <amount> [player]";

        if (args.length == 0){ //addbal

            if(sender.hasPermission("lewiscommands.setbalance")){
                sender.sendMessage(usage);
            }else{
                sender.sendMessage(noPerms);
            }
            return true;


        }else if(args.length == 1){  //takebal <amount>

            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + "This command is for players only without a target player!");
                return true;
            }

            Player player = (Player) sender;

            if(player.hasPermission("lewiscommands.setbalance")){



                float amount;

                try{
                    amount = Float.parseFloat(args[0]);
                    if(amount < 0){
                        player.sendMessage(prefix + "Please use a positive amount.");
                        return true;
                    }

                }catch (NumberFormatException e){
                    player.sendMessage(prefix + "Invalid number!");
                    return true;
                }

                if (!api.checkBal(player.getUniqueId().toString(), amount)) {

                    player.sendMessage(prefix + player.getDisplayName() + " cannot afford this!  " + player.getDisplayName() + " has $" + api.roundToTwoDecimalPlaces(api.getBal(player.getUniqueId().toString())) + ".");

                }

                api.takeBal(player.getUniqueId().toString(), amount);

                player.sendMessage(prefix + "Took $" + api.roundToTwoDecimalPlaces(amount) +  " from your balance!");
            }else{
                player.sendMessage(noPerms);
            }
            return true;


        }else if(args.length == 2){ //takebal [player] <amount>

            if(sender.hasPermission("lewiscommands.setbalance")){
                OfflinePlayer targetOfflinePlayer = Bukkit.getOfflinePlayer(api.getOfflineUUID(args[0]));

                if(!(targetOfflinePlayer.hasPlayedBefore())){
                    sender.sendMessage(playerNotFound);
                    return true;
                }

                String uuid = targetOfflinePlayer.getUniqueId().toString();

                float amount;

                try{
                    amount = Float.parseFloat(args[1]);
                    if(amount < 0){
                        sender.sendMessage(prefix + "Please use a positive amount.");
                        return true;
                    }
                }catch (NumberFormatException e){
                    sender.sendMessage(prefix + "Invalid number!");
                    return true;
                }

                api.takeBal(uuid, amount);

                sender.sendMessage(prefix + "Took $" + api.roundToTwoDecimalPlaces(amount) + " from " + api.getOfflineName(args[0]) + "'s balance!");

                return true;
            }else{
                sender.sendMessage(noPerms);
                return true;
            }

        }else{

            if(sender.hasPermission("lewiscommands.setbalance")){
                sender.sendMessage(usage);
            }else{
                sender.sendMessage(noPerms);
            }

            return true;

        }
    }
}
