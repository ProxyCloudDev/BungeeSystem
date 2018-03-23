package de.proxycloud.bungeesystem.utils.notify;

import de.proxycloud.bungeesystem.BungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class NotifyManager
{

    public boolean isSaved(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM notify WHERE uuid='" + uuid + "'");
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
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO notify VALUES('" + uuid + "', '0')");
        BungeeSystem.getInstance().getNotifyCache().put(uuid, 0);
    }

    public void setNotify(String uuid, int mode)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("UPDATE notify SET mode='" + mode + "' WHERE uuid='" + uuid +  "'");
        BungeeSystem.getInstance().getNotifyCache().remove(uuid);
        BungeeSystem.getInstance().getNotifyCache().put(uuid, mode);
    }

    public int getNotify(String uuid)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM notify WHERE uuid='" + uuid + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getInt("mode");
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
