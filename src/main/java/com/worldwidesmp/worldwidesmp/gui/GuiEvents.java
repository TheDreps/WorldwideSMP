
/*

All the events to open up a GUI or cancel clicks on protected items

 */
package com.worldwidesmp.worldwidesmp.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GuiEvents implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){ //Opens the Crafting GUI

        Player p = e.getPlayer();

        Crafting craftingGui = new Crafting();

        if(e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.CRAFTING_TABLE)) {

            if(p.isSneaking() && !(p.getInventory().getItemInMainHand().getType().equals(Material.AIR))){
                //do nothing
                return;
            }

            e.setCancelled(true);
            craftingGui.openInventory(p);

        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){ //Cancels placeholder clicks
        if(e.getCurrentItem() != null && e.getCurrentItem().equals(Items.placeholder)){
            e.setCancelled(true);
        }
    }
}
