package de.proxycloud.bungeesystem;

import de.proxycloud.bungeesystem.commands.*;
import de.proxycloud.bungeesystem.commands.lobby.HubCommand;
import de.proxycloud.bungeesystem.listener.*;
import de.proxycloud.bungeesystem.mysql.DatabaseManager;
import de.proxycloud.bungeesystem.utils.ban.BanManager;
import de.proxycloud.bungeesystem.utils.groups.GroupManager;
import de.proxycloud.bungeesystem.utils.groups.PrefixManager;
import de.proxycloud.bungeesystem.utils.maintenance.MaintenanceManager;
import de.proxycloud.bungeesystem.utils.motd.MOTDManager;
import de.proxycloud.bungeesystem.utils.mute.MuteManager;
import de.proxycloud.bungeesystem.utils.notify.NotifyManager;
import de.proxycloud.bungeesystem.utils.report.ReportManager;
import de.proxycloud.bungeesystem.utils.team.TeamManager;
import de.proxycloud.bungeesystem.utils.uuid.UUIDFetcher;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
@Getter
public class BungeeSystem extends Plugin
{

    @Getter
    private static BungeeSystem instance;

    private String prefix;
    private String report;
    private String team;
    private String party;
    private String friend;

    private DatabaseManager databaseManager;
    private final MOTDManager motdManager;
    private final MaintenanceManager maintenanceManager;
    private final UUIDFetcher uuidFetcher;
    private final GroupManager groupManager;
    private final PrefixManager prefixManager;
    private final ReportManager reportManager;
    private final NotifyManager notifyManager;
    private final TeamManager teamManager;
    private final BanManager banManager;
    private final MuteManager muteManager;

    private File file;
    private Configuration configuration;
    private final Map<String, String> prefixCache;
    private final Map<String, Integer> notifyCache;
    private final Map<String, List<String>> partyCache;
    private final Map<String, String> party2Cache;
    private final Map<String, List<String>> partyInviteCache;

    public BungeeSystem()
    {
        instance = this;
        this.prefixCache = new HashMap<>();
        this.notifyCache = new HashMap<>();
        this.party2Cache = new HashMap<>();
        this.partyCache = new HashMap<>();
        this.partyInviteCache = new HashMap<>();

        this.motdManager = new MOTDManager();
        this.maintenanceManager = new MaintenanceManager();
        this.uuidFetcher = new UUIDFetcher();
        this.groupManager = new GroupManager();
        this.prefixManager = new PrefixManager();
        this.reportManager = new ReportManager();
        this.notifyManager = new NotifyManager();
        this.teamManager = new TeamManager();
        this.banManager = new BanManager();
        this.muteManager = new MuteManager();
    }

