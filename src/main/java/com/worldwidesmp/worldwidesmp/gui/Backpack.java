package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.meta.ItemMeta;
//import static com.worldwidesmp.worldwidesmp.gui.Items.backpack;
import java.util.ArrayList;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.*;

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

    private void saveItems(Inventory inv,HumanEntity player) {
        ItemStack send = inventoryItem(inv);
        String id = WorldwideSMP.plugin.config.getString(player.getUniqueId().toString());
        plugin.config.addDefault("temp2",id);
        WorldwideSMP.plugin.config.set(player.getUniqueId().toString(),null);
        try {
            WorldwideSMP.plugin.config.set(id + " Amount", countItem(inv));
        } catch (NullPointerException err) {
            WorldwideSMP.plugin.config.addDefault(id + " Amount", countItem(inv));
        }
        if (inventoryItem(inv) != null)
            send.setAmount(1);
        try {
            WorldwideSMP.plugin.config.set(id, send);
        } catch (NullPointerException err) {
            WorldwideSMP.plugin.config.addDefault(id, send);
        }
        WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
        WorldwideSMP.plugin.saveConfig();
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void playerClickEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        try {
            if (e.getItem().getItemMeta().getDisplayName().equals("Backpack")) {
                Inventory inv = Bukkit.createInventory(null, 45, "Backpack");
                String lore = e.getItem().getItemMeta().getLore().get(0);
                ItemStack x = WorldwideSMP.plugin.config.getItemStack(e.getItem().getItemMeta().getLore().get(0));
                plugin.config.addDefault("temp3",e.getItem().getItemMeta().getLore().get(0));
                if(lore.equals(" "))
                {
                    ItemMeta meta = e.getItem().getItemMeta();
                    ArrayList<String> metaLore = new ArrayList<>();
                    metaLore.add(Items.convertToInvisibleString(String.valueOf(WorldwideSMP.plugin.config.getInt("Last"))));
                    plugin.config.addDefault("temp",Items.convertToInvisibleString("15"));
                    plugin.config.set("Last",plugin.config.getInt("Last")+1);
                    meta.setLore(metaLore);
                    e.getItem().setItemMeta(meta);
                }
                if (x != null) {
                    logger.info("x isnt null");
                    int condition = WorldwideSMP.plugin.config.getInt(Items.convertToInvisibleString(String.valueOf(WorldwideSMP.plugin.config.getInt("Last"))) + " Amount");
                    for (int i = 0; i < condition; i++)
                        inv.addItem(x);
                }
                openInventory(player, inv);
                try {
                    WorldwideSMP.plugin.config.set(e.getPlayer().getUniqueId().toString(), Items.convertToInvisibleString(String.valueOf(WorldwideSMP.plugin.config.getInt("Last"))));
                } catch(NullPointerException err){
                    WorldwideSMP.plugin.config.addDefault(e.getPlayer().getUniqueId().toString(), Items.convertToInvisibleString(String.valueOf(WorldwideSMP.plugin.config.getInt("Last"))));
                }

                WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
                WorldwideSMP.plugin.saveConfig();
//                WorldwideSMP.plugin.getConfig().options().copyDefaults(false);
//                WorldwideSMP.plugin.saveDefaultConfig();
            }
        } catch (NullPointerException ignored) { }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickInventory(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Backpack")) {
            if (e.getAction() != InventoryAction.HOTBAR_SWAP) {
                e.setCancelled(false);
                if (isInventoryEmpty(e.getInventory())) {
                    e.setCancelled(false);
                } else if (e.getClickedInventory().getContents()[e.getSlot()] != null && inventoryItem(e.getInventory()).getType() != e.getClickedInventory().getContents()[e.getSlot()].getType()) {
                    e.setCancelled(true);
                    e.getWhoClicked().sendMessage(ChatColor.RED + "You can only put one type of item, inside a backpack!");
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onCloseInventory(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals("Backpack")) {
            saveItems(e.getInventory(), e.getPlayer());
        }
    }
}
