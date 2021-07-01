package me.lewismercer.lewiscommands.commands.shop;

import me.lewismercer.lewiscommands.api.EcoAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings({"NullPointerException", "ConstantConditions"})
public class Inventories implements Listener {

    EcoAPI eco = new EcoAPI();

    private final Inventory store, utilities, items, confirm;
    ItemStack placeholder = createGuiItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    String prefix = ChatColor.DARK_PURPLE + "Store" + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
    public static HashMap<UUID, StoreItems> playerOrders = new HashMap<>();


    public Inventories(){
        store = Bukkit.createInventory(new StoreInventory(), 9, "Store - Home");
        utilities = Bukkit.createInventory(new UtilitiesInventory(), 9, "Store - Utilities");
        items = Bukkit.createInventory(new ItemsInventory(), 9, "Store - Items");
        confirm = Bukkit.createInventory(new ConfirmInventory(), 9, "Store - Confirm");


        initializeItems();
    }

    public void fillBlanks(Inventory inv){
        for(int i = 0; i<inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)){
                inv.setItem(i, placeholder);
            }
        }
    }

    public void initializeItems() {
        store.setItem(3, StoreItems.UTILITIES.getDisplayItem());
        store.setItem(5, StoreItems.ITEMS.getDisplayItem());
        fillBlanks(store);

        //utilities.setItem(0, StoreItems.DEATHCHEST.getDisplayItem());
        fillBlanks(utilities);

        items.setItem(0, StoreItems.NETHERRITE.getDisplayItem());
        fillBlanks(items);


        confirm.setItem(3, StoreItems.CONFIRMATION_CONFIRM.getDisplayItem());
        confirm.setItem(5, StoreItems.CONFIRMATION_CANCEL.getDisplayItem());
        fillBlanks(confirm);
    }


    static protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        if(Arrays.asList(lore).size() >= 1){
            meta.setLore(Arrays.asList(lore));
        }
        item.setItemMeta(meta);

        return item;
    }

    public void openShop(final HumanEntity ent) {
        ent.openInventory(store);
    }

    public boolean hasAvailableSlot(Player p){
        return p.getInventory().firstEmpty() != -1;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)){
            return;
        }
        if(e.getCurrentItem() == null){
            return;
        }

        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();


        if(e.getInventory().getHolder() instanceof StoreInventory){
            e.setCancelled(true);

            if(item.equals(StoreItems.UTILITIES.getDisplayItem())){
                p.openInventory(utilities);
            }else if(item.equals(StoreItems.ITEMS.getDisplayItem())){
                p.openInventory(items);
            }
        }
        else if(e.getInventory().getHolder() instanceof UtilitiesInventory){
            e.setCancelled(true);

            if(item.equals(StoreItems.DEATHCHEST.getDisplayItem())){
                playerOrders.put(p.getUniqueId(), StoreItems.DEATHCHEST);
                p.openInventory(confirm);
            }
        }else if(e.getInventory().getHolder() instanceof ItemsInventory){
            e.setCancelled(true);

            if(item.equals(StoreItems.NETHERRITE.getDisplayItem())){
                playerOrders.put(p.getUniqueId(), StoreItems.NETHERRITE);
                p.openInventory(confirm);
            }
        }else if(e.getInventory().getHolder() instanceof ConfirmInventory){
            e.setCancelled(true);

            if(item.equals(StoreItems.CONFIRMATION_CONFIRM.getDisplayItem())){
                Inventory inv = p.getInventory();
                int firstEmpty = inv.firstEmpty();
                StoreItems storeItem = playerOrders.get(p.getUniqueId());
                //Utility
                if(storeItem.getItem() == null){
                    p.closeInventory();
                    return;
                }

                //Items
                if(hasAvailableSlot(p)){
                        if(eco.checkBal(p.getUniqueId().toString(), storeItem.getBuyFromStorePrice())){
                            inv.setItem(firstEmpty, storeItem.getItem());
                            eco.takeBal(p.getUniqueId().toString(), storeItem.getBuyFromStorePrice());
                            p.sendMessage(prefix + "Brought " + storeItem.getItem().getAmount() + " " + storeItem.getDisplayItem().getItemMeta().getDisplayName() + ChatColor.RESET + " for $" + storeItem.getBuyFromStorePrice());
                        }else{
                            p.sendMessage(prefix + "Not enough money, sorry!");
                        }
                }
            }else if(item.equals(StoreItems.CONFIRMATION_CANCEL.getDisplayItem())){
                p.closeInventory();
                p.sendMessage(prefix + "Purchase cancelled!");
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(store) || e.getInventory().equals(utilities) || e.getInventory().equals(items)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        playerOrders.remove(e.getPlayer().getUniqueId());
    }
}

class StoreInventory implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null;
    }
}
class ItemsInventory implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null;
    }
}
class UtilitiesInventory implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null;
    }
}
class ConfirmInventory implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null;
    }
}
