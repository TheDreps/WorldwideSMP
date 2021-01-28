package me.lewismercer.lewiscommands.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.lewismercer.lewiscommands.sql.DatabaseLogin;
import me.lewismercer.lewiscommands.sql.MySQL;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EcoAPI {


    public static Connection c;
    MySQL SQL = new MySQL(DatabaseLogin.host, DatabaseLogin.port, DatabaseLogin.db, DatabaseLogin.user, DatabaseLogin.pw);

    public double getBal(String uuid){

        try{
            Bukkit.getLogger().info("String: " + uuid);
            c = SQL.open();
            ResultSet res = c.createStatement().executeQuery("SELECT * FROM `Eco` WHERE `UUID` = \"" + uuid + "\"");

            if (!res.next()) {
                setBal(uuid, 0);
                c = MySQL.closeConnection(c);
                return 0;
            } else{
                c = MySQL.closeConnection(c);
                return res.getDouble("BALANCE");
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        Bukkit.getLogger().warning("SQL Error! (Eco)");
        return 0;

    }

    public void setBal(String uuid, double amount) {

        if (amount < 0) {

            amount = 0;

        }

        if(!(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).hasPlayedBefore())){
            return;
        }

        try {

            c = SQL.open();
            ResultSet res = c.createStatement()
                    .executeQuery("SELECT * FROM `Eco` WHERE `UUID`= \"" + uuid + "\"");
            if (!res.next()) {
                c.createStatement()
                        .executeUpdate("INSERT INTO `Eco` (`UUID`,`BALANCE`) VALUES ('" + uuid + "','" + amount + "')");
            } else {
                c.createStatement()
                        .executeUpdate("UPDATE `Eco` SET `BALANCE`='" + amount + "' WHERE `UUID`='" + uuid + "'");
            }
            c = MySQL.closeConnection(c);

        } catch (SQLException sql) {
            sql.printStackTrace();
            Bukkit.getLogger().warning("SQL Error! (Eco)");
        }

    }


    public void addBal(String uuid, double amount) {

        if (amount < 0) {

            amount = Math.abs(amount);
            takeBal(uuid, amount);
            return;

        }

        try {

            amount = getBal(uuid) + amount;
            c = SQL.open();

            ResultSet res = c.createStatement()
                    .executeQuery("SELECT * FROM `Eco` WHERE `UUID`= '" + uuid + "'");
            if (!res.next()) {
                c.createStatement()
                        .executeUpdate("INSERT INTO `Eco` (`UUID`,`BALANCE`) VALUES ('" + uuid + "','" + amount + "')");
            } else {
                c.createStatement()
                        .executeUpdate("UPDATE `Eco` SET `BALANCE`='" + amount + "' WHERE `UUID`='" + uuid + "'");
            }
            c = MySQL.closeConnection(c);

        } catch (SQLException sql) {
            sql.printStackTrace();
            Bukkit.getLogger().warning("SQL Error! (Eco)");
        }

    }

    //

    public void takeBal(String uuid, double amount) {

        if (amount < 0) {

            amount = Math.abs(amount);
            addBal(uuid, amount);
            return;

        }

        if (!checkBal(uuid, amount)) {

            amount = getBal(uuid);

        }

        try {

            amount = getBal(uuid) - amount;
            c = SQL.open();

            ResultSet res = c.createStatement()
                    .executeQuery("SELECT * FROM `Eco` WHERE `UUID`= '" + uuid + "'");
            if (!res.next()) {
                c.createStatement()
                        .executeUpdate("INSERT INTO `Eco` (`UUID`,`BALANCE`) VALUES ('" + uuid + "','" + amount + "')");
            } else {
                c.createStatement()
                        .executeUpdate("UPDATE `Eco` SET `BALANCE`='" + amount + "' WHERE `UUID`='" + uuid + "'");
            }
            c = MySQL.closeConnection(c);

        } catch (SQLException sql) {
            sql.printStackTrace();
            Bukkit.getLogger().warning("SQL Error! (Eco)");
        }

    }

    public boolean checkBal(String uuid, double amount){
        double current = getBal(uuid);

        return amount <= current;
    }

    public UUID getOfflineUUID(String username){

        String urlPrefix = "https://api.mojang.com/users/profiles/minecraft/";

        try {
            URL url = new URL(urlPrefix + username);

            URLConnection request = url.openConnection();
            request.connect();


            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootObj = root.getAsJsonObject();
            String noDashesUUID = rootObj.get("id").getAsString();

            return UUID.fromString(addDashes(noDashesUUID));


        } catch (IOException e) {
            e.printStackTrace();
        }


        return UUID.fromString(addDashes("d66243e52e704048a70fee1eff78e283"));
    }

    public String getOfflineName(String username){

        String urlPrefix = "https://api.mojang.com/users/profiles/minecraft/";

        try {
            URL url = new URL(urlPrefix + username);

            URLConnection request = url.openConnection();
            request.connect();


            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootObj = root.getAsJsonObject();

            return rootObj.get("name").getAsString();


        } catch (IOException e) {
            e.printStackTrace();
        }


        return "Error404";
    }

    public String addDashes(String noDashesUUID){
        StringBuilder sb = new StringBuilder(noDashesUUID);
        sb.insert(20, '-');
        sb.insert(16, '-');
        sb.insert(12, '-');
        sb.insert(8, '-');
        return sb.toString();
    }

    public String roundToTwoDecimalPlaces(float val)
    {
        return String.format("%.2f", val);
    }

    public String roundToTwoDecimalPlaces(double val)
    {
        return String.format("%.2f", val);
    }


}
