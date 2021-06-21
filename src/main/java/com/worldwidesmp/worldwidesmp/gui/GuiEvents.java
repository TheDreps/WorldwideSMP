
/*

All the events to open up a GUI or cancel clicks on protected items

 */
package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.worldwidesmp.worldwidesmp.gui.EnchantingTable.createGuiItem;

public class GuiEvents implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){ //Opens the Crafting GUI

        if(!(e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {

            Player p = e.getPlayer();


            if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.CRAFTING_TABLE)) {

                if (p.isSneaking() && !(p.getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
                    //do nothing
                    return;
                }

                e.setCancelled(true);
                Crafting craftingGui = new Crafting();
                craftingGui.openInventory(p);

            }

            else if(e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE)){

                if (p.isSneaking() && !(p.getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
                    //do nothing
                    return;
                }

                e.setCancelled(true);
                EnchantingTable enchantingTable = new EnchantingTable();
                enchantingTable.openInventory(p);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){ //Cancels placeholder clicks
        try {
            if (e.getCurrentItem() != null) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                if (name.equals(" ")||name.equals(ChatColor.RED+"Cannot enchant item!")||name.equals(ChatColor.RED+"Place an item to enchant!"))
                    e.setCancelled(true);
                else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Close")) {
                    e.getInventory().close();
                    Player a = (Player) e.getWhoClicked();
                    a.updateInventory();
                }
            }
        } catch(Exception ignored) {}
    }
}
