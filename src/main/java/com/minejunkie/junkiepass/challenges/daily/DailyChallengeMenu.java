package com.minejunkie.junkiepass.challenges.daily;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.plugin.bukkit.menu.PlayerMenu;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.itembuilder.ItemBuilder;
import org.inventivetalent.itembuilder.MetaBuilder;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public class DailyChallengeMenu extends PlayerMenu {

    private JunkiePass plugin;
    private LocalTime midnight = LocalTime.MAX;
    private MetaBuilder noChallenges = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(ChatColor.RED + "No available challenges.");
    private ItemBuilder challengeItem = new ItemBuilder(Material.STAINED_GLASS_PANE);

    public DailyChallengeMenu(JunkiePass plugin) {
        super(plugin, 36, ChatColor.GOLD + "Daily Challenges");
        this.plugin = plugin;

        ItemStack backToJunkiePassMenu = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4).buildMeta().withDisplayName(org.bukkit.ChatColor.YELLOW + org.bukkit.ChatColor.ITALIC.toString() + "Back to Junkie Pass").item().build();
        ItemStack exit = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(org.bukkit.ChatColor.RED + org.bukkit.ChatColor.ITALIC.toString() + "Exit").item().build();
        ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(org.bukkit.ChatColor.BLACK + "").item().build();

        addItem(
                backToJunkiePassMenu,
                27,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getTierMenu().open(player);
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

        addUpdater((player, inventory) -> {
            JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            if (profile.getDailyChallenges().isEmpty()) {
                Duration duration = Duration.between(LocalTime.now(), midnight);
                String remaining = DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss");
                inventory.setItem(13, noChallenges.withLore(Arrays.asList(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Next available:", ChatColor.GOLD + remaining)).item().build());
            }
        }, 20L);

        fill(fill);
    }

    @Override
    public void onOpen(Player player, Inventory inventory, Object... args) {
        JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        update(profile, inventory);
    }

    private void update(JunkiePassProfile profile, Inventory inventory) {
        if (!profile.getDailyChallenges().isEmpty()) {
            int[] slots = new int[] { profile.getDailyChallenges().size() == 1 ? 13 : 11, 15};
            int counter = 0;
            for (Class clazz : profile.getDailyChallenges().keySet()) {
                Challenge challenge = plugin.getChallengeManager().getAllChallengesMap().get(clazz);
                ChallengeData data = profile.getDailyChallenges().get(clazz);
                inventory.setItem(slots[counter],
                        challengeItem.withDurability(data.getAmount() == 0 ? 1 : 4)
                                .buildMeta().withDisplayName(ChatColor.GOLD + challenge.getName() + ChatColor.DARK_GRAY + " [" + ChatColor.GRAY + String.valueOf((int) challenge.getExperience()) + ChatColor.GOLD + " ✪" + ChatColor.DARK_GRAY + "]")
                                .withLore(Arrays.asList(
                                        ChatColor.GRAY + challenge.getDescription(),
                                        ChatColor.GOLD + (data == null ? "0" : String.valueOf(challenge.getFormat() == Challenge.Format.WHOLE ? (int) data.getAmount() : data.getAmount())) + ChatColor.GRAY + "/" + ChatColor.GOLD + challenge.getAmountString())
                                ).item().build()
                );
                counter++;
            }
        } else {
            Duration duration = Duration.between(LocalTime.now(), midnight);
            String remaining = DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss");
            inventory.setItem(13, noChallenges.withLore(Arrays.asList(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Next available:", ChatColor.GOLD + remaining)).item().build());
        }
    }
}
