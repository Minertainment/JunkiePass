package com.minejunkie.junkiepass.challenges.jshards;

import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ShardChallenge extends Challenge<PlayerJoinEvent> {

    public ShardChallenge(JunkiePass plugin) {
        super(plugin, 1, 5, null);
    }

    @Override
    @EventHandler
    public void onIncrement(PlayerJoinEvent event) {

    }

    @Override
    public void onComplete(JunkiePassProfile profile, UUID uuid) {

    }
}
