package com.minejunkie.junkiepass.tiers;

import java.util.ArrayList;

public class Tier {

    private int tierLevel;
    private boolean hasFreeRewards;
    private boolean hasPaidRewards;
    private ArrayList<String> freeRewards;
    private ArrayList<String> freeRewardCommands;
    private ArrayList<String> paidRewards;
    private ArrayList<String> paidRewardCommands;

    public Tier(int tierLevel, boolean hasFreeRewards, boolean hasPaidRewards) {
        this(tierLevel, hasFreeRewards, hasPaidRewards, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Tier(int tierLevel, boolean hasFreeRewards, boolean hasPaidRewards, ArrayList<String> freeRewards, ArrayList<String> freeRewardCommands, ArrayList<String> paidRewards, ArrayList<String> paidRewardCommands) {
        this.tierLevel = tierLevel;
        this.hasFreeRewards = hasFreeRewards;
        this.hasPaidRewards = hasPaidRewards;
        this.freeRewards = freeRewards;
        this.freeRewardCommands = freeRewardCommands;
        this.paidRewards = paidRewards;
        this.paidRewardCommands = paidRewardCommands;
    }

    public Tier addFreeReward(String freeReward, String freeRewardCommand) {
        freeRewards.add(freeReward);
        freeRewardCommands.add(freeRewardCommand);
        return this;
    }

    public Tier addPaidReward(String paidReward, String paidRewardCommand) {
        paidRewards.add(paidReward);
        paidRewardCommands.add(paidRewardCommand);
        return this;
    }

    public int getTierLevel() {
        return tierLevel;
    }

    public boolean hasFreeRewards() {
        return hasFreeRewards;
    }

    public boolean hasPaidRewards() {
        return hasPaidRewards;
    }

    public ArrayList<String> getFreeRewards() {
        return freeRewards;
    }

    public ArrayList<String> getFreeRewardCommands() {
        return freeRewardCommands;
    }

    public ArrayList<String> getPaidRewards() {
        return paidRewards;
    }

    public ArrayList<String> getPaidRewardCommands() {
        return paidRewardCommands;
    }
}
