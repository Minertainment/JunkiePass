package com.minejunkie.junkiepass.challenges;

public class ChallengeData {

    private double amount;

    public ChallengeData(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public double complete(double amount) {
        return this.amount += amount;
    }

    public double completeOne() {
        return ++amount;
    }
}
