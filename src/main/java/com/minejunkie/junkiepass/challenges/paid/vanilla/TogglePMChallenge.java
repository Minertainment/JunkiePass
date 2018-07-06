package com.minejunkie.junkiepass.challenges.paid.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.configuration.GSONUtils;
import com.minertainment.athena.packets.PacketListener;
import com.minertainment.jcore.plugin.bungee.msg.commands.TogglePMCommand.TogglePMPacket;
import org.bukkit.Bukkit;

public class TogglePMChallenge extends Challenge {

    private JunkiePass plugin;

    public TogglePMChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "/togglepm Challenge", "Use the /togglepm command.", 1, 10);
        this.plugin = plugin;

        new TogglePMListener();
    }


    public class TogglePMListener extends PacketListener<TogglePMPacket> {

        TogglePMListener() {
            super("TOGGLE_PM");
        }

        @Override
        public TogglePMPacket parsePacket(String json) {
            return GSONUtils.getGson().fromJson(json, TogglePMPacket.class);
        }

        @Override
        public void readPacket(TogglePMPacket packet) {
            if (!plugin.getProfileManager().isLoaded(packet.getPlayer()) || Bukkit.getPlayer(packet.getPlayer()) == null) return;
            JunkiePassProfile profile = plugin.getProfileManager().getProfile(packet.getPlayer());

            if (!profile.isPaid()) return;

            ChallengeData data;
            if ((data = getChallengeData(profile)) == null) return;
            if (!data.isComplete()) increment(profile, Bukkit.getPlayer(packet.getPlayer()), data,1);
        }
    }

}
