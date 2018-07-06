package com.minejunkie.junkiepass.challenges.paid.prestige;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import me.clip.ezprestige.events.EZPrestigeEvent;
import org.bukkit.event.EventHandler;

public class PrestigeChallenge extends Challenge {

    private int prestige;

    public PrestigeChallenge(JunkiePass plugin, int prestige) {
        super(plugin, ChallengeType.PAID, "Prestige " + prestige + " Challenge", "Reach prestige " + prestige + ".", 1, 20);
        this.prestige = prestige;
    }

    @EventHandler
    public void onPrestige(EZPrestigeEvent event) {
        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        if (!profile.isPaid()) return;

        if (event.getNewPrestige().getPrestige() < prestige) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, 1);
    }

}
