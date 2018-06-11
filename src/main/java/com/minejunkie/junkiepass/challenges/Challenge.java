package com.minejunkie.junkiepass.challenges;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Challenge implements Listener {

    private transient JunkiePass plugin;
    private String name;
    private double amount;
    private double experience;
    private List<Double> milestones;

    public Challenge(JunkiePass plugin, String name, double amount, double experience) {
        this(plugin, name, amount, experience, new ArrayList<>());
    }

    public Challenge(JunkiePass plugin, String name, double amount, double experience, ArrayList<Double> milestones) {
        this.plugin = plugin;
        this.name = name;
        this.amount = amount;
        this.experience = experience;
        this.milestones = milestones;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void increment(JunkiePassProfile profile, Player player, ChallengeData data, double amount) {
        if (data.complete(amount) >= this.amount) {
            onComplete(profile, player);
            return;
        }

        if (milestones.isEmpty()) return;

        if (milestones.contains(data.getAmount())) {
            onMilestone(player, data);
        }
    }

    // Can be overridden if the double needs to be more precise.
    public void onMilestone(Player player, ChallengeData data) {
        player.sendMessage(
                String.format(getPlugin().getPrefix() + ChatColor.GOLD + ChatColor.BOLD.toString() + name + ": " + ChatColor.GREEN + "Milestone Achieved " + ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "%d" + ChatColor.DARK_GRAY + "/" + ChatColor.GOLD + "%d" + ChatColor.DARK_GRAY + "]", (int) Math.round(data.getAmount()), (int) Math.round(getAmount()))
        );
    }

    public void onComplete(JunkiePassProfile profile, Player player) {
        profile.addJunkiePassExperience(experience);
        profile.removeChallenge(this.getClass());
        player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have completed the " + ChatColor.GOLD + ChatColor.BOLD.toString() + name + "! " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + ChatColor.BOLD.toString() + "+" + experience + " XP" + ChatColor.DARK_GRAY + "]");
    }

    public JunkiePass getPlugin() {
        return plugin;
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

    public List<Double> getMilestones() {
        return milestones;
    }

    public JunkiePassProfile getProfile(UUID uuid) {
        return getPlugin().getProfileManager().getProfile(uuid);
    }

}
