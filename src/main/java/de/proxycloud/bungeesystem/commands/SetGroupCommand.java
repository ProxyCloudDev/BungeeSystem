package de.proxycloud.bungeesystem.commands;

import de.proxycloud.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;

/**
 * Developer ProxyCloud
 * Coded on 21.03.2018
 * Coded with IntelliJ
 */
public class SetGroupCommand extends Command
{

    private Configuration configuration;

    public SetGroupCommand(String name)
    {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings)
    {
        if(!(commandSender instanceof ProxiedPlayer)) return;
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if(!(player.hasPermission("system.setgroup"))) return;
        if(strings.length != 2)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Benutze§8: §8/§asetgroup §8<§aSpieler§8> §8<§aRang§8>"));
            return;
        }
        try
        {
            final String name = strings[0];
            final String group = strings[1];
            final String uuid = BungeeSystem.getInstance().getUuidFetcher().getUUID(name).toString();
            if(!(BungeeSystem.getInstance().getGroupManager().isRegistered(uuid)))
            {
                player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
                return;
            }
            BungeeSystem.getInstance().getProxy().getScheduler().runAsync(BungeeSystem.getInstance(), () ->
            {
                if(group.equalsIgnoreCase("Administrator"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "Administrator");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §4Administrator"));
                            sendPlayers(uuid, name, player, "§4Administrator");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("Developer"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "Developer");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §bDeveloper"));
                            sendPlayers(uuid, name, player, "§bDeveloper");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("SrModerator"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "SrModerator");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §cSrModerator"));
                            sendPlayers(uuid, name, player, "§cSrModerator");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("Moderator"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "Moderator");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §cModerator"));
                            sendPlayers(uuid, name, player, "§cModerator");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("Supporter"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "Supporter");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §9Supporter"));
                            sendPlayers(uuid, name, player, "§9Supporter");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("Builder"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "Builder");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §aBuilder"));
                            sendPlayers(uuid, name, player, "§aBuilder");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("YouTuber"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "YouTuber");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §5YouTuber"));
                            sendPlayers(uuid, name, player, "§5YouTuber");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("PremiumPlus"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "PremiumPlus");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §ePremium+"));
                            sendPlayers(uuid, name, player, "§ePremium+");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("Premium"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "Premium");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §6Premium"));
                            sendPlayers(uuid, name, player, "§6Premium");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                if(group.equalsIgnoreCase("Spieler"))
                {
                    BungeeSystem.getInstance().getGroupManager().setGroup(uuid, "Spieler");
                    if(BungeeSystem.getInstance().getProxy().getPlayer(name) != null)
                    {
                        try
                        {
                            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeSystem.getInstance().getFile());
                            BungeeSystem.getInstance().getProxy().getPlayer(name).disconnect(new TextComponent("§7Du §7wurdest §7vom §e" + this.configuration.getString("server.name")
                                    + " §7Netzwerk §c§lgekickt§8. \n\n §7Neuer §7Rang §8» §7Spieler"));
                            sendPlayers(uuid, name, player, "§7Spieler");
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
            });
        }
        catch(Exception e)
        {
            player.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7" + strings[0] + " §7ist §7nicht §7in §7der §7Datenbank §c§lregestriert§8."));
        }
    }

    private void sendPlayers(String uuid, String name, ProxiedPlayer player, String rang)
    {
        for(ProxiedPlayer players : BungeeSystem.getInstance().getProxy().getPlayers())
        {
            if(players.hasPermission("system.mute"))
            {
                if(BungeeSystem.getInstance().getNotifyCache().get(players.getUniqueId().toString()) == 0)
                {
                    players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                    players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + BungeeSystem.getInstance().getPrefixCache().get(uuid) + name + " §7wurde §7von " + BungeeSystem.getInstance().getPrefixCache().get(player.getUniqueId().toString()) + player.getName() + " §7erfolgreich §a§lgekickt§8."));
                    players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + "§7Neuer Rang §8» " + rang));
                    players.sendMessage(new TextComponent(BungeeSystem.getInstance().getPrefix() + " "));
                }
            }
        }
    }

}
