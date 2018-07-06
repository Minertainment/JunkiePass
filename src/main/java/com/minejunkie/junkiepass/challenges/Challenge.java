package com.minejunkie.junkiepass.challenges;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minejunkie.junkiepass.tiers.Tier;
import com.minejunkie.junkiepass.tiers.TierConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.UUID;

public abstract class Challenge implements Listener {

    private transient JunkiePass plugin;
    private ChallengeType type;
    private String name;
    private String description;
    private double amount;
    private double experience;
    private TreeSet<Double> milestones;
    private Format format;

    public Challenge(JunkiePass plugin, ChallengeType type, String name, String description, double amount, double experience) {
        this(plugin, type, name, description, amount, experience, new TreeSet<>(), Format.WHOLE);
    }

    public Challenge(JunkiePass plugin, ChallengeType type, String name, String description, double amount, double experience, Format format) {
        this(plugin, type, name, description, amount, experience, new TreeSet<>(), format);
    }

    public Challenge(JunkiePass plugin, ChallengeType type, String name, String description, double amount, double experience, TreeSet<Double> milestones, Format format) {
        this.plugin = plugin;
        this.type = type;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.experience = experience;
        this.milestones = milestones;
        this.format = format;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ChallengeData getChallengeData(JunkiePassProfile profile) {
        ChallengeData data = null;
        if (getType() == ChallengeType.DAILY) {
            if (profile.getDailyChallenges().containsKey(this.getClass())) data = profile.getDailyChallenges().get(this.getClass());
        } else {
            if (profile.getPaidChallenges().containsKey(this.getClass())) data = profile.getPaidChallenges().get(this.getClass());
        }
        return data;
    }

    public void increment(JunkiePassProfile profile, Player player, ChallengeData data, double amount) {
        if(!milestones.isEmpty() && data.getAmount() + amount < this.amount) {
            Iterator<Double> iter = milestones.descendingIterator();
            while (iter.hasNext()) {
                double milestone = iter.next();
                if(data.getAmount() < milestone && data.getAmount() + amount >= milestone) {
                    data.complete(amount);
                    onMilestone(player, data);
                    return;
                }
            }
        }

        if (data.complete(amount) >= this.amount)
            onComplete(profile, player);
    }

    // Can be overridden if the double needs to be more precise.
    public void onMilestone(Player player, ChallengeData data) {
        player.sendMessage(
                String.format(getPlugin().getPrefix() + ChatColor.GOLD + ChatColor.BOLD.toString() + name + ": " + ChatColor.GREEN + "Milestone Achieved " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "%s" + ChatColor.DARK_GRAY + "/" + ChatColor.GOLD + "%s" + ChatColor.DARK_GRAY + "]", String.valueOf((int) Math.round(data.getAmount())), getAmountString())
        );
    }

    public void onComplete(JunkiePassProfile profile, Player player) {
        profile.addJunkiePassExperience(experience);
        if (type == ChallengeType.DAILY) profile.removeDailyChallenge(this.getClass());
        else profile.getPaidChallenges().get(this.getClass()).setComplete(true);
        player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have completed the " + ChatColor.GOLD + ChatColor.BOLD.toString() + name + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + ChatColor.BOLD.toString() + "+" + experience + " XP" + ChatColor.DARK_GRAY + "]");

        int oldTier = profile.getJunkiePassTier();
        if (oldTier < 50) profile.updateJunkiePassTier();
        else return;

        if (oldTier != profile.getJunkiePassTier()) {
            int newTier = profile.getJunkiePassTier();

            boolean hasUnclaimed = false;
            for (int i = (oldTier + 1); i <= newTier; i++) {
                player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have reached" + ChatColor.GOLD + ChatColor.BOLD.toString() + " Tier " + i + ".");
                Tier tier = TierConfig.getTiers()[i-1];
                if (tier.hasFreeRewards() || (tier.hasPaidRewards() && profile.isPaid())) hasUnclaimed = true;
            }

            if (hasUnclaimed) player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have unclaimed rewards. Claim them with " + ChatColor.GOLD + "/jp claim" + ChatColor.GRAY + ".");
        }
    }

    public JunkiePass getPlugin() {
        return plugin;
    }

    public ChallengeType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Number getAmount() {
        if (format == Format.WHOLE) return ((int) amount);
        if (format == Format.DECIMAL) return amount;
        return amount;
    }

    public String getAmountString() {
        if (format == Format.WHOLE) return String.valueOf(getAmount());
        if (format == Format.DECIMAL) return String.format("%.2f", amount);
        return String.valueOf(amount);
    }

    public double getExperience() {
        return experience;
    }

    public TreeSet<Double> getMilestones() {
        return milestones;
    }

    public JunkiePassProfile getProfile(UUID uuid) {
        return getPlugin().getProfileManager().getProfile(uuid);
    }

    public Format getFormat() {
        return format;
    }

    public enum Format {
        WHOLE,
        DECIMAL
    }
}
