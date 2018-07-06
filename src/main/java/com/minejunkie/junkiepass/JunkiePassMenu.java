package com.minejunkie.junkiepass;

import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minejunkie.junkiepass.tiers.Tier;
import com.minejunkie.junkiepass.tiers.TierConfig;
import com.minertainment.athena.menu.PlayerMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.itembuilder.ItemBuilder;
import org.inventivetalent.itembuilder.MetaBuilder;

import java.util.ArrayList;

public class JunkiePassMenu extends PlayerMenu {

    private JunkiePass plugin;
    private MetaBuilder unclaimedRewards = new ItemBuilder(Material.STORAGE_MINECART).buildMeta().withDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Claim Rewards");
    private ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(ChatColor.BLACK + "").item().build();

    public JunkiePassMenu(JunkiePass plugin) {
        super(plugin, 36, ChatColor.GOLD + ChatColor.BOLD.toString() + "Junkie Pass");
        this.plugin = plugin;

        addItem(
                new ItemBuilder(Material.BOOK).buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Challenges").item().build(),
                11,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getChallengesMenu().open(player);
                }
        );

        addItem(
                new ItemBuilder(Material.NETHER_STAR).buildMeta().withDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "Tiers").item().build(),
                15,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getTierMenu().open(player);
                }
        );

        addItem(
                new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(ChatColor.RED + ChatColor.ITALIC.toString() + "Exit").item().build(),
                31,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                }
        );

        fill(fill);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        update(profile, inventory);
    }

    public void update(JunkiePassProfile profile, Inventory inventory) {
        inventory.setItem(13, fill);
        getPersonalUpdates(profile.getPlayer()).clear();
        if (profile.getClaimedTier() == profile.getJunkiePassTier()) return;
        int claimedTier = profile.getClaimedTier();
        int currentTier = profile.getJunkiePassTier();

        ArrayList<String> rewards = new ArrayList<>();
        boolean hasUnclaimed = false;
        for (int i = (claimedTier + 1); i <= currentTier; i++) {
            Tier tier = TierConfig.getTiers()[i-1];
            if (tier.hasFreeRewards() || (profile.isPaid() && tier.hasPaidRewards())) hasUnclaimed = true;
            tier.getFreeRewards().forEach(s -> rewards.add(ChatColor.GRAY + "- " + ChatColor.GOLD + s));
            tier.getPaidRewards().forEach(s -> rewards.add(ChatColor.GRAY + "- " + ChatColor.GOLD + s));
        }

        rewards.add(" ");
        rewards.add(ChatColor.RED + ChatColor.BOLD.toString() + "WARNING");
        rewards.add(ChatColor.RED + "Make sure you have open inventory slots.");

        if (hasUnclaimed) {
            inventory.setItem(13, unclaimedRewards.withLore(rewards).item().build());
            getPersonalUpdates(profile.getPlayer()).add(e -> {
                if (e.getSlot() == 13) {
                    if (plugin.getCommonUtils().getOpenInventorySlots(profile.getPlayer()) > 2)
                        Bukkit.dispatchCommand(profile.getPlayer(), "jp claim");
                    else profile.getPlayer().sendMessage(plugin.getPrefix() + ChatColor.RED + "Please open some inventory slots before claiming. You have been warned.");
                    update(profile, inventory);
                }
            });
        }
    }
}
