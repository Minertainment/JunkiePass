package com.minejunkie.junkiepass.challenges.daily.jshards;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.event.ShardRollEvent;
import com.minertainment.obj.ShardType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.util.Collections;
import java.util.TreeSet;

public class CommonShardChallenge extends Challenge {

    // Roll 2 Common Shards

    public CommonShardChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.DAILY, "Common Shard Challenge", 2, 5, new TreeSet<>(Collections.singletonList(1.00)));
    }

    @EventHandler
    public void onCommonRoll(ShardRollEvent event) {
        JunkiePassProfile profile = getProfile(event.getUUID());
        if (profile.getChallenges().containsKey(this.getClass())) {
            if (event.getShard().getShardType() == ShardType.COMMON) {
                ChallengeData data = profile.getChallenges().get(this.getClass());
                increment(profile, Bukkit.getPlayer(event.getUUID()), data, 1);
            }
        }
    }
}
