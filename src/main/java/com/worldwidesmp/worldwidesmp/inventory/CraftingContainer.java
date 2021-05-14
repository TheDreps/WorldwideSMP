package com.worldwidesmp.worldwidesmp.inventory;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryView;
import org.bukkit.inventory.InventoryView;

public class CraftingContainer extends Container {
    public final InventoryCrafting craftInventory;
    public final InventoryCraftResult resultInventory;
    private CraftInventoryView bukkitEntity;
    private final PlayerInventory playerInventory;

    public CraftingContainer(IInventory inv, PlayerInventory playerInv) {
        super(Containers.CRAFTING, -1);

        this.playerInventory = playerInv;
        this.resultInventory = new InventoryCraftResult();
        this.craftInventory = new InventoryCrafting(this, 3, 3, this.playerInventory.player);
        this.craftInventory.resultInventory = this.resultInventory;

        this.a(new Slot(inv, 10, 0, 0));
        this.a(new Slot(inv, 11, 0, 0));
        this.a(new Slot(inv, 12, 0, 0));
        this.a(new Slot(inv, 19, 0, 0));
        this.a(new Slot(inv, 20, 0, 0));
        this.a(new Slot(inv, 21, 0, 0));
        this.a(new Slot(inv, 28, 0, 0));
        this.a(new Slot(inv, 29, 0, 0));
        this.a(new Slot(inv, 30, 0, 0));
        this.a(new Slot(inv, 23, 0, 0));

        for (int i = 0; i < this.slots.size() - 1; i++) {
            this.craftInventory.setItem(i, this.slots.get(i).getItem());
        }
    }

    @Override
    public InventoryView getBukkitView() {
        if (this.bukkitEntity == null) {
            CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
            this.bukkitEntity = new CraftInventoryView(this.playerInventory.player.getBukkitEntity(), inventory, this);
        }
        return this.bukkitEntity;
    }

    @Override
    public boolean canUse(EntityHuman entityHuman) {
        return false;
    }
}
