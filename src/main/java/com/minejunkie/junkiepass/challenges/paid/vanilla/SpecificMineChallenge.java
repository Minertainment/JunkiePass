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

import java.util.List;
import java.util.TreeSet;

public class SpecificMineChallenge extends Challenge {

    private String mine;

    public SpecificMineChallenge(JunkiePass plugin, String mine, String name, double amount, TreeSet<Double> milestones) {
        super(plugin, ChallengeType.PAID, name, amount, 10, milestones);
        this.mine = mine;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()) == null) return;
        if (!PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()).getName().equalsIgnoreCase(mine)) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (profile.getPaidChallenges().containsKey(this.getClass())) {
            ChallengeData data = profile.getPaidChallenges().get(this.getClass());
            increment(profile, event.getPlayer(), data,1);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockExplode(TEBlockExplodeEvent event) {
        if (event.blockList().isEmpty()) return;
        if (PrisonMines.getAPI().getByLocation(event.blockList().get(0).getLocation()) == null) return;
        if (!PrisonMines.getAPI().getByLocation(event.blockList().get(0).getLocation()).getName().equalsIgnoreCase(mine)) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (profile.getPaidChallenges().containsKey(this.getClass())) {
            ChallengeData data = profile.getPaidChallenges().get(this.getClass());
            increment(profile, event.getPlayer(), data, countNotAir(event.blockList()) - 1);
        }
    }

    public int countNotAir(List<Block> blocks) {
        int counter = 0;
        for (Block block : blocks) {
            if (block == null || block.getType() == Material.AIR) continue;
            counter++;
        }
        return counter;
    }
}
