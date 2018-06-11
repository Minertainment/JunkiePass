package com.minejunkie.junkiepass.challenges;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChallengeManager {

    private JunkiePass plugin;
    private ArrayList<Challenge> challenges;

    public ChallengeManager(JunkiePass plugin) {
        this.plugin = plugin;
        challenges = new ArrayList<>();
    }

    public void registerChallenge(Challenge challenge) {
        challenges.add(challenge);
    }

    public void registerChallenges(Challenge... challenge) {
        challenges.addAll(Arrays.asList(challenge));
    }

    public String getChallengesString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < challenges.size(); i++) {
            String className = challenges.get(i).getClass().getName();
            sb.append(className.substring(className.lastIndexOf(".") + 1));
            if (i != challenges.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public Challenge containsChallenge(String challengeName) {
        for (Challenge challenge : challenges) {
            String className = challenge.getClass().getName();
            if (className.substring(className.lastIndexOf(".") + 1).equalsIgnoreCase(challengeName)) return challenge;
        }
        return null;
    }

    public Challenge getRandom() {
        Random random = plugin.getRandom();
        int randInt = random.nextInt(challenges.size());
        return challenges.get(randInt);
    }

    public boolean addChallenge(JunkiePassProfile profile, Class clazz) {
        if (profile.getChallenges().size() >= 2 || (!profile.getChallenges().isEmpty() && profile.getChallenges().containsKey(clazz))) return false;
        profile.getChallenges().put(clazz, new ChallengeData());
        return true;
    }

    public Challenge addRandomChallenge(JunkiePassProfile profile) {
        if (profile.getChallenges().size() >= 2) return null;

        Challenge challenge = null;
        while (challenge == null || profile.getChallenges().containsKey(challenge.getClass())) {
            challenge = getRandom();
        }

        profile.getChallenges().put(challenge.getClass(), new ChallengeData());
        return challenge;
    }
}
