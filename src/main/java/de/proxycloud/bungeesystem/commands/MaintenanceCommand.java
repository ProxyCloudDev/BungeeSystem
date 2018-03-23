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
public class MaintenanceCommand extends Command
{

    public MaintenanceCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(commandSender instanceof ProxiedPlayer)
        {
            final ProxiedPlayer player = (ProxiedPlayer) commandSender;
            if(!(player.hasPermission("system.maintenance"))) return;
            if(strings.length != 1)
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§amaintenance §8<§aan§8/§aaus§8>"));
                return;
            }
            if(strings[0].equalsIgnoreCase("an"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMaintenanceManager().setMaintenance(true);
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7hast §7die §cMaintenance §a§laktiviert§8."));
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(!(players.hasPermission("system.maintenance.bypass")))
                        {
                            players.disconnect(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cWir befinden uns nun in Wartungsarbeiten§8."));
                        }
                    }
                });
            }
            else if(strings[0].equalsIgnoreCase("aus"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMaintenanceManager().setMaintenance(false);
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7hast §7die §cMaintenance §c§ldeaktiviert§8."));
                });
            }
        }
        else
        {
            if(strings.length != 1)
            {
                commandSender.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§amaintenance §8<§aan§8/§aaus§8>"));
                return;
            }
            if(strings[0].equalsIgnoreCase("an"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMaintenanceManager().setMaintenance(true);
                    commandSender.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7hast §7die §cMaintenance §a§laktiviert§8."));
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(!(players.hasPermission("system.maintenance.bypass")))
                        {
                            players.disconnect(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cWir befinden uns nun in Wartungsarbeiten§8."));
                        }
                    }
                });
            }
            else if(strings[0].equalsIgnoreCase("aus"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMaintenanceManager().setMaintenance(false);
                    commandSender.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Du §7hast §7die §cMaintenance §c§ldeaktiviert§8."));
                });
            }
        }
    }
}
