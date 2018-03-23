package de.proxycloud.bungeesystem.utils.team;

import de.proxycloud.bungeesystem.BungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class TeamManager
{

    public boolean isSaved(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM team WHERE uuid='" + uuid + "'");
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

    public void build(String uuid)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO team VALUES('" + uuid + "', '0', '0', '0')");
    }

    public void setReport(String uuid, int id)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("UPDATE team SET report='" + id + "' WHERE uuid='" + uuid + "'");
    }

    public void setBan(String uuid, int id)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("UPDATE team SET ban='" + id + "' WHERE uuid='" + uuid + "'");
    }

    public void setMute(String uuid, int id)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("UPDATE team SET mute='" + id + "' WHERE uuid='" + uuid + "'");
    }

    public int getReport(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM team WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getInt("report");
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

    public int getBan(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM team WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getInt("ban");
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

    public int getMute(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM team WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getInt("mute");
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

}
