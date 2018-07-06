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

public class UncommonShardChallenge extends Challenge {

    public UncommonShardChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.DAILY, "Uncommon Shard Challenge", "Roll 1 Uncommon Shard.", 1, 5, Format.WHOLE);
    }

    @EventHandler
    public void onUncommonRoll(ShardRollEvent event) {
        JunkiePassProfile profile = getProfile(event.getUUID());
        if (profile.getDailyChallenges().containsKey(this.getClass())) {
            if (event.getShard().getShardType() == ShardType.UNCOMMON) {
                ChallengeData data = profile.getDailyChallenges().get(this.getClass());
                increment(profile, Bukkit.getPlayer(event.getUUID()), data, 1);
            }
        }
    }
}
