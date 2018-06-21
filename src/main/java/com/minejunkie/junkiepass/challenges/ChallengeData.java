package com.minejunkie.junkiepass.challenges;

public class ChallengeData {

    private double amount;

    private boolean isComplete;

    public ChallengeData() {
        this(0);
    }

    public ChallengeData(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public double complete(double amount) {
        return this.amount += amount;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

}
