package de.proxycloud.bungeesystem.utils.motd;

import de.proxycloud.bungeesystem.BungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class MOTDManager
{

    private String motd;

    public boolean isMOTD()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM motd");
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

    public void buildMOTD(String default1, String default2, String maintenance1, String maintenance2)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO motd VALUES('" + default1 + "', '" + default2 + "', '" + maintenance1 + "', '" + maintenance2 +"')");
    }

    public void setDefaultMOTD(int line, String motd)
    {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
        {
            if(line == 1)
            {
                BungeeSystem.getInstance().getDatabaseManager().update("UPDATE motd SET default1='" + motd + "'");
            }
            else if(line == 2)
            {
                BungeeSystem.getInstance().getDatabaseManager().update("UPDATE motd SET default2='" + motd + "'");
            }
        });
    }

    public void setMaintenanceMOTD(int line, String motd)
    {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
        {
            if(line == 1)
            {
                BungeeSystem.getInstance().getDatabaseManager().update("UPDATE motd SET maintenance1='" + motd + "'");
            }
            else if(line == 2)
            {
                BungeeSystem.getInstance().getDatabaseManager().update("UPDATE motd SET maintenance2='" + motd + "'");
            }
        });
    }

    public String getDefaultMOTD(int line)
    {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
        {
            if(line == 1)
            {
                this.motd = this.getDefaultMOTD1().replace("&", "§");
            }
            else if(line == 2)
            {
                this.motd = this.getDefaultMOTD2().replace("&", "§");
            }
        });
        return this.motd;
    }

    public String getMaintenaceMOTD(int line)
    {
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
        {
            if(line == 1)
            {
                this.motd = this.getMaintenanceMOTD1().replace("&", "§");
            }
            else if(line == 2)
            {
                this.motd = this.getMaintenanceMOTD2().replace("&", "§");
            }
        });
        return this.motd;
    }

    public String getDefaultMOTD1()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM motd");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("default1");
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

    public String getDefaultMOTD2()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM motd");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("default2");
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

    public String getMaintenanceMOTD1()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM motd");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("maintenance1");
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

    public String getMaintenanceMOTD2()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM motd");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("maintenance2");
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

}
