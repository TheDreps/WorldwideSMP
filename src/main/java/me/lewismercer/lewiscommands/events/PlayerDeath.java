package me.lewismercer.lewiscommands.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        EntityDamageEvent ede = e.getEntity().getLastDamageCause();
        if(ede instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) ede;
            Entity killer = edbe.getDamager();

            if(killer instanceof Zombie){
                Zombie zombie = (Zombie) edbe.getDamager();
                if(!(zombie.isAdult())){
                    e.setDeathMessage(e.getEntity().getDisplayName() + " got Ph1LzAed");
                }
            }
        }

        if(ede.getCause() == EntityDamageEvent.DamageCause.DROWNING || ede.getCause() == EntityDamageEvent.DamageCause.FALL){
            if(e.getEntity().getDisplayName().equalsIgnoreCase("skeletonlimes") || e.getEntity().getDisplayName().equalsIgnoreCase("AlexB2012") || e.getEntity().getDisplayName().equalsIgnoreCase("pcyksoo")){
                e.setDeathMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + e.getDeathMessage() + " (again)");
            }
        }
    }
}
