package de.proxycloud.bungeesystem.commands;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class NetzwerkstopCommand extends Command
{

    public NetzwerkstopCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(commandSender instanceof ProxiedPlayer)
        {
            final ProxiedPlayer player = (ProxiedPlayer) commandSender;
            if(!(player.hasPermission("system.stop"))) return;
            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
            {
                players.disconnect(new TextComponent(BungeeSystem.getInstance().getPrefix() + "\n\n§cDas Netzwerk startet neu§8.\n\n§aWir sind in maximal 60 Sekunden wieder §a§lonline§8."));
            }
            BungeeSystem.getInstance().getProxy().getScheduler().schedule(BungeeSystem.getInstance(), () ->
                    BungeeSystem.getInstance().getProxy().stop(), 2, TimeUnit.SECONDS);
        }
        else
        {
            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
            {
                players.disconnect(new TextComponent(BungeeSystem.getInstance().getPrefix() + "\n\n§cDas Netzwerk startet neu§8.\n\n§aWir sind in maximal 60 Sekunden wieder §a§lonline§8."));
            }
            BungeeSystem.getInstance().getProxy().getScheduler().schedule(BungeeSystem.getInstance(), () ->
                    BungeeSystem.getInstance().getProxy().stop(), 2, TimeUnit.SECONDS);
        }
    }
}
