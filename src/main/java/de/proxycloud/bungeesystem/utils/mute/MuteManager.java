package de.proxycloud.bungeesystem.utils.mute;

import de.proxycloud.bungeesystem.BungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class MuteManager
{

    public boolean isMuted(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM mute WHERE uuid='" + uuid + "'");
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

    public void mute(String uuid, String name, String grund, long seconds, String team)
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
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO mute VALUES('" + uuid + "', '" + name + "', '" + grund + "', '" + end + "', '" + team + "')");
        try
        {
            final String teamUUID = BungeeSystem.getInstance().getUuidFetcher().getUUID(team).toString();
            BungeeSystem.getInstance().getTeamManager().setMute(teamUUID, BungeeSystem.getInstance().getTeamManager().getMute(teamUUID) + 1);
        }
        catch(Exception e)
        {
            System.out.println("UUID was not founded.");
        }
    }

    public void unmute(String uuid)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("DELETE FROM mute WHERE uuid='" + uuid + "'");
    }

    public String getReason(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM mute WHERE uuid='" + uuid + "'");
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
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM mute WHERE uuid='" + uuid + "'");
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
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM mute WHERE uuid='" + uuid + "'");
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

    public List<String> getMutedUUIDs()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM mute");
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
