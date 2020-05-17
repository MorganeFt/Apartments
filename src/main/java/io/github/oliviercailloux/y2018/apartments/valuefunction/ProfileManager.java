package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public abstract class ProfileManager {

	private Map<ProfileType,Profile> profiles = new EnumMap<>(ProfileType.class);
	private boolean init = false;
	
	private void init() {
		Profile p = new Profile();
		profiles.put(ProfileType.STUDENT, p);
	}
	
}
