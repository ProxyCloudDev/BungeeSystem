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
public class BanCommand extends Command
{

    public BanCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.ban"))) return;
        if(strings.length != 2)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§aban §8<§aSpieler§8> §8<§aGrund§8>"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cClientmods §8▎ §7Permanent §8» §e1"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cSkin §8▎ §715 Tage §8» §e2"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cWerbung §8▎ §7Permanent §8» §e3"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cUsername §8▎ §730 Tage §8» §e4"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cBugusing §8▎ §73 Tage §8» §e5"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cTeaming §8▎ §77 Tage §8» §e6"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cRandom killing §8▎ §712 Stunden §8» §e7"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cComabt Logging §8▎ §73 Tage §8» §e8"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cBanumgehung §8▎ §7Permanent §8» §e9"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cRadikalismus §8▎ §7Permanent §8» §e10"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cHausverbot §8▎ §7Permanent §8» §e11"));
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
            if(BungeeSystem.getInstance().getBanManager().isBanned(uuid))
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7ist §7bereits §c§lgebannt"));
                return;
            }
            if(strings[1].equalsIgnoreCase("1"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Clientmods", -1, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cClientmods §8▎ §7Dauer §8» §cPermanent"));
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
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Skin", 1296000, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cSkin §8▎ §7Dauer §8» §c15 Tage"));
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
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Werbung", -1, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cWerbung §8▎ §7Dauer §8» §cPermanent"));
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
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Username", 2592000, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cUsername §8▎ §7Dauer §8» §c30 Tage"));
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
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Bugusing", 259200, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cBugusing §8▎ §7Dauer §8» §c3 Tage"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("6"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Teaming", 604800, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cTeaming §8▎ §7Dauer §8» §c7 Tage"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("7"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Random killing", 43200, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cRandom §ckilling §8▎ §7Dauer §8» §c12 Stunden"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("8"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Combat Logging", 259200, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cCombat §cLogging §8▎ §7Dauer §8» §c3 §cTage"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("9"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Banumgehung", -1, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cBanumgehung §8▎ §7Dauer §8» §cPermanent"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("10"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Radikalismus", -1, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cRadikalismus §8▎ §7Dauer §8» §cPermanent"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else if(strings[1].equalsIgnoreCase("11"))
            {
                BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                {
                    BungeeSystem.getInstance().getBanManager().ban(uuid, name, "Hausverbot", -1, player.getName());
                    for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                    {
                        if(players.hasPermission("system.ban"))
                        {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                            {
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgebannt§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Grund §8» §cHausverbot §8▎ §7Dauer §8» §cPermanent"));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                            }
                        }
                    }
                });
            }
            else
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§aban §8<§aSpieler§8> §8<§8aGrund§8>"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cClientmods §8▎ §7Permanent §8» §e1"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cSkin §8▎ §715 Tage §8» §e2"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cWerbung §8▎ §7Permanent §8» §e3"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cUsername §8▎ §730 Tage §8» §e4"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cBugusing §8▎ §73 Tage §8» §e5"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cTeaming §8▎ §77 Tage §8» §e6"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cRandom killing §8▎ §712 Stunden §8» §e7"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cComabt Logging §8▎ §73 Tage §8» §e8"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cBanumgehung §8▎ §7Permanent §8» §e9"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cRadikalismus §8▎ §7Permanent §8» §e10"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§cHausverbot §8▎ §7Permanent §8» §e11"));
                return;
            }
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
        }
    }
}
