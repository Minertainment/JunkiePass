package com.minejunkie.junkiepass.challenges;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Challenge<T extends Event> implements Listener {

    private JunkiePass plugin;
    private double amount;
    private double experience;
    private ArrayList<Double> milestones;

    public Challenge(JunkiePass plugin, double amount, double experience) {
        this(plugin, amount, experience, new ArrayList<>());
    }

    public Challenge(JunkiePass plugin, double amount, double experience, ArrayList<Double> milestones) {
        this.plugin = plugin;
        this.amount = amount;
        this.experience = experience;
        this.milestones = milestones;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public abstract void onIncrement(T event);

    public void onComplete(JunkiePassProfile profile, UUID uuid) {
        profile.addJunkiePassExperience(getExperience());
        profile.removeChallenge(this.getClass());
    }

    public JunkiePass getPlugin() {
        return plugin;
    }

    public double getAmount() {
        return amount;
    }

    public double getExperience() {
        return experience;
    }

    public ArrayList<Double> getMilestones() {
        return milestones;
    }

    public JunkiePassProfile getProfile(UUID uuid) {
        return getPlugin().getProfileManager().getProfile(uuid);
    }
}
