package com.minejunkie.junkiepass;

import com.minejunkie.junkiepass.challenges.ChallengeManager;
import com.minejunkie.junkiepass.challenges.ChallengesMenu;
import com.minejunkie.junkiepass.challenges.daily.DailyChallengeMenu;
import com.minejunkie.junkiepass.challenges.daily.jshards.CommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.jshards.UncommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.vanilla.BlocksBrokenChallenge;
import com.minejunkie.junkiepass.challenges.paid.PaidChallengeMenu;
import com.minejunkie.junkiepass.challenges.paid.vanilla.SpecificBlockChallenge;
import com.minejunkie.junkiepass.challenges.paid.vanilla.SpecificMineChallenge;
import com.minejunkie.junkiepass.commands.GiveChallengeCommand;
import com.minejunkie.junkiepass.commands.JunkiePassCommand;
import com.minejunkie.junkiepass.profiles.JunkiePassProfileManager;
import com.minejunkie.junkiepass.tiers.TierConfig;
import com.minejunkie.junkiepass.tiers.TierMenu;
import com.minejunkie.junkiepass.utils.CommonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class JunkiePass extends JavaPlugin {

    private JunkiePassProfileManager profileManager;
    private Random random;
    private JunkiePassLogger junkiePassLogger;
    private CommonUtils cu;
    private ChallengeManager challengeManager;
    private JunkiePassMenu junkiePassMenu;
    private ChallengesMenu challengesMenu;
    private DailyChallengeMenu dailyChallengeMenu;
    private PaidChallengeMenu paidChallengeMenu;
    private TierMenu tierMenu;

    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD.toString() + "Junkie Pass" + ChatColor.DARK_GRAY + "] ";

    public void onEnable() {
        profileManager = new JunkiePassProfileManager();
        random = new Random();
        junkiePassLogger = new JunkiePassLogger();

        cu = new CommonUtils(this);

        challengeManager = new ChallengeManager(this);

        getLogger().info("Registering challenges...");
        challengeManager.registerChallenges(
                new CommonShardChallenge(this),
                new UncommonShardChallenge(this),
                new BlocksBrokenChallenge(this),
                new SpecificMineChallenge(this, "G", "G Mine Challenge", 125000, cu.generateMilestones(125000, 2500)){},
                new SpecificMineChallenge(this, "L", "L Mine Challenge", 150000, cu.generateMilestones(150000, 2500)){},
                new SpecificMineChallenge(this, "X", "X Mine Challenge", 175000, cu.generateMilestones(175000, 2500)){},
                new SpecificMineChallenge(this, "Z", "Z Mine Challenge", 200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, Material.IRON_BLOCK, "Iron Block Challenge", 200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, Material.GOLD_BLOCK, "Gold Block Challenge", 200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, Material.DIAMOND_BLOCK, "Diamond Block Challenge", 200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, Material.COAL_BLOCK, "Coal Block Challenge", 200000, cu.generateMilestones(200000, 2500)){}
        );

        junkiePassMenu = new JunkiePassMenu(this);
        challengesMenu = new ChallengesMenu(this);
        dailyChallengeMenu = new DailyChallengeMenu(this);
        paidChallengeMenu = new PaidChallengeMenu(this);
        tierMenu = new TierMenu(this);

        new GiveChallengeCommand(this);
        new JunkiePassCommand(this);

        TierConfig tierConfig = new TierConfig(getDataFolder());
        tierConfig.loadConfig();
        tierConfig.saveDefaultConfig();
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

    public CommonUtils getCommonUtils() {
        return cu;
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

    public DailyChallengeMenu getDailyChallengeMenu() {
        return dailyChallengeMenu;
    }

    public PaidChallengeMenu getPaidChallengeMenu() {
        return paidChallengeMenu;
    }

    public TierMenu getTierMenu() {
        return tierMenu;
    }

    public String getPrefix() {
        return prefix;
    }
}
