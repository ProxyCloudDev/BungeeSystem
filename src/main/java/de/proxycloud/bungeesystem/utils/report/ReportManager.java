package de.proxycloud.bungeesystem.utils.report;

import de.proxycloud.bungeesystem.BungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class ReportManager
{

    public boolean isReported(String reportet)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM report WHERE reportet='" + reportet + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("reportet") != null;
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

    public void buildReport(String reportet, String reporter, String grund, String server)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("INSERT INTO report VALUES('" + reportet + "', '" + reporter + "', '" + grund + "', '" + server + "')");
    }

    public void deleteReport(String reportet)
    {
        BungeeSystem.getInstance().getDatabaseManager().update("DELETE FROM report WHERE reportet='" + reportet + "'");
    }

    public String getReporter(String reportet)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM report WHERE reportet='" + reportet + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("reporter");
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

    public String getReason(String reportet)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM report WHERE reportet='" + reportet + "'");
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

    public String getServer(String reportet)
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM report WHERE reportet='" + reportet + "'");
        try
        {
            if(resultSet.next())
            {
                return resultSet.getString("server");
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

    public List<String> getReports()
    {
        final ResultSet resultSet = BungeeSystem.getInstance().getDatabaseManager().query("SELECT * FROM report");
        final List<String> reports = new ArrayList<>();
        try
        {
            while(resultSet.next())
            {
                reports.add(resultSet.getString("reportet"));
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
        return reports;
    }

}
