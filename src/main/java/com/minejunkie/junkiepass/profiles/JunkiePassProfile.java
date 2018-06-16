package com.minejunkie.junkiepass.profiles;

import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minertainment.athena.profile.Profile;

import java.util.HashMap;
import java.util.UUID;

public class JunkiePassProfile extends Profile {

    // TODO weeklyChallenges

    private HashMap<Class, ChallengeData> challenges = new HashMap<>();
    private double junkiePassExperience = 10;
    private int junkiePassTier = 1;
    private boolean isPaid;

    public JunkiePassProfile(UUID uuid) {
        super(uuid);
    }

    public HashMap<Class, ChallengeData> getChallenges() {
        return challenges;
    }

    public void removeChallenge(Class challenge) {
        challenges.remove(challenge);
    }

    public void addChallenge(Class challenge) {
        challenges.put(challenge, new ChallengeData(0));
    }

    public double getJunkiePassExperience() {
        return junkiePassExperience;
    }

    public void setJunkiePassExperience(double junkiePassExperience) {
        this.junkiePassExperience = junkiePassExperience;
    }

    public void addJunkiePassExperience(double junkiePassExperience) {
        this.junkiePassExperience += junkiePassExperience;
    }

    public void updateJunkiePassTier() {
        junkiePassTier = (int) junkiePassExperience / 10;
    }

    public int getJunkiePassTier() {
        return junkiePassTier;
    }

    public void setJunkiePassTier(int junkiePassTier) {
        this.junkiePassTier = junkiePassTier;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
