package com.minejunkie.junkiepass.challenges.paid.jenchants;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.event.NukeEvent;
import org.bukkit.event.EventHandler;

public class NukeChallenge extends Challenge {

    public NukeChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "Nuke Challenge", "Nuke 1 mine.", 1, 20);
    }

    @EventHandler
    public void onNuke(NukeEvent event) {
        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (!profile.isPaid()) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, 1);
    }
}
