package de.proxycloud.bungeesystem.utils.ban;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class BanManager
{

    private Configuration configuration;

    public boolean isBanned(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM ban WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("uuid") != null;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void ban(String uuid, String name, String grund, long seconds, String team)
    {
        long end = 0;
        if(seconds == -1)
        {
            end = -1;
        }
        else
        {
            long current = System.currentTimeMillis();
            long millis = seconds * 1000;
            end = current + millis;
        }
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO ban VALUES('" + uuid + "', '" + name + "', '" + grund + "', '" + end + "', '" + team + "')");
        final String teamUUID = BungeeSystem.getInstance().getUuidFetcher().getUUID(team).toString();
        BungeeSystem.getInstance().getTeamManager().setBan(teamUUID, BungeeSystem.getInstance().getTeamManager().getBan(teamUUID) + 1);
        try
        {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
            if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e"
                        + this.configuration.getString("server.name")
                        + " §7Netzwerk §c§lgebannt§8. \n\n §7Grund §8» §c" + grund));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void unban(String uuid)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("DELETE FROM ban WHERE uuid='" + uuid + "'");
    }

    public String getReason(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM ban WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("reason");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getTeam(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM ban WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("team");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }

    public long getEnd(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM ban WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getLong("ende");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public List<String> getBannedUUIDs()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM ban");
        final List<String> uuids = new ArrayList<>();
        try
        {
            while(resultSet.next())
            {
                uuids.add(resultSet.getString("name"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return uuids;
    }

    public String getRemainingTime(String uuid) {
        long current = System.currentTimeMillis();
        long end = this.getEnd(uuid);
        if(end == -1) {
            return "§4Permanent";
        }
        long millis = end - current;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long weeks = 0;
        while (millis > 1000) {
            millis -= 1000;
            ++seconds;
        }
        while (seconds > 60) {
            seconds -= 60;
            ++minutes;
        }
        while (minutes > 60) {
            minutes -= 60;
            ++hours;
        }
        while (hours > 24) {
            hours -= 24;
            ++days;
        }
        while (days > 7) {
            days -= 7;
            ++weeks;
        }
        return "§e" + weeks + " §7Wochen§8, §e" + days + " §7Tage§8, §e" + hours + " §7Stunden§8, §e" + minutes + " §7Minuten§8, §e" + seconds + " §7Sekunden§8.";
    }

}
