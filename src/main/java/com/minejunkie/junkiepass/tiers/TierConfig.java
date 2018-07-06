package com.minejunkie.junkiepass.tiers;

import com.minertainment.athena.configuration.GSONConfig;

import java.io.File;

public class TierConfig extends GSONConfig {

    private static Tier[] tiers;

    public TierConfig(File directory) {
        super(directory, "tiers.json");

        tiers = new Tier[] {
                new Tier(1, false, true).addPaidReward("2x Common Keys", "crate givekey @p Common 2"),
                new Tier(2, false, false),
                new Tier(3, false, true).addPaidReward("15x E-Tokens", "te add @p 15"),
                new Tier(4, false, false),
                new Tier(5, false, true).addPaidReward("2x Uncommon Shards", "shard @p uncommon 2"),
                new Tier(6, false, false),
                new Tier(7, false, true).addPaidReward("2x Rare Keys", "crate givekey @p Rare 2").addPaidReward("2x Uncommon Keys", "crate givekey @p Uncommon 2"),
                new Tier(8, false, false),
                new Tier(9, true, true).addPaidReward("#Toxic Tag", "lp user @p set deluxetags.tag.toxic").addFreeReward("#Dealer Tag", "lp user @p set deluxetags.tag.dealer"),
                new Tier(10, false, false),
                new Tier(11, false, true).addPaidReward("Rainbow Leggings", "rainbow leggings @p"),
                new Tier(12, false, false),
                new Tier(13, false, true).addPaidReward("Kit Bedrock", "kit bedrock @p"),
                new Tier(14, false, false),
                new Tier(15, false, true).addPaidReward("+5 Fortune Book", "te egive @p fortune +5"),
                new Tier(16, false, false),
                new Tier(17, false, true).addPaidReward("1x Legendary Key", "crate givekey @p Legendary 1"),
                new Tier(18, true, false).addFreeReward("3x Common Keys", "crate givekey @p Common 3").addFreeReward("2x Uncommon Keys", "crate givekey @p Uncommon 2").addFreeReward("1x Rare Key", "crate givekey @p Rare 1"),
                new Tier(19, false, true).addPaidReward("30x E-Tokens", "te add @p 30"),
                new Tier(20, false, false),
                new Tier(21, false, true).addPaidReward("Rainbow Boots", "rainbow boots @p"),
                new Tier(22, false, false),
                new Tier(23, false, true).addPaidReward("1x Legendary Shard", "shard @p legendary 1"),
                new Tier(24, false, false),
                new Tier(25, false, true).addPaidReward("Kit Junkie", "kit junkie @p").addPaidReward("2x Rare Keys", "crate givekey @p Rare 2"),
                new Tier(26, false, false),
                new Tier(27, true, true).addPaidReward("1x Junkie Key", "crate givekey @p Junkie 1").addFreeReward("2x Uncommon Shards", "shard @p Uncommon 2").addFreeReward("1x Rare Shard", "shard @p Rare 1"),
                new Tier(28, false, false),
                new Tier(29, false, true).addPaidReward("1x Legendary Shard", "shard @p legendary 1"),
                new Tier(30, false, false),
                new Tier(31, false, true).addPaidReward("Paintball Gun", "paintball give @p"),
                new Tier(32, false, false),
                new Tier(33, false, true).addPaidReward("1x Junkie Shard", "shard @p junkie 1"),
                new Tier(34, false, false),
                new Tier(35, false, true).addPaidReward("70x E-Tokens", "te add @p 70"),
                new Tier(36, true, false).addFreeReward("50x E-Tokens", "te add @p 50").addFreeReward("1x Rare Key", "crate givekey @p Rare 1"),
                new Tier(37, false, true).addPaidReward("+4 Explosive Book", "te egive @p explosive +4"),
                new Tier(38, false, false),
                new Tier(39, false, true).addPaidReward("Obsidian Kit", "kit obsidian @p").addPaidReward("2x Rare Keys", "crate givekey @p Rare 2"),
                new Tier(40, false, false),
                new Tier(41, false, true).addPaidReward("Rainbow Helmet", "rainbow helmet @p"),
                new Tier(42, false, false),
                new Tier(43, false, true).addPaidReward("1x Junkie Key", "crate givekey @p Junkie 1").addPaidReward("2x Rare Shards", "shard @p Rare 2"),
                new Tier(44, false, false),
                new Tier(45, true, true).addPaidReward("Instant Spell Particle Effect", "lp user @p set playerparticles.effect.instantspell").addPaidReward("Quad Helix Particle Style", "lp user @p set playerparticles.style.quadhelix").addFreeReward("2x Rare Shards", "shard @p Rare 2").addFreeReward("1x Legendary Key", "crate givekey @p Legendary 1"),
                new Tier(46, false, false),
                new Tier(47, false, true).addPaidReward("70x E-Tokens", "te add @p 70"),
                new Tier(48, false, false),
                new Tier(49, false, true).addPaidReward("150x E-Tokens", "te add @p 150").addPaidReward("2x Junkie Keys", "crate givekey @p Junkie 2"),
                new Tier(50, true, true).addPaidReward("Rainbow Chestplate", "rainbow chestplate @p").addPaidReward("1x July Crate", "chest give @p july 1").addFreeReward("#Junkie Tag", "lp user @p set tag.junkie").addFreeReward("50x Tokens", "te add @p 50").addFreeReward("1x Junkie Shard", "shard @p Junkie 1"),
        };
    }

    public static Tier[] getTiers() {
        return tiers;
    }
}
