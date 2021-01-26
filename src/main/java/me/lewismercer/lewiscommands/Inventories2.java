package me.lewismercer.lewiscommands;

import me.lewismercer.lewiscommands.enums.InventoryTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Inventories2 {

    private final Inventory tpInv;

    public Inventories2(){
        tpInv = Bukkit.createInventory(null,27, ChatColor.RED + "" + ChatColor.BOLD + "Press confirm to complete payment!");

        fillInventory(tpInv, createGuiItemNoLure(Material.BLACK_STAINED_GLASS_PANE, ""));

        tpInv.setItem(10, new ItemStack(Material.AIR));
        tpInv.setItem(14, createGuiItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "CONFIRM", "WARNING: This can not be undone!"));
        tpInv.setItem(16, createGuiItemNoLure(Material.REDSTONE_BLOCK, ChatColor.DARK_RED + "" + ChatColor.BOLD + "CANCEL"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack createGuiItemNoLure(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }

    public static void fillInventory(Inventory inv, ItemStack item){
        for(int i = 0; i<inv.getSize(); i++){
            inv.setItem(i, item);
        }
    }

    // You can open the inventory with this
    public void openTpInv(final Player p) {
        p.openInventory(tpInv);
        Utils.playerOpenedInvs.put(p.getUniqueId(), InventoryTypes.TP);
    }

    public Inventory getTpInv(){
        return tpInv;
    }

}
