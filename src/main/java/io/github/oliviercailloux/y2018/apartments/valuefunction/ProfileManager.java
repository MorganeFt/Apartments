package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.EnumMap;
import java.util.Map;

public abstract class ProfileManager {

	private static Map<ProfileType,Profile> profiles = new EnumMap<>(ProfileType.class);
	private boolean init = false;
	
	private void init() {
		Profile p = new Profile.Builder().build();
		profiles.put(ProfileType.STUDENT, p);
	}
	
	public static Profile getProfile(ProfileType type) {
		return profiles.get(type);
	}
	
}
