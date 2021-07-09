package com.worldwidesmp.worldwidesmp.enchantments;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
//import io.papermc.paper.enchantments.EnchantmentRarity;
//import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SmeltingTouch extends Enchantment implements Listener {

    // INITIALIZER

    public SmeltingTouch(String namespace){
        super(new NamespacedKey(WorldwideSMP.plugin, namespace));
    }

    // METHODS


    @Override
    public @NotNull
    String getName() {
        return "Smelting Touch";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        if (this.getItemTarget() == EnchantmentTarget.TOOL) {
            return EnchantmentTarget.TOOL;
        }
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        if (SILK_TOUCH.equals(other)) {
            return true;
        }
        if(other.equals(this))
            return true;
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        switch(item.getType()){
            case DIAMOND_PICKAXE:
            case GOLDEN_PICKAXE:
            case IRON_PICKAXE:
            case NETHERITE_PICKAXE:
            case STONE_PICKAXE:
            case WOODEN_PICKAXE:
            case DIAMOND_AXE:
            case GOLDEN_AXE:
            case IRON_AXE:
            case NETHERITE_AXE:
            case STONE_AXE:
            case WOODEN_AXE:
            case DIAMOND_SHOVEL:
            case GOLDEN_SHOVEL:
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case NETHERITE_SHOVEL:
            case WOODEN_SHOVEL:
            case DIAMOND_HOE:
            case GOLDEN_HOE:
            case IRON_HOE:
            case NETHERITE_HOE:
            case STONE_HOE:
            case WOODEN_HOE:
            case SHEARS:
                return true;
        }
        return false;
    }

/* method to check if a given block can be smelted(spigot doesnt have this?)
    boolean canBeSmelted(ItemStack is){
        ItemStack result = null;
        Iterator<Recipe> iter = Bukkit.recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = iter.next();
            if (!(recipe instanceof FurnaceRecipe)) continue;
            if (((FurnaceRecipe) recipe).getInput().getType() != is.getType()) continue;
            result = recipe.getResult();
            break;
        }
        if(result != null){
            return true;
        }
        return false;
    }
*/

    // MECHANICS

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        ItemStack tool = e.getPlayer().getItemInHand();
        if (tool != null && tool.getEnchantments().containsKey(Enchantment.getByKey(WorldwideSMP.smeltingTouch.getKey()))) {
            e.setDropItems(false);
            if (tool.getEnchantments().containsKey(Enchantment.getByKey(WorldwideSMP.telekinesis.getKey()))) {
                for (ItemStack drop : b.getDrops(tool)) {
                    switch (drop.getType()) {
                        case RAW_IRON:
                            WorldwideSMP.addTelekineticDrop(p,new ItemStack(Material.IRON_INGOT,drop.getAmount()),e.getBlock().getLocation());
                            break;
                        case RAW_GOLD:
                            WorldwideSMP.addTelekineticDrop(p,new ItemStack(Material.GOLD_INGOT,drop.getAmount()),e.getBlock().getLocation());
                            break;
                        default:
                            WorldwideSMP.addTelekineticDrop(p,drop,e.getBlock().getLocation());
                    }
                }
            } else {
                for (ItemStack drop : b.getDrops(tool)) {
                    switch (drop.getType()) {
                        case RAW_IRON:
                            b.getWorld().dropItemNaturally(b.getLocation(),new ItemStack(Material.IRON_INGOT,drop.getAmount()));
                            break;
                        case RAW_GOLD:
                            b.getWorld().dropItemNaturally(b.getLocation(),new ItemStack(Material.GOLD_INGOT,drop.getAmount()));
                            break;
                        default:
                            b.getWorld().dropItemNaturally(b.getLocation(),drop);
                    }
                }

            }
        }
    }
}
