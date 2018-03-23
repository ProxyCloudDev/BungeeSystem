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
public class MaintenanceMOTDCommand extends Command
{

    public MaintenanceMOTDCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(commandSender instanceof ProxiedPlayer)
        {
            final ProxiedPlayer player = (ProxiedPlayer) commandSender;
            if(!(player.hasPermission("system.motd"))) return;
            if(strings.length >= 2)
            {
                if(strings[0].equalsIgnoreCase("1"))
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 1; i <= strings.length - 1; i++)
                    {
                        stringBuilder.append(strings[i]).append(" ");
                    }
                    BungeeSystem.getInstance().getMotdManager().setMaintenanceMOTD(1, stringBuilder.toString());
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Die §cMaintenance§8-§cMOTD§8-§c1 §7wurde §aerfolgreich §7gesetzt§8."));
                    return;
                }
                if(strings[0].equalsIgnoreCase("2"))
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 1; i <= strings.length - 1; i++)
                    {
                        stringBuilder.append(strings[i]).append(" ");
                    }
                    BungeeSystem.getInstance().getMotdManager().setMaintenanceMOTD(2, stringBuilder.toString());
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Die §cMaintenance§8-§cMOTD§8-§c2 §7wurde §aerfolgreich §7gesetzt§8."));
                    return;
                }
            }
            else
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§amaintenancemotd §8<§a1§8/§a2§8> §8<§aMOTD§8>"));
                return;
            }
        }
        else
        {
            if(strings.length >= 2)
            {
                if(strings[0].equalsIgnoreCase("1"))
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 1; i <= strings.length - 1; i++)
                    {
                        stringBuilder.append(strings[i]).append(" ");
                    }
                    BungeeSystem.getInstance().getMotdManager().setMaintenanceMOTD(1, stringBuilder.toString());
                    commandSender.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Die §cMaintenance§8-§cMOTD§8-§c1 §7wurde §aerfolgreich §7gesetzt§8."));
                    return;
                }
                if(strings[0].equalsIgnoreCase("2"))
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 1; i <= strings.length - 1; i++)
                    {
                        stringBuilder.append(strings[i]).append(" ");
                    }
                    BungeeSystem.getInstance().getMotdManager().setMaintenanceMOTD(2, stringBuilder.toString());
                    commandSender.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Die §cMaintenance§8-§cMOTD§8-§c2 §7wurde §aerfolgreich §7gesetzt§8."));
                    return;
                }
            }
            else
            {
                commandSender.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§amaintenancemotd §8<§a1§8/§a2§8> §8<§aMOTD§8>"));
                return;
            }
        }
    }
}
