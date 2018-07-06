package com.minejunkie.junkiepass.listeners;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Instant;
import java.util.Date;

public class PlayerListener implements Listener {

    private JunkiePass plugin;

    public PlayerListener(JunkiePass plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getProfileManager().isLoaded(player.getUniqueId())) return;
        JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        boolean newDaily = false;

        if (profile.getLastDaily() == 0) newDaily = true;
        long timeLastClaimed = profile.getLastDaily();
        Date lastClaimedDate = new Date(timeLastClaimed);
        if (lastClaimedDate.getDate() != Date.from(Instant.now()).getDate()) newDaily = true;

        if (!newDaily) return;
        plugin.getJunkiePassLogger().info(player.getName() + " needs a new challenge.");
        if (profile.getDailyChallenges().size() >= 2) return;
        plugin.getJunkiePassLogger().info(player.getName() + " has room for a new challenge.");

        Challenge challenge = plugin.getChallengeManager().addRandomDailyChallenge(profile);
        if (challenge != null)
            player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have been given the " + ChatColor.GOLD + ChatColor.BOLD.toString() + challenge.getName() + ".");
        profile.setLastDaily(System.currentTimeMillis());
        plugin.getJunkiePassLogger().info(player.getName() + " set last daily.");
    }
}
