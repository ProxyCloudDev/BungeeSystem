package de.proxycloud.bungeesystem.utils.groups;

import de.proxycloud.bungeesystem.BungeeSystem;

/**
 * Developer ProxyCloud
 * Coded on 19.03.2018
 * Coded with IntelliJ
 */
public class PrefixManager
{

    public String getPrefix(String uuid)
    {
        if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Administrator"))
        {
            return "§4";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Developer"))
        {
            return "§b";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("SrModerator"))
        {
            return "§c";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Moderator"))
        {
            return "§c";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Supporter"))
        {
            return "§9";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Builder"))
        {
            return "§a";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("YouTuber"))
        {
            return "§5";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("PremiumPlus"))
        {
            return "§e";
        }
        else if(BungeeSystem.getInstance().getGroupManager().getGroup(uuid).equalsIgnoreCase("Premium"))
        {
            return "§6";
        }
        else
        {
            return "§7";
        }
    }

}
