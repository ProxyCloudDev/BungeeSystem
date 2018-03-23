package de.proxycloud.bungeesystem.commands;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class KickCommand extends Command
{

    private Configuration configuration;

    public KickCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.kick"))) return;
        if(strings.length != 2)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§akick §8<§aSpieler§8> §8<§aGrund§8>"));
            return;
        }
        try
        {
            final String name = strings[0];
            final String grund = strings[1];
            if(BungeeSystem.getInstance().getProxy().getPlayer(name) == null) return;
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
            player.disconnect(new TextComponent("§7Du wurdest vom §e" + this.configuration.getString("server.name") + " §7Netzwerk §c§lgekickt§8. \n\n §7Grund §8» §c" + grund));
            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
            {
                if(players.hasPermission("system.kick"))
                {
                    if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                    {
                        final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgekickt§8."));
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §c" + grund));
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
