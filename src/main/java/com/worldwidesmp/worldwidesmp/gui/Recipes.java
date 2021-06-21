package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import com.worldwidesmp.worldwidesmp.utils.CraftingUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.*;

public class Recipes implements Listener{

    public void Recipes(){

    }

//    @EventHandler
//    public void invClickEvent(InventoryClickEvent e) {
//        if(e.getView().getTitle().equals("Crafting Table")){
//            logger.info("name correct");
//            if(e.getSlot()==23&&e.getClickedInventory().equals(e.getView().getTopInventory())&&e.getClickedInventory().getItem(23).getItemMeta().getDisplayName().equals("Backpack"))
//            {
//                logger.info("happened lol");
//                ItemStack clicked = e.getClickedInventory().getItem(23);
//                ItemMeta clickedMeta = clicked.getItemMeta();
//                ArrayList<String> metaLore = new ArrayList<>();
//                metaLore.add(Items.convertToInvisibleString(String.valueOf(WorldwideSMP.plugin.config.getInt("Last"))));
//                plugin.config.set("Last",plugin.config.getInt("Last")+1);
//                clickedMeta.setLore(metaLore);
//                clicked.setItemMeta(clickedMeta);
//                e.getClickedInventory().setItem(23,clicked);
//            }
//        }
//    }

}