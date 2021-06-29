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
import org.bukkit.configuration.file.FileConfiguration;
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
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
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

import javax.swing.*;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.util.*;

import static com.worldwidesmp.worldwidesmp.WorldwideSMP.logger;
import static com.worldwidesmp.worldwidesmp.WorldwideSMP.plugin;
import static java.lang.Math.round;

final class EnchantLevel {
    private final ArrayList<Enchantment> enchantments;
    private final ArrayList<Integer> levels;
    private final ArrayList<Integer> weight;

    public EnchantLevel(ArrayList<Enchantment> enchantments, ArrayList<Integer> levels, ArrayList<Integer> weight) {
        this.enchantments = enchantments;
        this.levels = levels;
        this.weight = weight;
    }

    public ArrayList<Enchantment> getEnchantments() {
        return enchantments;
    }

    public ArrayList<Integer> getLevels() {
        return levels;
    }

    public ArrayList<Integer> getWeight() {
        return weight;
    }
}

public class EnchantingTable implements Listener {
    private final ItemStack gray = createGuiItem(Material.GRAY_STAINED_GLASS_PANE," ");
    private final ItemStack red = createGuiItem(Material.RED_STAINED_GLASS_PANE," ");
    private final ItemStack blue = createGuiItem(Material.BLUE_STAINED_GLASS_PANE," ");
    private final ItemStack barrier = createGuiItem(Material.BARRIER,ChatColor.RED+"Close");
    private final ItemStack deny = createGuiItem(Material.ORANGE_STAINED_GLASS,ChatColor.RED+"Cannot enchant item!");
    private final ItemStack empty = createGuiItem(Material.ORANGE_STAINED_GLASS,ChatColor.RED+"Place an item to enchant!");
    Random random = new Random();
    Inventory inv;

    public EnchantingTable() {
        inv = Bukkit.createInventory(null, 54, "Enchanting Table");
        initializeItems();
    }

