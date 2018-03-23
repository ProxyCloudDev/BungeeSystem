package de.proxycloud.bungeesystem.commands.party;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Developer ProxyCloud
 * Coded on 21.03.2018
 * Coded with IntelliJ
 */
public class PartyCommand extends Command
{

    public PartyCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(strings.length == 2)
        {
            if(strings[0].equalsIgnoreCase("invite"))
            {
                if(player.getName().equalsIgnoreCase(strings[0])) return;
                if((BungeeSystem.getInstance().getParty2Cache().containsKey(player.getName())) && !(BungeeSystem.getInstance().getParty2Cache().get(player.getName()).equalsIgnoreCase(player.getName())))
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Du §7bist §7bereits §7in §7einer §c§lParty§8."));
                    return;
                }
                try
                {
                    final String name = strings[1];
                    final ProxiedPlayer playerTarget = BungeeSystem.getInstance().getProxy().getPlayer(name);
                    final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
                    if(playerTarget == null)
                    {
                        if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid)))
                        {
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                            return;
                        }
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7ist §7nicht §c§lonline§8."));
                        return;
                    }
                    if(BungeeSystem.getInstance().getParty2Cache().containsKey(playerTarget.getName()))
                    {
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7ist §7bereits §7in §7einer §c§lParty§8."));
                        return;
                    }
                    if(!(BungeeSystem.getInstance().getPartyInviteCache().containsKey(playerTarget.getName())))
                    {
                        BungeeSystem.getInstance().getPartyInviteCache().put(playerTarget.getName(), new ArrayList<>());
                    }
                    final List<String> anfragenList = BungeeSystem.getInstance().getPartyCache().get(playerTarget.getName());
                    if(anfragenList.contains(player.getName()))
                    {
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Du §7hast " + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7bereits §c§langefragt§8."));
                        return;
                    }
                    if(!(BungeeSystem.getInstance().getPartyCache().containsKey(player.getName())))
                    {
                        final List<String> partyList = new ArrayList<>();
                        BungeeSystem.getInstance().getPartyCache().put(player.getName(), partyList);
                        BungeeSystem.getInstance().getParty2Cache().put(player.getName(), player.getName());
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Du §7hast §7eine §7Party §a§lerstellt§8."));
                    }
                    final List<String> partyList = BungeeSystem.getInstance().getPartyCache().get(player.getName());
                    if(!(partyList.contains(player.getName())))
                    {
                        partyList.add(player.getName());
                    }
                    BungeeSystem.getInstance().getPartyCache().put(player.getName(), partyList);
                    anfragenList.add(player.getName());
                    BungeeSystem.getInstance().getPartyInviteCache().put(playerTarget.getName(), anfragenList);

                    playerTarget.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString())
                            + player.getName() + " §7hat §7dich §7in §7eine §7Party §a§leingeladen§8."));

                    final TextComponent annehmen = new TextComponent("§8» §aAnnehmen §8▎ ");
                    annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
                    annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + player.getName()));

                    final TextComponent ablehnen = new TextComponent("§8» §cAblehnen");
                    ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
                    ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + player.getName()));

                    playerTarget.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty()), new TextComponent(annehmen), new TextComponent(ablehnen));

                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Du §7hast " + BungeeSystem.getInstance().getPrefixCache().get(playerTarget.getUniqueId().toString()) + playerTarget.getName() + " §7in §7deine §7Party §a§leingeladen§8."));

                    BungeeSystem.getInstance().getProxy().getScheduler().schedule(BungeeSystem.getInstance(), () ->
                    {
                        if(player != null)
                        {
                            for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
                            {
                                if(BungeeSystem.getInstance().getPartyInviteCache().containsKey(players.getName()))
                                {
                                    final List<String> anfragenList2 = new ArrayList<>();
                                    if(anfragenList2.add(player.getName()))
                                    {
                                        anfragenList2.remove(player.getName());
                                    }
                                    BungeeSystem.getInstance().getPartyInviteCache().put(players.getName(), anfragenList2);
                                }
                            }
                            if((BungeeSystem.getInstance().getPartyCache().containsKey(player.getName())) && (BungeeSystem.getInstance().getPartyCache().get(player.getName()).size() <= 1))
                            {
                                BungeeSystem.getInstance().getPartyCache().remove(player.getName());
                                BungeeSystem.getInstance().getParty2Cache().remove(player.getName());
                                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Deine §7Party §7wurde §c§laufgelöst§8."));
                            }
                        }
                    }, 150, TimeUnit.SECONDS);
                }
                catch(Exception e)
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                }
            }
            else if(strings[0].equalsIgnoreCase("kick"))
            {
                if((BungeeSystem.getInstance().getParty2Cache().containsKey(player.getName()))
                        && (BungeeSystem.getInstance().getParty2Cache().get(player.getName()).equalsIgnoreCase(player.getName()))
                        && (BungeeSystem.getInstance().getPartyCache().containsKey(player.getName())))
                {
                    final String name = strings[1];
                    final ProxiedPlayer playerTarget = BungeeSystem.getInstance().getProxy().getPlayer(name);
                    final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
                    try
                    {
                        if(playerTarget == null)
                        {
                            if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid)))
                            {
                                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                                return;
                            }
                            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7ist §7nicht §c§lonline§8."));
                            return;
                        }
                        final List<String> partyList = BungeeSystem.getInstance().getPartyCache().get(player.getName());
                        if(partyList.contains(playerTarget.getName()))
                        {
                            partyList.remove(playerTarget.getName());
                            playerTarget.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Du §7wurdest §7von "
                                    + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName()
                                    + " §7aus §7der §7Party §c§lgekickt§8."));
                            for(String playerParty : partyList)
                            {
                                ProxiedPlayer partyPlayers = BungeeSystem.getInstance().getProxy().getPlayer(playerParty);

                                if(partyPlayers != null)
                                {
                                    partyPlayers.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name
                                            +  " §7wurde §7aus §7der §7Party §c§lgekickt§8."));
                                }
                            }
                            BungeeSystem.getInstance().getPartyCache().put(player.getName(), partyList);
                            final ProxiedPlayer playerTarget2 = BungeeSystem.getInstance().getProxy().getPlayer(BungeeSystem.getInstance().getParty2Cache().get(playerTarget.getName()));

                            BungeeSystem.getInstance().getProxy().getScheduler().schedule(BungeeSystem.getInstance(), () ->
                            {
                                if((playerTarget2 != null) && (BungeeSystem.getInstance().getPartyCache().containsKey(playerTarget2.getName())))
                                {
                                    if(BungeeSystem.getInstance().getPartyCache().get(playerTarget2.getName()).size() <= 1)
                                    {
                                        BungeeSystem.getInstance().getPartyCache().remove(playerTarget2.getName());
                                        BungeeSystem.getInstance().getParty2Cache().remove(playerTarget2.getName());
                                        playerTarget2.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Deine §7Party §7wurde §c§laufgelöst§8."));
                                    }
                                }
                            }, 150, TimeUnit.SECONDS);
                            BungeeSystem.getInstance().getParty2Cache().remove(playerTarget.getName());
                        }
                    }
                    catch(Exception e)
                    {
                        player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                    }
                }
                else
                {
                    player.sendMessage(new TextComponent(BungeeSystem.getInstance().getParty() + "§7Du §7bist §7kein §7Party §c§lBesitzer§8."));
                }
            }
            else if(strings[0].equalsIgnoreCase("accept"))
            {

            }
        }
    }
}
