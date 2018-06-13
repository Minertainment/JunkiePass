package com.minejunkie.junkiepass.challenges.daily.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import net.lightshard.prisonmines.PrisonMines;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.TreeSet;

public class BlocksBrokenChallenge extends Challenge {

    // Break 10,000 blocks (2500 for testing)

    public BlocksBrokenChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.DAILY, "Blocks Mined Challenge", 20000, 5,
                new TreeSet<>(Arrays.asList(1000.00, 2000.00, 3000.00, 4000.00, 5000.00, 6000.00, 7000.00, 8000.00, 9000.00, 10000.00, 11000.00, 12000.00, 13000.00, 14000.00, 15000.00, 16000.00, 17000.00, 18000.00, 19000.00)));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()) == null) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (profile.getChallenges().containsKey(this.getClass())) {
            ChallengeData data = profile.getChallenges().get(this.getClass());

            increment(profile, event.getPlayer(), data, getPlugin().getRandom().nextInt(35) + 1);
        }
    }

}
