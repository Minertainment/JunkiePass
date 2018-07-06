package com.minejunkie.junkiepass.challenges.paid.jshards;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.event.ShardRollEvent;
import com.minertainment.obj.ShardType;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.text.NumberFormat;

public class ShardRollChallenge extends Challenge {

    private ShardType shardType;

    public ShardRollChallenge(JunkiePass plugin, ShardType shardType, double amount) {
        super(plugin, ChallengeType.PAID, WordUtils.capitalizeFully(shardType.name()) + " Shard Challenge",
                "Roll " + NumberFormat.getIntegerInstance().format(amount) + " " + WordUtils.capitalizeFully(shardType.name()) + " shards.", amount, 20);
        this.shardType = shardType;
    }

    @EventHandler
    public void onRoll(ShardRollEvent event) {
        JunkiePassProfile profile = getProfile(event.getUUID());
        if (!profile.isPaid()) return;
        if (event.getShard().getShardType() != shardType) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, Bukkit.getPlayer(event.getUUID()), data, 1);
    }
}
