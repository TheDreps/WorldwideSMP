package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.apache.logging.log4j.core.appender.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.logger;
import static com.worldwidesmp.worldwidesmp.gui.Items.air;
import static com.worldwidesmp.worldwidesmp.gui.Items.backpack;

public class Backpack implements Listener {
    public Inventory backpackInv;
    public Inventory backup;
    public int invSize;
    public boolean backupEmpty = false;
    public boolean saved = true;

    public Backpack(int size) {
        invSize = size;
        backpackInv = Bukkit.createInventory(null, invSize, "Backpack");
    }

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
    public void clickEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand().getType() == Material.LEATHER) {
            if(saved) {
                ItemStack x = WorldwideSMP.plugin.config.getItemStack(e.getPlayer().getName());
                if (x != null)
                    for (int i = 0; i < WorldwideSMP.plugin.config.getInt(e.getPlayer().getName() + "Amount"); i++)
                        backpackInv.addItem(x);
                logger.info("restored");
                saved=false;
                openInventory(player, backpackInv);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickInventory(InventoryClickEvent e) {
        if (e.getInventory() == backpackInv) {
            if (e.getAction() != InventoryAction.HOTBAR_SWAP) {
                e.setCancelled(false);
                logger.info("click");
                if (isInventoryEmpty(backpackInv)) {
                    e.setCancelled(false);
                    logger.info("empty");
                } else {
                    logger.info("not empty");
                    if (e.getClickedInventory().getContents()[e.getSlot()] != null && inventoryItem(backpackInv).getType() != e.getClickedInventory().getContents()[e.getSlot()].getType())
                        e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onCloseInventory(InventoryCloseEvent e) {
        if (e.getInventory() == backpackInv&&!saved) {
            backupEmpty = true;
            ItemStack send = inventoryItem(backpackInv);
            WorldwideSMP.plugin.config.addDefault(e.getPlayer().getName()+"Amount", countItem(backpackInv));
            if (inventoryItem(backpackInv) != null)
                send.setAmount(1);
            WorldwideSMP.plugin.config.addDefault(e.getPlayer().getName(), send);
            logger.info("saved");
            for (int i = 0; i < backpackInv.getContents().length; i++) {
                backpackInv.setItem(i, air);
            }
            saved=true;
        }
    }
}
