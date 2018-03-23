package de.proxycloud.bungeesystem.commands;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import sun.plugin.perf.PluginRollup;

/**
 * Developer ProxyCloud
 * Coded on 20.03.2018
 * Coded with IntelliJ
 */
public class ReportCommand extends Command
{

    public ReportCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(strings.length == 1)
        {
            if((strings[0].equalsIgnoreCase("list")) && (player.hasPermission("system.report")))
            {
                if(BungeeSystem.getInstance().getReportManager().getReports().isEmpty())
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Es §7sind §7zurzeit §ckeine §7Report §7offen§8."));
                    return;
                }
                if(BungeeSystem.getInstance().getReportManager().getReports().size() == 1)
                {
                    BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                    {
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Es §7ist §7zurzeit §c1 §7Report §7offen§8."));
                        for(String report : BungeeSystem.getInstance().getReportManager().getReports())
                        {
                            final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(report).toString();
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + report + " §8» §c" + BungeeSystem.getInstance().getReportManager().getReason(report)));

                            final TextComponent annehmen = new TextComponent("§8» §aAnnehmen §8▎ ");
                            annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
                            annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report accept " + report));

                            final TextComponent ablehnen = new TextComponent("§8» §cAblehnen");
                            ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
                            ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report delete " + report));

                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport()), new TextComponent(annehmen), new TextComponent(ablehnen));
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                            return;
                        }
                    });
                }
                if(BungeeSystem.getInstance().getReportManager().getReports().size() >= 1)
                {
                    BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
                    {
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Es §7sind §7zurzeit §c" + BungeeSystem.getInstance().getReportManager().getReports().size() + " §7Reports §7offen§8."));
                        for(String report : BungeeSystem.getInstance().getReportManager().getReports())
                        {
                            final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(report).toString();
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + report + " §8» §c" + BungeeSystem.getInstance().getReportManager().getReason(report)));

                            final TextComponent annehmen = new TextComponent("§8» §aAnnehmen §8▎ ");
                            annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
                            annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report accept " + report));

                            final TextComponent ablehnen = new TextComponent("§8» §cAblehnen");
                            ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
                            ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report delete " + report));

                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport()), new TextComponent(annehmen), new TextComponent(ablehnen));
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                            return;
                        }
                    });
                }
            }
            else
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Benutze§8: §8/§areport §8<§aSpieler§8> §8<§aGrund§8>"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Gründe§8: §cHacking§8, §cTeaming§8, §cBeleidigung§8, §cWerbung"));
            }
        }
        else if(strings.length == 2)
        {
            if((strings[0].equalsIgnoreCase("accept") && (player.hasPermission("system.report"))))
            {
                try
                {
                    final ProxiedPlayer playerTarget = BungeeSystem.getInstance().getProxy().getPlayer(strings[1]);
                    if(playerTarget == null)
                    {
                        final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(strings[1]).toString();
                        if(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid))
                        {
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Der §7Report " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[1] + " §7ist §7nicht §c§lregestriert§8."));
                            return;
                        }
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7" + strings[1] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                        return;
                    }
                    if(BungeeSystem.getInstance().getReportManager().isReported(strings[1]))
                    {
                        final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(strings[1]).toString();
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Du §7hast §7den §7Report " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[1] + " §7erfolgreich §a§langenommen§8."));
                        if(!(player.getServer().getInfo().getName().equalsIgnoreCase(playerTarget.getServer().getInfo().getName())))
                        {
                            player.connect(playerTarget.getServer().getInfo());
                        }
                        else
                        {
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Du §7bist §cbereits §7auf §7dem §7Server §7von " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[1] + "§8."));
                        }
                        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> {
                            BungeeSystem.getInstance().getReportManager().deleteReport(strings[1]);
                            BungeeSystem.getInstance().getTeamManager().setReport(player.getUniqueId().toString(), BungeeSystem.getInstance().getTeamManager().getReport(player.getUniqueId().toString()) + 1);
                        });
                        return;
                    }
                    final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(strings[1]).toString();
                    if(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid))
                    {
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Der §7Report " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[1] + " §7ist §7nicht §c§lregestriert§8."));
                        return;
                    }
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7" + strings[1] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                }
                catch(Exception e)
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7" + strings[1] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                }
            }
            else if((strings[0].equalsIgnoreCase("delete") && (player.hasPermission("system.report"))))
            {
                try
                {
                    final ProxiedPlayer playerTarget = BungeeSystem.getInstance().getProxy().getPlayer(strings[1]);
                    if(playerTarget == null)
                    {
                        final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(strings[1]).toString();
                        if(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid))
                        {
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Der §7Report " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[1] + " §7ist §7nicht §c§lregestriert§8."));
                            return;
                        }
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7" + strings[1] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                        return;
                    }
                    if(BungeeSystem.getInstance().getReportManager().isReported(strings[1]))
                    {
                        final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(strings[1]).toString();
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Du §7hast §7den §7Report " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[1] + " §7erfolgreich §a§lgelöscht§8."));
                        BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> BungeeSystem.getInstance().getReportManager().deleteReport(strings[1]));
                        return;
                    }
                    final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(strings[1]).toString();
                    if(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid))
                    {
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Der §7Report " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[1] + " §7ist §7nicht §c§lregestriert§8."));
                        return;
                    }
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7" + strings[1] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                    return;
                }
                catch(Exception e)
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7" + strings[1] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                }
            }
            else if(!(strings[0].equalsIgnoreCase("accept") && !(strings[0].equalsIgnoreCase("delete"))))
            {
                final ProxiedPlayer playerTarget = BungeeSystem.getInstance().getProxy().getPlayer(strings[0]);
                final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(strings[0]).toString();
                if(playerTarget == null) return;
                if(BungeeSystem.getInstance().getReportManager().isReported(strings[0]))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Der §7Report " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[0] + " §7ist §7bereits §c§lregestriert§8."));
                    return;
                }
                if(strings[1].equalsIgnoreCase("Hacking"))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Du §7hast " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[0] + " §7erfolgreich §a§lreported§8."));
                    BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> BungeeSystem.getInstance().getReportManager().buildReport(strings[0], player.getName(), "Hacking", playerTarget.getServer().getInfo().getName()));
                    for(final ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers()) {
                        if(players.hasPermission("system.report")) {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0) {

                                final TextComponent annehmen = new TextComponent("§8» §aAnnehmen §8▎ ");
                                annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
                                annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report accept " + playerTarget.getName()));

                                final TextComponent ablehnen = new TextComponent("§8» §cAblehnen");
                                ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
                                ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report delete " + playerTarget.getName()));

                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + playerTarget.getName() + " §7wurde §7für §c§lHacking §7reportet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport()), new TextComponent(annehmen), new TextComponent(ablehnen));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                            }
                            return;
                        }
                        return;
                    }
                }
                else if(strings[1].equalsIgnoreCase("Teaming"))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Du §7hast " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[0] + " §7erfolgreich §a§lreported§8."));
                    BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> BungeeSystem.getInstance().getReportManager().buildReport(strings[0], player.getName(), "Teaming", playerTarget.getServer().getInfo().getName()));
                    for(final ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers()) {
                        if(players.hasPermission("system.report")) {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0) {

                                final TextComponent annehmen = new TextComponent("§8» §aAnnehmen §8▎ ");
                                annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
                                annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report accept " + playerTarget.getName()));

                                final TextComponent ablehnen = new TextComponent("§8» §cAblehnen");
                                ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
                                ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report delete " + playerTarget.getName()));

                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + playerTarget.getName() + " §7wurde §7für §c§lTeaming §7reportet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport()), new TextComponent(annehmen), new TextComponent(ablehnen));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                            }
                            return;
                        }
                        return;
                    }
                }
                else if(strings[1].equalsIgnoreCase("Beleidigung"))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Du §7hast " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[0] + " §7erfolgreich §a§lreported§8."));
                    BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> BungeeSystem.getInstance().getReportManager().buildReport(strings[0], player.getName(), "Beleidigung", playerTarget.getServer().getInfo().getName()));
                    for(final ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers()) {
                        if(players.hasPermission("system.report")) {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0) {

                                final TextComponent annehmen = new TextComponent("§8» §aAnnehmen §8▎ ");
                                annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
                                annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report accept " + playerTarget.getName()));

                                final TextComponent ablehnen = new TextComponent("§8» §cAblehnen");
                                ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
                                ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report delete " + playerTarget.getName()));

                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + playerTarget.getName() + " §7wurde §7für §c§lBeleidigung §7reportet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport()), new TextComponent(annehmen), new TextComponent(ablehnen));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                            }
                            return;
                        }
                        return;
                    }
                }
                else if(strings[1].equalsIgnoreCase("Werbung"))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Du §7hast " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + strings[0] + " §7erfolgreich §a§lreported§8."));
                    BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () -> BungeeSystem.getInstance().getReportManager().buildReport(strings[0], player.getName(), "Werbung", playerTarget.getServer().getInfo().getName()));
                    for(final ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers()) {
                        if(players.hasPermission("system.report")) {
                            if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0) {

                                final TextComponent annehmen = new TextComponent("§8» §aAnnehmen §8▎ ");
                                annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
                                annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report accept " + playerTarget.getName()));

                                final TextComponent ablehnen = new TextComponent("§8» §cAblehnen");
                                ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
                                ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report delete " + playerTarget.getName()));

                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + playerTarget.getName() + " §7wurde §7für §c§lWerbung §7reportet§8."));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport()), new TextComponent(annehmen), new TextComponent(ablehnen));
                                players.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + " "));
                            }
                            return;
                        }
                        return;
                    }
                }
                else
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Benutze§8: §8/§areport §8<§aSpieler§8> §8<§aGrund§8>"));
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Gründe§8: §cHacking§8, §cTeaming§8, §cBeleidigung§8, §cWerbung"));
                }
            }
            else
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Benutze§8: §8/§areport §8<§aSpieler§8> §8<§aGrund§8>"));
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Gründe§8: §cHacking§8, §cTeaming§8, §cBeleidigung§8, §cWerbung"));
            }
        }
        else
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Benutze§8: §8/§areport §8<§aSpieler§8> §8<§aGrund§8>"));
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getReport() + "§7Gründe§8: §cHacking§8, §cTeaming§8, §cBeleidigung§8, §cWerbung"));
        }
    }
}
