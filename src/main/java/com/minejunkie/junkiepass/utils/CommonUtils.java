package com.minejunkie.junkiepass.utils;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.TreeSet;

public class CommonUtils {

    private JunkiePass plugin;

    public CommonUtils(JunkiePass plugin) {
        this.plugin = plugin;
    }

    public TreeSet<Double> generateMilestones(double amount, double interval) {
        if (interval > amount) return new TreeSet<>();
        if (amount % interval > 0) return new TreeSet<>();
        TreeSet<Double> treeSet = new TreeSet<>();
        for (double i = interval; i < amount; i += interval) {
            treeSet.add(i);
        }
        return treeSet;
    }

    public boolean redeemJunkiePass(JunkiePassProfile profile) {
        if (profile.isPaid()) return false;
        profile.setPaid(true);
        return true;
    }

    public boolean redeemJunkiePass(JunkiePassProfile profile, int level) {
        if (profile.isPaid()) return false;
        profile.setPaid(true);
        profile.setJunkiePassTier(level);
        profile.setJunkiePassExperience(level * 10);
        return true;
    }

    public int getOpenInventorySlots(Player player) {
        int count = 0;
        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) count++;
        }
        return count;
    }
}
