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
public class UnmuteCommand extends Command
{

    public UnmuteCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.unmute"))) return;
        if(strings.length != 1)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§aunmute §8<§aSpieler§8>"));
            return;
        }
        try
        {
            String name = strings[0];
            String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
            if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid)))
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                return;
            }
            if(!(BungeeSystem.getInstance().getMuteManager().isMuted(uuid)))
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7ist §7nicht §c§lgemutet§8."));
                return;
            }
            BungeeSystem.getInstance().getMuteManager().unmute(uuid);
            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
            {
                if(player.hasPermission("system.mute"))
                {
                    if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                    {
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7wurde von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §a§lentmutet§8."));
                        players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                    }
                }
            }
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
        }
    }
}
