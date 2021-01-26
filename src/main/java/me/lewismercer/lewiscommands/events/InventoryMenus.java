package me.lewismercer.lewiscommands.events;

import me.lewismercer.lewiscommands.Inventories2;
import me.lewismercer.lewiscommands.Utils;
import me.lewismercer.lewiscommands.enums.InventoryTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryMenus implements Listener {

    private void giveItem(Player p, ItemStack ts){
        if(p.getInventory().firstEmpty() == -1){
            p.getLocation().getWorld().dropItem(p.getLocation(), ts);
        }else{
            p.getInventory().addItem(ts);
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(!(Utils.playerOpenedInvs.containsKey(p.getUniqueId()) && Utils.playerOpenedInvs.get(p.getUniqueId()).equals(InventoryTypes.TP))) return;

        Inventory pInv = p.getInventory();

    }

//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent e){
//        if(!(e.getView().getTitle().contains("Please place 32 diamonds here!")) || e.getClickedInventory() instanceof PlayerInventory) return;
//
//        ItemStack clickedItem = e.getCurrentItem();
//
//        // verify current item is not null
//        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
//
//        Player p = (Player) e.getWhoClicked();
//
//
//        if(!(e.getSlot() == 10)){
//            e.setCancelled(true);
//        }
//
//        if(e.getSlot() == 16){
//            if(e.getInventory().getItem(10) == null || e.getInventory().getItem(10).getType() == Material.AIR){
//                p.closeInventory();
//            }else{
//                giveItem(p, e.getInventory().getItem(10));
//                p.closeInventory();
//            }
//        }else if(e.getSlot() == 14){
//            if(e.getInventory().getItem(10) == null || e.getInventory().getItem(10).getType() == Material.AIR){
//                p.closeInventory();
//                p.sendMessage(ChatColor.RED + "Please place the payment first!");
//            }else if(!(e.getInventory().getItem(10).getType().equals(Material.DIAMOND))){
//
//            }
//        }
//
//    }
//
//    // Cancel dragging in our inventory
//    @EventHandler
//    public void onInventoryDrag(InventoryDragEvent e) {
//        if(!(e.getView().getTitle().contains("Please place 32 diamonds here!")) || e.getInventory() instanceof PlayerInventory) return;
//        e.setCancelled(true);
//    }
//
//    @EventHandler
//    public void onInventoryMove(InventoryMoveItemEvent e){
//        if(e.getDestination().equals()){
//
//        }
//    }

}