    @Override
    public void onEnable()
    {
        this.getProxy().getScheduler().runAsync(this, () ->
        {
            this.buildConfig();
            this.buildStrings();
            this.initDatabase();
            this.init();

            try
            {
                if(!(this.motdManager.isMOTD()))
                {
                    this.motdManager.buildMOTD("&eProxyCloud &8| &7Dein &aMinecraft &7Netzwerk &8» &e1.8", " &8» &a&lONLINE &8× &7BungeeSystem &aUpdate &8» &7alles &a&lbesser", "&eProxyCloud &8| &7Dein &aMinecraft &7Netzwerk &8» &e1.8", " &8» &7Wir §7befinden &7uns &7in &c&lWartungsarbeiten");
                }
                if(!(this.maintenanceManager.isMaintenance()))
                {
                    this.maintenanceManager.buildMaintenance(0);
                }
                List<String> uuid = this.groupManager.getAllUUIDs();
                for(String string : uuid)
                {
                    this.prefixCache.put(string, this.prefixManager.getPrefix(string));
                    System.out.println("[UUID] " + string + " | [GROUP] " + this.groupManager.getGroup(string));
                }
                for(ProxiedPlayer players : this.getProxy().getPlayers())
                {
                    if(players.hasPermission("system.notify"))
                    {
                        if(this.notifyCache.containsKey(players.getUniqueId().toString()))
                        {
                            this.notifyCache.remove(players.getUniqueId().toString());
                            return;
                        }
                        this.notifyCache.put(players.getUniqueId().toString(), this.notifyManager.getNotify(players.getUniqueId().toString()));
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println(" ");
                System.out.println("[MySQL] Bitte überprüfe deine MySQL Daten!");
                System.out.println(" ");
            }
        });
        System.out.println(" ");
        System.out.println("______                             _____           _                 ");
        System.out.println("| ___ \\                           /  ___|         | |                ");
        System.out.println("| |_/ /_   _ _ __   __ _  ___  ___\\ `--. _   _ ___| |_ ___ _ __ ___  ");
        System.out.println("| ___ \\ | | | '_ \\ / _` |/ _ \\/ _ \\`--. \\ | | / __| __/ _ \\ '_ ` _ \\ ");
        System.out.println("| |_/ / |_| | | | | (_| |  __/  __/\\__/ / |_| \\__ \\ ||  __/ | | | | |");
        System.out.println("\\____/ \\__,_|_| |_|\\__, |\\___|\\___\\____/ \\__, |___/\\__\\___|_| |_| |_|");
        System.out.println("                    __/ |                 __/ |                      ");
        System.out.println("                   |___/                 |___/                       ");
        System.out.println(" ");
        System.out.println("Coded by ProxyCloud | Discord: ProxyCloud#5588");
        System.out.println(" ");
    }

    @Override
    public void onDisable()
    {
        this.databaseManager.disconnect();
        if(!(this.prefixCache.isEmpty()))
        {
            this.prefixCache.clear();
        }
        System.out.println(" ");
        System.out.println("______            ");
        System.out.println("| ___ \\           ");
        System.out.println("| |_/ /_   _  ___ ");
        System.out.println("| ___ \\ | | |/ _ \\");
        System.out.println("| |_/ / |_| |  __/");
        System.out.println("\\____/ \\__, |\\___|");
        System.out.println("        __/ |     ");
        System.out.println("       |___/      ");
        System.out.println(" ");
        System.out.println("See you next Time!");
        System.out.println(" ");
    }

    private void init()
    {
        this.getProxy().getPluginManager().registerListener(this, new ServerConnectListener());
        this.getProxy().getPluginManager().registerListener(this, new ServerSwitchListener());
        this.getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener());
        this.getProxy().getPluginManager().registerListener(this, new ProxyPingListener());
        this.getProxy().getPluginManager().registerListener(this, new ChatListener());

        this.getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand("maintenance"));
        this.getProxy().getPluginManager().registerCommand(this, new DefaultMOTDCommand("defaultmotd"));
        this.getProxy().getPluginManager().registerCommand(this, new MaintenanceMOTDCommand("maintenancemotd"));
        this.getProxy().getPluginManager().registerCommand(this, new NetzwerkstopCommand("netzwerkstop"));
        this.getProxy().getPluginManager().registerCommand(this, new BroadcastCommand("bc"));
        this.getProxy().getPluginManager().registerCommand(this, new BungeeCommand("bungee"));
        this.getProxy().getPluginManager().registerCommand(this, new BungeeCommand("bungeecord"));
        this.getProxy().getPluginManager().registerCommand(this, new BungeeCommand("proxy"));
        this.getProxy().getPluginManager().registerCommand(this, new BungeeCommand("proxyserver"));
        this.getProxy().getPluginManager().registerCommand(this, new PingCommand("ping"));
        this.getProxy().getPluginManager().registerCommand(this, new TeamchatCommand("tc"));
        this.getProxy().getPluginManager().registerCommand(this, new TeamchatCommand("teamchat"));
        this.getProxy().getPluginManager().registerCommand(this, new NotifyCommand("notify"));
        this.getProxy().getPluginManager().registerCommand(this, new GetIPCommand("getip"));
        this.getProxy().getPluginManager().registerCommand(this, new ReportCommand("report"));
        this.getProxy().getPluginManager().registerCommand(this, new StatusCommand("status"));
        this.getProxy().getPluginManager().registerCommand(this, new BanCommand("ban"));
        this.getProxy().getPluginManager().registerCommand(this, new UnbanCommand("unban"));
        this.getProxy().getPluginManager().registerCommand(this, new MuteCommand("mute"));
        this.getProxy().getPluginManager().registerCommand(this, new UnmuteCommand("unmute"));
        this.getProxy().getPluginManager().registerCommand(this, new MutelistCommand("mutelist"));
        this.getProxy().getPluginManager().registerCommand(this, new BanlistCommand("banlist"));
        this.getProxy().getPluginManager().registerCommand(this, new WartungCommand("wartung"));
        this.getProxy().getPluginManager().registerCommand(this, new KickCommand("kick"));
        this.getProxy().getPluginManager().registerCommand(this, new HubCommand("hub"));
        this.getProxy().getPluginManager().registerCommand(this, new HubCommand("lobby"));
        this.getProxy().getPluginManager().registerCommand(this, new HubCommand("l"));
        this.getProxy().getPluginManager().registerCommand(this, new HubCommand("leave"));
        this.getProxy().getPluginManager().registerCommand(this, new WhereAmICommand("whereami"));
        this.getProxy().getPluginManager().registerCommand(this, new OnlineCommand("online"));
        this.getProxy().getPluginManager().registerCommand(this, new HelpCommand("help"));
        this.getProxy().getPluginManager().registerCommand(this, new CheckCommand("check"));
        this.getProxy().getPluginManager().registerCommand(this, new CheckbanCommand("checkban"));
        this.getProxy().getPluginManager().registerCommand(this, new CheckmuteCommand("checkmute"));
        this.getProxy().getPluginManager().registerCommand(this, new JoinCommand("join"));
        this.getProxy().getPluginManager().registerCommand(this, new SetGroupCommand("setgroup"));
    }

