package com.minejunkie.junkiepass.challenges.paid.jenchants;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.event.MineBombExplodeEvent;
import org.bukkit.event.EventHandler;

public class MineBombChallenge extends Challenge {

    public MineBombChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "Mine Bomb Challenge", "Shoot 20 mine bombs.", 20, 20);
    }

    @EventHandler
    public void onMineBomb(MineBombExplodeEvent event) {
        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, 1);
    }
}
