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

//        TextComponent toni = new TextComponent(" - Toni");
//        toni.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/phonytoni" ) );
//
//        TextComponent kitseus = new TextComponent(" - Kitseus");
//        kitseus.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/kitseus" ) );
//
//        TextComponent will = new TextComponent(" - Will");
//        will.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/imindebt69" ) );
//
//        TextComponent kersey = new TextComponent(" - Kersey");
//        kersey.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/kersey_" ) );
//
//        TextComponent bel = new TextComponent(" - Bel");
//        bel.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.twitch.tv/bel_icia" ) );

        if(!(sender instanceof Player)){
            sender.sendMessage("nope");
        }else{
            Player p = (Player) sender;
            p.sendMessage("\n");
            p.spigot().sendMessage(start);
            p.spigot().sendMessage(lewis);
            p.spigot().sendMessage(hunter);
            //p.spigot().sendMessage(toni);
            //p.spigot().sendMessage(kitseus);
            //p.spigot().sendMessage(will);
            //p.spigot().sendMessage(kersey);
            //p.spigot().sendMessage(bel);
            p.sendMessage("\n");
        }

        return false;
    }
}
