
/*

All the events to open up a GUI or cancel clicks on protected items

 */
package com.worldwidesmp.worldwidesmp.gui;

import com.worldwidesmp.worldwidesmp.WorldwideSMP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.worldwidesmp.worldwidesmp.gui.EnchantingTable.createGuiItem;

public class GuiEvents implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){ //Opens the Crafting GUI

        if(!(e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {

            Player p = e.getPlayer();


            if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.CRAFTING_TABLE)) {

                if (p.isSneaking() && !(p.getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
                    //do nothing
                    return;
                }

                e.setCancelled(true);
                Crafting craftingGui = new Crafting();
                craftingGui.openInventory(p);

            }

            else if(e.getClickedBlock() != null && e.getClickedBlock().getType()==Material.ENCHANTING_TABLE){

                if (p.isSneaking() && !(p.getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
                    //do nothing
                    return;
                }
                int bookshelves = 0;
                e.setCancelled(true);
                EnchantingTable enchantingTable = new EnchantingTable();
                Location enchantingLoc = e.getClickedBlock().getLocation();
                int x = (int)enchantingLoc.getX();
                int y = (int)enchantingLoc.getY();
                int z = (int)enchantingLoc.getZ();
                World world = e.getPlayer().getWorld();

                if(world.getBlockAt(x+1,y,z).getType()==Material.AIR&&world.getBlockAt(x+1,y+1,z).getType()==Material.AIR) {
                    if (world.getBlockAt(x + 2, y, z).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x + 2, y+1, z).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }
                if(world.getBlockAt(x-1,y,z).getType()==Material.AIR&&world.getBlockAt(x-1,y+1,z).getType()==Material.AIR) {
                    if (world.getBlockAt(x - 2, y, z).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x - 2, y+1, z).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }
                if(world.getBlockAt(x,y,z+1).getType()==Material.AIR&&world.getBlockAt(x,y+1,z+1).getType()==Material.AIR) {
                    if (world.getBlockAt(x , y, z+ 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x , y+1, z+ 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }
                if(world.getBlockAt(x,y,z-1).getType()==Material.AIR&&world.getBlockAt(x,y+1,z-1).getType()==Material.AIR) {
                    if (world.getBlockAt(x , y, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x , y+1, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }

                if(world.getBlockAt(x+1,y,z+1).getType()==Material.AIR&&world.getBlockAt(x+1,y+1,z+1).getType()==Material.AIR){
                    if (world.getBlockAt(x+1 , y, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x+1 , y+1, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x+2 , y, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x+2 , y+1, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x+2 , y, z + 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x+2 , y+1, z + 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }
                if(world.getBlockAt(x-1,y,z-1).getType()==Material.AIR&&world.getBlockAt(x+1,y+1,z+1).getType()==Material.AIR){
                    if (world.getBlockAt(x-1 , y, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x-1 , y+1, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x-2 , y, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x-2 , y+1, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x-2 , y, z - 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x-2 , y+1, z - 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }
                if(world.getBlockAt(x-1,y,z+1).getType()==Material.AIR&&world.getBlockAt(x+1,y+1,z+1).getType()==Material.AIR){
                    if (world.getBlockAt(x-1 , y, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x-1 , y+1, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x-2 , y, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x-2 , y+1, z + 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x-2 , y, z + 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x-2 , y+1, z + 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }
                if(world.getBlockAt(x+1,y,z-1).getType()==Material.AIR&&world.getBlockAt(x+1,y+1,z+1).getType()==Material.AIR){
                    if (world.getBlockAt(x+1 , y, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x+1 , y+1, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x+2 , y, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x+2 , y+1, z - 2).getType() == Material.BOOKSHELF)
                        bookshelves+=1;

                    if (world.getBlockAt(x+2 , y, z - 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                    if(world.getBlockAt(x+2 , y+1, z - 1).getType() == Material.BOOKSHELF)
                        bookshelves+=1;
                }

                if(bookshelves>15)
                    bookshelves=15;

                try {
                    WorldwideSMP.plugin.config.set(p.getUniqueId().toString()+"Bookshelves", bookshelves);
                } catch (NullPointerException err) {
                    WorldwideSMP.plugin.config.addDefault(p.getUniqueId().toString()+"Bookshelves", bookshelves);
                }
                WorldwideSMP.plugin.getConfig().options().copyDefaults(true);
                WorldwideSMP.plugin.saveConfig();

                enchantingTable.openInventory(p);
            }

            else if(e.getClickedBlock() != null && e.getClickedBlock().getType()==Material.ANVIL){

                if (p.isSneaking() && !(p.getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
                    //do nothing
                    return;
                }

                e.setCancelled(true);
                Anvil anvil = new Anvil();
                anvil.openInventory(p);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){ //Cancels placeholder clicks
        try {
            if (e.getCurrentItem() != null) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                if (name.equals(" ")||name.equals(ChatColor.RED+"Cannot enchant item!")||name.equals(ChatColor.RED+"Place an item to enchant!"))
                    e.setCancelled(true);
                else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Close")) {
                    e.getWhoClicked().closeInventory();
                    Player a = (Player) e.getWhoClicked();
                    a.updateInventory();
                }
            }
        } catch(Exception ignored) {}
    }
}
