package com.minejunkie.junkiepass.challenges.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import net.lightshard.prisonmines.PrisonMines;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class BlocksBrokenChallenge extends Challenge {

    // Break 10,000 blocks (2500 for testing)

    public BlocksBrokenChallenge(JunkiePass plugin) {
        super(plugin, "Blocks Mined Challenge", 2500, 5, new ArrayList<>(Arrays.asList(500.00, 1000.00, 1500.00, 2000.00)));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()) == null) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (profile.getChallenges().containsKey(this.getClass())) {
            ChallengeData data = profile.getChallenges().get(this.getClass());

            increment(profile, event.getPlayer(), data, 1);
        }
    }

}
