package com.minejunkie.junkiepass.profiles;

import com.minertainment.athena.profile.ProfileManager;

import java.util.UUID;

public class JunkiePassProfileManager extends ProfileManager<JunkiePassProfile> {

    @Override
    public JunkiePassProfile createProfile(UUID uuid) {
        return new JunkiePassProfile(uuid);
    }

}
