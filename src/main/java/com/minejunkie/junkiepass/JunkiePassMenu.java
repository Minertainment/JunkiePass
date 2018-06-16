package com.minejunkie.junkiepass;

import com.minertainment.athena.menu.StaticMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.inventivetalent.itembuilder.ItemBuilder;

public class JunkiePassMenu extends StaticMenu {

    private JunkiePass plugin;

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

        fill(new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability(15).buildMeta().withDisplayName(ChatColor.BLACK + "").item().build());
    }

}
