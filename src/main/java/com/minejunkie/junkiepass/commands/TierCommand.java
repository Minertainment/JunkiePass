package com.minejunkie.junkiepass.commands;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.Athena;
import com.minertainment.athena.commands.CommandContext;
import com.minertainment.athena.commands.Permission;
import com.minertainment.athena.commands.bukkit.AthenaBukkitCommand;
import com.minertainment.athena.commands.exceptions.CommandException;
import com.minertainment.athena.commands.exceptions.CommandNumberFormatException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TierCommand extends AthenaBukkitCommand {

    private JunkiePass plugin;

    public TierCommand(JunkiePass plugin) {
        super("tier", "/tier <info/set/give> <player> <amt>", "Gives or sets a player's junkie pass tier.", new Permission("junkiepass.admin"));
        this.plugin = plugin;

        Athena.getCommandManager().registerCommand(this);
    }

    @Override
    public void onCommand(CommandSender sender, CommandContext args) throws CommandException {
        if (args.argsLength() == 2) {
            if (!args.getString(0).equalsIgnoreCase("info")) throw new CommandException(ChatColor.RED + "Usage: " + getUsage());

            String playerName = args.getString(1);
            Player player;

            if ((player = Bukkit.getPlayer(playerName)) == null) throw new CommandException(ChatColor.RED + "Player not found.");
            if (!plugin.getProfileManager().isLoaded(player.getUniqueId())) throw new CommandException(ChatColor.RED + "Player's profile is not loaded.");
            JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + player.getName() + (player.getName().endsWith("s") ? "'" : "'s") + " Junkie Pass Tier is " + profile.getJunkiePassTier() + ".");
            return;
        }

        if (args.argsLength() != 3) throw new CommandException(ChatColor.RED + "Usage: " + getUsage());

        String actionString = args.getString(0);
        TierAction action;

        if (actionString.equalsIgnoreCase("set")) action = TierAction.SET;
        else if (actionString.equalsIgnoreCase("give")) action = TierAction.GIVE;
        else throw new CommandException(ChatColor.RED + "Usage: " + getUsage());

        String playerName = args.getString(1);
        Player player;

        if ((player = Bukkit.getPlayer(playerName)) == null) throw new CommandException(ChatColor.RED + "Player not found.");

        int amount;

        try {
            amount = args.getInteger(2);
        } catch (CommandNumberFormatException e) {
            throw new CommandException(ChatColor.RED + "Usage: " + getUsage());
        }

        if (amount < 0 || amount > 50) throw new CommandException(ChatColor.RED + "Invalid tier level (0-50).");

        if (!plugin.getProfileManager().isLoaded(player.getUniqueId())) throw new CommandException(ChatColor.RED + "Player's profile is not loaded.");
        JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());

        if (action == TierAction.SET) {
            if (profile.getJunkiePassTier() > amount) profile.setClaimedTier(amount);
            profile.setJunkiePassTier(amount);
            profile.setJunkiePassExperience(amount * 10);
        }

        if (action == TierAction.GIVE) profile.setJunkiePassExperience(profile.addJunkiePassTier(amount) * 10);

        sender.sendMessage(ChatColor.GREEN + "Successfully " + (action == TierAction.SET ? "set" : "gave") + " " + player.getName() + (player.getName().endsWith("s") ? "'" : "'s") + " Junkie Pass " + (action == TierAction.SET ? "Tier to " + amount + "." : amount + " Tiers."));
        player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC + "Your Junkie Pass Tier has been updated. You may have unclaimed rewards.");
    }

    enum TierAction {
        SET,
        GIVE
    }
}
