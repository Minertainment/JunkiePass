package com.minejunkie.junkiepass.challenges.paid.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.configuration.GSONUtils;
import com.minertainment.athena.packets.PacketListener;
import com.minertainment.athena.plugin.bukkit.chat.commands.ToggleMentionsCommand.ToggleMentionsPacket;
import org.bukkit.Bukkit;

public class ToggleMentionsChallenge extends Challenge {

    private JunkiePass plugin;

    public ToggleMentionsChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "/sound Challenge", "Use the /sound command.", 1, 10);
        this.plugin = plugin;

        new ToggleMentionsListener();
    }

    public class ToggleMentionsListener extends PacketListener<ToggleMentionsPacket> {

        ToggleMentionsListener() {
            super("TOGGLE_MENTIONS");
        }

        @Override
        public ToggleMentionsPacket parsePacket(String json) {
            return GSONUtils.getGson().fromJson(json, ToggleMentionsPacket.class);
        }

        @Override
        public void readPacket(ToggleMentionsPacket packet)  {
            if (!plugin.getProfileManager().isLoaded(packet.getPlayer()) || Bukkit.getPlayer(packet.getPlayer()) == null) return;
            JunkiePassProfile profile = plugin.getProfileManager().getProfile(packet.getPlayer());

            ChallengeData data;
            if ((data = getChallengeData(profile)) == null) return;
            if (!data.isComplete()) increment(profile, Bukkit.getPlayer(packet.getPlayer()), data,1);
        }
    }
}
