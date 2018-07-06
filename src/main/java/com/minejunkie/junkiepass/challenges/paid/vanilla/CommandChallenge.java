package com.minejunkie.junkiepass.challenges.paid.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandChallenge extends Challenge {

    private String command;

    public CommandChallenge(JunkiePass plugin, String command, String name, String description, double amount, double experience) {
        super(plugin, ChallengeType.PAID, name, description, amount, experience);
        this.command = command;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        if (!profile.isPaid()) return;

        if (!event.getMessage().equalsIgnoreCase(command)) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, 1);
    }
}
