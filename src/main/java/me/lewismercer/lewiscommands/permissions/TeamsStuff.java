package me.lewismercer.lewiscommands.permissions;

import me.lewismercer.lewiscommands.api.PermissionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TeamsStuff {

    PermissionsAPI api = new PermissionsAPI();

    public void createTeams(Player p, Rank rank){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();

        if(scoreboard.getTeam(rank.name()) == null){
            Team idk = scoreboard.registerNewTeam(rank.name());
            idk.setColor(api.getColour(rank));
        }


        scoreboard.getTeam(rank.name()).addEntry(p.getName());
    }
}
