package com.minejunkie.junkiepass.challenges.paid.ranks;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import me.clip.ezrankspro.events.RankupEvent;
import org.bukkit.event.EventHandler;

public class EZRanksChallenge extends Challenge {

    public EZRanksChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "Z Rank Challenge", "Reach Z rank 3 times.", 3, 20);
    }

    @EventHandler
    public void onRankup(RankupEvent event) {
        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        if (!event.getNewRank().equalsIgnoreCase("Z")) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, 1);
    }
}
