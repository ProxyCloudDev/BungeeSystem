package de.proxycloud.bungeesystem.utils.maintenance;

import de.proxycloud.bungeesystem.BungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class MaintenanceManager
{

    private int i;

    public boolean isMaintenance()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM maintenance");
        try
        {
            final boolean bool = resultSet.next();
            return bool;
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

    public void buildMaintenance(int mode)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO maintenance VALUES('" + mode + "')");
    }

    public void setMaintenance(boolean mode)
    {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
        {
            if(mode)
            {
                BungeeSystem.getInstance().getDatabaseManager().update("UPDATE maintenance SET mode='1'");
            }
            else
            {
                BungeeSystem.getInstance().getDatabaseManager().update("UPDATE maintenance SET mode='0'");
            }
        });
    }

    public int getMaintenance()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM maintenance");
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
