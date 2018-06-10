package com.minejunkie.junkiepass;

import com.minejunkie.junkiepass.profiles.JunkiePassProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class JunkiePass extends JavaPlugin {

    private JunkiePassProfileManager profileManager;
    private String prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "Junkie Pass" + ChatColor.GRAY + "]";

    public void onEnable() {
        profileManager = new JunkiePassProfileManager();
    }

    public void onDisable() {

    }

    public JunkiePassProfileManager getProfileManager() {
        return profileManager;
    }

    public String getPrefix() {
        return prefix;
    }
}
