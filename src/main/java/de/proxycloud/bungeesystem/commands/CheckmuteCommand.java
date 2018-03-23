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
public class CheckmuteCommand extends Command
{

    public CheckmuteCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.check"))) return;
        if(strings.length != 1)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§acheckmute §8<§aSpieler§8>"));
            return;
        }
        try
        {
            final String name = strings[0];
            final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
            BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
            {
                if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid)))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                    return;
                }
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Muteinformation §7über §8» " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name));
                if(BungeeSystem.getInstance().getMuteManager().isMuted(uuid))
                {
                    final String team = BungeeSystem.getInstance().getMuteManager().getTeam(uuid);
                    final String teamUUID = BungeeSystem.getInstance().getUuidFetcher().getUUID(team).toString();
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Dieser §7Spieler §7ist §c§lgemutet§8."));
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §c" + BungeeSystem.getInstance().getMuteManager().getReason(uuid)));
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Verbleibende §7Zeit §8» " + BungeeSystem.getInstance().getMuteManager().getRemainingTime(uuid)));
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Gemutet §7von §8» " + BungeeSystem.getInstance().getPrefixCache().get(teamUUID) + team));
                }
                else
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Dieser §7Spieler §7ist §7nicht §a§lgemutet§8."));
                }
            });
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
        }
    }
}
