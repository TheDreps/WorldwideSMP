package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import com.worldwidesmp.worldwidesmp.utils.CraftingUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.logger;
import static com.worldwidesmp.worldwidesmp.WorldwideSMP.plugin;

public class Crafting implements Listener {
    private final Inventory inv;
    private final ItemStack ph = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);

    public Crafting() {
        inv = Bukkit.createInventory(null, 45, "Crafting Table");

        initializeItems();
    }

    public void initializeItems() {
        ItemMeta meta = ph.getItemMeta();
        meta.setDisplayName(" ");
        ph.setItemMeta(meta);
        for (int i = 0; i < 10; i++) {
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
        for (int i = 35; i < 45; i++) {
            inv.setItem(i, ph);
        }
    }

    public void openInventory(final HumanEntity e) {
        e.openInventory(inv);
    }

    public void givePlayer(HumanEntity player,ItemStack item){
        if(!player.getInventory().addItem(item).isEmpty())
        {
            World world = player.getWorld();
            world.dropItemNaturally(player.getLocation(), item);
        }
    }

    @EventHandler
    public void invCloseEvent(InventoryCloseEvent e){
        if (e.getView().getTitle().equals("Crafting Table")) {
            ArrayList<ItemStack> drops = new ArrayList<>();
            drops.add(e.getInventory().getItem(10));
            drops.add(e.getInventory().getItem(11));
            drops.add(e.getInventory().getItem(12));
            drops.add(e.getInventory().getItem(19));
            drops.add(e.getInventory().getItem(20));
            drops.add(e.getInventory().getItem(21));
            drops.add(e.getInventory().getItem(28));
            drops.add(e.getInventory().getItem(29));
            drops.add(e.getInventory().getItem(30));
            for(ItemStack i : drops)
                if(i!=null)
                    givePlayer(e.getPlayer(), i);
        }
    }

    @EventHandler
    public void invDragEvent(InventoryDragEvent e) {
        if (e.getView().getTitle().equals("Crafting Table")) {
            if(e.getView().getTopInventory().getItem(23)==null&&e.getRawSlots().contains(23))
                e.setCancelled(true);
            if (e.getView().getTopInventory().equals(e.getInventory()) && e.getInventorySlots().iterator().next() == 23) {
                if (e.getOldCursor().getAmount() != 0) {
                    e.setCancelled(true);
                } else {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            removeCraftingSlots(e.getView().getTopInventory());
                            ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                            setResult(result, e.getView());
                        }
                    }.runTaskLaterAsynchronously(plugin, 2);
                }
            } else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                        setResult(result, e.getView());
                    }
                }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
            }
        }
    }


    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Crafting Table")) {
            if (e.getView().getTopInventory().equals(e.getClickedInventory()) && e.getSlot() == 23){
                if(e.getAction().equals(InventoryAction.DROP_ONE_SLOT))
                {
                    e.setCancelled(true);
                }
                else {
                    ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                    setResult(result, e.getView());
                    if (e.isShiftClick()) {
                        e.setCancelled(true);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                while (CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked()).equals(result)) {
                                    if (!(e.getView().getBottomInventory().addItem(result).isEmpty())) {

                                        ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                                        setResult(result, e.getView());
                                        break;
                                    }
                                    removeCraftingSlots(e.getView().getTopInventory());
                                }
                                e.setCancelled(false);
                            }
                        }.runTaskLaterAsynchronously(plugin, 2);
                    } else {
                        if (e.getCursor().getAmount() == 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    removeCraftingSlots(e.getView().getTopInventory());
                                    ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                                    setResult(result, e.getView());
                                }
                            }.runTaskLaterAsynchronously(plugin, 2);
                        } else {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (e.getCursor().getAmount() == 0) {
                                        e.setCursor(e.getView().getTopInventory().getItem(23));
                                        removeCraftingSlots(e.getView().getTopInventory());
                                    } else {
                                        ItemStack cursor = e.getCursor();
                                        e.setCursor(e.getView().getTopInventory().getItem(23));
                                        e.getView().getTopInventory().setItem(23, cursor);
                                    }
                                    ItemStack result = CraftingUtils.getCraftingResult(e.getInventory(), e.getWhoClicked());
                                    setResult(result, e.getView());
                                }
                            }.runTaskLaterAsynchronously(plugin, 2);
                        }
                    }
                }
            }
            else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
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