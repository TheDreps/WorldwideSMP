package com.worldwidesmp.worldwidesmp.enchantments;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class HeadChance extends Enchantment implements Listener {

    // INITIALIZER

    public HeadChance(String namespace) {
        super(new NamespacedKey(WorldwideSMP.plugin, namespace));
    }

    // METHODS

    @Override
    public @NotNull
    String getName() {
        return "Head Chance";
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        if (this.getItemTarget() == EnchantmentTarget.WEAPON) {
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
        return other.equals(this);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        switch (item.getType()) {
            case NETHERITE_SWORD:
            case DIAMOND_SWORD:
            case IRON_SWORD:
            case STONE_SWORD:
            case WOODEN_SWORD:
            case BOW:
            case CROSSBOW:
                return true;
        }
        return false;
    }

    private ItemStack getMobHead(EntityType entity) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        switch(entity)
        {
            case BLAZE:
                skullMeta.setOwner("MHF_Blaze");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Blaze Head");
                break;
            case CHICKEN:
                skullMeta.setOwner("MHF_Chicken");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Chicken Head");
                break;
            case COW:
                skullMeta.setOwner("MHF_Cow");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Cow Head");
                break;
            case CREEPER:
                skullMeta.setOwner("MHF_Creeper");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Creeper Head");
                break;
            case ENDERMAN:
                skullMeta.setOwner("MHF_Enderman");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Enderman Head");
                break;
            case GHAST:
                skullMeta.setOwner("MHF_Ghast");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Ghast Head");
                break;
            case IRON_GOLEM:
                skullMeta.setOwner("MHF_Golem");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Iron Golem Head");
                break;
            case PIG:
                skullMeta.setOwner("MHF_Pig");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Pig Head");
                break;
            case SHEEP:
                skullMeta.setOwner("MHF_Sheep");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Sheep Head");
                break;
            case SKELETON:
                skullMeta.setOwner("MHF_Skeleton");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Skeleton Head");
                break;
            case SLIME:
                skullMeta.setOwner("MHF_Slime");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Slime Head");
                break;
            case SPIDER:
                skullMeta.setOwner("MHF_Spider");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Spider Head");
                break;
            case SQUID:
                skullMeta.setOwner("MHF_Squid");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Squid Head");
                break;
            case VILLAGER:
                skullMeta.setOwner("MHF_Villager");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Villager Head");
                break;
            case ZOMBIE:
                skullMeta.setOwner("MHF_Zombie");
                skullMeta.setDisplayName(ChatColor.YELLOW+"Zombie Head");
                break;
            default:
                return null;
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }

    // MECHANICS

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        Player player = e.getEntity().getKiller();
        ItemStack item = player.getItemInHand();
        if (item.containsEnchantment(this)) {
            ItemStack head = getMobHead(e.getEntity().getType());
            if(head!=null){
                Random random = new Random();
                int chance = item.getEnchantmentLevel(this);
                boolean drop = item.containsEnchantment(WorldwideSMP.telekinesis);
                if (random.nextInt(100)<chance) {
                    if(drop)
                        WorldwideSMP.addTelekineticDrop(player, head, e.getEntity().getLocation());
                    else
                        player.getWorld().dropItemNaturally(e.getEntity().getLocation(),head);
                }
                for (ItemStack is : e.getDrops()) {
                    if(drop)
                        WorldwideSMP.addTelekineticDrop(player, is, e.getEntity().getLocation());
                    else
                        player.getWorld().dropItemNaturally(e.getEntity().getLocation(),is);
                }
                e.getDrops().clear();

            }
        }
    }
}
