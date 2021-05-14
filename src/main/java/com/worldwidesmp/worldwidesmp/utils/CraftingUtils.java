package com.worldwidesmp.worldwidesmp.utils;

import com.worldwidesmp.worldwidesmp.inventory.CraftingContainer;
import net.minecraft.server.v1_16_R3.RecipeCrafting;
import net.minecraft.server.v1_16_R3.Recipes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CraftingUtils {
    public static ItemStack getCraftingResult(Inventory inventory, HumanEntity player) {
        CraftingContainer c = new CraftingContainer(((CraftInventory)inventory).getInventory(), ((CraftPlayer)player).getHandle().inventory);

        net.minecraft.server.v1_16_R3.ItemStack itemstack = net.minecraft.server.v1_16_R3.ItemStack.b;
        World world = ((CraftWorld) player.getWorld()).getHandle();
        Optional<RecipeCrafting> optional = world.getMinecraftServer().getCraftingManager().craft(Recipes.CRAFTING, c.craftInventory, world);
        if (optional.isPresent()) {
            RecipeCrafting recipecrafting = optional.get();
            if (c.resultInventory.a(world, ((CraftPlayer)player).getHandle(), recipecrafting)) {
                itemstack = recipecrafting.a(c.craftInventory);
            }
        }

        return CraftItemStack.asBukkitCopy(itemstack);
    }
}
