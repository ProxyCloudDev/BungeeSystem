package de.proxycloud.bungeesystem.listener;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class ChatListener implements Listener
{

    @EventHandler
    public void on(final ChatEvent event)
    {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if(BungeeSystem.getInstance().getMuteManager().isMuted(player.getUniqueId().toString()))
        {
            long current = System.currentTimeMillis();
            long end = BungeeSystem.getInstance().getMuteManager().getEnd(player.getUniqueId().toString());
            if(((current < end ? 1 : 0) | (end == -1L ? 1 : 0)) != 0)
            {
                if(event.getMessage().startsWith("/")) return;
                event.setCancelled(true);
                player.sendMessage(new TextComponent(" "));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7wurdest §7aus §7dem §7Chat §c§lausgeschlossen§8."));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §c" + BungeeSystem.getInstance().getMuteManager().getReason(player.getUniqueId().toString())));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Verbleibende §7Zeit §8» §e" + BungeeSystem.getInstance().getMuteManager().getRemainingTime(player.getUniqueId().toString())));
                player.sendMessage(new TextComponent(" "));
            }
            else
            {
                BungeeSystem.getInstance().getMuteManager().unmute(player.getUniqueId().toString());
                for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                {
                    if(player.hasPermission("system.mute"))
                    {
                        if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                        {
                            players.sendMessage(new TextComponent(" "));
                            players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7wurde vom §eSystem §a§lentmutet§8."));
                            players.sendMessage(new TextComponent(" "));
                        }
                    }
                }
            }
        }
    }

}
