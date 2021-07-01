package me.lewismercer.lewiscommands.events;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Location loc = e.getTo();
        World world = loc.getWorld();
        if(world.getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType().equals(Material.COMPOSTER) || world.getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType().equals(Material.CAULDRON)){
            if(!(world.getBlockAt(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ()).getType().equals(Material.AIR))){
                e.getPlayer().kickPlayer("Don't abuse glitches, this has been logged.");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "A player has been removed for abusing glitches, this will be logged to keep a fair and fun experience for everyone");
            }
        }
    }
}
