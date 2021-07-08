package com.worldwidesmp.worldwidesmp.utils;

import com.worldwidesmp.worldwidesmp.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.RecipeCrafting;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CraftingUtils {
    public static ItemStack getCraftingResult(Inventory inventory, HumanEntity player) {
//        CraftingContainer c = new CraftingContainer(((CraftInventory)inventory).getInventory(), ((CraftPlayer)player).getHandle().getInventory());
//        net.minecraft.world.item.ItemStack itemstack = net.minecraft.world.item.ItemStack.b;
//        World world = ((CraftWorld) player.getWorld()).getHandle();
//        Optional<RecipeCrafting> optional = world.getMinecraftServer().getCraftingManager().craft(Recipes.CRAFTING, c.craftInventory, world);
//        if (optional.isPresent()) {
//            RecipeCrafting recipecrafting = optional.get();
//            if (c.resultInventory.a(world, ((CraftPlayer)player).getHandle(), recipecrafting)) {
//                itemstack = recipecrafting.a(c.craftInventory);
//            }
//        }

//        return CraftItemStack.asBukkitCopy(itemstack);
            return null;
    }
}
