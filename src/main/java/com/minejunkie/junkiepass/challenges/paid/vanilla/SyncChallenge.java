package com.minejunkie.junkiepass.challenges.paid.vanilla;

import com.minejunkie.junkiepass.JunkiePass;
import com.minejunkie.junkiepass.challenges.Challenge;
import com.minejunkie.junkiepass.challenges.ChallengeData;
import com.minejunkie.junkiepass.challenges.ChallengeType;
import com.minejunkie.junkiepass.profiles.JunkiePassProfile;
import com.minertainment.athena.configuration.GSONUtils;
import com.minertainment.athena.packets.PacketListener;
import com.minertainment.jcore.plugin.bungee.commands.SyncCommand.SyncCMDPacket;
import org.bukkit.Bukkit;

public class SyncChallenge extends Challenge {

    private JunkiePass plugin;

    public SyncChallenge(JunkiePass plugin) {
        super(plugin, ChallengeType.PAID, "/sync Challenge", "Use the /sync command.", 1, 10);
        this.plugin = plugin;

        new SyncPacketListener();
    }

    public class SyncPacketListener extends PacketListener<SyncCMDPacket> {

        public SyncPacketListener() {
            super("SYNC_CMD");
        }

        @Override
        public SyncCMDPacket parsePacket(String json) {
            return GSONUtils.getGson().fromJson(json, SyncCMDPacket.class);
        }

        @Override
        public void readPacket(SyncCMDPacket packet) {
            if (!plugin.getProfileManager().isLoaded(packet.getPlayer()) || Bukkit.getPlayer(packet.getPlayer()) == null) return;
            JunkiePassProfile profile = plugin.getProfileManager().getProfile(packet.getPlayer());

            if (!profile.isPaid()) return;

            ChallengeData data;
            if ((data = getChallengeData(profile)) == null) return;
            if (!data.isComplete()) increment(profile, Bukkit.getPlayer(packet.getPlayer()), data,1);
        }
    }
}
