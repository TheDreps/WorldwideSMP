package me.lewismercer.lewiscommands.commands.eco;

import me.lewismercer.lewiscommands.api.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pay implements CommandExecutor {

    EcoAPI api = new EcoAPI();

    /*

    p = player (command sender)
    p2 = player (target player)

     */


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Eco" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
        String noPerms = prefix + "Sorry you do not have the required permissions.";
        String playerNotFound = prefix + "That player has never joined the server before.";
        String usage = prefix + "Usage: /pay <player> <amount>";

        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "This command is for players only!");
            return true;
        }

        Player p = (Player) sender;

        if (args.length <= 1) {

            if (p.hasPermission("lewiscommands.pay")) {
                p.sendMessage(usage);
            } else {
                p.sendMessage(noPerms);
            }
            return true;

        } else if (args.length == 2) {

            if (p.hasPermission("lewiscommands.pay")) {
                OfflinePlayer p2 = Bukkit.getOfflinePlayer(api.getOfflineUUID(args[0]));

                if(!(p2.hasPlayedBefore())){
                    p.sendMessage(playerNotFound);
                    return true;
                }

                String uuid = p.getUniqueId().toString();
                String uuid2 = api.getOfflineUUID(args[0]).toString();

                float amount;

                try{
                    amount = Float.parseFloat(args[1]);
                    if(amount < 0){
                        p.sendMessage(prefix + "Please use a positive amount.");
                        return true;
                    }
                }catch (NumberFormatException e){
                    p.sendMessage(prefix + "Invalid number!");
                    return true;
                }




                if(api.checkBal(uuid, amount)){
                    api.addBal(uuid2, amount);
                    api.takeBal(uuid, amount);

                    p.sendMessage(prefix + "You sent $" + api.roundToTwoDecimalPlaces(amount) + " to " + api.getOfflineName(args[0]));

                    if (p2.isOnline()) {
                        Player player2online = (Player) p2;
                        player2online.sendMessage(prefix + "You received $" + api.roundToTwoDecimalPlaces(amount) + " from " + p.getDisplayName() + "!");
                    }

                }else{
                    p.sendMessage(prefix + "You can't afford this!");
                }

            } else {
                p.sendMessage(noPerms);
            }
            return true;

        } else {

            if (p.hasPermission("lewiscommands.pay")) {
                p.sendMessage(usage);
            } else {
                p.sendMessage(noPerms);
            }

            return true;
        }
    }
}
