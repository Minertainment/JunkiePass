package com.minejunkie.junkiepass.challenges.paid.jregrades;

import com.minejunkie.jregrade.events.RegradeEvent;
import com.minejunkie.jregrade.utils.RegradeUtils;
import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.TreeSet;

public class RegradeChallenge extends Challenge {

    private Enchantment enchantment;
    private ChallengeType type;

    public RegradeChallenge(JunkiePass plugin, Enchantment enchantment, ChallengeType type, double amount, double experience, TreeSet<Double> milestones) {
        super(plugin, type, "Regrade Challenge", "Successfully regrade " + (enchantment == Enchantment.DIG_SPEED ? "Efficiency" : "Fortune") + " on your pickaxe " + amount + " times.", amount, experience, milestones, Format.WHOLE);
        this.enchantment = enchantment;
        this.type = type;
    }

    @EventHandler
    public void onRegrade(RegradeEvent event) {
        Player player = Bukkit.getPlayer(event.getUuid());
        JunkiePassProfile profile = getProfile(player.getUniqueId());

        if (event.getEnchantment() != enchantment) return;
        if (event.getResult() != RegradeUtils.Result.SUCCESS) return;

        ChallengeData data;
        if ((data = getChallengeData(profile)) == null) return;
        if (!data.isComplete()) increment(profile, Bukkit.getPlayer(event.getUuid()), data, 1);
    }
}
