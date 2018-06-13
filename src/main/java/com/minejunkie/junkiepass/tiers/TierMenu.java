package com.minejunkie.junkiepass.tiers;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.menu.PlayerMenu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.itembuilder.ItemBuilder;

public class TierMenu extends PlayerMenu {

    private JunkiePass plugin;
    private ItemStack backToJunkiePassMenu, exit, fill, nextPage, previousPage;
    private ItemBuilder hasRewardItem = new ItemBuilder(Material.STORAGE_MINECART);
    private ItemBuilder noRewardItem = new ItemBuilder(Material.MINECART);

    public TierMenu(JunkiePass plugin) {
        super(plugin, 54, ChatColor.AQUA + ChatColor.BOLD.toString() + "Tiers Menu");
        this.plugin = plugin;

        backToJunkiePassMenu = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4).buildMeta().withDisplayName(org.bukkit.ChatColor.YELLOW + org.bukkit.ChatColor.ITALIC.toString() + "Back to Junkie Pass").item().build();
        exit = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(14).buildMeta().withDisplayName(org.bukkit.ChatColor.RED + org.bukkit.ChatColor.ITALIC.toString() + "Exit").item().build();
        fill = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(org.bukkit.ChatColor.BLACK + "").item().build();
        nextPage = new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Next Page").item().build();
        previousPage = new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GREEN + ChatColor.ITALIC.toString() + "Previous Page").item().build();

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

        int freeStart = 9, paidStart = 27;
        Tier[] tiers = TierConfig.getTiers();
        for (int i = (9 * (page - 1)) + 1; i <= (9 * page); i++) {
            inventory.setItem(freeStart++, getFreeRewardItem(tiers[i - 1]));
            inventory.setItem(paidStart++, getPaidRewardItem(tiers[i - 1]));
        }
    }

    public ItemStack getFreeRewardItem(Tier tier) {
        if (tier.hasFreeRewards()) {
            return hasRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).item().build();
        } else return noRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).item().build();
    }

    public ItemStack getPaidRewardItem(Tier tier) {
        if (tier.hasPaidRewards()) {
            return hasRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).item().build();
        } else return noRewardItem.buildMeta().withDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Tier " + tier.getTierLevel()).item().build();
    }
}
