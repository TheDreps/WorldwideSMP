package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import static com.worldwidesmp.worldwidesmp.gui.Items.air;

public class Backpack implements Listener {

    public void openInventory(final HumanEntity e, Inventory inventory) {
        e.openInventory(inventory);
    }

    public boolean isInventoryEmpty(Inventory inventory) {
        for (ItemStack i : inventory.getContents()) {
            if (i != null)
                return false;
        }
        return true;
    }

    public ItemStack inventoryItem(Inventory inventory) {
        for (ItemStack i : inventory.getContents()) {
            if (i != null)
                return i;
        }
        return null;
    }

    public int countItem(Inventory inventory) {
        int count = 0;
        for (ItemStack i : inventory.getContents()) {
            if (i != null)
                count += i.getAmount();
        }
        return count;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void playerClickEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand().getType() == Material.LEATHER) {
                Inventory inv = Bukkit.createInventory(null, 45, "Backpack");
                ItemStack x = WorldwideSMP.plugin.config.getItemStack(e.getPlayer().getName());
                if (x != null)
                    for (int i = 0; i < WorldwideSMP.plugin.config.getInt(e.getPlayer().getName() + "Amount"); i++)
                        inv.addItem(x);
//                logger.info("restored");
                openInventory(player, inv);

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickInventory(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Backpack")) {
            if (e.getAction() != InventoryAction.HOTBAR_SWAP) {
                e.setCancelled(false);
//                logger.info("click");
                if (isInventoryEmpty(e.getInventory())) {
                    e.setCancelled(false);
//                    logger.info("empty");
                } else {
//                    logger.info("not empty");
                    if (e.getClickedInventory().getContents()[e.getSlot()] != null && inventoryItem(e.getInventory()).getType() != e.getClickedInventory().getContents()[e.getSlot()].getType())
                        e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onCloseInventory(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals("Backpack")) {
            Inventory inv = e.getInventory();
            ItemStack send = inventoryItem(inv);
            WorldwideSMP.plugin.config.addDefault(e.getPlayer().getName()+"Amount", countItem(inv));
            if (inventoryItem(inv) != null)
                send.setAmount(1);
            WorldwideSMP.plugin.config.addDefault(e.getPlayer().getName(), send);
//            logger.info("saved");
            for (int i = 0; i < inv.getContents().length; i++) {
                inv.setItem(i, air);
            }
        }
    }
}
