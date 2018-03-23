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
public class MuteCommand extends Command
{

    public MuteCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.mute"))) return;
        if(strings.length != 2)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§amute §8<§aSpieler§8> §8<§aGrund§8>"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cBeleidigung §8▎ §71 Tag §8» §e1"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cTeam-Beleidigung §8▎ §713 Tage §8» §e2"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cServer-Beleidigung §8▎ §7Permanent §8» §e3"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cSpam §8▎ §72 Stunden §8» §e4"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cProvokation §8▎ §712 Stunden §8» §e5"));
            return;
        }
        try
        {
            final String name = strings[0];
            final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
            if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid)))
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                return;
            }
            if(BungeeSystem.getInstance().getMuteManager().isMuted(uuid))
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7ist §7bereits §c§lgemutet"));
                return;
            }
            if(strings[1].equalsIgnoreCase("1"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMuteManager().mute(uuid, name, "Beleidigung", 86499, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.mute"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgemutet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cBeleidigung §8▎ §7Dauer §8» §c1 Tag"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("2"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMuteManager().mute(uuid, name, "Team-Beleidigung", 259200, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.mute"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgemutet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cTeam§c-§cBeleidigung §8▎ §7Dauer §8» §c3 Tage"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("3"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMuteManager().mute(uuid, name, "Server-Beleidigung", -1, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.mute"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgemutet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cServer§c-§cBeleidigung §8▎ §7Dauer §8» §cPermanent"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("4"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMuteManager().mute(uuid, name, "Spam", 7200, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.mute"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgemutet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cSpam §8▎ §7Dauer §8» §c2 Stunden"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("5"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getMuteManager().mute(uuid, name, "Provokation", 43200, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.mute"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgemutet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cProvokation §8▎ §7Dauer §8» §c12 §cStunden"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§8/§amute §8<§aSpieler§8> §8<§aGrund§8>"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cBeleidigung §8▎ §71 Tag §8» §e1"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cTeam-Beleidigung §8▎ §713 Tage §8» §e2"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cServer-Beleidigung §8▎ §7Permanent §8» §e3"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cSpam §8▎ §72 Stunden §8» §e4"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cProvokation §8▎ §712 Stunden §8» §e5"));
                return;
            }
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
        }
    }

}
