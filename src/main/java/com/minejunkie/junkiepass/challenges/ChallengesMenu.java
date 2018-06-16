package com.minejunkie.junkiepass.challenges;

import com.minejunkie.junkiepass.JunkiePass;
import com.minertainment.athena.menu.StaticMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.inventivetalent.itembuilder.ItemBuilder;

public class ChallengesMenu extends StaticMenu {

    private JunkiePass plugin;

    public ChallengesMenu(JunkiePass plugin) {
        super(plugin, 36, ChatColor.GOLD + ChatColor.BOLD.toString() + "Challenges Menu");
        this.plugin = plugin;

        addItem(
                new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GOLD + "Daily Challenges").item().build(),
                11,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getDailyChallengeMenu().open(player);
                }
        );

        addItem(
                new ItemBuilder(Material.PAPER).buildMeta().withDisplayName(ChatColor.GOLD + "Weekly Challenges").item().build(),
                15,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getDailyChallengeMenu().open(player);
                }
        );

        addItem(
                new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(4).buildMeta().withDisplayName(ChatColor.YELLOW + ChatColor.ITALIC.toString() + "Back to Junkie Pass").item().build(),
                27,
                e -> {
                    Player player = (Player) e.getWhoClicked();
                    player.closeInventory();
                    plugin.getJunkiePassMenu().open(player);
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

        fill(new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(ChatColor.BLACK + "").item().build());
    }
}
