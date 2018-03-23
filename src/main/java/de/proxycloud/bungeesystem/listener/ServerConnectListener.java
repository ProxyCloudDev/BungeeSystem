package de.proxycloud.bungeesystem.listener;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class ServerConnectListener implements Listener
{

    private Configuration configuration;

    @EventHandler
    public void on(final ServerConnectEvent event)
    {
        final ProxiedPlayer player = event.getPlayer();

        if(BungeeSystem.getInstance().getBanManager().isBanned(player.getUniqueId().toString()))
        {
            long current = System.currentTimeMillis();
            long end = BungeeSystem.getInstance().getBanManager().getEnd(player.getUniqueId().toString());
            if(((current < end ? 1 : 0) | (end == -1L ? 1 : 0)) != 0)
            {
                try
                {
                    this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                    event.setCancelled(true);
                    player.disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name") + " §7Netzwerk §c§lgebannt§8. \n\n §7Grund §8» §c"
                            + BungeeSystem.getInstance().getBanManager().getReason(player.getUniqueId().toString()) + " \n §7Verbleibende Zeit §8» §e"
                            + BungeeSystem.getInstance().getBanManager().getRemainingTime(player.getUniqueId().toString())));
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
                BungeeSystem.getInstance().getBanManager().unban(player.getUniqueId().toString());
                for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                {
                    if(player.hasPermission("system.ban"))
                    {
                        if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                        {
                            players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7wurde vom §eSystem §a§lentbannt§8."));
                            players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                        }
                    }
                }
            }
        }

        if(BungeeSystem.getInstance().getMaintenanceManager().getMaintenance() == 1)
        {
            if(!(player.hasPermission("system.maintenance.bypass")))
            {
                event.setCancelled(true);
                player.disconnect(new TextComponent(BungeeSystem.getInstance().getPrefix() + " §cWir befinden uns zurzeit in §c§lWartungsarbeiten§8."));
            }
        }

        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
        {
            if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(player.getUniqueId().toString())))
            {
                if(player.hasPermission("system.administrator"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "Administrator");
                }
                else if(player.hasPermission("system.developer"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "Developer");
                }
                else if(player.hasPermission("system.srmoderator"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "SrModerator");
                }
                else if(player.hasPermission("system.moderator"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "Moderator");
                }
                else if(player.hasPermission("system.supporter"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "Supporter");
                }
                else if(player.hasPermission("system.builder"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "Builder");
                }
                else if(player.hasPermission("system.youtuber"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "YouTuber");
                }
                else if(player.hasPermission("system.premiumplus"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "PremiumPlus");
                }
                else if(player.hasPermission("system.premium"))
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "Premium");
                }
                else
                {
                    BungeeSystem.getInstance().getGroupManager().registerUUID(player.getUniqueId().toString(), "Spieler");
                }
                BungeeSystem.getInstance().getPrefixCache().put(player.getUniqueId().toString(), BungeeSystem.getInstance().getPrefixManager().getPrefix(player.getUniqueId().toString()));
                return;
            }
            BungeeSystem.getInstance().getPrefixCache().put(player.getUniqueId().toString(), BungeeSystem.getInstance().getPrefixManager().getPrefix(player.getUniqueId().toString()));
            if(player.hasPermission("system.notify"))
            {
                if(!(BungeeSystem.getInstance().getNotifyManager().isSaved(player.getUniqueId().toString())))
                {
                    BungeeSystem.getInstance().getNotifyManager().build(player.getUniqueId().toString());
                    BungeeSystem.getInstance().getNotifyCache().put(player.getUniqueId().toString(), BungeeSystem.getInstance().getNotifyManager().getNotify(player.getUniqueId().toString()));
                    return;
                }
                BungeeSystem.getInstance().getNotifyCache().put(player.getUniqueId().toString(), BungeeSystem.getInstance().getNotifyManager().getNotify(player.getUniqueId().toString()));
            }
            if(player.hasPermission("system.team"))
            {
                if(!(BungeeSystem.getInstance().getTeamManager().isSaved(player.getUniqueId().toString())))
                {
                    BungeeSystem.getInstance().getTeamManager().build(player.getUniqueId().toString());
                }
            }
        });

        BungeeSystem.getInstance().getProxy().getScheduler().schedule(BungeeSystem.getInstance(), () ->
        {
            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
            {
                try
                {
                    this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                    BaseComponent[] headerComponent = TextComponent.fromLegacyText("\n  §e" + this.configuration.getString("server.name") +" §8┃ §7Dein §aMinecraft §7Netzwerk §8» §e1.8 \n " +
                            "§7Es sind gerade §8» §e" + BungeeSystem.getInstance().getProxy().getPlayers().size() + "§8/§e" + BungeeSystem.getInstance().getProxy().getConfig().getPlayerLimit()
                            + " \n §7Aktueller Server §8» §e" + players.getServer().getInfo().getName() + "  \n");
                    BaseComponent[] footerComponent = TextComponent.fromLegacyText("\n  §7Teamspeak³ §8» §e" + this.configuration.getString("server.teamspeak") + " \n §7Twitter §8» §e" + this.configuration.getString("server.twitter") + "\n §7Shop §8» §e" + this.configuration.getString("server.shop") + "  \n");
                    players.setTabHeader(headerComponent, footerComponent);
                }
                catch(NullPointerException e)
                {
                    System.out.println("No server founded");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }, 1, TimeUnit.SECONDS);
    }

}
