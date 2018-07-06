package com.minejunkie.junkiepass.challenges.paid.dman;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.deliveryman.event.DeliveryCollectEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.TreeSet;

public class DeliveryManChallenge extends Challenge {

    public DeliveryManChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "Delivery Man Challenge", "Claim 10 rewards at the delivery man.", 10, 30, new TreeSet<>(Arrays.asList(2.0, 4.0, 6.0, 8.0)), Format.WHOLE);
    }

    @EventHandler
    public void onClaim(DeliveryCollectEvent event) {
        Player player = event.getPlayer();
        JunkiePassProfile profile = getProfile(player.getUniqueId());

        if (!profile.isPaid()) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, player, data, 1);
    }
}
