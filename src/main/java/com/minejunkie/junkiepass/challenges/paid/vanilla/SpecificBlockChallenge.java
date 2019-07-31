package com.minejunkie.junkiepass.challenges.paid.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.event.CubedEvent;
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

    public SpecificBlockChallenge(JunkiePass plugin, ChallengeType type, Material material, String name, String description, double amount, TreeSet<Double> milestones) {
        super(plugin, type, name, description, amount, type == ChallengeType.DAILY ? 5 : 30, milestones, Format.WHOLE);
        this.material = material;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()) == null) return;
        if (event.getBlock().getType() != material) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, 1);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockExplode(TEBlockExplodeEvent event) {
        if (event.blockList().isEmpty()) return;
        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        ChallengeData data;

        if ((data = getChallengeData(profile)) == null) return;
        if (data.isComplete()) return;
        int counter = 0;

        for (Block block : event.blockList()) {
            if (block.getType() == material) counter++;
        }

        if (counter == 0) return;
        increment(profile, event.getPlayer(), data, counter - 1);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockCubed(CubedEvent event) {
        if (event.getBlocks().isEmpty()) return;
        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        ChallengeData data;

        if ((data = getChallengeData(profile)) == null) return;
        if (data.isComplete()) return;
        int counter = 0;

        for (Block block : event.getBlocks()) {
            if (block.getType() == material) counter++;
        }

        if (counter == 0) return;
        increment(profile, event.getPlayer(), data, counter - 1);
    }

}
