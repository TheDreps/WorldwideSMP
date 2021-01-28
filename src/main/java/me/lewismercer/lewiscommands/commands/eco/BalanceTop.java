package me.lewismercer.lewiscommands.commands.eco;

import me.lewismercer.lewiscommands.api.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.UUID;

public class BalanceTop implements CommandExecutor {

    EcoAPI api = new EcoAPI();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Eco" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
        String noPerms = prefix + "Sorry you do not have the required permissions.";


        if(!sender.hasPermission("lewiscommands.balance.others")){
            sender.sendMessage(noPerms);
            return false;
        }


        StringBuilder sb = new StringBuilder(prefix + "Top 5 players with the largest balance:\n" + ChatColor.DARK_GRAY + "-\n");

        ArrayList<String> uuidList = api.getTopBal();
        for(int i = 0; i < uuidList.size(); i++){
            int pos = i + 1;
            sb.append(ChatColor.LIGHT_PURPLE + "#" + pos + " " + ChatColor.RESET +  Bukkit.getOfflinePlayer(UUID.fromString(uuidList.get(i))).getName() + ChatColor.GRAY + " $" + api.roundToTwoDecimalPlaces(api.getBal(uuidList.get(i))) +
                    "\n");
        }

        sb.append(ChatColor.DARK_GRAY + "-\n");

        sender.sendMessage(sb.toString());
        return true;
    }
}
