package com.minejunkie.junkiepass.challenges.paid.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent;
import net.lightshard.prisonmines.PrisonMines;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.TreeSet;

public class SpecificBlockChallenge extends Challenge {

    private Material material;

    public SpecificBlockChallenge(JunkiePass plugin, Material material, String name, double amount, TreeSet<Double> milestones) {
        super(plugin, ChallengeType.PAID, name, amount, 10, milestones);
        this.material = material;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()) == null) return;
        if (event.getBlock().getType() != material) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (profile.getPaidChallenges().containsKey(this.getClass())) {
            ChallengeData data = profile.getPaidChallenges().get(this.getClass());
            increment(profile, event.getPlayer(), data,1);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockExplode(TEBlockExplodeEvent event) {
        if (event.blockList().isEmpty()) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (profile.getPaidChallenges().containsKey(this.getClass())) {

            ChallengeData data = profile.getPaidChallenges().get(this.getClass());
            int counter = 0;
            for (Block block : event.blockList()) {
                if (block.getType() == material) counter++;
            }

            if (counter == 0) return;
            increment(profile, event.getPlayer(), data, counter - 1);
        }
    }
}
