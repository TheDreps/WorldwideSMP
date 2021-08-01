package com.worldwidesmp.worldwidesmp;

import com.worldwidesmp.worldwidesmp.enchantments.SmeltingTouch;
import com.worldwidesmp.worldwidesmp.enchantments.Telekinesis;
import com.worldwidesmp.worldwidesmp.gui.*;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

public final class WorldwideSMP extends JavaPlugin {

    public static WorldwideSMP plugin;
    public static Logger logger = Bukkit.getLogger();
    PluginManager pm = Bukkit.getPluginManager();
    public FileConfiguration config = this.getConfig();
    public static Telekinesis telekinesis;
    public static SmeltingTouch smeltingTouch;

    @Override
    public void onEnable() {
        plugin = this;
//        NamespacedKey key = new NamespacedKey(this, "backpack");
//
//        ShapedRecipe recipe = new ShapedRecipe(key, Items.backpack);
//
//        recipe.shape("LLL", "L L", "LLL");
//
//        recipe.setIngredient('L', Material.LEATHER);
//
//        Bukkit.addRecipe(recipe);

        telekinesis = new Telekinesis("telekinesis");
        registerEnchantment(telekinesis);

        smeltingTouch = new SmeltingTouch("smeltingTouch");
        registerEnchantment(smeltingTouch);
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        //
        try{
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            if(byKey.containsKey(telekinesis.getKey())){
                byKey.remove(telekinesis.getKey());
            }else if(byKey.containsKey(smeltingTouch.getKey())){
                byKey.remove(smeltingTouch.getKey());
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            if(byName.containsKey(telekinesis.getName())){
                byName.remove(telekinesis.getName());
            }else if(byName.containsKey(smeltingTouch.getKey())){
                byName.remove(smeltingTouch.getKey());
            }

        }catch (Exception ignored){}
    }

    private void registerCommands(){
        //
    }

    private void registerEvents(){
        pm.registerEvents(new GuiEvents(), this);
        pm.registerEvents(new Crafting(), this);
        pm.registerEvents(new Backpack(), this);
        pm.registerEvents(new Recipes(), this);
        pm.registerEvents(new EnchantingTable(),this);
        pm.registerEvents(new Anvil(),this);
        pm.registerEvents(telekinesis, this);
        pm.registerEvents(smeltingTouch, this);
<<<<<<< Updated upstream
        pm.registerEvents(new EnchantingTable(),this);
=======
        pm.registerEvents(headChance, this);
>>>>>>> Stashed changes
    }

    public static void registerEnchantment(Enchantment ench){
        boolean registered = true;
        try{
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(ench);
        }catch (Exception e){
            registered = false;
            e.printStackTrace();
        }
    }

    public static void addTelekineticDrop(Player player,ItemStack is, Location location){
        if(!(player.getInventory().addItem(is).isEmpty()))
        {
            player.getWorld().dropItemNaturally(location, is);
        }
    }

    public static void givePlayer(HumanEntity player, ItemStack item){
        if(!player.getInventory().addItem(item).isEmpty())
        {
            World world = player.getWorld();
            world.dropItemNaturally(player.getLocation(), item);
        }
    }
}
