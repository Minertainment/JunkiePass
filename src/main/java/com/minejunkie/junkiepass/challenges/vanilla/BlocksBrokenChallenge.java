package com.minejunkie.junkiepass.challenges.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class BlocksBrokenChallenge extends Challenge<BlockBreakEvent> {

    public BlocksBrokenChallenge(JunkiePass plugin) {
        super(plugin, 10000, 5, (ArrayList<Double>) Arrays.asList(2500.00, 5000.00, 7500.00));
    }

    @Override
    @EventHandler
    public void onIncrement(BlockBreakEvent event) {
        // TODO Check if inside of a mine (PrisonMines API)

        JunkiePassProfile profile = getProfile(event.getPlayer().getUniqueId());
        if (profile.getChallenges().containsKey(this.getClass())) {
            ChallengeData data = profile.getChallenges().get(this.getClass());

            if (data.completeOne() == getAmount()) {

                event.getPlayer().sendMessage(
                        getPlugin().getPrefix() + ChatColor.GOLD + "You have mined %f" + ChatColor.GRAY + "/" + ChatColor.GOLD + "%f" + " blocks."
                );

                onComplete(profile, event.getPlayer().getUniqueId());
                return;
            }

            if (getMilestones().contains(data.getAmount())) {
                event.getPlayer().sendMessage(
                        String.format(getPlugin().getPrefix() + ChatColor.GOLD + "You have mined %f" + ChatColor.GRAY + "/" + ChatColor.GOLD + "%f" + " blocks.", data.getAmount(), getAmount())
                );
            }
        }
    }
}
