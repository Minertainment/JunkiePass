package com.minejunkie.junkiepass.tiers;

import com.minertainment.athena.configuration.GSONConfig;

import java.io.File;

public class TierConfig extends GSONConfig {

    private static Tier[] tiers;

    public TierConfig(File directory) {
        super(directory, "tiers.json");

        tiers = new Tier[] {
                new Tier(1, false, true),
                new Tier(2, false, false),
                new Tier(3, false, true),
                new Tier(4, false, false),
                new Tier(5, false, true),
                new Tier(6, false, false),
                new Tier(7, false, true),
                new Tier(8, false, false),
                new Tier(9, true, true).addFreeReward("#Dealer Tag", "lp user @p set tag.dealer"),
                new Tier(10, false, false),
                new Tier(11, false, true),
                new Tier(12, false, false),
                new Tier(13, false, true),
                new Tier(14, false, false),
                new Tier(15, false, true),
                new Tier(16, false, false),
                new Tier(17, false, true),
                new Tier(18, true, false).addFreeReward("3x Common Keys", "crate givekey @p Common 3").addFreeReward("2x Uncommon Keys", "crate givekey @p Uncommon 2").addFreeReward("1x Rare Key", "crate givekey @p Rare 1"),
                new Tier(19, false, true),
                new Tier(20, false, false),
                new Tier(21, false, true),
                new Tier(22, false, false),
                new Tier(23, false, true),
                new Tier(24, false, false),
                new Tier(25, false, true),
                new Tier(26, false, false),
                new Tier(27, true, true).addFreeReward("2x Uncommon Shards", "shard @p Uncommon 2").addFreeReward("1x Rare Shard", "shard @p Rare 1"),
                new Tier(28, false, false),
                new Tier(29, false, true),
                new Tier(30, false, false),
                new Tier(31, false, true),
                new Tier(32, false, false),
                new Tier(33, false, true),
                new Tier(34, false, false),
                new Tier(35, false, true),
                new Tier(36, true, false).addFreeReward("50x Tokens", "te add @p 50").addFreeReward("1x Rare Key", "crate givekey @p Rare 1"),
                new Tier(37, false, true),
                new Tier(38, false, false),
                new Tier(39, false, true),
                new Tier(40, false, false),
                new Tier(41, false, true),
                new Tier(42, false, false),
                new Tier(43, false, true),
                new Tier(44, false, false),
                new Tier(45, true, true).addFreeReward("2x Rare Shard", "shard @p Rare 2").addFreeReward("1x Legendary Key", "crate givekey @p Legendary 1"),
                new Tier(46, false, false),
                new Tier(47, false, true),
                new Tier(48, false, false),
                new Tier(49, false, true),
                new Tier(50, true, true).addFreeReward("#Junkie Tag", "lp user @p set tag.junkie").addFreeReward("50x Tokens", "te add @p 50").addFreeReward("1x Junkie Shard", "shard @p Junkie 1"),
        };
    }

    public static Tier[] getTiers() {
        return tiers;
    }
}
