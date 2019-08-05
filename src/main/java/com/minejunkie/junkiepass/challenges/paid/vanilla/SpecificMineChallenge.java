package com.minejunkie.junkiepass.challenges.paid.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.event.CubedEvent;
import com.minertainment.event.TEBlockExplodeEvent;
import net.lightshard.prisonmines.PrisonMines;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import java.text.NumberFormat;
import java.util.List;
import java.util.TreeSet;

public class SpecificMineChallenge extends Challenge {

    private String mine;

    public SpecificMineChallenge(JunkiePass plugin, ChallengeType type, String mine, String name, double amount, TreeSet<Double> milestones) {
        super(plugin, type, name, "Break " + NumberFormat.getIntegerInstance().format(amount) + " blocks at the " + mine + " mine.", amount, type == ChallengeType.DAILY ? 5 : 30, milestones, Format.WHOLE);
        this.mine = mine;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()) == null) return;
        if (!PrisonMines.getAPI().getByLocation(event.getBlock().getLocation()).getName().equalsIgnoreCase(mine)) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data,1);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBlockExplode(TEBlockExplodeEvent event) {
        if (event.blockList().isEmpty()) return;
        if (PrisonMines.getAPI().getByLocation(event.blockList().get(0).getLocation()) == null) return;
        if (!PrisonMines.getAPI().getByLocation(event.blockList().get(0).getLocation()).getName().equalsIgnoreCase(mine)) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, countNotAir(event.blockList()) - 1);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onCubed(CubedEvent event) {
        if (event.getBlocks().isEmpty()) return;
        if (PrisonMines.getAPI().getByLocation(event.getBlocks().get(0).getLocation()) == null) return;
        if (!PrisonMines.getAPI().getByLocation(event.getBlocks().get(0).getLocation()).getName().equalsIgnoreCase(mine)) return;

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, event.getPlayer(), data, countNotAir(event.getBlocks()) - 1);
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
