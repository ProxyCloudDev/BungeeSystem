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
public class OnlineCommand extends Command
{

    public OnlineCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        int i = BungeeSystem.getInstance().getProxy().getPlayers().size();
        if(i == 1)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Es §7ist §7zurzeit §e1 §7Spieler §a§lonline§8."));
        }
        else
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Es §7sind §7zurzeit §e" + i +" §7Spieler §a§lonline§8."));
        }
    }
}
