package de.proxycloud.bungeesystem.commands;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class NotifyCommand extends Command
{

    public NotifyCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.notify"))) return;
        if(strings.length != 0)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§anotify"));
            return;
        }
        if(BungeeSystem.getInstance().getNotifyCache().get(player.getUniqueId().toString()) == 0)
        {
            BungeeSystem.getInstance().getNotifyCache().remove(player.getUniqueId().toString());
            BungeeSystem.getInstance().getNotifyCache().put(player.getUniqueId().toString(), 1);
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7hast §7die §9Teamnachrichten §c§ldeaktiviert§8."));
        }
        else if(BungeeSystem.getInstance().getNotifyCache().get(player.getUniqueId().toString()) == 1)
        {
            BungeeSystem.getInstance().getNotifyCache().remove(player.getUniqueId().toString());
            BungeeSystem.getInstance().getNotifyCache().put(player.getUniqueId().toString(), 0);
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7hast §7die §9Teamnachrichten §a§laktiviert§8."));
        }
    }
}
