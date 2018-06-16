package com.minejunkie.junkiepass.challenges.weekly;

import com.minejunkie.junkiepass.JunkiePass;
import com.minertainment.athena.menu.StaticMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.itembuilder.ItemBuilder;

public class WeeklyChallengeMenu extends StaticMenu {

    private JunkiePass plugin;

    public WeeklyChallengeMenu(JunkiePass plugin) {
        super(plugin, 45, ChatColor.GOLD + "Weekly Challenges");
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

        addUpdater((player, inventory) -> {

        }, 20L);
    }
}
