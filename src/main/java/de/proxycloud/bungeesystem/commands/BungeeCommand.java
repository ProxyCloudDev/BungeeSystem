package de.proxycloud.bungeesystem.commands;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class BungeeCommand extends Command
{

    public BungeeCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7The §7Plugin §7is §7running §7with §7BungeeCord§8.§7jar §e1.8"));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Developer §8» §bProxyCloud"));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Version §8» §a" + BungeeSystem.getInstance().getDescription().getVersion()));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
        return;
    }
}
