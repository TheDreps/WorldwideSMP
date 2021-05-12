package com.worldwidesmp.worldwidesmp.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Crafting implements Listener {
    private final Inventory inv;
    private final ItemStack ph = Items.placeholder;

    public Crafting(){
        inv = Bukkit.createInventory(null, 45, Component.text("Crafting Table"));

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

    }




}
