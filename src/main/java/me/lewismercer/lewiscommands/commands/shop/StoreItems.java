package me.lewismercer.lewiscommands.commands.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("SpellCheckingInspection")
public enum StoreItems {

    DEATHCHEST(0, 15000, Inventories.createGuiItem(Material.CHEST, "§bBuy your stuff back after death", "Must be within an hour of death!", "do /dc list to check", "§d$15,000" ), null),


    NETHERRITE(2000,2300, Inventories.createGuiItem(Material.NETHERITE_INGOT, "§bNetherrite ingot","§d$2,300"), new ItemStack(Material.NETHERITE_INGOT, 1)),


    UTILITIES(0, 0, Inventories.createGuiItem(Material.DIAMOND_PICKAXE, "§bUtilities"), null),
    ITEMS(0, 0, Inventories.createGuiItem(Material.GRASS_BLOCK, "§bItems"), null),

    CONFIRMATION_CONFIRM(0, 0, Inventories.createGuiItem(Material.GREEN_CONCRETE, "§2§lCONFIRM", "§4This is §lNOT §r§4refundable!"), null),
    CONFIRMATION_CANCEL(0, 0, Inventories.createGuiItem(Material.RED_CONCRETE, "§4§lCANCEL"), null);




    private final ItemStack displayItem;
    private final ItemStack item;
    private final double sellToStorePrice;
    private final double buyFromStorePrice;
    StoreItems(final double sellToStorePrice, final double buyFromStorePrice, final ItemStack displayItem, ItemStack item){
        this.displayItem = displayItem;
        this.sellToStorePrice = sellToStorePrice;
        this.buyFromStorePrice = buyFromStorePrice;
        this.item = item;
    }

    public ItemStack getDisplayItem(){
        return this.displayItem;
    }

    public ItemStack getItem(){
        return this.item;
    }

    public double getSellToStorePrice(){
        return this.sellToStorePrice;
    }

    public double getBuyFromStorePrice(){
        return this.buyFromStorePrice;
    }

}