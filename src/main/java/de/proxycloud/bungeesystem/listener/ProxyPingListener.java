package de.proxycloud.bungeesystem.listener;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class ProxyPingListener implements Listener
{

    @EventHandler
    public void on(final ProxyPingEvent event)
    {
        final ServerPing serverPing = event.getResponse();

        if(BungeeSystem.getInstance().getMaintenanceManager().getMaintenance() == 0)
        {
            serverPing.setDescription(BungeeSystem.getInstance().getMotdManager().getDefaultMOTD1().replace("&", "§") + "\n" +
                    BungeeSystem.getInstance().getMotdManager().getDefaultMOTD2().replace("&", "§"));
        }
        else
        {
            serverPing.setDescription(BungeeSystem.getInstance().getMotdManager().getMaintenanceMOTD1().replace("&", "§") + "\n" +
                    BungeeSystem.getInstance().getMotdManager().getMaintenanceMOTD2().replace("&", "§"));
            serverPing.setVersion(new ServerPing.Protocol("§4•§c● Maintenance", 2));
        }
        event.setResponse(serverPing);
    }

}
