package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import com.worldwidesmp.worldwidesmp.utils.CraftingUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Crafting implements Listener {
    private final Inventory inv;
    private final ItemStack ph = Items.placeholder;
    private final ItemStack air = Items.air;

    public Crafting(){
        inv = Bukkit.createInventory(null, 45, "Crafting Table");

        initializeItems();
    }

    public void initializeItems(){


        for(int i = 0; i<10; i++){
            inv.setItem(i, ph);
        }
        inv.setItem(13, ph);
        inv.setItem(14, ph);
        inv.setItem(15, ph);
        inv.setItem(17, ph);
        inv.setItem(18, ph);
        inv.setItem(22, ph);
        inv.setItem(24, ph);
        inv.setItem(26, ph);
        inv.setItem(27, ph);
        inv.setItem(31, ph);
        inv.setItem(32, ph);
        inv.setItem(33, ph);
        for(int i = 35; i<45; i++){
            inv.setItem(i, ph);
        }
    }


    public void openInventory(final HumanEntity e) {
        e.openInventory(inv);
    }

    @EventHandler
    public void invClickEvent(InventoryClickEvent e){

        HashMap<Integer, Integer> cc = new HashMap<>();
        cc.put(1, 10);
        cc.put(2, 11);
        cc.put(3, 12);
        cc.put(4, 19);
        cc.put(5, 20);
        cc.put(6, 21);
        cc.put(7, 28);
        cc.put(8, 29);
        cc.put(9, 30);
        cc.put(10, 23);

        ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
        if (result.getType() != Material.AIR){

            if(e.getSlot() == 23 && e.getView().getTopInventory().equals(e.getClickedInventory())){
                //
            }


            new BukkitRunnable(){
                @Override
                public void run() {
                    setResult(result, e.getView());
                }
            }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);

            Bukkit.getLogger().info(result.getType().toString() + ", " + result.getAmount());
        }else{

            new BukkitRunnable(){
                @Override
                public void run() {
                    setResult(air, e.getView());
                }
            }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);

        }

    }

    private void setResult(ItemStack result, InventoryView iv){

        iv.getTopInventory().setItem(23, result);

    }




}
