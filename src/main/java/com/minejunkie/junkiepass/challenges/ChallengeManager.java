package com.minejunkie.junkiepass.challenges;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChallengeManager {

    private JunkiePass plugin;
    private ArrayList<Challenge> dailyChallenges, weeklyChallenges, allChallenges;

    public ChallengeManager(JunkiePass plugin) {
        this.plugin = plugin;
        dailyChallenges = new ArrayList<>();
        weeklyChallenges = new ArrayList<>();
        allChallenges = new ArrayList<>();
    }

    public void registerChallenge(ChallengeType type, Challenge challenge) {
        if (type == ChallengeType.DAILY) dailyChallenges.add(challenge);
        if (type == ChallengeType.WEEKLY) weeklyChallenges.add(challenge);
        allChallenges.add(challenge);
    }

    public void registerChallenges(Challenge... challenges) {
        for (Challenge challenge : challenges) {
            if (challenge.getType() == ChallengeType.DAILY) dailyChallenges.add(challenge);
            else if (challenge.getType() == ChallengeType.WEEKLY) weeklyChallenges.add(challenge);
        }
        allChallenges.addAll(Arrays.asList(challenges));
        plugin.getJunkiePassLogger().info("Registered " + challenges.length + " challenges.");
    }

    public String getChallengesString(ChallengeType type) {
        ArrayList<Challenge> challenges;
        if (type == ChallengeType.DAILY) challenges = dailyChallenges;
        else if (type == ChallengeType.WEEKLY) challenges = weeklyChallenges;
        else challenges = allChallenges;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < challenges.size(); i++) {
            String className = challenges.get(i).getClass().getName();
            sb.append(className.substring(className.lastIndexOf(".") + 1));
            if (i != challenges.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public Challenge containsChallenge(ChallengeType type, String challengeName) {
        ArrayList<Challenge> challenges;
        if (type == ChallengeType.DAILY) challenges = dailyChallenges;
        else if (type == ChallengeType.WEEKLY) challenges = weeklyChallenges;
        else challenges = allChallenges;

        for (Challenge challenge : challenges) {
            String className = challenge.getClass().getName();
            if (className.substring(className.lastIndexOf(".") + 1).equalsIgnoreCase(challengeName)) return challenge;
        }
        return null;
    }

    public Challenge getRandom(ChallengeType type) {
        ArrayList<Challenge> challenges;
        if (type == ChallengeType.DAILY) challenges = dailyChallenges;
        else if (type == ChallengeType.WEEKLY) challenges = weeklyChallenges;
        else challenges = allChallenges;


        Random random = plugin.getRandom();
        int randInt = random.nextInt(challenges.size());
        return challenges.get(randInt);
    }

    public boolean addChallenge(JunkiePassProfile profile, Class clazz) {
        if (profile.getChallenges().size() >= 2 || (!profile.getChallenges().isEmpty() && profile.getChallenges().containsKey(clazz))) return false;
        profile.getChallenges().put(clazz, new ChallengeData());
        return true;
    }

    public Challenge addRandomDailyChallenge(JunkiePassProfile profile) {
        if (profile.getChallenges().size() >= 2) return null;

        Challenge challenge = null;
        while (challenge == null || profile.getChallenges().containsKey(challenge.getClass())) {
            challenge = getRandom(ChallengeType.DAILY);
        }

        profile.getChallenges().put(challenge.getClass(), new ChallengeData());
        return challenge;
    }

    public ArrayList<Challenge> getDailyChallenges() {
        return dailyChallenges;
    }

    public ArrayList<Challenge> getWeeklyChallenges() {
        return weeklyChallenges;
    }

    public ArrayList<Challenge> getAllChallenges() {
        return allChallenges;
    }
}
