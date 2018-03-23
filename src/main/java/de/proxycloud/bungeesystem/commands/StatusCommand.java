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
public class StatusCommand extends Command
{

    public StatusCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.status"))) return;
        if(strings.length != 1)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§astatus §8<§aTeammitglied§8>"));
            return;
        }
        try
        {
            String name = strings[0];
            String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
            if(!(BungeeSystem.getInstance().getTeamManager().isSaved(uuid)))
            {
                if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid)))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                    return;
                }
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7ist §7kein §c§lTeammitglied§8."));
                return;
            }
            final String prefix = BungeeSystem.getInstance().getPrefixCache().get(uuid);
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Das §7Teammitglied " + prefix + name + " §7hat §e" + BungeeSystem.getInstance().getTeamManager().getBan(uuid) + " §7Bans §a§lgetätigt§8."));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Das §7Teammitglied " + prefix + name + " §7hat §e" + BungeeSystem.getInstance().getTeamManager().getMute(uuid) + " §7Mutes §a§lgetätigt§8."));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Das §7Teammitglied " + prefix + name + " §7hat §e" + BungeeSystem.getInstance().getTeamManager().getReport(uuid) + " §7Reports §a§langenommen§8."));
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
        }
    }
}
