package com.minejunkie.junkiepass.utils;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;

import java.util.TreeSet;

public class CommonUtils {

    private JunkiePass plugin;

    public CommonUtils(JunkiePass plugin) {
        this.plugin = plugin;
    }

    public TreeSet<Double> generateMilestones(double amount, double interval) {
        if (interval > amount) return new TreeSet<>();
        if (amount % interval > 0) return new TreeSet<>();
        TreeSet<Double> treeSet = new TreeSet<>();
        for (double i = interval; i < amount; i += interval) {
            treeSet.add(i);
        }
        return treeSet;
    }

    public boolean redeemJunkiePass(JunkiePassProfile profile) {
        if (profile.isPaid()) return false;
        profile.setPaid(true);
        plugin.getChallengeManager().getPaidChallenges().forEach((challenge -> profile.addPaidChallenge(challenge.getClass())));
        return true;
    }

}
