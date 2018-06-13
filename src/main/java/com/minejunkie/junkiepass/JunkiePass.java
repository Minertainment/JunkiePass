package com.minejunkie.junkiepass;

import com.minejunkie.junkiepass.challenges.ChallengeManager;
import com.minejunkie.junkiepass.challenges.ChallengesMenu;
import com.minejunkie.junkiepass.challenges.daily.jshards.CommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.jshards.UncommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.vanilla.BlocksBrokenChallenge;
import com.minejunkie.junkiepass.commands.GiveChallengeCommand;
import com.minejunkie.junkiepass.commands.JunkiePassCommand;
import com.minejunkie.junkiepass.profiles.JunkiePassProfileManager;
import com.minejunkie.junkiepass.tiers.TierMenu;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class JunkiePass extends JavaPlugin {

    private JunkiePassProfileManager profileManager;
    private Random random;
    private JunkiePassLogger junkiePassLogger;
    private ChallengeManager challengeManager;
    private JunkiePassMenu junkiePassMenu;
    private ChallengesMenu challengesMenu;
    private TierMenu tierMenu;

    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD.toString() + "Junkie Pass" + ChatColor.DARK_GRAY + "] ";

    public void onEnable() {
        profileManager = new JunkiePassProfileManager();
        random = new Random();
        junkiePassLogger = new JunkiePassLogger();

        challengeManager = new ChallengeManager(this);

        junkiePassLogger.info("Registering challenges...");
        challengeManager.registerChallenges(
                new CommonShardChallenge(this),
                new UncommonShardChallenge(this),
                new BlocksBrokenChallenge(this)
        );

        junkiePassMenu = new JunkiePassMenu(this);
        challengesMenu = new ChallengesMenu(this);
        tierMenu = new TierMenu(this);

        new GiveChallengeCommand(this);
        new JunkiePassCommand(this);
    }

    public void onDisable() {

    }

    public JunkiePassProfileManager getProfileManager() {
        return profileManager;
    }

    public Random getRandom() {
        return random;
    }

    public JunkiePassLogger getJunkiePassLogger() {
        return junkiePassLogger;
    }

    public ChallengeManager getChallengeManager() {
        return challengeManager;
    }

    public JunkiePassMenu getJunkiePassMenu() {
        return junkiePassMenu;
    }

    public ChallengesMenu getChallengesMenu() {
        return challengesMenu;
    }

    public TierMenu getTierMenu() {
        return tierMenu;
    }

    public String getPrefix() {
        return prefix;
    }

}
