package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.worldwidesmp.worldwidesmp.WorldwideSMP.logger;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Telekenises implements Listener {

    public Telekenises() {

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        try {
            if (e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.BLUE+"Telekenises")) {
                e.setDropItems(false);

                if(!e.getPlayer().getInventory().addItem(e.getBlock().getDrops(e.getPlayer().getItemInHand()).iterator().next()).isEmpty())
                {
                    World world = e.getPlayer().getWorld();
                    Location location = e.getBlock().getLocation();
                    world.dropItemNaturally(location, e.getBlock().getDrops(e.getPlayer().getItemInHand()).iterator().next());
                }
            }
        } catch(Exception ignored){}
    }
}
