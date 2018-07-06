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

public class JPRedeemCommand extends AthenaBukkitCommand {

    private JunkiePass plugin;

    public JPRedeemCommand(JunkiePass plugin) {
        super("jpredeem", "/jpredeem (player)", "Redeem the Junkie Pass.", new Permission("junkiepass.admin"), new Character[] {'l'});
        this.plugin = plugin;

        Athena.getCommandManager().registerCommand(this);
    }

    @Override
    public void onCommand(CommandSender sender, CommandContext args) throws CommandException {
        if (args.argsLength() > 1) throw new CommandException(ChatColor.RED + "Usage: " + getUsage());

        boolean hasFlag;
        int level = 0;
        if (hasFlag = args.hasFlag('l')) {
            try {
                level = args.getFlagInteger('l');
            } catch (CommandNumberFormatException e) {
                throw new CommandException("Flag must contain an integer.");
            }
        }

        if (hasFlag && (level < 1 || level > 50)) throw new CommandException("Flag must be between 1-50.");

        if (args.argsLength() == 0) {
            if (!(sender instanceof Player)) return;
            JunkiePassProfile profile = plugin.getProfileManager().getProfile(((Player) sender).getUniqueId());
            boolean redeemed = hasFlag ? plugin.getCommonUtils().redeemJunkiePass(profile, level) : plugin.getCommonUtils().redeemJunkiePass(profile);
            if (redeemed) sender.sendMessage(ChatColor.GREEN + "Redeemed.");
            else sender.sendMessage(ChatColor.GREEN + "Already redeemed.");
            return;
        }

        if (args.argsLength() == 1) {
            String playerName = args.getString(0);
            Player target;
            if ((target = Bukkit.getPlayer(playerName)) == null) throw new CommandException(ChatColor.RED + "Player not found.");
            JunkiePassProfile profile = plugin.getProfileManager().getProfile(target.getUniqueId());
            if (profile.isPaid()) throw new CommandException(ChatColor.RED + "Player is already premium.");
            if (hasFlag) plugin.getCommonUtils().redeemJunkiePass(profile, level);
            else plugin.getCommonUtils().redeemJunkiePass(profile);
            target.sendMessage(plugin.getPrefix() + ChatColor.GOLD + "Redeemed Junkie Pass!");
        }
    }
}
