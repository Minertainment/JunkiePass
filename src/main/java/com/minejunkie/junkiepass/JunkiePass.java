package com.minejunkie.junkiepass;

import com.minejunkie.junkiepass.challenges.ChallengeManager;
import com.minejunkie.junkiepass.challenges.daily.jshards.CommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.jshards.UncommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.vanilla.BlocksBrokenChallenge;
import com.minejunkie.junkiepass.commands.GiveChallengeCommand;
import com.minejunkie.junkiepass.profiles.JunkiePassProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class JunkiePass extends JavaPlugin {

    private JunkiePassProfileManager profileManager;
    private ChallengeManager challengeManager;
    private JunkiePassLogger junkiePassLogger;
    private Random random;
    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD.toString() + "Junkie Pass" + ChatColor.DARK_GRAY + "] ";

    public void onEnable() {
        profileManager = new JunkiePassProfileManager();
        junkiePassLogger = new JunkiePassLogger();

        challengeManager = new ChallengeManager(this);

        junkiePassLogger.info("Registering challenges...");
        challengeManager.registerChallenges(
                new CommonShardChallenge(this),
                new UncommonShardChallenge(this),
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

    public JunkiePassLogger getJunkiePassLogger() {
        return junkiePassLogger;
    }

    public Random getRandom() {
        return random;
    }

    public String getPrefix() {
        return prefix;
    }
}
