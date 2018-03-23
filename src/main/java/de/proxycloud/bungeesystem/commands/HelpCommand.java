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
public class HelpCommand extends Command
{

    public HelpCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§alobby §8- §7sendet §7dich §7auf §7die §7Lobby§8."));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§aping §8- §7zeige §7deinen §7Ping §7an§8."));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§aonline §8- §7zeigt §7dir §7die §7online §7Spieleranzahl§8."));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§ayoutuber §8- §7listet §7die §7YouTuber §7Bedingungen §7auf§8."));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§areport §8- §7reporte §7einen Spieler§8."));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§awhereami §8- §7zeigt §7dir §7an §7auf §7welchem §7Server §7du §7bist§8."));
        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
    }
}
