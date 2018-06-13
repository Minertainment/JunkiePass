package com.minejunkie.junkiepass.commands;

import com.minejunkie.junkiepass.JunkiePass;
import com.minertainment.athena.Athena;
import com.minertainment.athena.commands.CommandContext;
import com.minertainment.athena.commands.Permission;
import com.minertainment.athena.commands.bukkit.AthenaBukkitCommand;
import com.minertainment.athena.commands.exceptions.CommandException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JunkiePassCommand extends AthenaBukkitCommand {

    private JunkiePass plugin;

    public JunkiePassCommand(JunkiePass plugin) {
        super("junkiepass", "/junkiepass", "Opens the Junkie Pass menu.", new Permission("junkiepass.free"), "jpass", "jp", "pass");
        this.plugin = plugin;
        Athena.getCommandManager().registerCommand(this);
    }

    @Override
    public void onCommand(CommandSender sender, CommandContext args) throws CommandException {
        if (args.argsLength() > 0) throw new CommandException(ChatColor.RED + "Usage: " + getUsage());
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        plugin.getJunkiePassMenu().open(player);
    }
}
