package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import com.worldwidesmp.worldwidesmp.utils.CraftingUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.logger;

public class Crafting implements Listener {
    private final Inventory inv;
    private final ItemStack ph = Items.placeholder;

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
        inv.setItem(16, ph);
        inv.setItem(17, ph);
        inv.setItem(18, ph);
        inv.setItem(22, ph);
        inv.setItem(24, ph);
        inv.setItem(25, ph);
        inv.setItem(26, ph);
        inv.setItem(27, ph);
        inv.setItem(31, ph);
        inv.setItem(32, ph);
        inv.setItem(33, ph);
        inv.setItem(34, ph);
        for(int i = 35; i<45; i++){
            inv.setItem(i, ph);
        }
    }

    public void openInventory(final HumanEntity e) {
        e.openInventory(inv);
    }

    @EventHandler
    public void invDragEvent(InventoryDragEvent e){
        logger.info("something happened");
        if (e.getView().getTitle().equals("Crafting Table")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                    setResult(result, e.getView());
                }
            }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
        }
    }

    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        logger.info("something happened");
        if (e.getView().getTitle().equals("Crafting Table")) {
            if (e.getSlot() == 23 && e.getView().getTopInventory().equals(e.getClickedInventory())&&e.getView().getTopInventory().getItem(23) != null) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                            if (e.isShiftClick()) {
                                logger.info("shiftClicked!");
                                ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                                setResult(result, e.getView());
                                removeCraftingSlots(e.getView().getTopInventory());
                                while (CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked()).equals(result)) {
                                    e.getView().getBottomInventory().addItem(result);
                                    removeCraftingSlots(e.getView().getTopInventory());
                                }
                            } else {
                                logger.info("clicked on 23");
                                removeCraftingSlots(e.getView().getTopInventory());
                            }

                            ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                            setResult(result, e.getView());
                    }
                }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
            }
        }
    }

    private void reduceSlot(int i,Inventory inv){
        ItemStack is = inv.getItem(i);
        if(is!=null&&is.getAmount()>0) {
            is.setAmount(is.getAmount() - 1);
            inv.setItem(i, is);
        }
    }

    private void removeCraftingSlots(Inventory inv){
        reduceSlot(10, inv);
        reduceSlot(11, inv);
        reduceSlot(12, inv);
        reduceSlot(19, inv);
        reduceSlot(20, inv);
        reduceSlot(21, inv);
        reduceSlot(28, inv);
        reduceSlot(29, inv);
        reduceSlot(30, inv);
        reduceSlot(23, inv);
    }

    private void setResult(ItemStack result, InventoryView iv){

        iv.getTopInventory().setItem(23, result);

    }
}