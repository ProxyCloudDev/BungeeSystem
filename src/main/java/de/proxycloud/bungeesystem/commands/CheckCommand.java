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
public class CheckCommand extends Command
{

    public CheckCommand(String name)
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
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§acheck §8<§aSpieler§8>"));
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
                String group;
                if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Administrator"))
                {
                    group = "§4Administrator";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Developer"))
                {
                    group = "§bDeveloper";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("SrModerator"))
                {
                    group = "§cSrModerator";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Moderator"))
                {
                    group = "§cModerator";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Supporter"))
                {
                    group = "§9Supporter";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Builder"))
                {
                    group = "§aBuilder";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("YouTuber"))
                {
                    group = "§5YouTuber";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("PremiumPlus"))
                {
                    group = "§ePremium+";
                }
                else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Premium"))
                {
                    group = "§6Premium";
                }
                else
                {
                    group = "§7Spieler";
                }
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Name §8» " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7UUID §8» §e" + uuid));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Rang §8» §7" + group));
                if(BungeeSystem.getInstance().getBanManager().isBanned(uuid))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Ban §8» §a✔"));
                }
                else
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Ban §8» §c✘"));
                }
                if(BungeeSystem.getInstance().getMuteManager().isMuted(uuid))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Mute §8» §a✔"));
                }
                else
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Mute §8» §c✘"));
                }
            });
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
        }
    }
}
