package com.worldwidesmp.worldwidesmp.enchantments;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
//import io.papermc.paper.enchantments.EnchantmentRarity;
//import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.logging.Logger;

public class Telekinesis extends Enchantment implements Listener {
    // CONSTRUCTOR

    public Telekinesis(String namespace){
        super(new NamespacedKey(WorldwideSMP.plugin, namespace));
    }

    // METHODS
    @Override
    public @NotNull
    String getName() {
        return "Telekinesis";
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
        if (this.getItemTarget() == EnchantmentTarget.TOOL){
            return EnchantmentTarget.TOOL;
        }else if(this.getItemTarget() == EnchantmentTarget.WEAPON){
            return EnchantmentTarget.WEAPON;
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
            case DIAMOND_SWORD:
            case GOLDEN_SWORD:
            case IRON_SWORD:
            case NETHERITE_SWORD:
            case STONE_SWORD:
            case WOODEN_SWORD:
            case SHEARS:
            case BOW:
            case TRIDENT:
            case CROSSBOW:
                return true;
        }
        return false;
    }

    // MECHANICS
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBroken(BlockBreakEvent e) {
        try {
            if (!e.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.getByKey(WorldwideSMP.smeltingTouch.getKey())) && e.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.getByKey(WorldwideSMP.telekinesis.getKey()))) {
                e.setDropItems(false);
                for (ItemStack is : e.getBlock().getDrops(e.getPlayer().getItemInHand())) {
                    WorldwideSMP.addTelekineticDrop(e.getPlayer(),is,e.getBlock().getLocation());
                }
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void onEntityLootDrop(EntityDeathEvent e){
        Player killer = e.getEntity().getKiller();
        ItemStack weapon = killer.getPlayer().getItemInHand();
        if(weapon.getEnchantments().containsKey(Enchantment.getByKey(WorldwideSMP.telekinesis.getKey()))){
            for(ItemStack is:e.getDrops()){
                WorldwideSMP.addTelekineticDrop(killer,is,e.getEntity().getLocation());
            }
            e.getDrops().clear();
        }
    }

}
