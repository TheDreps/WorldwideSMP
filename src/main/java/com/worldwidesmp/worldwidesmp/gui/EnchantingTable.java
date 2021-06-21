package com.worldwidesmp.worldwidesmp.gui;

import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.Title;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import com.worldwidesmp.worldwidesmp.utils.CraftingUtils;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_16_R3.IInventory;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryEnchanting;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.util.*;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.logger;
import static com.worldwidesmp.worldwidesmp.WorldwideSMP.plugin;
import static java.lang.Math.round;

final class EnchantLevel {
    private final ArrayList<Enchantment> enchantments;
    private final ArrayList<Integer> levels;

    public EnchantLevel(ArrayList<Enchantment> enchantments, ArrayList<Integer> levels) {
        this.enchantments = enchantments;
        this.levels = levels;
    }

    public ArrayList<Enchantment> getEnchantments() {
        return enchantments;
    }

    public ArrayList<Integer> getLevels() {
        return levels;
    }
}

public class EnchantingTable implements Listener {
    private final ItemStack gray = createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ");
    private final ItemStack red = createGuiItem(Material.RED_STAINED_GLASS_PANE," ");
    private final ItemStack yellow = createGuiItem(Material.YELLOW_STAINED_GLASS_PANE," ");
    private final ItemStack barrier = createGuiItem(Material.BARRIER,ChatColor.RED+"Close");
    private final ItemStack deny = createGuiItem(Material.RED_STAINED_GLASS,ChatColor.RED+"Cannot enchant item!");
    private final ItemStack empty = createGuiItem(Material.RED_STAINED_GLASS,ChatColor.RED+"Place an item to enchant!");
    Random random = new Random();
    Inventory inv;

    public EnchantingTable() {
        inv = Bukkit.createInventory(null, 54, "Enchanting Table");
        initializeItems();
    }

    public void initializeItems() {
        inv.setItem(0, yellow);
        inv.setItem(1, gray);
        inv.setItem(2, red);
        inv.setItem(3, red);
        inv.setItem(4, red);
        inv.setItem(5, red);
        inv.setItem(6, red);
        inv.setItem(7, gray);
        inv.setItem(8, yellow);
        inv.setItem(9, gray);
        inv.setItem(10, gray);
        inv.setItem(11, gray);
        inv.setItem(12, red);
        inv.setItem(14, red);
        inv.setItem(15, gray);
        inv.setItem(16, gray);
        inv.setItem(17, gray);
        inv.setItem(18, gray);
        inv.setItem(19, gray);
        inv.setItem(20, gray);
        inv.setItem(21, gray);
        inv.setItem(22, gray);
        inv.setItem(23, gray);
        inv.setItem(24, gray);
        inv.setItem(25, gray);
        inv.setItem(26, gray);
        inv.setItem(27, gray);
        inv.setItem(28, gray);
        inv.setItem(30, gray);
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
        inv.setItem(45,yellow);
        inv.setItem(46,gray);
        inv.setItem(47,gray);
        inv.setItem(48,gray);
        inv.setItem(49,barrier);
        inv.setItem(50,gray);
        inv.setItem(51,gray);
        inv.setItem(52,gray);
        inv.setItem(53,yellow);
        empty(inv);
    }

