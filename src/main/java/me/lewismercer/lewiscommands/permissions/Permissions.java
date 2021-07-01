package me.lewismercer.lewiscommands.permissions;

import me.lewismercer.lewiscommands.LewisCommands;
import me.lewismercer.lewiscommands.api.PermissionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Permissions implements Listener {

    PermissionsAPI api = new PermissionsAPI();

    public static ArrayList<String> defaultPermissions = new ArrayList<>();
    public static ArrayList<String> modPermissions = new ArrayList<>();
    public static ArrayList<String> idkPermissions = new ArrayList<>();
    public static ArrayList<String> supporterPermissions = new ArrayList<>();

    public static HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    public void setupPermissions(Player p){
        PermissionAttachment attachment = p.addAttachment(LewisCommands.plugin);
        playerPermissions.put(p.getUniqueId(), attachment);
        permissionsSetter(p.getUniqueId());
    }

    private void permissionsSetter(UUID uuid){
        PermissionAttachment attachment = playerPermissions.get(uuid);

        Rank rank = api.getRank(uuid);

        switch(rank){
            case GOD:
                attachment.setPermission("*", true);
            case IDK:
                for (String idkPermission : idkPermissions) {
                    attachment.setPermission(idkPermission, true);
                }
            case MOD:
                for (String modPermission : modPermissions) {
                    attachment.setPermission(modPermission, true);
                }
            case CC:
                // do nothing
            case DEV:
                //nothing
            case SUPPORTER:
                for (String supporterPermission : supporterPermissions) {
                    attachment.setPermission(supporterPermission, true);
                }
            default:
                for (String defaultPermission : defaultPermissions) {
                    attachment.setPermission(defaultPermission, true);
                }
                if(rank != Rank.IDK && rank != Rank.DEV && rank != Rank.GOD) {
                    attachment.setPermission("bukkit.command.plugins", false);
                    attachment.setPermission("owoifier.use", false);
                }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        TeamsStuff ts = new TeamsStuff();
        Player p = e.getPlayer();
        setupPermissions(p);

        p.setDisplayName(api.getChatName(p) + "");
        ts.createTeams(p, api.getRank(p.getUniqueId()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        try{
            e.getPlayer().removeAttachment(playerPermissions.get(uuid));
        }catch(IllegalArgumentException exception){
            Bukkit.getLogger().info("No attachment to remove");
        }

        playerPermissions.remove(uuid);
        PermissionsAPI.rankCache.remove(uuid);
    }



    public static void addDefaultPermissions() {
        defaultPermissions.add("bukkit.command.tps");
        defaultPermissions.add("chairs.sit");
        defaultPermissions.add("chairs.use");
        defaultPermissions.add("lewiscommands.balance");
        defaultPermissions.add("lewiscommands.balance.others");
        defaultPermissions.add("lewiscommands.pay");
        defaultPermissions.add("lewiscommands.sell");
        defaultPermissions.add("lewiscommands.store");
        defaultPermissions.add("smoothtimber.*");
    }
    public static void addModPermissions(){
        modPermissions.add("minecraft.command.ban");
        modPermissions.add("minecraft.command.ban-ip");
        modPermissions.add("minecraft.command.banlist");
        modPermissions.add("minecraft.command.kick");
        modPermissions.add("minecraft.command.pardon");
        modPermissions.add("minecraft.command.pardon-ip");
        modPermissions.add("bukkit.command.whitelist.*");
        modPermissions.add("minecraft.command.whitelist");
        modPermissions.add("coreprotect.inspect");
        modPermissions.add("lewiscommands.mute");
        modPermissions.add("lewiscommands.nickname.others");
        modPermissions.add("holoremove.use");
    }
    public static void addSupporterPermissions(){
        supporterPermissions.add("lewiscommands.nickname");
        supporterPermissions.add("pci.blast-furnace");
        supporterPermissions.add("pci.cartography");
        supporterPermissions.add("pci.craft");
        supporterPermissions.add("pci.enchanttable");
        supporterPermissions.add("pci.enchant.use-max-level");
        supporterPermissions.add("pci.enderchest");
        supporterPermissions.add("pci.furnace");
        supporterPermissions.add("pci.loom");
        supporterPermissions.add("pci.smoker");
        supporterPermissions.add("pci.smithingtable");
        supporterPermissions.add("pci.stonecutter");
    }

    public static void addIdkPermissions(){
        idkPermissions.add("coreprotect.*");
        idkPermissions.add("bukkit.command.reload");
        idkPermissions.add("chunky.chunky");
    }

    public static void addPermissions(){
        addDefaultPermissions();
        addModPermissions();
        addSupporterPermissions();
        addIdkPermissions();
    }

}
