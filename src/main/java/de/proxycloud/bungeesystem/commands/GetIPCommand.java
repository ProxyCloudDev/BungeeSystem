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
public class GetIPCommand extends Command
{

    public GetIPCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.getip"))) return;
        if(strings.length != 1)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§agetip §8<§aSpieler§8>"));
            return;
        }
        String name = strings[0];
        if(BungeeSystem.getInstance().getProxy().getPlayer(name) == null) return;
        String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
        ProxiedPlayer playerTarget = BungeeSystem.getInstance().getProxy().getPlayer(name);
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Die §7IP §7von " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7lautet §e" + playerTarget.getAddress().toString()));
    }
}
