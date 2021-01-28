package me.lewismercer.lewiscommands.commands.eco;

import me.lewismercer.lewiscommands.api.EcoAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sell implements CommandExecutor {

    EcoAPI api = new EcoAPI();
    Double netheriteValue = 2000.00;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_PURPLE + "Eco" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
        String noPerms = prefix + "Sorry you do not have the required permissions.";
        String usage = prefix + "Usage: Do /sell while holding a netherite ingot in your hand. (For $" + netheriteValue + ")";

        if(!(sender instanceof Player)){
            sender.sendMessage("Sorry this command is intended for players only!");
            return false;
        }

        Player p = (Player) sender;

        if(p.hasPermission("lewiscommands.sell")){
            ItemStack hand = p.getInventory().getItemInMainHand();
            int slot = p.getInventory().getHeldItemSlot();
            if(hand.getType().equals(Material.NETHERITE_INGOT)){

                if(args.length >= 1 && args[0].equalsIgnoreCase("confirm")){
                    api.addBal(p.getUniqueId().toString(), netheriteValue);

                    if(hand.getAmount() == 1){
                        p.getInventory().clear(slot);
                    }else{
                        hand.setAmount(hand.getAmount() - 1);
                        p.getInventory().setItem(slot, hand);
                    }

                    p.sendMessage(prefix + "You successfully sold 1 netherite ingot for $" + netheriteValue + "!");

                    return true;

                }else{
                    p.sendMessage(prefix + "Sell 1 netherite ingot for $" + netheriteValue + "? Type /sell confirm if you are sure!");
                    return false;
                }


            }else{
                p.sendMessage(usage);
                return false;
            }
        }else{
            p.sendMessage(noPerms);
            return false;
        }
    }
}
