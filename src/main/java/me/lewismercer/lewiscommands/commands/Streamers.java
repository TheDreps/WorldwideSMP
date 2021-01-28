package me.lewismercer.lewiscommands.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Streamers implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        TextComponent start = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "Streamers that play on this SMP (click on name):");

        TextComponent lewis = new TextComponent(" - Lewis");
        lewis.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/lewis_mercer" ) );

        TextComponent hunter = new TextComponent(" - Hunter");
        hunter.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/skeletonlimes" ) );

        TextComponent end = new TextComponent("\nIf you play this SMP and do live streaming let Lewis know to be added to this list!");


        if(!(sender instanceof Player)){
            sender.sendMessage("nope");
        }else{
            Player p = (Player) sender;
            p.sendMessage("\n");
            p.spigot().sendMessage(start);
            p.spigot().sendMessage(lewis);
            p.spigot().sendMessage(hunter);
            p.spigot().sendMessage(end);
            p.sendMessage("\n");
        }

        return false;
    }
}
