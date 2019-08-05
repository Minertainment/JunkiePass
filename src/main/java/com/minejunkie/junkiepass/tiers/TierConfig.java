package com.minejunkie.junkiepass.tiers;

import com.minertainment.athena.configuration.GSONConfig;

import java.io.File;

public class TierConfig extends GSONConfig {

    private static Tier[] tiers;

    public TierConfig(File directory) {
        super(directory, "tiers.json");

        tiers = new Tier[] {
                new Tier(1, false, true).addPaidReward("4x Common Keys", "crate give to @p Common 4"),
                new Tier(2, false, false),
                new Tier(3, false, true).addPaidReward("21x E-Tokens", "te add @p 21"),
                new Tier(4, false, false),
                new Tier(5, false, true).addPaidReward("10x Regrade Artifacts", "giveartifact @p 10"),
                new Tier(6, false, false),
                new Tier(7, false, true).addPaidReward("2x Rare Keys", "crate give to @p Rare 2").addPaidReward("2x Uncommon Keys", "crate give to @p Uncommon 2"),
                new Tier(8, false, false),
                new Tier(9, true, true).addPaidReward("#Groot Tag", "lp user @p set deluxetags.tag.groot").addFreeReward("10x Regrade Artifacts", "giveartifact @p 10").addFreeReward("#uwu Tag", "lp user @p set deluxetags.tag.uwu"),
                new Tier(10, false, false),
                new Tier(11, false, true).addPaidReward("Spiderman Track", "lp user @p set tracks.spiderman"),
                new Tier(12, false, false),
                new Tier(13, false, true).addPaidReward("Kit Bedrock", "kit bedrock @p"),
                new Tier(14, false, false),
                new Tier(15, false, true).addPaidReward("+5 Fortune Book", "te egive @p fortune +5"),
                new Tier(16, false, false),
                new Tier(17, false, true).addPaidReward("1x Legendary Key", "crate give to @p Legendary 1"),
                new Tier(18, true, false).addFreeReward("3x Common Keys", "crate give to @p Common 3").addFreeReward("2x Uncommon Keys", "crate give to @p Uncommon 2").addFreeReward("1x Rare Key", "crate give to @p Rare 1"),
                new Tier(19, false, true).addPaidReward("50x E-Tokens", "te add @p 50"),
                new Tier(20, false, false),
                new Tier(21, false, true).addPaidReward("10x Flawless Charms", "giveflawless @p 10"),
                new Tier(22, false, false),
                new Tier(23, false, true).addPaidReward("3x Divine Charms", "givedivine @p 3"),
                new Tier(24, false, false),
                new Tier(25, false, true).addPaidReward("Kit Junkie", "kit junkie @p").addPaidReward("2x Rare Keys", "crate give to @p Rare 2").addFreeReward("FLY COMMAND", "lp user @p set essentials.fly"),
                new Tier(26, false, false),
                new Tier(27, true, true).addPaidReward("2x Junkie Key", "crate give to @p Junkie 2").addFreeReward("15x Regrade Artifacts", "giveartifact @p 15").addFreeReward("2x Flawless Charms", "giveflawless @p 2"),
                new Tier(28, false, false),
                new Tier(29, false, true).addPaidReward("5x Divine Charms", "givedivine @p 5"),
                new Tier(30, false, false),
                new Tier(31, false, true).addPaidReward("8x Flawless Charms", "giveflawless @p 8"),
                new Tier(32, false, false),
                new Tier(33, false, true).addPaidReward("30x Regrade Artifact", "giveartifact @p 30"),
                new Tier(34, false, false),
                new Tier(35, false, true).addPaidReward("100x E-Tokens", "te add @p 100"),
                new Tier(36, true, false).addFreeReward("50x E-Tokens", "te add @p 50").addFreeReward("1x Rare Key", "crate give to @p Rare 1"),
                new Tier(37, false, true).addPaidReward("+4 Explosive Book", "te egive @p explosive +4"),
                new Tier(38, false, false),
                new Tier(39, false, true).addPaidReward("Obsidian Kit", "kit obsidian @p").addPaidReward("2x Rare Keys", "crate give to @p Rare 2"),
                new Tier(40, false, false),
                new Tier(41, false, true).addPaidReward("Weed Track", "lp user @p set tracks.weed"),
                new Tier(42, false, false),
                new Tier(43, false, true).addPaidReward("1x Junkie Key", "crate give to @p Junkie 1").addPaidReward("5x Flawless Charms", "giveflawless @p 5"),
                new Tier(44, false, false),
                new Tier(45, true, true).addPaidReward("20x Regrade Artifacts", "giveartifact @p 20").addPaidReward("20x Flawless Charms", "giveflawless @p 20").addFreeReward("10x Regrade Artifacts", "giveartifact @p 10").addFreeReward("1x Legendary Key", "crate give to @p Legendary 1"),
                new Tier(46, false, false),
                new Tier(47, false, true).addPaidReward("100x E-Tokens", "te add @p 100"),
                new Tier(48, false, false),
                new Tier(49, false, true).addPaidReward("150x E-Tokens", "te add @p 150").addPaidReward("2x Junkie Keys", "crate give to @p Junkie 2"),
                new Tier(50, true, true).addPaidReward("August Crate", "chest give @p august 1").addPaidReward("Rainbow Track", "lp user @p set tracks.rainbow").addFreeReward("#Junkie2 Tag", "lp user @p set deluxetags.tag.junkie2").addFreeReward("50x Tokens", "te add @p 50").addFreeReward("15x Divine Charms", "givedivine @p 15"),
        };
    }

    public static Tier[] getTiers() {
        return tiers;
    }
}