    private void initDatabase()
    {
        try
        {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
            this.databaseManager = new DatabaseManager(this.configuration.getString("mysql.hostname")
                    , this.configuration.getString("mysql.database")
                    , this.configuration.getString("mysql.username")
                    , this.configuration.getString("mysql.password"));
            this.databaseManager.connect();
            if(this.databaseManager.isConnected())
            {
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS motd(default1 VARCHAR(100), default2 VARCHAR(100), maintenance1 VARCHAR(100), maintenance2 VARCHAR(100))");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS maintenance(mode INT)");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS groups(uuid VARCHAR(64), groupname VARCHAR(100))");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS notify(uuid VARCHAR(64), mode INT)");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS permissions(groupname VARCHAR(100), permission VARCHAR(100))");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS report(reportet VARCHAR(16), reporter VARCHAR(16), reason VARCHAR(32), server VARCHAR(32))");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS ban(uuid VARCHAR(64), name VARCHAR(16), reason VARCHAR(32), ende VARCHAR(100), team VARCHAR(16))");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS mute(uuid VARCHAR(64), name VARCHAR(16), reason VARCHAR(32), ende VARCHAR(100), team VARCHAR(16))");
                this.databaseManager.update("CREATE TABLE IF NOT EXISTS team(uuid VARCHAR(64), ban INT, mute INT, report INT)");
            }
        }
        catch(IOException e)
        {
            System.out.println(" ");
            System.out.println("[MySQL] Bitte überprüfe deine MySQL Daten!");
            System.out.println(" ");
        }
    }

    private void buildConfig()
    {
        if(!(this.getDataFolder().exists()))
        {
            this.getDataFolder().mkdirs();
        }
        this.file = new File("plugins/BungeeSystem", "config.yml");
        if(!(this.file.exists()))
        {
            try
            {
                this.file.createNewFile();
                this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
                this.configuration.set("mysql.hostname", "localhost");
                this.configuration.set("mysql.database", "netzwerk");
                this.configuration.set("mysql.username", "root");
                this.configuration.set("mysql.password", "");

                this.configuration.set("prefix", "&2•&a● Bungee &8▎ ");
                this.configuration.set("report", "&4•&c● Report &8▎ ");
                this.configuration.set("team", "&6•&e● TEAM &8▎ ");
                this.configuration.set("party", "&d•&5● Party &8▎ ");
                this.configuration.set("friend", "&9•&3● Freunde &8▎ ");

                this.configuration.set("server.name", "DeinServerDE");
                this.configuration.set("server.teamspeak", "ts.DeinServer.de");
                this.configuration.set("server.twitter", "@DeinServerDE");
                this.configuration.set("server.shop", "shop.DeinServer.de");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void buildStrings()
    {
        try
        {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.getFile());
            this.prefix = this.configuration.getString("prefix").replace("&", "§");
            this.report = this.configuration.getString("report").replace("&", "§");
            this.team = this.configuration.getString("team").replace("&", "§");
            this.party = this.configuration.getString("party").replace("&", "§");
            this.friend = this.configuration.getString("friend").replace("&", "§");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
