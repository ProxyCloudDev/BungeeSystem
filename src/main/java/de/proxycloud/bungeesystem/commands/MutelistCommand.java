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
public class MutelistCommand extends Command
{

    public MutelistCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.mute"))) return;
        if(strings.length != 0)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§amutelist"));
            return;
        }
        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
        {
            if(BungeeSystem.getInstance().getMuteManager().getMutedUUIDs().isEmpty())
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Es §7sind §7zurzeit §ckeine §7Spieler §7gemuted§8."));
                return;
            }
            if(BungeeSystem.getInstance().getMuteManager().getMutedUUIDs().size() == 1)
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Es §7ist §7zurzeit §c1 §7Spieler §7gemuted§8."));
                for(String banned : BungeeSystem.getInstance().getMuteManager().getMutedUUIDs())
                {
                    try
                    {
                        String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(banned).toString();
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + banned + " §8» §c" + BungeeSystem.getInstance().getMuteManager().getReason(uuid)));
                    }
                    catch(Exception e)
                    {
                        return;
                    }
                }
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                return;
            }
            if(BungeeSystem.getInstance().getMuteManager().getMutedUUIDs().size() >= 1)
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Es §7sind §7zurzeit §c" + BungeeSystem.getInstance().getMuteManager().getMutedUUIDs().size() + " §7Spieler §7gemuted§8."));
                for(String banned : BungeeSystem.getInstance().getMuteManager().getMutedUUIDs())
                {
                    try
                    {
                        String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(banned).toString();
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + banned + " §8» §c" + BungeeSystem.getInstance().getMuteManager().getReason(uuid)));
                    }
                    catch(Exception e)
                    {
                        return;
                    }
                }
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                return;
            }
        });
    }
}
