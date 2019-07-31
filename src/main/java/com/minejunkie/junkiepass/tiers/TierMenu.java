package com.minejunkie.junkiepass.tiers;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.plugin.bukkit.menu.PlayerMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.itembuilder.ItemBuilder;
import org.inventivetalent.itembuilder.MetaBuilder;

import java.util.ArrayList;

public class TierMenu extends PlayerMenu {

    private JunkiePass plugin;

    private ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(org.bukkit.ChatColor.BLACK + "").item().build();
    private ItemStack nextPage = new ItemBuilder(Material.ARROW).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Next Page").item().build();
    private ItemStack previousPage = new ItemBuilder(Material.ARROW).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Previous Page").item().build();
    private ItemStack unlockedItem = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(13).buildMeta().withDisplayName(ChatColor.GREEN + "Unlocked").item().build();
    private ItemStack lockedItem = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(ChatColor.RED + "Locked").item().build();
    private ItemStack freeRewards = new ItemBuilder(Material.PAPER).withAmount(1).buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "FREE REWARDS v").item().build();
    private ItemStack paidRewards = new ItemBuilder(Material.PAPER).withAmount(1).buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "PAID REWARDS ^").item().build();

    private MetaBuilder unclaimedRewards = new ItemBuilder(Material.STORAGE_MINECART).buildMeta().withDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Claim Rewards");
    private ItemBuilder currentItem = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4);
    private ItemBuilder hasRewardItem = new ItemBuilder(Material.STORAGE_MINECART);
    private ItemBuilder noRewardItem = new ItemBuilder(Material.MINECART);

    public TierMenu(JunkiePass plugin) {
        super(plugin, 54, ChatColor.AQUA + ChatColor.BOLD.toString() + "Tiers Menu");
        this.plugin = plugin;

        ItemStack exit = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(org.bukkit.ChatColor.RED + org.bukkit.ChatColor.ITALIC.toString() + "Exit").item().build();
        ItemStack challenges = new ItemBuilder(Material.BOOK).buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.ITALIC.toString() + "Challenges Menu").item().build();

        addItem(
                exit,
                48,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                }
        );

        addItem(
                challenges,
                49,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getChallengesMenu().open(player);
                }
        );

        fill(fill);

    }

    @Override
    public void onOpen(Player player, Inventory inventory, Object... args) {
        JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        update(profile, inventory, 1);
    }

    public void update(JunkiePassProfile profile, Inventory inventory, int page) {
        Player player = Bukkit.getPlayer(profile.getUniqueId());
        getPersonalUpdates(player).clear();
        inventory.setItem(0, freeRewards);
        inventory.setItem(4, null);
        inventory.setItem(36, paidRewards);
        inventory.setItem(45, fill);
        inventory.setItem(50, fill);
        inventory.setItem(53, fill);

        if (profile.getClaimedTier() != profile.getJunkiePassTier()) {
            int claimedTier = profile.getClaimedTier();
            int currentTier = profile.getJunkiePassTier();

            ArrayList<String> rewards = new ArrayList<>();
            boolean hasUnclaimed = false;
            for (int i = (claimedTier + 1); i <= currentTier; i++) {
                Tier tier = TierConfig.getTiers()[i - 1];
                if (tier.hasFreeRewards() || (profile.isPaid() && tier.hasPaidRewards())) hasUnclaimed = true;
                tier.getFreeRewards().forEach(s -> rewards.add(ChatColor.GRAY + "- " + ChatColor.GOLD + s));
                if (profile.isPaid()) tier.getPaidRewards().forEach(s -> rewards.add(ChatColor.GRAY + "- " + ChatColor.GOLD + s));
            }

            rewards.add(" ");
            rewards.add(ChatColor.RED + ChatColor.BOLD.toString() + "WARNING");
            rewards.add(ChatColor.RED + "Make sure you have open inventory slots.");

            if (hasUnclaimed) {
                inventory.setItem(50, unclaimedRewards.withLore(rewards).item().build());
                getPersonalUpdates(player).add(e -> {
                    if (e.getSlot() == 50) {
                        if (plugin.getCommonUtils().getOpenInventorySlots(player) > 2)
                            Bukkit.dispatchCommand(player, "jp claim");
                        else
                            player.sendMessage(plugin.getPrefix() + ChatColor.RED + "Please open some inventory slots before claiming. You have been warned.");
                        update(profile, inventory, page);
                    }
                });
            }
        }

        ItemStack statusItem = new ItemBuilder(Material.EMERALD).buildMeta()
                .withDisplayName(ChatColor.GREEN + "JunkiePass Season 2")
                .withLore(
                        ChatColor.GRAY + (profile.isPaid() ? "Premium" : "Free") + " Junkie Pass",
                        ChatColor.GRAY + "Tier: " + ChatColor.AQUA + profile.getJunkiePassTier(),
                        ChatColor.GRAY + "EXP: " + ChatColor.AQUA + (profile.getJunkiePassExperience() % 10) + ChatColor.GRAY + "/" + ChatColor.GREEN + "10"
                ).item().build();

        inventory.setItem(4, statusItem);

        if (page > 1) {
            ItemStack previous = previousPage.clone();
            previous.setAmount(page - 1);
            inventory.setItem(45, previous);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 45) {
                    update(profile, inventory, page - 1);
                }
            });
        }

        if (page < 6) {
            ItemStack next = nextPage.clone();
            next.setAmount(page + 1);
            inventory.setItem(53, next);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 53) {
                    update(profile, inventory, page + 1);
                }
            });
        }

        int freeStart = 9, indicatorStart = 18, paidStart = 27;
        Tier[] tiers = TierConfig.getTiers();
        for (int i = (9 * (page - 1)) + 1; i <= (9 * page); i++) {
            inventory.setItem(freeStart++, i <= 50 ? getFreeRewardItem(tiers[i - 1]) : fill);
            inventory.setItem(indicatorStart++, i <= 50 ? getTierStatusItem(profile, tiers[i - 1].getTierLevel()) : fill);
            inventory.setItem(paidStart++, i <= 50 ? getPaidRewardItem(tiers[i - 1]) : fill);
        }
    }

    public ItemStack getTierStatusItem(JunkiePassProfile profile, int tierLevel) {
        if (profile.getJunkiePassTier() >= tierLevel) return unlockedItem;
        if (profile.getJunkiePassTier() + 1 >= tierLevel) return currentItem.buildMeta().withDisplayName(ChatColor.GRAY + String.valueOf((int) profile.getJunkiePassExperience() % 10) + ChatColor.GOLD + "/" + ChatColor.GRAY + "10" + ChatColor.GOLD + " ✪").item().build();
        else return lockedItem;
    }

    public ItemStack getFreeRewardItem(Tier tier) {
        if (tier.hasFreeRewards()) {
            ArrayList<String> newLore = new ArrayList<>();
            tier.getFreeRewards().forEach(s -> newLore.add(ChatColor.GRAY + "- " + ChatColor.GOLD + s));
            return hasRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).withLore(newLore).item().build();
        } else return noRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).item().build();
    }

    public ItemStack getPaidRewardItem(Tier tier) {
        if (tier.hasPaidRewards()) {
            ArrayList<String> newLore = new ArrayList<>();
            tier.getPaidRewards().forEach(s -> newLore.add(ChatColor.GRAY + "- " + ChatColor.GOLD + s));
            return hasRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).withLore(newLore).item().build();
        } else return noRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).item().build();
    }
}
