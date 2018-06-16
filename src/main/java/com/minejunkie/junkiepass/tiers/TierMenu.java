package com.minejunkie.junkiepass.tiers;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.menu.PlayerMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.itembuilder.ItemBuilder;

import java.util.ArrayList;

public class TierMenu extends PlayerMenu {

    private JunkiePass plugin;

    private ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(org.bukkit.ChatColor.BLACK + "").item().build();
    private ItemStack nextPage = new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Next Page").item().build();
    private ItemStack previousPage = new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Previous Page").item().build();
    private ItemStack unlockedItem = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(13).buildMeta().withDisplayName(ChatColor.GREEN + "Unlocked").item().build();
    private ItemStack lockedItem = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(ChatColor.RED + "Locked").item().build();

    private ItemBuilder currentItem = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4);
    private ItemBuilder hasRewardItem = new ItemBuilder(Material.STORAGE_MINECART);
    private ItemBuilder noRewardItem = new ItemBuilder(Material.MINECART);

    public TierMenu(JunkiePass plugin) {
        super(plugin, 54, ChatColor.AQUA + ChatColor.BOLD.toString() + "Tiers Menu");
        this.plugin = plugin;

        ItemStack backToJunkiePassMenu = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4).buildMeta().withDisplayName(org.bukkit.ChatColor.YELLOW + org.bukkit.ChatColor.ITALIC.toString() + "Back to Junkie Pass").item().build();
        ItemStack exit = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(org.bukkit.ChatColor.RED + org.bukkit.ChatColor.ITALIC.toString() + "Exit").item().build();

        addItem(
                backToJunkiePassMenu,
                45,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getJunkiePassMenu().open(player);
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
    public void onOpen(Player player, Inventory inventory) {
        JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        update(profile, inventory, 1);
    }

    public void update(JunkiePassProfile profile, Inventory inventory, int page) {
        Player player = profile.getPlayer();
        getPersonalUpdates(player).clear();
        inventory.setItem(51, null);
        inventory.setItem(47, null);

        if (page > 1) {
            ItemStack previous = previousPage.clone();
            previous.setAmount(page - 1);
            inventory.setItem(47, previous);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 47) {
                    update(profile, inventory, page - 1);
                }
            });
        }

        if (page < 6) {
            ItemStack next = nextPage.clone();
            next.setAmount(page + 1);
            inventory.setItem(51, next);
            getPersonalUpdates(player).add(e -> {
                if (e.getSlot() == 51) {
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
