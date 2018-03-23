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
public class BroadcastCommand extends Command
{

    public BroadcastCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.bc"))) return;
        if(strings.length >= 1)
        {
            String msg = "";
            for(int i = 0; i <= strings.length - 1; i++)
            {
                msg = msg + strings[i] + " ";
            }
            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
            {
                players.sendMessage(BungeeSystem.getInstance().getPrefix() + "§a§l" + msg.replace("&", "§"));
            }
        }
        else
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§abc §8<§aNachricht§8>"));
        }
    }
}
