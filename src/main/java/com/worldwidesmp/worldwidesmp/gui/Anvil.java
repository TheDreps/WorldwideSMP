package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.utils.CraftingUtils;
import net.minecraft.world.inventory.ClickAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.givePlayer;
import static com.worldwidesmp.worldwidesmp.WorldwideSMP.plugin;
import static com.worldwidesmp.worldwidesmp.gui.EnchantingTable.createGuiItem;

public class Anvil implements Listener {

    private final ItemStack gray = createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ");
    private final ItemStack yellow = createGuiItem(Material.YELLOW_STAINED_GLASS_PANE," ");
    private final ItemStack green = createGuiItem(Material.GREEN_STAINED_GLASS_PANE," ");
    private final ItemStack red = createGuiItem(Material.RED_STAINED_GLASS_PANE," ");
    private final ItemStack barrier = createGuiItem(Material.BARRIER, ChatColor.RED+"Close");

    Inventory inv;

    public Anvil() {
        inv = Bukkit.createInventory(null, 54, "Anvil");
        initializeItems();
    }

    public void initializeItems() {
        inv.setItem(0, gray);
        inv.setItem(1, gray);
        inv.setItem(2, gray);
        inv.setItem(3, gray);
        inv.setItem(4, gray);
        inv.setItem(5, gray);
        inv.setItem(6, gray);
        inv.setItem(7, gray);
        inv.setItem(8, gray);
        inv.setItem(9, gray);
        inv.setItem(10, gray);
        inv.setItem(11, red);
        inv.setItem(12, red);
        inv.setItem(14, red);
        inv.setItem(15, red);
        inv.setItem(16, gray);
        inv.setItem(17, gray);
        inv.setItem(18, gray);
        inv.setItem(19, gray);
        inv.setItem(20, red);
        inv.setItem(21, gray);
        inv.setItem(22, gray);
        inv.setItem(23, gray);
        inv.setItem(24, red);
        inv.setItem(25, gray);
        inv.setItem(26, gray);
        inv.setItem(27, gray);
        inv.setItem(28, gray);
        inv.setItem(30, gray);
        inv.setItem(31, gray);
        inv.setItem(32, gray);
        inv.setItem(34, gray);
        inv.setItem(35, gray);
        inv.setItem(36, gray);
        inv.setItem(37, gray);
        inv.setItem(38, gray);
        inv.setItem(39, gray);
        inv.setItem(40, gray);
        inv.setItem(41, gray);
        inv.setItem(42, gray);
        inv.setItem(43, gray);
        inv.setItem(44, gray);
        inv.setItem(45,gray);
        inv.setItem(46,gray);
        inv.setItem(47,gray);
        inv.setItem(48,gray);
        inv.setItem(49,barrier);
        inv.setItem(50,gray);
        inv.setItem(51,gray);
        inv.setItem(52,gray);
        inv.setItem(53,gray);
    }

    public void openInventory(final HumanEntity e) {
        e.openInventory(inv);
    }

    private void fillLeft(ItemStack item,Inventory inv){
        inv.setItem(11,item);
        inv.setItem(12,item);
        inv.setItem(20,item);
    }

    private void fillRight(ItemStack item,Inventory inv){
        inv.setItem(14,item);
        inv.setItem(15,item);
        inv.setItem(24,item);
    }

    private void calculate(Inventory inv, HumanEntity player){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(inv.getItem(29)==null)
                {
                    fillLeft(red,inv);
                }
                if(inv.getItem(33)==null){
                    fillRight(red,inv);
                }
                if (inv.getItem(33)!=null&&inv.getItem(29)!=null){
                    fillLeft(green,inv);
                    fillRight(green,inv);
                }
                if(inv.getItem(33)!=null&&inv.getItem(29)==null){
                    fillRight(yellow,inv);
                    fillLeft(red,inv);
                }
                if(inv.getItem(33)==null&&inv.getItem(29)!=null){
                    fillLeft(yellow,inv);
                    fillRight(red,inv);
                }
            }
        }.runTaskLaterAsynchronously(plugin, 2);
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent e){
        if(e.getView().getTitle().equals("Anvil")) {
            calculate(e.getInventory(), e.getWhoClicked());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals("Anvil")) {
            if (e.isShiftClick()) {
                e.setCancelled(true);
            } else if (e.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
                e.setCancelled(true);
            } else {
                calculate(e.getInventory(), e.getWhoClicked());
            }
        }
    }

    @EventHandler
    public void invCloseEvent(InventoryCloseEvent e){
        if (e.getView().getTitle().equals("Anvil")) {
            ItemStack item1 = e.getInventory().getItem(29);
            ItemStack item2 = e.getInventory().getItem(33);
            if(item1!=null)
                givePlayer(e.getPlayer(), item1);
            if(item2!=null)
                givePlayer(e.getPlayer(), item2);
        }
    }
}
