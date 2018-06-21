package com.minejunkie.junkiepass.challenges.paid;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.menu.PlayerMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.itembuilder.ItemBuilder;

import java.util.Arrays;

public class PaidChallengeMenu extends PlayerMenu {

    private JunkiePass plugin;

    private ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(org.bukkit.ChatColor.BLACK + "").item().build();
    private ItemStack nextPage = new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Next Page").item().build();
    private ItemStack previousPage = new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Previous Page").item().build();
    private ItemBuilder challengeIndicator = new ItemBuilder(Material.STAINED_GLASS_PANE);

    public PaidChallengeMenu(JunkiePass plugin) {
        super(plugin, 36, ChatColor.GOLD + "Premium Challenges");
        this.plugin = plugin;

        ItemStack backToJunkiePassMenu = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4).buildMeta().withDisplayName(org.bukkit.ChatColor.YELLOW + org.bukkit.ChatColor.ITALIC.toString() + "Back to Junkie Pass").item().build();
        ItemStack exit = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(org.bukkit.ChatColor.RED + org.bukkit.ChatColor.ITALIC.toString() + "Exit").item().build();

        addItem(
                backToJunkiePassMenu,
                27,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getJunkiePassMenu().open(player);
                }
        );

        addItem(
                exit,
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
        update(profile, inventory, 1);
    }

    public void update(JunkiePassProfile profile, Inventory inventory, int page) {
        Player player = profile.getPlayer();
        getPersonalUpdates(player).clear();
        inventory.setItem(29, null);
        inventory.setItem(33, null);

        if (!profile.isPaid()) {
            inventory.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .withDurability(14).buildMeta()
                    .withDisplayName(ChatColor.RED + "No access!")
                    .withLore(Arrays.asList(ChatColor.GRAY + "Purchase a Junkie Pass @", ChatColor.GOLD + "store.minejunkie.com"))
                    .item().build()
            );

            return;
        }

        int challenges = plugin.getChallengeManager().getPaidChallenges().size();

        if (page > 1) {
            ItemStack previous;
            previous = previousPage.clone();
            previous.setAmount(page - 1);
            inventory.setItem(29, previous);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 29) {
                    update(profile, inventory, page - 1);
                }
            });
        }

        if (page < Math.ceil(challenges / 5D)) {
            ItemStack next;
            next = nextPage.clone();
            next.setAmount(page + 1);
            inventory.setItem(33, next);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 33) {
                    update(profile, inventory, page + 1);
                }
            });
        }

        int challengesLeft = challenges - ((page - 1) * 5);

        int start = 9;
        for (int i = 0; i < 5; i++) {
            if (challengesLeft == 0) {
                inventory.setItem(start, fill);
                start += 2;
                continue;
            }

            int durability;
            Challenge challenge = plugin.getChallengeManager().getPaidChallenges().get(challenges - challengesLeft);
            ChallengeData data = null;

            if (challenge == null || profile.getPaidChallenges().get(challenge.getClass()) == null) {
                plugin.getJunkiePassLogger().warn("Challenge was null from " + player.getName() + "'s profile.");
                durability = 14;
            } else {
                data = profile.getPaidChallenges().get(challenge.getClass());
                if (data.getAmount() == 0) durability = 1;
                else if (data.isComplete()) durability = 5;
                else durability = 4;
            }

            inventory.setItem(
                    start,
                    challengeIndicator.withDurability(durability)
                            .buildMeta().withDisplayName(ChatColor.GOLD + challenge.getName())
                            .withLore(Arrays.asList(
                                        ChatColor.GRAY + String.valueOf((int) challenge.getExperience()) + ChatColor.GOLD + " ✪",
                                        ChatColor.GOLD + (data == null ? "0" : String.valueOf(data.getAmount())) + ChatColor.GRAY + "/" + ChatColor.GOLD + challenge.getAmount()
                                    )
                            ).item().build()
            );

            challengesLeft--;
            start += 2;
            fill(fill);
        }
    }
}
