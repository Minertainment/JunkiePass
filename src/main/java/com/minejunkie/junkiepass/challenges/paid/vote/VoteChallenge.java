package com.minejunkie.junkiepass.challenges.paid.vote;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class VoteChallenge extends Challenge {

    public VoteChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "Vote Challenge", "Vote 10 times.", 10, 20);
    }

    @EventHandler
    public void onVote(VotifierEvent event) {
        Player player;
        if ((player = Bukkit.getPlayer(event.getVote().getUsername())) == null) return;

        JunkiePassProfile profile = getProfile(player.getUniqueId());
        if (!profile.isPaid()) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, player, data, 1);
    }

}
