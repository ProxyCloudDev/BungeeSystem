package de.proxycloud.bungeesystem.commands;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
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
public class JoinCommand extends Command
{

    public JoinCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.join"))) return;
        if(strings.length != 1)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§ajoin §8<§aServer§8>"));
            return;
        }
        try
        {
            player.connect(BungeeSystem.getInstance().getProxy().getServerInfo(strings[0]));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7bist §7nun §7auf §7Server§8: §e§l" + strings[0]));
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Dieser §eServer §7ist §7nicht §c§lregestriert§8."));
        }
    }
}
