package com.minejunkie.junkiepass.commands;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.Athena;
import com.minertainment.athena.commands.CommandContext;
import com.minertainment.athena.commands.Permission;
import com.minertainment.athena.commands.bukkit.AthenaBukkitCommand;
import com.minertainment.athena.commands.exceptions.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveChallengeCommand extends AthenaBukkitCommand {

    private JunkiePass plugin;

    public GiveChallengeCommand(JunkiePass plugin) {
        super("givechallenge", "/givechallenge <challenge> (player)", "Gives a challenge to a player.", new Permission("junkiepass.givechallenge"), "givechal");
        this.plugin = plugin;
        Athena.getCommandManager().registerCommand(this);
    }

    @Override
    public void onCommand(CommandSender sender, CommandContext args) throws CommandException {
        if (args.argsLength() == 0 || args.argsLength() > 2) {
            sender.sendMessage("Available challenges: " + plugin.getChallengeManager().getChallengesString());
            throw new CommandException(ChatColor.RED + "Usage: " + getUsage());
        }

        if (args.argsLength() == 1) {
            Player player;
            if ((player = Bukkit.getPlayer(args.getString(0))) == null) throw new CommandException(ChatColor.RED + "Player not found.");

            JunkiePassProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            Challenge challenge = plugin.getChallengeManager().addRandomChallenge(profile);
            if (challenge == null) {
                throw new CommandException(ChatColor.RED + player.getName() + " has too many active challenges.");
            } else {
                player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have been given the " + ChatColor.GOLD + ChatColor.BOLD.toString() + challenge.getName() + ".");
            }
        }

        if (args.argsLength() == 2) {
            Challenge challenge;

            if ((challenge = plugin.getChallengeManager().containsChallenge(args.getString(0))) == null) {
                sender.sendMessage("Available challenges: " + plugin.getChallengeManager().getChallengesString());
                throw new CommandException(ChatColor.RED + "Challenge not found.");
            }

            Player player;
            if ((player = Bukkit.getPlayer(args.getString(1))) == null) throw new CommandException(ChatColor.RED + "Player not found.");
            if (plugin.getChallengeManager().addChallenge(plugin.getProfileManager().getProfile(player.getUniqueId()), challenge.getClass())) {
                player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have been given the " + ChatColor.GOLD + ChatColor.BOLD.toString() + challenge.getName() + ".");
            } else throw new CommandException(ChatColor.RED + player.getName() + " has too many active challenges or is already participating in the challenge.");
        }
    }
}
