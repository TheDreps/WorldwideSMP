package me.lewismercer.lewiscommands.api;

import me.lewismercer.lewiscommands.permissions.Rank;
import me.lewismercer.lewiscommands.sql.DatabaseLogin;
import me.lewismercer.lewiscommands.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PermissionsAPI {

    public static Rank defaultRank = Rank.DEFAULT;
    NicknameAPI nickAPI = new NicknameAPI();

    public static HashMap<UUID, Rank> rankCache = new HashMap<>();

    public static Connection c;
    MySQL SQL = new MySQL(DatabaseLogin.host, DatabaseLogin.port, DatabaseLogin.db, DatabaseLogin.user, DatabaseLogin.pw);


    public static Rank strToRank(String str){
        return Rank.valueOf(str);
    }

    public static String rankToStr(Rank rank){
        return rank.toString();
    }


    public Rank getRank(UUID uuid){

        if(rankCache.containsKey(uuid)){
            return rankCache.get(uuid);
        }


        String uuidStr = uuid.toString();

        try{
            c = SQL.open();
            ResultSet res = c.createStatement().executeQuery("SELECT * FROM `Permissions` WHERE `UUID` = \"" + uuidStr + "\"");

            if (!res.next()) {
                setRank(uuid, defaultRank);
                c = MySQL.closeConnection(c);
                rankCache.put(uuid, defaultRank);
                return defaultRank;
            } else{
                c = MySQL.closeConnection(c);
                return strToRank(res.getString("RANK"));
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        Bukkit.getLogger().warning("SQL Error! (Permissions)");
        return Rank.DEFAULT;
    }

    public void setRank(UUID uuid, Rank rank){
        String uuidStr = uuid.toString();

        String rankStr = rankToStr(rank);

        if(!(Bukkit.getOfflinePlayer(UUID.fromString(uuidStr)).hasPlayedBefore())){
            return;
        }

        try {

            c = SQL.open();
            ResultSet res = c.createStatement()
                    .executeQuery("SELECT * FROM `Permissions` WHERE `UUID`= \"" + uuidStr + "\"");
            if (!res.next()) {
                c.createStatement()
                        .executeUpdate("INSERT INTO `Permissions` (`UUID`,`RANK`) VALUES ('" + uuidStr + "','" + rankStr + "')");
            } else {
                c.createStatement()
                        .executeUpdate("UPDATE `Permissions` SET `RANK`='" + rankStr + "' WHERE `UUID`='" + uuidStr + "'");
            }
            c = MySQL.closeConnection(c);

            rankCache.put(uuid, rank);

        } catch (SQLException sql) {
            sql.printStackTrace();
            Bukkit.getLogger().warning("SQL Error! (Permissions)");
        }
    }

    public String getChatName(Player p){
        UUID uuid = p.getUniqueId();

        String name = p.getName();
        if(p.hasPermission("lewiscommands.nickname") && !nickAPI.getNick(p.getUniqueId()).equals("reset")){
            name = getColour(p) + "~" + ChatColor.translateAlternateColorCodes('&', nickAPI.getNick(uuid));
        }



        switch(getRank(uuid)){
            case GOD:
                return(ChatColor.DARK_RED + "" + ChatColor.BOLD + "GOD " + ChatColor.RESET + ChatColor.DARK_RED + name + ChatColor.RESET);
            case IDK:
                return(ChatColor.GOLD + "" + ChatColor.BOLD + "IDK " + ChatColor.RESET + ChatColor.GOLD + name + ChatColor.RESET);
            case MOD:
                return(ChatColor.BLUE + "" + ChatColor.BOLD + "MOD " + ChatColor.RESET + ChatColor.BLUE + name + ChatColor.RESET);
            case DEV:
                return(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "DEV " + ChatColor.RESET + ChatColor.DARK_AQUA + name  + ChatColor.RESET);

            case CC:
                return(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CC " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + name + ChatColor.RESET);

            case SUPPORTER:
                return(ChatColor.RED + "" + ChatColor.BOLD + "SUPPORTER " + ChatColor.RESET + ChatColor.RED + name + ChatColor.RESET);

            default:
                return(ChatColor.AQUA + p.getName() + "" + ChatColor.RESET);
        }
    }

    public ChatColor getColour(Rank rank){
        switch(rank){
            case GOD:
                return ChatColor.DARK_RED;
            case IDK:
                return ChatColor.GOLD;
            case MOD:
                return ChatColor.BLUE;
            case DEV:
                return ChatColor.DARK_AQUA;
            case CC:
                return ChatColor.LIGHT_PURPLE;
            case SUPPORTER:
                return ChatColor.RED;
            default:
                return ChatColor.AQUA;
        }
    }

    public ChatColor getColour(Player p){
        return getColour(getRank(p.getUniqueId()));
    }
}
