package de.proxycloud.bungeesystem.mysql;

import java.sql.*;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class DatabaseManager
{

    private Connection connection;

    private final String hostname;
    private final String database;
    private final String username;
    private final String password;

    private final String url;

    public DatabaseManager(String hostname, String database, String username, String password)
    {
        this.hostname = hostname;
        this.database = database;
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + this.hostname + ":3306/" + this.database;
    }

    public void connect()
    {
        if(!(isConnected()))
        {
            try
            {
                this.connection = DriverManager.getConnection(this.url, this.username, this.password);
                System.out.println("[MySQL] The Connection to MySQL was sucsefulled created.");
            }
            catch(SQLException e)
            {
                System.out.println("[MySQL] Failed to connect to MySQL.");
            }
        }
    }

    public void disconnect()
    {
        if(isConnected())
        {
            try
            {
                this.connection.close();
                this.connection = null;
                System.out.println("[MySQL] The Connection from MySQL is disconnected");
            }
            catch(SQLException e)
            {
                System.out.println("[MySQL] Failed to disconnect from MySQL.");
            }
        }
    }

    public void update(String qry)
    {
        try
        {
            final PreparedStatement preparedStatement = this.connection.prepareStatement(qry);
            preparedStatement.executeUpdate(qry);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet query(String qry)
    {
        try
        {
            final PreparedStatement preparedStatement = this.connection.prepareStatement(qry);
            return preparedStatement.executeQuery(qry);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected()
    {
        return this.connection != null;
    }

}