    public void initializeItems() {
        inv.setItem(0, blue);
        inv.setItem(1, gray);
        inv.setItem(2, red);
        inv.setItem(3, red);
        inv.setItem(4, red);
        inv.setItem(5, red);
        inv.setItem(6, red);
        inv.setItem(7, gray);
        inv.setItem(8, blue);
        inv.setItem(9, gray);
        inv.setItem(10, gray);
        inv.setItem(11, gray);
        inv.setItem(13, red);
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
        inv.setItem(45,blue);
        inv.setItem(46,gray);
        inv.setItem(47,gray);
        inv.setItem(48,gray);
        inv.setItem(49,barrier);
        inv.setItem(50,gray);
        inv.setItem(51,gray);
        inv.setItem(52,gray);
        inv.setItem(53,blue);
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

    public String convertEnchantToName(Enchantment enchantment){
        if(enchantment.equals(Enchantment.PROTECTION_ENVIRONMENTAL))
            return "Protection";
        else if(enchantment.equals(Enchantment.PROTECTION_FIRE))
            return "Fire Protection";
        else if(enchantment.equals(Enchantment.PROTECTION_EXPLOSIONS))
            return "Blast Protection";
        else if(enchantment.equals(Enchantment.PROTECTION_PROJECTILE))
            return "Projectile Protection";
        else if(enchantment.equals(Enchantment.PROTECTION_FALL))
            return "Feather Falling";
        else if(enchantment.equals(Enchantment.OXYGEN))
            return "Respiration";
        else if(enchantment.equals(Enchantment.WATER_WORKER))
            return "Aqua Affinity";
        else if(enchantment.equals(Enchantment.THORNS))
            return "Thorns";
        else if(enchantment.equals(Enchantment.DURABILITY))
            return "Unbreaking";
        else if(enchantment.equals(Enchantment.DEPTH_STRIDER))
            return "Depth Strider";
        else if(enchantment.equals(Enchantment.DAMAGE_ALL))
            return "Sharpness";
        else if(enchantment.equals(Enchantment.DAMAGE_UNDEAD))
            return "Smite";
        else if(enchantment.equals(Enchantment.DAMAGE_ARTHROPODS))
            return "Bane Of Arthropods";
        else if(enchantment.equals(Enchantment.KNOCKBACK))
            return "Knockback";
        else if(enchantment.equals(Enchantment.FIRE_ASPECT))
            return "Fire Aspect";
        else if(enchantment.equals(Enchantment.LOOT_BONUS_MOBS))
            return "Looting";
        else if(enchantment.equals(Enchantment.SWEEPING_EDGE))
            return "Sweeping Edge";
        else if(enchantment.equals(Enchantment.DIG_SPEED))
            return "Efficiency";
        else if(enchantment.equals(Enchantment.LOOT_BONUS_BLOCKS))
            return "Fortune";
        else if(enchantment.equals(Enchantment.SILK_TOUCH))
            return "Silk Touch";
        else if(enchantment.equals(Enchantment.LURE))
            return "Lure";
        else if(enchantment.equals(Enchantment.LUCK))
            return "Luck Of The Sea";
        else if(enchantment.equals(Enchantment.ARROW_DAMAGE))
            return "Power";
        else if(enchantment.equals(Enchantment.ARROW_KNOCKBACK))
            return "Punch";
        else if(enchantment.equals(Enchantment.ARROW_INFINITE))
            return "Infinity";
        else if(enchantment.equals(Enchantment.MULTISHOT))
            return "Multishot";
        else if(enchantment.equals(Enchantment.PIERCING))
            return "Piercing";
        else if(enchantment.equals(Enchantment.QUICK_CHARGE))
            return "Quick Charge";
        else if(enchantment.equals(Enchantment.LOYALTY))
            return "Loyalty";
        else if(enchantment.equals(Enchantment.CHANNELING))
            return "Channeling";
        else if(enchantment.equals(Enchantment.RIPTIDE))
            return "Riptide";
        else if(enchantment.equals(Enchantment.IMPALING))
            return "Impaling";
        return null;
    }

    public Enchantment convertNameToEnchant(String name){
        if(name.equals("Protection"))
            return Enchantment.PROTECTION_ENVIRONMENTAL;
        if(name.equals("Fire Protection"))
            return Enchantment.PROTECTION_FIRE;
        if(name.equals("Blast Protection"))
            return Enchantment.PROTECTION_EXPLOSIONS;
        if(name.equals("Projectile Protection"))
            return Enchantment.PROTECTION_PROJECTILE;
        if(name.equals("Feather Falling"))
            return Enchantment.PROTECTION_FALL;
        if(name.equals("Respiration"))
            return Enchantment.OXYGEN;
        if(name.equals("Aqua Affinity"))
            return Enchantment.WATER_WORKER;
        if(name.equals("Thorns"))
            return Enchantment.THORNS;
        if(name.equals("Unbreaking"))
            return Enchantment.DURABILITY;
        if(name.equals("Depth Strider"))
            return Enchantment.DEPTH_STRIDER;
        if(name.equals("Sharpness"))
            return Enchantment.DAMAGE_ALL;
        if(name.equals("Smite"))
            return Enchantment.DAMAGE_UNDEAD;
        if(name.equals("Bane Of Arthropods"))
            return Enchantment.DAMAGE_ARTHROPODS;
        if(name.equals("Knockback"))
            return Enchantment.KNOCKBACK;
        if(name.equals("Fire Aspect"))
            return Enchantment.FIRE_ASPECT;
        if(name.equals("Looting"))
            return Enchantment.LOOT_BONUS_MOBS;
        if(name.equals("Sweeping Edge"))
            return Enchantment.SWEEPING_EDGE;
        if(name.equals("Efficiency"))
            return Enchantment.DIG_SPEED;
        if(name.equals("Fortune"))
            return Enchantment.LOOT_BONUS_BLOCKS;
        if(name.equals("Silk Touch"))
            return Enchantment.SILK_TOUCH;
        if(name.equals("Lure"))
            return Enchantment.LURE;
        if(name.equals("Luck Of The Sea"))
            return Enchantment.LUCK;
        if(name.equals("Power"))
            return Enchantment.ARROW_DAMAGE;
        if(name.equals("Punch"))
            return Enchantment.ARROW_KNOCKBACK;
        if(name.equals("Infinity"))
            return Enchantment.ARROW_INFINITE;
        if(name.equals("Multishot"))
            return Enchantment.MULTISHOT;
        if(name.equals("Piercing"))
            return Enchantment.PIERCING;
        if(name.equals("Quick Charge"))
            return Enchantment.QUICK_CHARGE;
        if(name.equals("Loyalty"))
            return Enchantment.LOYALTY;
        if(name.equals("Channeling"))
            return Enchantment.CHANNELING;
        if(name.equals("Riptide"))
            return Enchantment.RIPTIDE;
        if(name.equals("Impaling"))
            return Enchantment.IMPALING;
        return null;
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
        ArrayList<Integer> weights = new ArrayList<>();
        if(item.getType()==Material.LEATHER_HELMET||item.getType()==Material.CHAINMAIL_HELMET||item.getType()==Material.GOLDEN_HELMET||item.getType()==Material.DIAMOND_HELMET||item.getType()==Material.NETHERITE_HELMET||item.getType()==Material.TURTLE_HELMET)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }

            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            if(level>=30) {
                levels.add(3);
                enchantments.add(Enchantment.OXYGEN);
                weights.add(2);
            }
            else if(level>=20) {
                levels.add(2);
                enchantments.add(Enchantment.OXYGEN);
                weights.add(2);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.OXYGEN);
                weights.add(2);
            }
            if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.WATER_WORKER);
                weights.add(2);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.LEATHER_CHESTPLATE||item.getType()==Material.CHAINMAIL_CHESTPLATE||item.getType()==Material.GOLDEN_CHESTPLATE||item.getType()==Material.DIAMOND_CHESTPLATE||item.getType()==Material.NETHERITE_CHESTPLATE)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.LEATHER_LEGGINGS||item.getType()==Material.CHAINMAIL_LEGGINGS||item.getType()==Material.GOLDEN_LEGGINGS||item.getType()==Material.DIAMOND_LEGGINGS||item.getType()==Material.NETHERITE_LEGGINGS)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.LEATHER_BOOTS||item.getType()==Material.CHAINMAIL_BOOTS||item.getType()==Material.GOLDEN_BOOTS||item.getType()==Material.DIAMOND_BOOTS||item.getType()==Material.NETHERITE_BOOTS)
        {
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            if(level>=23&&level<=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            else if(level>=17) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            if(level>=30) {
                levels.add(3);
                enchantments.add(Enchantment.DEPTH_STRIDER);
                weights.add(2);
            }
            else if(level>=20) {
                levels.add(2);
                enchantments.add(Enchantment.DEPTH_STRIDER);
                weights.add(2);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.DEPTH_STRIDER);
                weights.add(2);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.WOODEN_SWORD||item.getType()==Material.STONE_SWORD||item.getType()==Material.IRON_SWORD||item.getType()==Material.DIAMOND_SWORD||item.getType()==Material.NETHERITE_SWORD)
        {
            if(level>=32) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            if(level>=25) {
                levels.add(2);
                enchantments.add(Enchantment.KNOCKBACK);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.KNOCKBACK);
                weights.add(5);
            }
            if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.FIRE_ASPECT);
                weights.add(2);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.FIRE_ASPECT);
                weights.add(2);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
                weights.add(2);
            }
            if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.SWEEPING_EDGE);
                weights.add(2);
            }
            else if(level>=14) {
                levels.add(2);
                enchantments.add(Enchantment.SWEEPING_EDGE);
                weights.add(2);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.SWEEPING_EDGE);
                weights.add(2);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.WOODEN_PICKAXE||item.getType()==Material.STONE_PICKAXE||item.getType()==Material.IRON_PICKAXE||item.getType()==Material.DIAMOND_PICKAXE||item.getType()==Material.NETHERITE_PICKAXE)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.WOODEN_AXE||item.getType()==Material.STONE_AXE||item.getType()==Material.IRON_AXE||item.getType()==Material.DIAMOND_AXE||item.getType()==Material.NETHERITE_AXE)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.WOODEN_HOE||item.getType()==Material.STONE_HOE||item.getType()==Material.IRON_HOE||item.getType()==Material.DIAMOND_HOE||item.getType()==Material.NETHERITE_HOE)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.WOODEN_SHOVEL||item.getType()==Material.STONE_SHOVEL||item.getType()==Material.IRON_SHOVEL||item.getType()==Material.DIAMOND_SHOVEL||item.getType()==Material.NETHERITE_SHOVEL)
        {
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.FISHING_ROD)
        {
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LURE);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LURE);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LURE);
                weights.add(2);
            }
            if(level>=33) {
                levels.add(2);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(1);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            if(level>=21)
            {
                levels.add(3);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.BOW){
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            if(level>=32) {
                levels.add(2);
                enchantments.add(Enchantment.ARROW_KNOCKBACK);
                weights.add(2);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_KNOCKBACK);
                weights.add(2);
            }
            if(level>=20) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_INFINITE);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.CROSSBOW){
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            if(level>=20)
            {
                levels.add(1);
                enchantments.add(Enchantment.MULTISHOT);
                weights.add(2);
            }
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            if(level>=32) {
                levels.add(2);
                enchantments.add(Enchantment.QUICK_CHARGE);
                weights.add(5);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.QUICK_CHARGE);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.TRIDENT){
            if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.LOYALTY);
                weights.add(5);
            }
            else if(level>=19) {
                levels.add(2);
                enchantments.add(Enchantment.LOYALTY);
                weights.add(5);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.LOYALTY);
                weights.add(5);
            }
            if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.CHANNELING);
                weights.add(1);
            }
            if(level>=31) {
                levels.add(3);
                enchantments.add(Enchantment.RIPTIDE);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.RIPTIDE);
                weights.add(2);
            }
            else if(level>=17) {
                levels.add(1);
                enchantments.add(Enchantment.RIPTIDE);
                weights.add(2);
            }
            if(level>=33) {
                levels.add(5);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=25) {
                levels.add(4);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=17) {
                levels.add(3);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
        }
        else if(item.getType()==Material.BOOK){
            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
                weights.add(10);
            }

            if(level>=34) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=18) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FIRE);
                weights.add(5);
            }

            if(level>=21) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            else if(level>=3) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_PROJECTILE);
                weights.add(5);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
                weights.add(2);
            }
            if(level>=30) {
                levels.add(3);
                enchantments.add(Enchantment.OXYGEN);
                weights.add(2);
            }
            else if(level>=20) {
                levels.add(2);
                enchantments.add(Enchantment.OXYGEN);
                weights.add(2);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.OXYGEN);
                weights.add(2);
            }
            if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.WATER_WORKER);
                weights.add(2);
            }
            if(level>=50) {
                levels.add(3);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.THORNS);
                weights.add(1);
            }
            if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DURABILITY);
                weights.add(5);
            }
            if(level>=23&&level<=29) {
                levels.add(4);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            else if(level>=17) {
                levels.add(3);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.PROTECTION_FALL);
                weights.add(5);
            }
            if(level>=30) {
                levels.add(3);
                enchantments.add(Enchantment.DEPTH_STRIDER);
                weights.add(2);
            }
            else if(level>=20) {
                levels.add(2);
                enchantments.add(Enchantment.DEPTH_STRIDER);
                weights.add(2);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.DEPTH_STRIDER);
                weights.add(2);
            }
            if(level>=32) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            else if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            else if(level>=12) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_ALL);
                weights.add(10);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_UNDEAD);
                weights.add(5);
            }
            if(level>=29) {
                levels.add(4);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            else if(level>=13) {
                levels.add(2);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
                weights.add(5);
            }
            if(level>=25) {
                levels.add(2);
                enchantments.add(Enchantment.KNOCKBACK);
                weights.add(5);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.KNOCKBACK);
                weights.add(5);
            }
            if(level>=30) {
                levels.add(2);
                enchantments.add(Enchantment.FIRE_ASPECT);
                weights.add(2);
            }
            else if(level>=10) {
                levels.add(1);
                enchantments.add(Enchantment.FIRE_ASPECT);
                weights.add(2);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_MOBS);
                weights.add(2);
            }
            if(level>=23) {
                levels.add(3);
                enchantments.add(Enchantment.SWEEPING_EDGE);
                weights.add(2);
            }
            else if(level>=14) {
                levels.add(2);
                enchantments.add(Enchantment.SWEEPING_EDGE);
                weights.add(2);
            }
            else if(level>=5) {
                levels.add(1);
                enchantments.add(Enchantment.SWEEPING_EDGE);
                weights.add(2);
            }
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.DIG_SPEED);
                weights.add(10);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
                weights.add(2);
            }
            if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.SILK_TOUCH);
                weights.add(1);
            }
            if(level>=33) {
                levels.add(3);
                enchantments.add(Enchantment.LURE);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.LURE);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(1);
                enchantments.add(Enchantment.LURE);
                weights.add(2);
            }
            if(level>=33) {
                levels.add(2);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(1);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            else if(level>=15) {
                levels.add(3);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            if(level>=21)
            {
                levels.add(3);
                enchantments.add(Enchantment.LUCK);
                weights.add(2);
            }
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_DAMAGE);
                weights.add(10);
            }
            if(level>=32) {
                levels.add(2);
                enchantments.add(Enchantment.ARROW_KNOCKBACK);
                weights.add(2);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_KNOCKBACK);
                weights.add(2);
            }
            if(level>=20) {
                levels.add(1);
                enchantments.add(Enchantment.ARROW_INFINITE);
                weights.add(1);
            }
            if(level>=20)
            {
                levels.add(1);
                enchantments.add(Enchantment.MULTISHOT);
                weights.add(2);
            }
            if(level>=31) {
                levels.add(4);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            else if(level>=21) {
                levels.add(3);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            else if(level>=11) {
                levels.add(2);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.PIERCING);
                weights.add(10);
            }
            if(level>=32) {
                levels.add(2);
                enchantments.add(Enchantment.QUICK_CHARGE);
                weights.add(5);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.QUICK_CHARGE);
                weights.add(5);
            }
            if(level>=26) {
                levels.add(3);
                enchantments.add(Enchantment.LOYALTY);
                weights.add(5);
            }
            else if(level>=19) {
                levels.add(2);
                enchantments.add(Enchantment.LOYALTY);
                weights.add(5);
            }
            else if(level>=12) {
                levels.add(1);
                enchantments.add(Enchantment.LOYALTY);
                weights.add(5);
            }
            if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.CHANNELING);
                weights.add(1);
            }
            if(level>=31) {
                levels.add(3);
                enchantments.add(Enchantment.RIPTIDE);
                weights.add(2);
            }
            else if(level>=24) {
                levels.add(2);
                enchantments.add(Enchantment.RIPTIDE);
                weights.add(2);
            }
            else if(level>=17) {
                levels.add(1);
                enchantments.add(Enchantment.RIPTIDE);
                weights.add(2);
            }
            if(level>=33) {
                levels.add(5);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=25) {
                levels.add(4);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=17) {
                levels.add(3);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=9) {
                levels.add(2);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
            else if(level>=1) {
                levels.add(1);
                enchantments.add(Enchantment.IMPALING);
                weights.add(2);
            }
        }
        return new EnchantLevel(enchantments,levels,weights);
    }

    public void calculate(ItemStack item,Inventory inv,HumanEntity player) {
        if (item == null)
            empty(inv);
        else {
            if (item.getItemMeta().hasEnchants()||(inv.getItem(14)!=null&&inv.getItem(14).getType()!=Material.LAPIS_LAZULI))
                deny(inv);
            else {
                List<Map<?, ?>> listEnchants = null;

                boolean go_to = false;
                if(plugin.config.contains(player.getUniqueId() + "Enchants")&&plugin.config.isList(player.getUniqueId() + "Enchants")) {
                    FileConfiguration config = plugin.getConfig();
                    listEnchants = config.getMapList(player.getUniqueId() + "Enchants");
                    if(listEnchants.isEmpty())
                        go_to=true;
                }
                else {
                    go_to = true;
                }
                Map<?, ?> enchants = null;
                if(!go_to)
                    enchants = listEnchants.get(0);

                if (!go_to&&enchants.containsKey(item.getType().getKey().toString() + "1")) {
                    String[] topEnchant = ((String) enchants.get(item.getType().getKey().toString() + "1")).split(" ");
                    String[] middleEnchant = ((String) enchants.get(item.getType().getKey().toString() + "2")).split(" ");
                    String[] bottomEnchant = ((String) enchants.get(item.getType().getKey().toString() + "3")).split(" ");

                    int topLevel = Integer.parseInt(topEnchant[topEnchant.length - 1]);
                    int middleLevel = Integer.parseInt(middleEnchant[middleEnchant.length - 1]);
                    int bottomLevel = Integer.parseInt(bottomEnchant[bottomEnchant.length - 1]);

                    String enchantNameTop = topEnchant[0];
                    for (int i = 1; i < topEnchant.length - 1; i++) {
                        enchantNameTop = enchantNameTop + " " + topEnchant[i];
                    }
                    String enchantNameMiddle = middleEnchant[0];
                    for (int i = 1; i < middleEnchant.length - 1; i++) {
                        enchantNameMiddle = enchantNameMiddle + " " + middleEnchant[i];
                    }
                    String enchantNameBottom = bottomEnchant[0];
                    for (int i = 1; i < bottomEnchant.length - 1; i++) {
                        enchantNameBottom = enchantNameBottom + " " + bottomEnchant[i];
                    }

                    ItemStack top = new ItemStack(Material.EXPERIENCE_BOTTLE, topLevel);
                    ItemMeta topMeta = top.getItemMeta();
                    topMeta.setDisplayName(ChatColor.BLUE + enchantNameTop);
                    top.setItemMeta(topMeta);


                    ItemStack middle = new ItemStack(Material.EXPERIENCE_BOTTLE, middleLevel);
                    ItemMeta middleMeta = middle.getItemMeta();
                    middleMeta.setDisplayName(ChatColor.BLUE + enchantNameMiddle);
                    middle.setItemMeta(middleMeta);


                    ItemStack bottom = new ItemStack(Material.EXPERIENCE_BOTTLE, bottomLevel);
                    ItemMeta bottomMeta = bottom.getItemMeta();
                    bottomMeta.setDisplayName(ChatColor.BLUE + enchantNameBottom);
                    bottom.setItemMeta(bottomMeta);

                    inv.setItem(29, top);
                    inv.setItem(31, middle);
                    inv.setItem(33, bottom);
                } else {
                    int bookshelves = plugin.getConfig().getInt(player.getUniqueId().toString() + "Bookshelves");
                    int baseLevel = random.nextInt(9 - 1) + 1 + (int) Math.floor(bookshelves / 2) + random.nextInt(bookshelves + 1);
                    int baseLevelTop = (int) Math.floor(Math.max(baseLevel / 3, 1));
                    int baseLevelMiddle = (int) Math.floor(baseLevel * 2 / 3 + 1);
                    int baseLevelBottom = (int) Math.floor(Math.max(baseLevel, bookshelves * 2));

                    int enchantability = getEnchantability(item);

                    int rand_enchantability = 1 + random.nextInt(enchantability / 4 + 1) + random.nextInt(enchantability / 4 + 1);
                    int k = baseLevelTop + rand_enchantability;
                    float rand_bonus_percent = 1 + (random.nextFloat() + random.nextFloat() - 1) * 0.15f;
                    int finalTop = round(k * rand_bonus_percent);
                    if (finalTop < 1) finalTop = 1;

                    rand_enchantability = 1 + random.nextInt(enchantability / 4 + 1) + random.nextInt(enchantability / 4 + 1);
                    k = baseLevelMiddle + rand_enchantability;
                    rand_bonus_percent = 1 + (random.nextFloat() + random.nextFloat() - 1) * 0.15f;
                    int finalMiddle = round(k * rand_bonus_percent);
                    if (finalMiddle < 1) finalMiddle = 1;

                    rand_enchantability = 1 + random.nextInt(enchantability / 4 + 1) + random.nextInt(enchantability / 4 + 1);
                    k = baseLevelBottom + rand_enchantability;
                    rand_bonus_percent = 1 + (random.nextFloat() + random.nextFloat() - 1) * 0.15f;
                    int finalBottom = round(k * rand_bonus_percent);
                    if (finalBottom < 1) finalBottom = 1;

                    if (finalTop < 1) finalTop = 1;
                    if (finalMiddle < 1) finalMiddle = 1;
                    if (finalBottom < 1) finalBottom = 1;

                    if (getEnchants(item, finalTop).getEnchantments().isEmpty()) {
                        deny(inv);
                        return;
                    }

                    try {
                        WorldwideSMP.plugin.config.set(player.getUniqueId().toString() + "Top", finalTop);
                    } catch (NullPointerException err) {
                        WorldwideSMP.plugin.config.addDefault(player.getUniqueId().toString() + "Top", finalTop);
                    }
                    WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
                    WorldwideSMP.plugin.saveConfig();

                    try {
                        WorldwideSMP.plugin.config.set(player.getUniqueId().toString() + "Middle", finalMiddle);
                    } catch (NullPointerException err) {
                        WorldwideSMP.plugin.config.addDefault(player.getUniqueId().toString() + "Middle", finalMiddle);
                    }
                    WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
                    WorldwideSMP.plugin.saveConfig();

                    try {
                        WorldwideSMP.plugin.config.set(player.getUniqueId().toString() + "Bottom", finalBottom);
                    } catch (NullPointerException err) {
                        WorldwideSMP.plugin.config.addDefault(player.getUniqueId().toString() + "Bottom", finalBottom);
                    }
                    WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
                    WorldwideSMP.plugin.saveConfig();

                    int weightSumTop = 0;
                    for (int i = 0; i < getEnchants(item, finalTop).getLevels().size(); i++) {
                        weightSumTop += getEnchants(item, finalTop).getWeight().get(i);
                    }
                    int weightSumMiddle = 0;
                    for (int i = 0; i < getEnchants(item, finalMiddle).getLevels().size(); i++) {
                        weightSumMiddle += getEnchants(item, finalMiddle).getWeight().get(i);
                    }
                    int weightSumBottom = 0;
                    for (int i = 0; i < getEnchants(item, finalBottom).getLevels().size(); i++) {
                        weightSumBottom += getEnchants(item, finalBottom).getWeight().get(i);
                    }
                    int weightTop = random.nextInt(weightSumTop + 2) + 1;
                    int weightMiddle = random.nextInt(weightSumMiddle + 2) + 1;
                    int weightBottom = random.nextInt(weightSumBottom + 2) + 1;

                    Enchantment chosenTop = null;
                    int chosenLevelTop = 0;
                    for (int i = 0; i < getEnchants(item, finalTop).getLevels().size(); i++) {
                        weightTop -= getEnchants(item, finalTop).getWeight().get(i);
                        if (weightTop < 0) {
                            ArrayList<Enchantment> enchantsList = getEnchants(item, finalTop).getEnchantments();
                            chosenTop = enchantsList.get(i);
                            chosenLevelTop = getEnchants(item, finalTop).getLevels().get(i);
                            break;
                        }
                    }

                    Enchantment chosenMiddle = null;
                    int chosenLevelMiddle = 0;
                    for (int i = 0; i < getEnchants(item, finalMiddle).getLevels().size(); i++) {
                        weightMiddle -= getEnchants(item, finalMiddle).getWeight().get(i);
                        if (weightMiddle < 0) {
                            chosenMiddle = getEnchants(item, finalMiddle).getEnchantments().get(i);
                            chosenLevelMiddle = getEnchants(item, finalMiddle).getLevels().get(i);
                            break;
                        }
                    }
                    Enchantment chosenBottom = null;
                    int chosenLevelBottom = 0;
                    for (int i = 0; i < getEnchants(item, finalBottom).getLevels().size(); i++) {
                        weightBottom -= getEnchants(item, finalBottom).getWeight().get(i);
                        if (weightBottom < 0) {
                            chosenBottom = getEnchants(item, finalBottom).getEnchantments().get(i);
                            chosenLevelBottom = getEnchants(item, finalBottom).getLevels().get(i);
                            break;
                        }
                    }
                    try {
                        ItemStack top = new ItemStack(Material.EXPERIENCE_BOTTLE, baseLevelTop);
                        ItemMeta topMeta = top.getItemMeta();
                        topMeta.setDisplayName(ChatColor.BLUE + convertEnchantToName(chosenTop) + " " + chosenLevelTop);
                        top.setItemMeta(topMeta);


                        ItemStack middle = new ItemStack(Material.EXPERIENCE_BOTTLE, baseLevelMiddle);
                        ItemMeta middleMeta = middle.getItemMeta();
                        middleMeta.setDisplayName(ChatColor.BLUE + convertEnchantToName(chosenMiddle) + " " + chosenLevelMiddle);
                        middle.setItemMeta(middleMeta);

                        ItemStack bottom = new ItemStack(Material.EXPERIENCE_BOTTLE, baseLevelBottom);
                        ItemMeta bottomMeta = bottom.getItemMeta();
                        bottomMeta.setDisplayName(ChatColor.BLUE + convertEnchantToName(chosenBottom) + " " + chosenLevelBottom);
                        bottom.setItemMeta(bottomMeta);

                        Map<String,String> enchantsMap = new HashMap<>();
                        enchantsMap.put(item.getType().getKey().toString()+"1",top.getItemMeta().getDisplayName().substring(2) + " " +baseLevelTop);
                        enchantsMap.put(item.getType().getKey().toString()+"2",middle.getItemMeta().getDisplayName().substring(2) + " " +baseLevelMiddle);
                        enchantsMap.put(item.getType().getKey().toString()+"3",bottom.getItemMeta().getDisplayName().substring(2) + " " +baseLevelBottom);

                        List<Map<?,?>> sendEnchants = new ArrayList<>();
                        sendEnchants.add(enchantsMap);
                        try {
                            WorldwideSMP.plugin.config.set(player.getUniqueId() + "Enchants", sendEnchants);
                        } catch (NullPointerException err) {
                            WorldwideSMP.plugin.config.addDefault(player.getUniqueId() + "Enchants", sendEnchants);
                        }
                        WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
                        WorldwideSMP.plugin.saveConfig();

                        inv.setItem(29, top);
                        inv.setItem(31, middle);
                        inv.setItem(33, bottom);

                    } catch (Exception ignored) {
                        calculate(item, inv, player);
                    }
                }
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
////        Location loc = new Location(e.getPlayer().getWorld(), 0,0,0);
////        e.getPlayer().openEnchanting(loc,true);
//        Enchantment a = Enchantment.DAMAGE_ALL;
//        Enchantment b = Enchantment.DAMAGE_UNDEAD;
//        logger.info(String.valueOf(b.conflictsWith(a)));
//    }


    @EventHandler
    public void onInvDrag(InventoryDragEvent e) {
        if (e.getView().getTitle().equals("Enchanting Table")) {
            ItemStack item = e.getOldCursor();
            if (e.getRawSlots().contains(14)&&item != null && item.getType() != Material.LAPIS_LAZULI)
                e.setCancelled(true);
            else if(e.getRawSlots().contains(12)){
                if(e.getOldCursor().getAmount()>1){
                    Iterator<Integer> itr = e.getRawSlots().iterator();
                    itr.next();
                    if(itr.hasNext()){
                        e.setCancelled(true);
                    }
                    else{
                        item.setAmount(item.getAmount()-1);
                        e.setCursor(item);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                        item.setAmount(1);
                        e.getView().getTopInventory().setItem(12,item);
                            }
                        }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
                    }
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        calculate(e.getView().getTopInventory().getItem(12), e.getView().getTopInventory(), e.getWhoClicked());
                    }
                }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        try {
            if (e.getView().getTitle().equals("Enchanting Table")) {
                if(e.isShiftClick()||e.getAction()== InventoryAction.HOTBAR_SWAP)
                {
                        e.setCancelled(true);
                }
                if((e.getClickedInventory().equals(e.getView().getTopInventory()) && e.getSlot() == 14)) {
                    ItemStack item = e.getCursor();
                    if ((!(item.getType()==Material.AIR&&e.getView().getTopInventory().getItem(14).getType()==Material.LAPIS_LAZULI))&&(item != null && item.getType() != Material.LAPIS_LAZULI))
                        e.setCancelled(true);

                }
                else if((e.getClickedInventory().equals(e.getView().getTopInventory()) && e.getSlot() == 12)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ItemStack item = e.getView().getTopInventory().getItem(12);
                            if (item!=null&&item.getAmount() > 1) {
                                item.setAmount(item.getAmount() - 1);
                                e.setCursor(item);
                                item.setAmount(1);
                                e.getView().getTopInventory().setItem(12, item);
                            }
                            calculate(e.getView().getTopInventory().getItem(12), e.getView().getTopInventory(), e.getWhoClicked());
                        }
                    }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
                }
                else if(e.getClickedInventory().equals(e.getView().getBottomInventory())) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!(e.getView().getTopInventory().getItem(29).getType() == Material.EXPERIENCE_BOTTLE && e.getView().getTopInventory().getItem(12) != null))
                                calculate(e.getView().getTopInventory().getItem(12), e.getView().getTopInventory(), e.getWhoClicked());
                        }
                    }.runTaskLaterAsynchronously(WorldwideSMP.plugin, 2);
                }
                if(e.getClickedInventory().equals(e.getView().getTopInventory()) &&(e.getSlot()==29||e.getSlot()==31||e.getSlot()==33)) {
                    e.setCancelled(true);
                    if (!(e.getClickedInventory().getItem(29).getItemMeta().getDisplayName().equals(ChatColor.RED + "Place an item to enchant!") || e.getClickedInventory().getItem(29).getItemMeta().getDisplayName().equals(ChatColor.RED + "Cannot enchant item!"))) {
                        Player player = (Player) e.getWhoClicked();
                        boolean fail = false;
                        if(e.getView().getTopInventory().getItem(14)==null)
                            fail=true;
                        else {
                            switch (e.getSlot()) {
                                case 29:
                                    if (e.getView().getTopInventory().getItem(14).getAmount() < 1)
                                        fail = true;
                                    break;
                                case 31:
                                    if (e.getView().getTopInventory().getItem(14).getAmount() < 2)
                                        fail = true;
                                    break;
                                case 33:
                                    if (e.getView().getTopInventory().getItem(14).getAmount() < 3)
                                        fail = true;
                                    break;
                            }
                        }
                        if(!fail) {
                            if (player.getLevel() >= e.getClickedInventory().getItem(e.getSlot()).getAmount()) {
                                switch (e.getSlot()) {
                                    case 29:
                                        player.setLevel(player.getLevel() - 1);
                                        break;
                                    case 31:
                                        player.setLevel(player.getLevel() - 2);
                                        break;
                                    case 33:
                                        player.setLevel(player.getLevel() - 3);
                                        break;
                                }

                                if (plugin.config.contains(player.getUniqueId() + "Enchants")) {
                                    plugin.config.set(player.getUniqueId() + "Enchants", "");
                                    WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
                                    WorldwideSMP.plugin.saveConfig();
                                }


                                ItemStack item = e.getClickedInventory().getItem(12);
                                String name = e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName();
                                String[] names = name.split(" ");
                                String resultName = names[0];
                                resultName = resultName.substring(2);
                                for (int i = 1; i < names.length - 1; i++) {
                                    resultName = resultName + " " + names[i];
                                }
                                int amount = Integer.parseInt(names[names.length - 1]);
                                if (item.getType() == Material.BOOK) {
                                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
                                    meta.addStoredEnchant(convertNameToEnchant(resultName), amount, true);
                                    book.setItemMeta(meta);
                                    e.getClickedInventory().setItem(12, book);
                                } else {
                                    item.addEnchantment(convertNameToEnchant(resultName), amount);
                                    //pick another enchantment
                                    int rand_continue = random.nextInt(50);
                                    int finalLevel = 0;
                                    switch (e.getSlot()) {
                                        case 29:
                                            finalLevel = WorldwideSMP.plugin.config.getInt(player.getUniqueId().toString() + "Top");
                                            break;
                                        case 31:
                                            finalLevel = WorldwideSMP.plugin.config.getInt(player.getUniqueId().toString() + "Middle");
                                            break;
                                        case 33:
                                            finalLevel = WorldwideSMP.plugin.config.getInt(player.getUniqueId().toString() + "Bottom");
                                            break;
                                    }
                                    int continueLevel = finalLevel;
                                    for (; ; )
                                        if (continueLevel >= rand_continue) {
                                            ArrayList<Enchantment> enchants = getEnchants(item, finalLevel).getEnchantments();
                                            ArrayList<Integer> levels = getEnchants(item, finalLevel).getLevels();
                                            ArrayList<Integer> weights = getEnchants(item, finalLevel).getWeight();

                                            ArrayList<Enchantment> alreadyEnchants = new ArrayList<Enchantment>(item.getEnchantments().keySet());

                                            for (int i = 0; i < enchants.size(); i++) {
                                                for (int j = 0; j < alreadyEnchants.size(); j++) {
                                                    if (enchants.get(i).conflictsWith(alreadyEnchants.get(j))) {
                                                        i--;
                                                        enchants.remove(i);
                                                    }
                                                }
                                            }

                                            int weightSum = 0;
                                            for (Integer integer : weights) {
                                                weightSum += integer;
                                            }
                                            int weight = random.nextInt(weightSum + 2) + 1;
                                            Enchantment chosen = null;
                                            int chosenLevel = 0;
                                            do {
                                                for (int i = 0; i < weights.size(); i++) {
                                                    weight -= getEnchants(item, finalLevel).getWeight().get(i);
                                                    if (weight < 0) {
                                                        ArrayList<Enchantment> enchantsList = enchants;
                                                        chosen = enchantsList.get(i);
                                                        chosenLevel = levels.get(i);
                                                        break;
                                                    }
                                                }
                                            } while (chosen == null);

                                            item.addEnchantment(chosen, chosenLevel);
                                            continueLevel /= 2;
                                        } else {
                                            break;
                                        }
                                }
                                switch (e.getSlot()) {
                                    case 29:
                                        ItemStack reduce = e.getView().getTopInventory().getItem(14);
                                        reduce.setAmount(reduce.getAmount()-1);
                                        e.getView().getTopInventory().setItem(14,reduce);
                                        break;
                                    case 31:
                                        ItemStack reduce2 = e.getView().getTopInventory().getItem(14);
                                        reduce2.setAmount(reduce2.getAmount()-2);
                                        e.getView().getTopInventory().setItem(14,reduce2);
                                        break;
                                    case 33:
                                        ItemStack reduce3 = e.getView().getTopInventory().getItem(14);
                                        reduce3.setAmount(reduce3.getAmount()-3);
                                        e.getView().getTopInventory().setItem(14,reduce3);
                                        break;
                                }
                                deny(e.getClickedInventory());
                            } else {
                                player.sendMessage(ChatColor.RED + "You don't have enough levels.");
                            }
                        } else{
                            player.sendMessage(ChatColor.RED + "You don't have enough lapis.");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        } catch (Exception ignored){}
    }

    @EventHandler
    public void invCloseEvent(InventoryCloseEvent e){
        if (e.getView().getTitle().equals("Enchanting Table")) {
            ItemStack item = e.getInventory().getItem(12);
            ItemStack lapis = e.getInventory().getItem(14);
            if(item!=null)
                givePlayer(e.getPlayer(), item);
            if(lapis!=null)
                givePlayer(e.getPlayer(), lapis);
        }
    }

}
