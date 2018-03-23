package de.proxycloud.bungeesystem.listener;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
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
public class ServerSwitchListener implements Listener
{

    private Configuration configuration;

    @EventHandler
    public void on(final ServerSwitchEvent event)
    {
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
