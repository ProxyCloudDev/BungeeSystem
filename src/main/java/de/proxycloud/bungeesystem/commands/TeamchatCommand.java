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
public class TeamchatCommand extends Command
{

    public TeamchatCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.tc"))) return;
        if(strings.length >= 1)
        {
            if(BungeeSystem.getInstance().getNotifyCache().get(player.getUniqueId().toString()) == 1) return;
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < strings.length; i++) stringBuilder.append(strings[i]).append(" §7");
            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
            {
                if(players.hasPermission("system.tc"))
                {
                    if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                    {
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getTeam() + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §8» §7" + stringBuilder.toString()));
                    }
                }
            }
        }
    }
}
