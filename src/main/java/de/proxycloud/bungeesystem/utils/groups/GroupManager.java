package de.proxycloud.bungeesystem.utils.groups;

import de.proxycloud.bungeesystem.BungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class GroupManager
{

    public boolean isRegistered(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM groups WHERE uuid='" + uuid + "'");
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

    public void registerUUID(String uuid, String group)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO groups VALUES('" + uuid + "', '" + group + "')");
    }

    public void setGroup(String uuid, String group)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("UPDATE groups SET groupname='" + group + "' WHERE uuid='" + uuid + "'");
    }

    public String getGroup(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM groups WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("groupname");
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

    public List<String> getAllUUIDs()
    {
        final List<String> uuid = new ArrayList<>();
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM groups");
        try
        {
            while(resultSet.next())
            {
                uuid.add(resultSet.getString("uuid"));
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
        return uuid;
    }

}