    static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity e) {
        e.openInventory(inv);
    }

    public void deny(Inventory inv){
        inv.setItem(29, deny);
        inv.setItem(31, deny);
        inv.setItem(33, deny);
    }

    public void empty(Inventory inv){
        inv.setItem(29, empty);
        inv.setItem(31, empty);
        inv.setItem(33, empty);
    }

    public int getEnchantability(ItemStack item) {

        if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET)
            return 15;
        if (item.getType() == Material.CHAINMAIL_BOOTS || item.getType() == Material.CHAINMAIL_LEGGINGS || item.getType() == Material.CHAINMAIL_CHESTPLATE || item.getType() == Material.CHAINMAIL_HELMET)
            return 12;
        if (item.getType() == Material.IRON_BOOTS || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.IRON_HELMET)
            return 9;
        if (item.getType() == Material.GOLDEN_BOOTS || item.getType() == Material.GOLDEN_LEGGINGS || item.getType() == Material.GOLDEN_CHESTPLATE || item.getType() == Material.GOLDEN_HELMET)
            return 25;
        if (item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_HELMET)
            return 10;
        if (item.getType() == Material.NETHERITE_BOOTS || item.getType() == Material.NETHERITE_LEGGINGS || item.getType() == Material.NETHERITE_CHESTPLATE || item.getType() == Material.NETHERITE_HELMET)
            return 15;
        if (item.getType() == Material.WOODEN_SWORD || item.getType() == Material.WOODEN_PICKAXE || item.getType() == Material.WOODEN_AXE || item.getType() == Material.WOODEN_SHOVEL || item.getType() == Material.WOODEN_HOE)
            return 12;
        if (item.getType() == Material.STONE_SWORD || item.getType() == Material.STONE_PICKAXE || item.getType() == Material.STONE_AXE || item.getType() == Material.STONE_SHOVEL || item.getType() == Material.STONE_HOE)
            return 9;
        if (item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.GOLDEN_PICKAXE || item.getType() == Material.GOLDEN_AXE || item.getType() == Material.GOLDEN_SHOVEL || item.getType() == Material.GOLDEN_HOE)
            return 25;
        if (item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.DIAMOND_PICKAXE || item.getType() == Material.DIAMOND_AXE || item.getType() == Material.DIAMOND_SHOVEL || item.getType() == Material.DIAMOND_HOE)
            return 10;
        if (item.getType() == Material.NETHERITE_SWORD || item.getType() == Material.NETHERITE_PICKAXE || item.getType() == Material.NETHERITE_AXE || item.getType() == Material.NETHERITE_SHOVEL || item.getType() == Material.NETHERITE_HOE)
            return 15;
        return 1;
    }

    public EnchantLevel getEnchants(ItemStack item,int level){
        ArrayList<Enchantment> enchantments = new ArrayList<>();
        ArrayList<Integer> levels = new ArrayList<>();
        if(item.getType()==Material.LEATHER_HELMET||item.getType()==Material.CHAINMAIL_HELMET||item.getType()==Material.GOLDEN_HELMET||item.getType()==Material.DIAMOND_HELMET||item.getType()==Material.NETHERITE_HELMET||item.getType()==Material.TURTLE_HELMET)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }

            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            if(level>=30) {
                levels.add(3);
                enchantments.add(Enchantment.OXYGEN);
            }
            else if(level>=20) {
                levels.add(2);
                enchantments.add(Enchantment.OXYGEN);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.OXYGEN);
            }
            if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.WATER_WORKER);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.LEATHER_CHESTPLATE||item.getType()==Material.CHAINMAIL_CHESTPLATE||item.getType()==Material.GOLDEN_CHESTPLATE||item.getType()==Material.DIAMOND_CHESTPLATE||item.getType()==Material.NETHERITE_CHESTPLATE)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.LEATHER_LEGGINGS||item.getType()==Material.CHAINMAIL_LEGGINGS||item.getType()==Material.GOLDEN_LEGGINGS||item.getType()==Material.DIAMOND_LEGGINGS||item.getType()==Material.NETHERITE_LEGGINGS)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.LEATHER_BOOTS||item.getType()==Material.CHAINMAIL_BOOTS||item.getType()==Material.GOLDEN_BOOTS||item.getType()==Material.DIAMOND_BOOTS||item.getType()==Material.NETHERITE_BOOTS)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
            }
            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
            }
            if(level>=23&&level<=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FALL);
            }
            else if(level>=17) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FALL);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FALL);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FALL);
            }
            if(level>=30) {
                levels.add(3);
                enchantments.add(Enchantment.DEPTH_STRIDER);
            }
            else if(level>=20) {
                levels.add(2);
                enchantments.add(Enchantment.DEPTH_STRIDER);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.DEPTH_STRIDER);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.WOODEN_SWORD||item.getType()==Material.STONE_SWORD||item.getType()==Material.IRON_SWORD||item.getType()==Material.DIAMOND_SWORD||item.getType()==Material.NETHERITE_SWORD)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_ALL);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_ALL);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_ALL);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_ALL);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
            }
            if(level>=25) {
                levels.add(2);
                enchantments.add(Enchantment.KNOCKBACK);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.KNOCKBACK);
            }
            if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.FIRE_ASPECT);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.FIRE_ASPECT);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
            }
            if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.SWEEPING_EDGE);
            }
            else if(level>=14) {
                levels.add(2);
                enchantments.add(Enchantment.SWEEPING_EDGE);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.SWEEPING_EDGE);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.WOODEN_PICKAXE||item.getType()==Material.STONE_PICKAXE||item.getType()==Material.IRON_PICKAXE||item.getType()==Material.DIAMOND_PICKAXE||item.getType()==Material.NETHERITE_PICKAXE)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.WOODEN_AXE||item.getType()==Material.STONE_AXE||item.getType()==Material.IRON_AXE||item.getType()==Material.DIAMOND_AXE||item.getType()==Material.NETHERITE_AXE)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.WOODEN_HOE||item.getType()==Material.STONE_HOE||item.getType()==Material.IRON_HOE||item.getType()==Material.DIAMOND_HOE||item.getType()==Material.NETHERITE_HOE)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.WOODEN_SHOVEL||item.getType()==Material.STONE_SHOVEL||item.getType()==Material.IRON_SHOVEL||item.getType()==Material.DIAMOND_SHOVEL||item.getType()==Material.NETHERITE_SHOVEL)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.FISHING_ROD)
        {
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LURE);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LURE);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LURE);
            }
            if(level>=33) {
                levels.add(2);
                enchantments.add(Enchantment.LUCK);
            }
            else if(level>=24) {
                levels.add(1);
                enchantments.add(Enchantment.LUCK);
            }
            else if(level>=15) {
            levels.add(3);
            enchantments.add(Enchantment.LUCK);
            }
            if(level>=21)
            {
                levels.add(3);
                enchantments.add(Enchantment.LUCK);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.BOW){
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.ARROW_DAMAGE);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.ARROW_DAMAGE);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.ARROW_DAMAGE);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_DAMAGE);
            }
            if(level>=32) {
                levels.add(2);
                enchantments.add(Enchantment.ARROW_KNOCKBACK);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_KNOCKBACK);
            }
            if(level>=20) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_INFINITE);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        else if(item.getType()==Material.CROSSBOW){
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
            if(level>=20)
            {
                levels.add(1);
                enchantments.add(Enchantment.MULTISHOT);
            }
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.PIERCING);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PIERCING);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.PIERCING);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PIERCING);
            }
            enchantments.add(Enchantment.PIERCING);
            if(level>=32) {
                levels.add(2);
                enchantments.add(Enchantment.QUICK_CHARGE);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.QUICK_CHARGE);
            }
        }
        else if(item.getType()==Material.TRIDENT){
            if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.LOYALTY);
            }
            else if(level>=19) {
                levels.add(2);
                enchantments.add(Enchantment.LOYALTY);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.LOYALTY);
            }
            if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.CHANNELING);
            }
            if(level>=31) {
                levels.add(3);
                enchantments.add(Enchantment.RIPTIDE);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.RIPTIDE);
            }
            else if(level>=17) {
                levels.add(1);
                enchantments.add(Enchantment.RIPTIDE);
            }
            if(level>=33) {
                levels.add(5);
                enchantments.add(Enchantment.IMPALING);
            }
            else if(level>=25) {
                levels.add(4);
                enchantments.add(Enchantment.IMPALING);
            }
            else if(level>=17) {
                levels.add(3);
                enchantments.add(Enchantment.IMPALING);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.IMPALING);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.IMPALING);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
            }
        }
        return new EnchantLevel(enchantments,levels);
    }

    public void calculate(ItemStack item,Inventory inv){
        if(item==null)
            empty(inv);
        else {
            if (item.getItemMeta().hasEnchants())
                deny(inv);
            else{
                //getting base level
                int bookshelves = 15;
                int baseLevel = random.nextInt(9-1)+1 + (int)Math.floor(bookshelves/2) + random.nextInt(bookshelves+1);
                int baseLevelTop = (int)Math.floor(Math.max(baseLevel/3,1));
                int baseLevelMiddle = (int)Math.floor(baseLevel*2/3+1);
                int baseLevelBottom = (int)Math.floor(Math.max(baseLevel,bookshelves*2));

                int finalTop = (int)((1+(random.nextFloat()+random.nextFloat() - 1)* 0.15f)+0.5)*baseLevel + random.nextInt(getEnchantability(item)/2/2+1) + random.nextInt(getEnchantability(item)/2/2+1) + 1;
                int finalMiddle = (int)((1+(random.nextFloat()+random.nextFloat() - 1)* 0.15f)+0.5)*baseLevel + random.nextInt(getEnchantability(item)/2/2+1) + random.nextInt(getEnchantability(item)/2/2+1) + 1;
                int finalBottom = (int)((1+(random.nextFloat()+random.nextFloat() - 1)* 0.15f)+0.5)*baseLevel + random.nextInt(getEnchantability(item)/2/2+1) + random.nextInt(getEnchantability(item)/2/2+1) + 1;

                if(finalTop<1) finalTop=1;
                if(finalMiddle<1) finalMiddle=1;
                if(finalBottom<1) finalBottom=1;

                if(getEnchants(item,finalTop).isEmpty()) {
                    deny(inv);
                    return;
                }
                getEnchants(item,finalMiddle);
                getEnchants(item,finalBottom);
                inv.setItem(29, new ItemStack(Material.EXPERIENCE_BOTTLE,baseLevelTop));
                inv.setItem(31, new ItemStack(Material.EXPERIENCE_BOTTLE,baseLevelMiddle));
                inv.setItem(33, new ItemStack(Material.EXPERIENCE_BOTTLE,baseLevelBottom));
            }
        }
    }

    public void givePlayer(HumanEntity player,ItemStack item){
        if(!player.getInventory().addItem(item).isEmpty())
        {
            World world = player.getWorld();
            world.dropItemNaturally(player.getLocation(), item);
        }
    }

//    @EventHandler
//    public void temp(PlayerInteractEvent e){
//        Location loc = new Location(e.getPlayer().getWorld(), 0,0,0);
//        e.getPlayer().openEnchanting(loc,true);
//    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if(e.getView().getTitle().equals("Enchanting Table"))
            if((e.getClickedInventory().equals(e.getView().getTopInventory())&&e.getSlot()==13)||e.getClickedInventory().equals(e.getView().getBottomInventory()))
        new BukkitRunnable() {
            @Override
            public void run() {
                calculate(e.getView().getTopInventory().getItem(13),e.getView().getTopInventory());
            }
        }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
    }

    @EventHandler
    public void invCloseEvent(InventoryCloseEvent e){
        if (e.getView().getTitle().equals("Enchanting Table")) {
            ItemStack drop = e.getInventory().getItem(13);
            if(drop!=null)
                givePlayer(e.getPlayer(), drop);
        }
    }

}
