package com.minejunkie.junkiepass.challenges.paid;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.plugin.bukkit.menu.PlayerMenu;
import org.bukkit.Bukkit;
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
        super(plugin, 54, ChatColor.GOLD + "Premium Challenges");
        this.plugin = plugin;

        ItemStack backToJunkiePassMenu = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4).buildMeta().withDisplayName(org.bukkit.ChatColor.YELLOW + org.bukkit.ChatColor.ITALIC.toString() + "Back to Junkie Pass").item().build();
        ItemStack exit = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(org.bukkit.ChatColor.RED + org.bukkit.ChatColor.ITALIC.toString() + "Exit").item().build();

        addItem(
                backToJunkiePassMenu,
                45,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getTierMenu().open(player);
                }
        );

        addItem(
                exit,
                49,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
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
        inventory.setItem(47, null);
        inventory.setItem(51, null);

        int challenges = plugin.getChallengeManager().getPaidChallenges().size();

        if (page > 1) {
            ItemStack previous;
            previous = previousPage.clone();
            previous.setAmount(page - 1);
            inventory.setItem(47, previous);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 47) {
                    update(profile, inventory, page - 1);
                }
            });
        }

        if (page < Math.ceil(challenges / 8D)) {
            ItemStack next;
            next = nextPage.clone();
            next.setAmount(page + 1);
            inventory.setItem(51, next);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 51) {
                    update(profile, inventory, page + 1);
                }
            });
        }

        int challengesLeft = challenges - ((page - 1) * 8);

        int start = 10;
        for (int i = 0; i < 8; i++) {
            if (challengesLeft == 0) {
                inventory.setItem(start, fill);
                if (i != 3) start += 2;
                else start += 12;
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
                            .buildMeta().withDisplayName(ChatColor.GOLD + challenge.getName() + ChatColor.DARK_GRAY + " [" + ChatColor.GRAY + String.valueOf((int) challenge.getExperience()) + ChatColor.GOLD + " ✪" + ChatColor.DARK_GRAY + "]")
                            .withLore(Arrays.asList(
                                        ChatColor.GRAY + challenge.getDescription(),
                                        ChatColor.GOLD + (data == null ? "0" : String.valueOf((int) data.getAmount())) + ChatColor.GRAY + "/" + ChatColor.GOLD + challenge.getAmountString()
                                    )
                            ).item().build()
            );

            challengesLeft--;
            if (i != 3) start += 2;
            else start += 12;
            fill(fill);
        }
    }
}
