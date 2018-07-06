package com.minejunkie.junkiepass;

import com.minejunkie.junkiepass.challenges.ChallengeManager;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.challenges.ChallengesMenu;
import com.minejunkie.junkiepass.challenges.daily.DailyChallengeMenu;
import com.minejunkie.junkiepass.challenges.daily.jshards.CommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.jshards.UncommonShardChallenge;
import com.minejunkie.junkiepass.challenges.daily.vanilla.BlocksBrokenChallenge;
import com.minejunkie.junkiepass.challenges.paid.PaidChallengeMenu;
import com.minejunkie.junkiepass.challenges.paid.dman.DeliveryManChallenge;
import com.minejunkie.junkiepass.challenges.paid.jenchants.MineBombChallenge;
import com.minejunkie.junkiepass.challenges.paid.jenchants.NukeChallenge;
import com.minejunkie.junkiepass.challenges.paid.jshards.ShardRollChallenge;
import com.minejunkie.junkiepass.challenges.paid.prestige.PrestigeChallenge;
import com.minejunkie.junkiepass.challenges.paid.ranks.EZRanksChallenge;
import com.minejunkie.junkiepass.challenges.paid.vanilla.*;
import com.minejunkie.junkiepass.challenges.paid.vote.VoteChallenge;
import com.minejunkie.junkiepass.commands.GiveChallengeCommand;
import com.minejunkie.junkiepass.commands.JPRedeemCommand;
import com.minejunkie.junkiepass.commands.JunkiePassCommand;
import com.minejunkie.junkiepass.commands.TierCommand;
import com.minejunkie.junkiepass.listeners.PlayerListener;
import com.minejunkie.junkiepass.profiles.JunkiePassProfileManager;
import com.minejunkie.junkiepass.tiers.TierConfig;
import com.minejunkie.junkiepass.tiers.TierMenu;
import com.minejunkie.junkiepass.utils.CommonUtils;
import com.minertainment.obj.ShardType;
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
                // Daily
                new CommonShardChallenge(this),
                new UncommonShardChallenge(this),
                new BlocksBrokenChallenge(this),
                new SpecificMineChallenge(this, ChallengeType.DAILY,"H", "H Mine Challenge", 10000, cu.generateMilestones(10000, 1000)){},
                new SpecificMineChallenge(this, ChallengeType.DAILY,"J", "J Mine Challenge", 10000, cu.generateMilestones(10000, 1000)){},
                new SpecificMineChallenge(this, ChallengeType.DAILY,"K", "K Mine Challenge", 10000, cu.generateMilestones(10000, 1000)){},
                new SpecificMineChallenge(this, ChallengeType.DAILY,"M", "M Mine Challenge", 10000, cu.generateMilestones(10000, 1000)){},
                new SpecificBlockChallenge(this, ChallengeType.DAILY, Material.IRON_BLOCK,"Iron Block Challenge","Mine 7,500 Iron Blocks.",7500, cu.generateMilestones(7500, 750)){},
                new SpecificBlockChallenge(this, ChallengeType.DAILY, Material.GOLD_BLOCK,"Gold Block Challenge","Mine 7,500 Gold Blocks.",7500, cu.generateMilestones(7500, 750)){},
                new SpecificBlockChallenge(this, ChallengeType.DAILY, Material.DIAMOND_BLOCK,"Diamond Block Challenge","Mine 7,500 Diamond Blocks.",7500, cu.generateMilestones(7500, 750)){},
                new SpecificBlockChallenge(this, ChallengeType.DAILY, Material.COAL_BLOCK,"Coal Block Challenge","Mine 7,500 Coal Blocks.",7500, cu.generateMilestones(7500, 750)){},

                // Premium
                //
                new CommandChallenge(this, "/mine", "/mine Challenge", "Use the /mine command.", 1, 10){},
                new CommandChallenge(this, "/cshop", "/cshop Challenge", "Use the /cshop command.", 1, 10){},
                new CommandChallenge(this, "/ench", "/ench Challenge", "Use the /enchant command.", 1, 10){},
                new SyncChallenge(this),
                new TogglePMChallenge(this),
                new ToggleMentionsChallenge(this),
                new CommandChallenge(this, "/vote", "/vote Challenge", "Use the /vote command.", 1, 10){},
                new CommandChallenge(this, "/core", "/core Challenge", "Use the /core command.", 1, 10){},
                //

                //
                new ShardRollChallenge(this, ShardType.COMMON, 10){},
                new ShardRollChallenge(this, ShardType.UNCOMMON, 5){},
                new ShardRollChallenge(this, ShardType.RARE, 3){},
                new ShardRollChallenge(this, ShardType.LEGENDARY, 2){},
                new ShardRollChallenge(this, ShardType.JUNKIE, 1){},

                new MineBombChallenge(this),
                new NukeChallenge(this),

                new DeliveryManChallenge(this),
                //

                //
                new VoteChallenge(this),
                new EZRanksChallenge(this),
                new PrestigeChallenge(this, 1){},
                new PrestigeChallenge(this, 5){},
                new PrestigeChallenge(this, 10){},
                new PrestigeChallenge(this, 20){},
                new PrestigeChallenge(this, 30){},
                new PrestigeChallenge(this, 40){},
                //

                //
                new SpecificMineChallenge(this, ChallengeType.PAID,"G", "G Mine Challenge", 125000, cu.generateMilestones(125000, 2500)){},
                new SpecificMineChallenge(this, ChallengeType.PAID,"L", "L Mine Challenge", 150000, cu.generateMilestones(150000, 2500)){},
                new SpecificMineChallenge(this, ChallengeType.PAID,"X", "X Mine Challenge", 175000, cu.generateMilestones(175000, 2500)){},
                new SpecificMineChallenge(this, ChallengeType.PAID,"Z", "Z Mine Challenge", 200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, ChallengeType.PAID, Material.IRON_BLOCK,"Iron Block Challenge","Mine 200,000 Iron Blocks.",200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, ChallengeType.PAID, Material.GOLD_BLOCK,"Gold Block Challenge","Mine 200,000 Gold Blocks.",200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, ChallengeType.PAID, Material.DIAMOND_BLOCK,"Diamond Block Challenge","Mine 200,000 Diamond Blocks.",200000, cu.generateMilestones(200000, 2500)){},
                new SpecificBlockChallenge(this, ChallengeType.PAID, Material.COAL_BLOCK,"Coal Block Challenge","Mine 200,000 Coal Blocks.",200000, cu.generateMilestones(200000, 2500)){}
                //
        );

        junkiePassMenu = new JunkiePassMenu(this);
        challengesMenu = new ChallengesMenu(this);
        dailyChallengeMenu = new DailyChallengeMenu(this);
        paidChallengeMenu = new PaidChallengeMenu(this);
        tierMenu = new TierMenu(this);

        new GiveChallengeCommand(this);
        new JunkiePassCommand(this);
        new JPRedeemCommand(this);
        new TierCommand(this);
        new PlayerListener(this);

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
