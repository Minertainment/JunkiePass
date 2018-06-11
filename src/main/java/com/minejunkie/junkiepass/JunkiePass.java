package com.minejunkie.junkiepass;

import com.minejunkie.junkiepass.challenges.ChallengeManager;
import com.minejunkie.junkiepass.challenges.jshards.CommonShardChallenge;
import com.minejunkie.junkiepass.challenges.vanilla.BlocksBrokenChallenge;
import com.minejunkie.junkiepass.commands.GiveChallengeCommand;
import com.minejunkie.junkiepass.profiles.JunkiePassProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class JunkiePass extends JavaPlugin {

    private JunkiePassProfileManager profileManager;
    private ChallengeManager challengeManager;
    private Random random;
    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD.toString() + "Junkie Pass" + ChatColor.DARK_GRAY + "] ";

    public void onEnable() {
        profileManager = new JunkiePassProfileManager();

        challengeManager = new ChallengeManager(this);
        challengeManager.registerChallenges(
                new CommonShardChallenge(this),
                new BlocksBrokenChallenge(this)
        );

        new GiveChallengeCommand(this);

        random = new Random();
    }

    public void onDisable() {

    }

    public JunkiePassProfileManager getProfileManager() {
        return profileManager;
    }

    public ChallengeManager getChallengeManager() {
        return challengeManager;
    }

    public Random getRandom() {
        return random;
    }

    public String getPrefix() {
        return prefix;
    }
}
