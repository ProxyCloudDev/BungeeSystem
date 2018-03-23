package de.proxycloud.bungeesystem.commands.lobby;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class HubCommand extends Command
{

    public HubCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.getServer().getInfo().getName().equalsIgnoreCase("Lobby")))
        {
            try
            {
                player.connect(BungeeSystem.getInstance().getProxy().getServerInfo("Lobby"));
            }
            catch(Exception e)
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Der §7Lobby §7Server §7muss §a§lLobby §7heißen§8."));
            }
        }
        else
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7bist §7bereits §7auf §7der §e§lLobby§8."));
        }
    }
}
