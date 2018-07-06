package com.minejunkie.junkiepass.commands;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minejunkie.junkiepass.tiers.Tier;
import com.minejunkie.junkiepass.tiers.TierConfig;
import com.minertainment.athena.Athena;
import com.minertainment.athena.commands.CommandContext;
import com.minertainment.athena.commands.Permission;
import com.minertainment.athena.commands.bukkit.AthenaBukkitCommand;
import com.minertainment.athena.commands.exceptions.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class JunkiePassCommand extends AthenaBukkitCommand {

    private JunkiePass plugin;

    public JunkiePassCommand(JunkiePass plugin) {
        super("junkiepass", "/jp (claim)", "Opens the Junkie Pass menu.", new Permission("junkiepass.free"), "jpass", "jp", "pass");
        this.plugin = plugin;
        Athena.getCommandManager().registerCommand(this);
    }

    @Override
    public void onCommand(CommandSender sender, CommandContext args) throws CommandException {
        if (args.argsLength() > 1) throw new CommandException(ChatColor.RED + "Usage: " + getUsage());

        if (args.argsLength() == 0) {
            if (!(sender instanceof Player)) return;
            Player player = (Player) sender;
            plugin.getJunkiePassMenu().open(player);
        }

        if (args.argsLength() == 1) {
            if (args.getString(0).equalsIgnoreCase("claim")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
                    if (profile.getClaimedTier() == profile.getJunkiePassTier()) throw new CommandException(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC + "Nothing to claim!");
                    if (profile.getClaimedTier() > profile.getJunkiePassTier()) throw new CommandException(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC + "An error occured.");

                    int claimed = profile.getClaimedTier();
                    int current = profile.getJunkiePassTier();

                    ArrayList<String> commands = new ArrayList<>();
                    for (int i = (claimed + 1); i <= current; i++) {
                        Tier tier = TierConfig.getTiers()[i - 1];
                        if (tier.hasFreeRewards()) commands.addAll(tier.getFreeRewardCommands());
                        if (profile.isPaid() && tier.hasPaidRewards()) commands.addAll(tier.getPaidRewardCommands());

                        for (String command : commands) {
                            String replaced = command.replaceAll("@p", player.getName());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaced);
                            plugin.getJunkiePassLogger().info("Redeeming rewards for junkie pass: " + replaced);
                        }
                        commands.clear();
                    }

                    profile.setClaimedTier(profile.getJunkiePassTier());
                }
            } else throw new CommandException(ChatColor.RED + "Usage: " + getUsage());
        }

    }
}
