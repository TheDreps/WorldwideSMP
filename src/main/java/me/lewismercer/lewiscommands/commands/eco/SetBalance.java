package me.lewismercer.lewiscommands.commands.eco;

import me.lewismercer.lewiscommands.api.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBalance implements CommandExecutor {

    EcoAPI api = new EcoAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Eco" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
        String noPerms = prefix + "Sorry you do not have the required permissions.";
        String playerNotFound = prefix + "That player has never joined the server before.";
        String usage = prefix + "Usage: /setbal <amount> [player]";

        if (args.length == 0){ //setbal

            if(sender.hasPermission("lewiscommands.setbalance")){
                sender.sendMessage(usage);
                return true;
            }else{
                sender.sendMessage(noPerms);
                return true;
            }




        }else if(args.length == 1){ //setbal <amount>

            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + "This command is for players only without a target player!");
                return true;
            }

            Player p = (Player) sender;

            if(p.hasPermission("lewiscommands.setbalance")){

                float amount;

                try{
                    amount = Float.parseFloat(args[0]);
                    if(amount < 0){
                        p.sendMessage(prefix + "Please use a positive amount.");
                        return true;
                    }
                }catch (NumberFormatException e){
                    p.sendMessage(prefix + "Invalid number!");
                    return true;
                }

                api.setBal(p.getUniqueId().toString(), amount);

                p.sendMessage(prefix + "Set your balance to $" + api.roundToTwoDecimalPlaces(amount) + "!");
                return true;
            }else{
                p.sendMessage(noPerms);
                return true;
            }


        }else if(args.length == 2){ //setbal [player] <amount>

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

                api.setBal(uuid, amount);

                sender.sendMessage(prefix + "Set " + api.getOfflineName(args[0]) + "'s balance to $" + api.roundToTwoDecimalPlaces(amount) + "!");

            }else{
                sender.sendMessage(noPerms);
            }
            return true;

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
