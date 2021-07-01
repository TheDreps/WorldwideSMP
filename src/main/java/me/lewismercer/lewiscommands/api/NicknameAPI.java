package me.lewismercer.lewiscommands.api;

import me.lewismercer.lewiscommands.sql.DatabaseLogin;
import me.lewismercer.lewiscommands.sql.MySQL;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class NicknameAPI {
    public static String defaultNick = "reset";

    public static HashMap<UUID, String> nickCache = new HashMap<>();

    public static Connection c;
    MySQL SQL = new MySQL(DatabaseLogin.host, DatabaseLogin.port, DatabaseLogin.db, DatabaseLogin.user, DatabaseLogin.pw);

    public String getNick(UUID uuid){

        if(nickCache.containsKey(uuid)){
            return nickCache.get(uuid);
        }

        String uuidStr = uuid.toString();

        try{
            c = SQL.open();
            ResultSet res = c.createStatement().executeQuery("SELECT * FROM `Nicknames` WHERE `UUID` = \"" + uuidStr + "\"");

            if (!res.next()) {
                setNick(uuid, defaultNick);
                c = MySQL.closeConnection(c);
                nickCache.put(uuid, defaultNick);
                return defaultNick;
            } else{
                c = MySQL.closeConnection(c);
                return res.getString("NICKNAME");
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        Bukkit.getLogger().warning("SQL Error! (Eco)");
        return "reset";
    }

    public void setNick(UUID uuid, String nick){
        String uuidStr = uuid.toString();

        if(!(Bukkit.getOfflinePlayer(UUID.fromString(uuidStr)).hasPlayedBefore())){
            return;
        }

        try {

            c = SQL.open();
            ResultSet res = c.createStatement()
                    .executeQuery("SELECT * FROM `Nicknames` WHERE `UUID`= \"" + uuidStr + "\"");
            if (!res.next()) {
                c.createStatement()
                        .executeUpdate("INSERT INTO `Nicknames` (`UUID`,`NICKNAME`) VALUES ('" + uuidStr + "','reset')");
            } else {
                c.createStatement()
                        .executeUpdate("UPDATE `Nicknames` SET `NICKNAME`='" + nick + "' WHERE `UUID`='" + uuidStr + "'");
            }
            c = MySQL.closeConnection(c);

            nickCache.put(uuid, nick);

        } catch (SQLException sql) {
            sql.printStackTrace();
            Bukkit.getLogger().warning("SQL Error! (Nicknames)");
        }
    }
}
