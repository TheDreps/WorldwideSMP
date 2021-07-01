package me.lewismercer.lewiscommands.events;

import me.lewismercer.lewiscommands.LewisCommands;
import me.lewismercer.lewiscommands.api.PermissionsAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class PlayerChat implements Listener {

    PermissionsAPI api = new PermissionsAPI();

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        List<String> MutedPlayers = LewisCommands.plugin.getConfig().getStringList("Muted");
        if(MutedPlayers.contains(e.getPlayer().getName().toLowerCase())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You are currently muted!");
            return;
        }



        e.setFormat(api.getChatName(p) + " " + e.getMessage());




    }



}
