package io.github.oliviercailloux.y2018.apartments.valuefunction;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

public class Profile {
	
	private double minFloorArea;
	private int minNbBedrooms;
	private int minNbSleeping;
	private int minNbBathroom;
	private int minNbMinNight;
	private double maxPricePerNight;
	private boolean mustWifi;
	private boolean mustTele;
	private boolean mustTerrace;
	
	private Profile() {}
	
	public static class Builder {

		private Profile profileToBuild;
		private boolean minFloorAreaKnown;
		private boolean minNbBedroomsKnown;
		private boolean minNbSleepingKnown;
		private boolean minNbBathroomKnown;
		private boolean minNbMinNightKnown;
		private boolean maxPricePerNightKnown;
		private boolean mustWifiKnown;
		private boolean mustTeleKnown;
		private boolean mustTerraceKnown;
		

		public Builder() {
			profileToBuild = new Profile();
		}

		public Profile build() {
			Profile buildApartment = this.profileToBuild;
			this.profileToBuild = new Profile();
			return buildApartment;
		}

		public Builder setMinFloorArea(double minFloorArea) {
			profileToBuild.minFloorArea = minFloorArea;
			minFloorAreaKnown=true;
			return this;
		}

		public Builder setMinNbBedrooms(int minNbBedrooms) {
			profileToBuild.minNbBedrooms = minNbBedrooms;
			minNbBedroomsKnown=true;
			return this;
		}

		public Builder setMinNbSleeping(int minNbSleeping) {
			profileToBuild.minNbSleeping = minNbSleeping;
			minNbSleepingKnown=true;
			return this;
		}

		public Builder setMinNbBathroom(int minNbBathroom) {
			profileToBuild.minNbBathroom = minNbBathroom;
			minNbBathroomKnown=true;
			return this;
		}

		public Builder setMinNbMinNight(int minNbMinNight) {
			profileToBuild.minNbMinNight = minNbMinNight;
			minNbMinNightKnown=true;
			return this;
		}

		public Builder setMaxPricePerNight(double maxPricePerNight) {
			profileToBuild.maxPricePerNight = maxPricePerNight;
			maxPricePerNightKnown=true;
			return this;
		}
		
		public Builder setMustWifi(boolean mustWifi) {
			profileToBuild.mustWifi = mustWifi;
			mustWifiKnown=true;
			return this;
		}

		public Builder setMustTele(boolean mustTele) {
			profileToBuild.mustTele = mustTele;
			mustTeleKnown=true;
			return this;
		}

		public Builder setMustTerrace(boolean mustTerrace) {
			profileToBuild.mustTerrace = mustTerrace;
			mustTerraceKnown=true;
			return this;
		}
	}

	public double getMinFloorArea() {
		return minFloorArea;
	}

	public int getMinNbBedrooms() {
		return minNbBedrooms;
	}

	public int getMinNbSleeping() {
		return minNbSleeping;
	}

	public int getMinNbBathroom() {
		return minNbBathroom;
	}

	public int getMinNbMinNight() {
		return minNbMinNight;
	}

	public double getMaxPricePerNight() {
		return maxPricePerNight;
	}

	public boolean isMustWifi() {
		return mustWifi;
	}

	public boolean isMustTele() {
		return mustTele;
	}

	public boolean isMustTerrace() {
		return mustTerrace;
	}
}




