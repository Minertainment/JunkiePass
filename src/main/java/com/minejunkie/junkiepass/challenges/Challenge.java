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
    private double amount;
    private double experience;
    private TreeSet<Double> milestones;

    public Challenge(JunkiePass plugin, ChallengeType type, String name, double amount, double experience) {
        this(plugin, type, name, amount, experience, new TreeSet<>());
    }

    public Challenge(JunkiePass plugin, ChallengeType type, String name, double amount, double experience, TreeSet<Double> milestones) {
        this.plugin = plugin;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.experience = experience;
        this.milestones = milestones;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
                String.format(getPlugin().getPrefix() + ChatColor.GOLD + ChatColor.BOLD.toString() + name + ": " + ChatColor.GREEN + "Milestone Achieved " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "%d" + ChatColor.DARK_GRAY + "/" + ChatColor.GOLD + "%d" + ChatColor.DARK_GRAY + "]", (int) Math.round(data.getAmount()), (int) Math.round(getAmount()))
        );
    }

    public void onComplete(JunkiePassProfile profile, Player player) {
        profile.addJunkiePassExperience(experience);
        profile.removeDailyChallenge(this.getClass());
        player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have completed the " + ChatColor.GOLD + ChatColor.BOLD.toString() + name + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + ChatColor.BOLD.toString() + "+" + experience + " XP" + ChatColor.DARK_GRAY + "]");

        int oldTier = profile.getJunkiePassTier();
        profile.updateJunkiePassTier();

        // If it has changed...
        if (oldTier != profile.getJunkiePassTier()) {
            player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have reached" + ChatColor.GOLD + ChatColor.BOLD.toString() + " Tier " + profile.getJunkiePassTier() + ".");
            Tier tier = TierConfig.getTiers()[profile.getJunkiePassTier() - 1];

            // TODO Add event for when their experience is changed.
            // TODO Add a way to claim rewards if their inventory is full.
            boolean gifted = false;
            if (tier.hasFreeRewards() && !tier.getFreeRewards().isEmpty()) {
                for (String s : tier.getFreeRewards()) {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replaceAll("@p", player.getName()));
                }
                gifted = true;
            }

            if (profile.isPaid() || player.hasPermission("minejunkie.junkiepass")) {
                if (tier.hasPaidRewards() && !tier.getPaidRewards().isEmpty()) {
                    for (String s : tier.getPaidRewards()) {
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), s.replaceAll("@p", player.getName()));
                    }
                    gifted = true;
                }
            }

            if (gifted) player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "Your rewards have been gifted.");
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

    public double getAmount() {
        return amount;
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
}
