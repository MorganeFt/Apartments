package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.utils.RandomRange;

/**
 * The public class ApartmentValueFunction enables to compute the subjective
 * values of apartments. This is provided by the creation of an object
 * ApartmentValueFunction which contains for each valuable attribute of an
 * apartment : An object of and an associated weight.
 */
public class ApartmentValueFunction {

	/**
	 * The 10 next arguments are the objects used to compute the value function of
	 * the characteristics of an apartment
	 */
	private PartialValueFunction<Double> floorAreaValueFunction;

	private PartialValueFunction<Double> nbBedroomsValueFunction;

	private PartialValueFunction<Double> nbSleepingValueFunction;

	private PartialValueFunction<Double> nbBathroomsValueFunction;

	private PartialValueFunction<Boolean> terraceValueFunction;

	private PartialValueFunction<Double> floorAreaTerraceValueFunction;

	private PartialValueFunction<Boolean> wifiValueFunction;

	private PartialValueFunction<Double> pricePerNightValueFunction;

	private PartialValueFunction<Double> nbMinNightValueFunction;

	private PartialValueFunction<Boolean> teleValueFunction;

	/**
	 * The 10 next arguments gives the weight of an apartment characteristic
	 * subjective value in the calculation of the Apartment total subjective value
	 */
	private double floorAreaSubjectiveValueWeight;

	private double nbBedroomsSubjectiveValueWeight;

	private double nbSleepingSubjectiveValueWeight;

	private double nbBathroomsSubjectiveValueWeight;

	private double terraceSubjectiveValueWeight;

	private double floorAreaTerraceSubjectiveValueWeight;

	private double wifiSubjectiveValueWeight;

	private double pricePerNightSubjectiveValueWeight;

	private double nbMinNightSubjectiveValueWeight;

	private double teleSubjectiveValueWeight;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentValueFunction.class);

	/**
	 * Constructor of the object. By default, all the objects are
	 * ConstantValueFunction objects. By default, all the weights have the same
	 * value and their sum is 1. The setters functions enable to set those two.
	 */
	public ApartmentValueFunction() {
		this.floorAreaValueFunction = new ConstantValueFunction<>(0d);
		this.nbBedroomsValueFunction = new ConstantValueFunction<>(0d);
		this.nbSleepingValueFunction = new ConstantValueFunction<>(0d);
		this.nbBathroomsValueFunction = new ConstantValueFunction<>(0d);
		this.terraceValueFunction = new ConstantValueFunction<>(0d);
		this.floorAreaTerraceValueFunction = new ConstantValueFunction<>(0d);
		this.wifiValueFunction = new ConstantValueFunction<>(0d);
		this.pricePerNightValueFunction = new ConstantValueFunction<>(0d);
		this.nbMinNightValueFunction = new ConstantValueFunction<>(0d);
		this.teleValueFunction = new ConstantValueFunction<>(0d);

		this.floorAreaSubjectiveValueWeight = 0.1;
		this.nbBedroomsSubjectiveValueWeight = 0.1;
		this.nbSleepingSubjectiveValueWeight = 0.1;
		this.nbBathroomsSubjectiveValueWeight = 0.1;
		this.terraceSubjectiveValueWeight = 0.1;
		this.floorAreaTerraceSubjectiveValueWeight = 0.1;
		this.wifiSubjectiveValueWeight = 0.1;
		this.pricePerNightSubjectiveValueWeight = 0.1;
		this.nbMinNightSubjectiveValueWeight = 0.1;
		this.teleSubjectiveValueWeight = 0.1;
	}
	
	public ApartmentValueFunction(ProfileType type) {
		Profile current = ProfileManager.getProfile(type);
		
		
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * floor area.
	 *
	 * @param floorAreaValueFunction
	 */
	public void setFloorAreaValueFunction(PartialValueFunction<Double> floorAreaValueFunction) {
		this.floorAreaValueFunction = checkNotNull(floorAreaValueFunction);
		LOGGER.info("The floor area preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * number of bedrooms.
	 *
	 * @param nbBedroomsValueFunction
	 */
	public void setNbBedroomsValueFunction(PartialValueFunction<Double> nbBedroomsValueFunction) {
		this.nbBedroomsValueFunction = checkNotNull(nbBedroomsValueFunction);
		LOGGER.info("The number of bedrooms preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * accommodation capacity.
	 *
	 * @param nbSleepingValueFunction
	 */
	public void setNbSleepingValueFunction(PartialValueFunction<Double> nbSleepingValueFunction) {
		this.nbSleepingValueFunction = checkNotNull(nbSleepingValueFunction);
		LOGGER.info("The number of sleeping preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * number of bathrooms.
	 *
	 * @param nbBathroomsValueFunction
	 */
	public void setNbBathroomsValueFunction(PartialValueFunction<Double> nbBathroomsValueFunction) {
		this.nbBathroomsValueFunction = checkNotNull(nbBathroomsValueFunction);
		LOGGER.info("The number of bathrooms preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * presence of a terrace.
	 *
	 * @param terraceValueFunction
	 */
	public void setTerraceValueFunction(PartialValueFunction<Boolean> terraceValueFunction) {
		this.terraceValueFunction = checkNotNull(terraceValueFunction);
		LOGGER.info("The terrace preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * floor area of an existing terrace.
	 *
	 * @param floorAreaTerraceValueFunction
	 */
	public void setFloorAreaTerraceValueFunction(PartialValueFunction<Double> floorAreaTerraceValueFunction) {
		this.floorAreaTerraceValueFunction = checkNotNull(floorAreaTerraceValueFunction);
		LOGGER.info("The floor area of the terrace preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * presence of a wireless connection.
	 *
	 * @param wifiValueFunction
	 */
	public void setWifiValueFunction(PartialValueFunction<Boolean> wifiValueFunction) {
		this.wifiValueFunction = checkNotNull(wifiValueFunction);
		LOGGER.info("The wifi preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * price per night.
	 *
	 * @param pricePerNightValueFunction
	 */
	public void setPricePerNightValueFunction(PartialValueFunction<Double> pricePerNightValueFunction) {
		this.pricePerNightValueFunction = checkNotNull(pricePerNightValueFunction);
		LOGGER.info("The price per night preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * minimum number of nights.
	 *
	 * @param nbMinNightValueFunction
	 */
	public void setNbMinNightValueFunction(PartialValueFunction<Double> nbMinNightValueFunction) {
		this.nbMinNightValueFunction = checkNotNull(nbMinNightValueFunction);
		LOGGER.info("The number of minimum night preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * presence of a television.
	 *
	 * @param teleValueFunction
	 */
	public void setTeleValueFunction(PartialValueFunction<Boolean> teleValueFunction) {
		this.teleValueFunction = checkNotNull(teleValueFunction);
		LOGGER.info("The tele preferencies has been set");
	}

	/**
	 * Set the weight of the floor area subjective value corresponding to the
	 * importance of the floor area criteria.
	 *
	 * @param floorAreaSubjectiveValueWeight >= 0
	 */
	public void setFloorAreaSubjectiveValueWeight(double floorAreaSubjectiveValueWeight) {
		checkArgument(floorAreaSubjectiveValueWeight >= 0, "The weight of the floor area cannot be negative");
		this.floorAreaSubjectiveValueWeight = floorAreaSubjectiveValueWeight;
		LOGGER.info("The floor area weight has been set to {}", floorAreaSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the number of bedrooms subjective value corresponding to
	 * the importance of the number of bedrooms criteria.
	 *
	 * @param nbBedroomsSubjectiveValueWeight >= 0
	 */
	public void setNbBedroomsSubjectiveValueWeight(double nbBedroomsSubjectiveValueWeight) {
		checkArgument(nbBedroomsSubjectiveValueWeight >= 0, "The weight of the number of bedrooms cannot be negative");
		this.nbBedroomsSubjectiveValueWeight = nbBedroomsSubjectiveValueWeight;
		LOGGER.info("The number of bedrooms weight has been set to {}", nbBedroomsSubjectiveValueWeight);
	}

	/**
	 * set the weight of the number of sleeping subjective value corresponding to
	 * the importance of the number of sleeping criteria.
	 *
	 * @param nbSleepingSubjectiveValueWeight >= 0
	 */
	public void setNbSleepingSubjectiveValueWeight(double nbSleepingSubjectiveValueWeight) {
		checkArgument(nbSleepingSubjectiveValueWeight >= 0, "The weight of the sleeping cannot be negative");
		this.nbSleepingSubjectiveValueWeight = nbSleepingSubjectiveValueWeight;
		LOGGER.info("The number of sleeping weight has been set to {}", nbSleepingSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the number of bathrooms subjective value corresponding to
	 * the importance of the number of bathrooms criteria.
	 *
	 * @param nbBathroomsSubjectiveValueWeight >= 0
	 */
	public void setNbBathroomsSubjectiveValueWeight(double nbBathroomsSubjectiveValueWeight) {
		checkArgument(nbBathroomsSubjectiveValueWeight >= 0,
				"The weight of the number of bathrooms cannot be negative");
		this.nbBathroomsSubjectiveValueWeight = nbBathroomsSubjectiveValueWeight;
		LOGGER.info("The number of bathrooms weight has been set to {}", nbBathroomsSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the terrace subjective value corresponding to the
	 * importance of the terrace criteria.
	 *
	 * @param terraceSubjectiveValueWeight >= 0
	 */
	public void setTerraceSubjectiveValueWeight(double terraceSubjectiveValueWeight) {
		checkArgument(terraceSubjectiveValueWeight >= 0, "The weight of the terrace cannot be negative");
		this.terraceSubjectiveValueWeight = terraceSubjectiveValueWeight;
		LOGGER.info("The terrace weight has been set to {}", terraceSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the terrace floor area subjective value corresponding to
	 * the importance of the terrace floor area criteria.
	 *
	 * @param floorAreaTerraceSubjectiveValueWeight >= 0
	 */
	public void setFloorAreaTerraceSubjectiveValueWeight(double floorAreaTerraceSubjectiveValueWeight) {
		checkArgument(floorAreaTerraceSubjectiveValueWeight >= 0,
				"The weight of the floor area terrace cannot be negative");
		this.floorAreaTerraceSubjectiveValueWeight = floorAreaTerraceSubjectiveValueWeight;
		LOGGER.info("The floor area of the terrace weight has been set to {}", floorAreaTerraceSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the WiFi subjective value corresponding to the importance
	 * of the WiFi criteria.
	 *
	 * @param wifiSubjectiveValueWeight >= 0
	 */
	public void setWifiSubjectiveValueWeight(double wifiSubjectiveValueWeight) {
		checkArgument(wifiSubjectiveValueWeight >= 0, "The weight of the wifi cannot be negative");
		this.wifiSubjectiveValueWeight = wifiSubjectiveValueWeight;
		LOGGER.info("The wifi weight has been set to {}", wifiSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the price per night subjective value corresponding to the
	 * importance of the price per night criteria.
	 *
	 * @param pricePerNightSubjectiveValueWeight >= 0
	 */
	public void setPricePerNightSubjectiveValueWeight(double pricePerNightSubjectiveValueWeight) {
		checkArgument(pricePerNightSubjectiveValueWeight >= 0, "The weight of the price per night cannot be negative");
		this.pricePerNightSubjectiveValueWeight = pricePerNightSubjectiveValueWeight;
		LOGGER.info("The price per night weight has been set to {}", pricePerNightSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the minimum number of nights subjective value corresponding
	 * to the importance of the minimum number of nights criteria.
	 *
	 * @param nbMinNightSubjectiveValueWeight >= 0
	 */
	public void setNbMinNightSubjectiveValueWeight(double nbMinNightSubjectiveValueWeight) {
		checkArgument(nbMinNightSubjectiveValueWeight >= 0,
				"The weight of the minimum number of nights cannot be negative");
		this.nbMinNightSubjectiveValueWeight = nbMinNightSubjectiveValueWeight;
		LOGGER.info("The number of minimum night weight has been set to {}", nbMinNightSubjectiveValueWeight);
	}

	/**
	 * Set the weight of the television subjective value corresponding to the
	 * importance of the television criteria.
	 *
	 * @param teleSubjectiveValueWeight >= 0
	 */
	public void setTeleSubjectiveValueWeight(double teleSubjectiveValueWeight) {
		checkArgument(teleSubjectiveValueWeight >= 0, "The weight of the tele cannot be negative");
		this.teleSubjectiveValueWeight = teleSubjectiveValueWeight;
		LOGGER.info("The tele weight has been set to {}", teleSubjectiveValueWeight);
	}

	/**
	 * This function return the subjective value of the Apartment in parameter. For
	 * each valuable attribute of this apartment, the subjective value is computed
	 * by the associated PartialValueFunction object. The weighted sum of these
	 * subjective values is returned by the function. When the PartialValueFunction
	 * object of an attribute hasn't been set, the subjective value given to the
	 * corresponding attribute will be 0.
	 * 
	 * @param apart an object of type Apartment
	 * @return a double : the weighted sum of the apartment attributes subjective
	 *         values
	 */
	public double getSubjectiveValue(Apartment apart) {
		double floorAreaSubjectiveValue;
		double nbBedroomsSubjectiveValue;
		double nbSleepingSubjectiveValue;
		double nbBathroomsSubjectiveValue;
		double terraceSubjectiveValue;
		double floorAreaTerraceSubjectiveValue;
		double wifiSubjectiveValue;
		double pricePerNightSubjectiveValue;
		double nbMinNightSubjectiveValue;
		double teleSubjectiveValue;

		checkArgument(
				floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea()) >= 0
						&& floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea()) <= 1,
				"The subjective value of floor area should be between 0 and 1");
		floorAreaSubjectiveValue = floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea());
		LOGGER.info("The floor area subjective value has been set to {}", floorAreaSubjectiveValue);

		checkArgument(
				nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms()) >= 0
						&& nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms()) <= 1,
				"The subjective value of the number of bedrooms should be between 0 and 1");
		nbBedroomsSubjectiveValue = nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms());
		LOGGER.info("The number of bedrooms subjective value has been set to {}", nbBedroomsSubjectiveValue);

		checkArgument(
				nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping()) >= 0
						&& nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping()) <= 1,
				"The subjective value of the number of sleeping should be between 0 and 1");
		nbSleepingSubjectiveValue = nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping());
		LOGGER.info("The number of sleeping subjective value has been set to {}", nbSleepingSubjectiveValue);

		checkArgument(
				nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms()) >= 0
						&& nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms()) <= 1,
				"The subjective value of the number of bathrooms should be between 0 and 1");
		nbBathroomsSubjectiveValue = nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms());
		LOGGER.info("The number of bathrooms subjective value has been set to {}", nbBathroomsSubjectiveValue);

		checkArgument(
				terraceValueFunction.getSubjectiveValue(apart.getTerrace()) >= 0
						&& terraceValueFunction.getSubjectiveValue(apart.getTerrace()) <= 1,
				"The subjective value of the terrace should be between 0 and 1");
		terraceSubjectiveValue = terraceValueFunction.getSubjectiveValue(apart.getTerrace());
		LOGGER.info("The terrace subjective value has been set to {}", terraceSubjectiveValue);

		checkArgument(
				floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace()) >= 0
						&& floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace()) <= 1,
				"The subjective value of the floor area of the terrace should be between 0 and 1");
		floorAreaTerraceSubjectiveValue = floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace());
		LOGGER.info("The floor area of the terrace subjective value has been set to {}",
				floorAreaTerraceSubjectiveValue);

		checkArgument(
				wifiValueFunction.getSubjectiveValue(apart.getWifi()) >= 0
						&& wifiValueFunction.getSubjectiveValue(apart.getWifi()) <= 1,
				"The subjective value of the wifi should be between 0 and 1");
		wifiSubjectiveValue = wifiValueFunction.getSubjectiveValue(apart.getWifi());
		LOGGER.info("The wifi subjective value has been set to {}", wifiSubjectiveValue);

		checkArgument(
				pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight()) >= 0
						&& pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight()) <= 1,
				"The subjective value of the price per night should be between 0 and 1");
		pricePerNightSubjectiveValue = pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight());
		LOGGER.info("the price per night subjective value has been set to {}", pricePerNightSubjectiveValue);

		checkArgument(
				nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight()) >= 0
						&& nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight()) <= 1,
				"The subjective value of the minimum number of nights should be between 0 and 1");
		nbMinNightSubjectiveValue = nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight());
		LOGGER.info("The minimum number of nights subjective value has been set to {}", nbMinNightSubjectiveValue);

		checkArgument(
				teleValueFunction.getSubjectiveValue(apart.getTele()) >= 0
						&& teleValueFunction.getSubjectiveValue(apart.getTele()) <= 1,
				"The subjective value of the presence of a tele should be between 0 and 1");
		teleSubjectiveValue = teleValueFunction.getSubjectiveValue(apart.getTele());
		LOGGER.info("the tele subjective value has been set to {}", teleSubjectiveValue);

		return ((floorAreaSubjectiveValue * floorAreaSubjectiveValueWeight
				+ nbBedroomsSubjectiveValue * nbBedroomsSubjectiveValueWeight
				+ nbSleepingSubjectiveValue * nbSleepingSubjectiveValueWeight
				+ nbBathroomsSubjectiveValue * nbBathroomsSubjectiveValueWeight
				+ terraceSubjectiveValue * terraceSubjectiveValueWeight
				+ floorAreaTerraceSubjectiveValue * floorAreaTerraceSubjectiveValueWeight
				+ wifiSubjectiveValue * wifiSubjectiveValueWeight
				+ pricePerNightSubjectiveValue * pricePerNightSubjectiveValueWeight
				+ nbMinNightSubjectiveValue * nbMinNightSubjectiveValueWeight
				+ teleSubjectiveValue * teleSubjectiveValueWeight)
				/ (floorAreaSubjectiveValueWeight + nbBedroomsSubjectiveValueWeight + nbSleepingSubjectiveValueWeight
						+ nbBathroomsSubjectiveValueWeight + terraceSubjectiveValueWeight
						+ floorAreaTerraceSubjectiveValueWeight + wifiSubjectiveValueWeight
						+ pricePerNightSubjectiveValueWeight + nbMinNightSubjectiveValueWeight
						+ teleSubjectiveValueWeight));

	}

	/**
	 * Allows us to create a ApartmentValueFunction object with random values
	 *
	 * @return a randomized instance of an ApartmentValueFunction
	 */
	public static ApartmentValueFunction getRandomApartmentValueFunction() {

		Random random = new Random();

		ApartmentValueFunction apartValueFunction = new ApartmentValueFunction();

		DiscreteValueFunction<Double> nbBedroomsEndBoundMap = DiscreteValueFunction.discreteValueFunctionBeetween(4, 6);
		DiscreteValueFunction<Double> nbSleepingEndBoundMap = DiscreteValueFunction.discreteValueFunctionBeetween(4, 6);
		DiscreteValueFunction<Double> nbBathroomsEndBoundMap = DiscreteValueFunction.discreteValueFunctionBeetween(1,
				3);

		int floorAreaEndBound = random.nextInt(80) + 21;
		boolean terraceEndBound = (random.nextInt(2) == 1);
		int floorAreaTerraceEndBound = random.nextInt(80) + 21;
		boolean wifiEndBound = (random.nextInt(2) == 1);
		int pricePerNightEndBound = random.nextInt(180) + 21;
		int nbMinNightEndBound = random.nextInt(7) + 3;
		boolean teleEndBound = (random.nextInt(2) == 1);

		int floorAreaStartBound = random.nextInt(floorAreaEndBound);
		int floorAreaTerraceStartBound = random.nextInt(floorAreaTerraceEndBound);
		int pricePerNightStartBound = random.nextInt(pricePerNightEndBound);
		int nbMinNightStartBound = random.nextInt(nbMinNightEndBound);

		apartValueFunction.setFloorAreaValueFunction(new LinearValueFunction(floorAreaStartBound, floorAreaEndBound));
		apartValueFunction.setNbBedroomsValueFunction(nbBedroomsEndBoundMap);
		apartValueFunction.setNbSleepingValueFunction(nbSleepingEndBoundMap);
		apartValueFunction.setNbBathroomsValueFunction(nbBathroomsEndBoundMap);
		apartValueFunction.setTerraceValueFunction(new BooleanValueFunction(terraceEndBound));
		apartValueFunction.setFloorAreaTerraceValueFunction(
				new LinearValueFunction(floorAreaTerraceStartBound, floorAreaTerraceEndBound));
		apartValueFunction.setWifiValueFunction(new BooleanValueFunction(wifiEndBound));
		apartValueFunction
				.setPricePerNightValueFunction(new LinearValueFunction(pricePerNightStartBound, pricePerNightEndBound));
		apartValueFunction
				.setNbMinNightValueFunction(new ReversedLinearValueFunction(nbMinNightStartBound, nbMinNightEndBound));
		apartValueFunction.setTeleValueFunction(new BooleanValueFunction(teleEndBound));

		List<Double> weightRange = RandomRange.weightRangeOfSum(1d, 10);

		LOGGER.info("Weight has been set to : {}", weightRange);

		apartValueFunction.floorAreaSubjectiveValueWeight = weightRange.get(0);
		apartValueFunction.nbBedroomsSubjectiveValueWeight = weightRange.get(1);
		apartValueFunction.nbSleepingSubjectiveValueWeight = weightRange.get(2);
		apartValueFunction.nbBathroomsSubjectiveValueWeight = weightRange.get(3);
		apartValueFunction.terraceSubjectiveValueWeight = weightRange.get(4);
		apartValueFunction.floorAreaTerraceSubjectiveValueWeight = weightRange.get(5);
		apartValueFunction.wifiSubjectiveValueWeight = weightRange.get(6);
		apartValueFunction.pricePerNightSubjectiveValueWeight = weightRange.get(7);
		apartValueFunction.nbMinNightSubjectiveValueWeight = weightRange.get(8);
		apartValueFunction.teleSubjectiveValueWeight = weightRange.get(9);

		return apartValueFunction;
	}

	/**
	 * We make the assumption (by casting), that the runtime PartialValueFunction
	 * associated to criteria is a LinearValueFunction, even if in real life it
	 * would be a discrete criteria (e.g. the number of bedrooms)
	 * 
	 * The goal is to replace a LinearValueFunction's bound by a new bound Warning :
	 * The values of the object should be instantiate before using this function or
	 * an error will appear
	 * 
	 * @param criterion the criterion to adapt. This criterion should not be a
	 *                  boolean as TV for example.
	 * @param newBound  the new bound to define
	 * @param lower     true if we want to adapt the lower bound, false on the other
	 *                  case
	 * @return an object ApartmentValueFunction
	 */
	public ApartmentValueFunction adaptBounds(Criterion criterion, double newBound, boolean lower) {

		ApartmentValueFunction avf = this.cloneAVF();
		LinearValueFunction lvf;

		switch (criterion) {
		case FLOOR_AREA:
			checkArgument(avf.floorAreaValueFunction instanceof LinearValueFunction);
			lvf = (LinearValueFunction) avf.floorAreaValueFunction;
			avf.setFloorAreaValueFunction(adaptLinearValueFunction(lvf, newBound, lower));
			break;
		case FLOOR_AREA_TERRACE:
			checkArgument(avf.floorAreaTerraceValueFunction instanceof LinearValueFunction);
			lvf = (LinearValueFunction) avf.floorAreaTerraceValueFunction;
			avf.setFloorAreaTerraceValueFunction(adaptLinearValueFunction(lvf, newBound, lower));
			break;
		case PRICE_PER_NIGHT:
			checkArgument(avf.pricePerNightValueFunction instanceof LinearValueFunction);
			lvf = (LinearValueFunction) avf.pricePerNightValueFunction;
			avf.setPricePerNightValueFunction(adaptLinearValueFunction(lvf, newBound, lower));
			break;
		case NB_SLEEPING:
			checkArgument(avf.nbSleepingValueFunction instanceof LinearValueFunction);
			lvf = (LinearValueFunction) avf.nbSleepingValueFunction;
			avf.setNbSleepingValueFunction(adaptLinearValueFunction(lvf, newBound, lower));
			break;
		case NB_BATHROOMS:
			checkArgument(avf.nbBathroomsValueFunction instanceof LinearValueFunction);
			lvf = (LinearValueFunction) avf.nbBathroomsValueFunction;
			avf.setNbBathroomsValueFunction(adaptLinearValueFunction(lvf, newBound, lower));
			break;
		case NB_BEDROOMS:
			checkArgument(avf.nbBedroomsValueFunction instanceof LinearValueFunction);
			lvf = (LinearValueFunction) avf.nbBedroomsValueFunction;
			avf.setNbBedroomsValueFunction(adaptLinearValueFunction(lvf, newBound, lower));
			break;
		case NB_MIN_NIGHT:
			checkArgument(avf.nbMinNightValueFunction instanceof LinearValueFunction);
			lvf = (LinearValueFunction) avf.nbMinNightValueFunction;
			avf.setNbMinNightValueFunction(adaptLinearValueFunction(lvf, newBound, lower));
			break;
		// Here, we don't look at TELE, WIFI and TERRACE as they are boolean value (so
		// don't have bounds)
		// $CASES-OMITTED$
		default:
			throw new IllegalArgumentException();
		}

		return avf;
	}

	/**
	 * Adapt linear value function by defining a new lower or upper bound
	 *
	 * @param oldLVF   the old linear value function used
	 * @param newBound the new lower or upper bound
	 * @param lower    used to say whether we change the lower or upper bound
	 * @return an new object LinearValueFunction set with new bound
	 */
	private static LinearValueFunction adaptLinearValueFunction(LinearValueFunction oldLVF, double newBound,
			boolean lower) {
		if (lower) {
			return new LinearValueFunction(newBound, oldLVF.getInterval().upperEndpoint());
		}

		return new LinearValueFunction(oldLVF.getInterval().lowerEndpoint(), newBound);

	}

	/**
	 * This method assumes that the preference between true and false is known but
	 * doesn't matter.
	 *
	 * @param moreImportant is the criterion that is to be prioritized in this
	 *                      object of ApartmentValueFunction
	 * @param lessImportant is the criterion that is to be less important in this
	 *                      object of ApartmentValueFunction
	 * @return an object ApartmentValueFunction
	 */
	public ApartmentValueFunction adaptWeight(Criterion moreImportant, Criterion lessImportant) {

		checkNotNull(lessImportant, "This criterion cannot be null");
		checkNotNull(moreImportant, "This criterion cannot be null");
		checkArgument(!Objects.equals(moreImportant, lessImportant), "Both fields are the same.");
		ApartmentValueFunction avf = cloneAVF();
		double weightSum = avf.getSubjectiveValueWeight(moreImportant) + avf.getSubjectiveValueWeight(lessImportant);

		avf = avf.setSubjectiveValueWeight(moreImportant, 9 * weightSum / 10);
		avf = avf.setSubjectiveValueWeight(lessImportant, weightSum / 10);

		return avf;
	}

	/**
	 * Gives the subjective value weight of a criterion awt
	 *
	 * @param awt the criterion we want to know the value
	 * @return the subjective value weight
	 */
	double getSubjectiveValueWeight(Criterion awt) {
		switch (awt) {
		case TELE:
			return teleSubjectiveValueWeight;
		case TERRACE:
			return terraceSubjectiveValueWeight;
		case WIFI:
			return wifiSubjectiveValueWeight;
		case FLOOR_AREA:
			return floorAreaSubjectiveValueWeight;
		case FLOOR_AREA_TERRACE:
			return floorAreaTerraceSubjectiveValueWeight;
		case NB_BATHROOMS:
			return nbBathroomsSubjectiveValueWeight;
		case NB_BEDROOMS:
			return nbBedroomsSubjectiveValueWeight;
		case NB_SLEEPING:
			return nbSleepingSubjectiveValueWeight;
		case NB_MIN_NIGHT:
			return nbMinNightSubjectiveValueWeight;
		case PRICE_PER_NIGHT:
			return pricePerNightSubjectiveValueWeight;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Sets the subjective value weight of a criterion
	 *
	 * @param awt   the criterion we want to set
	 * @param value the value we want to assign at this criterion
	 * @return an object ApartmentValueFunction with the modified criterion
	 */
	public ApartmentValueFunction setSubjectiveValueWeight(Criterion awt, double value) {

		ApartmentValueFunction avf = cloneAVF();

		switch (awt) {
		case TELE:
			avf.setTeleSubjectiveValueWeight(value);
			break;
		case TERRACE:
			avf.setTerraceSubjectiveValueWeight(value);
			break;
		case WIFI:
			avf.setWifiSubjectiveValueWeight(value);
			break;
		case FLOOR_AREA:
			avf.setFloorAreaSubjectiveValueWeight(value);
			break;
		case FLOOR_AREA_TERRACE:
			avf.setFloorAreaTerraceSubjectiveValueWeight(value);
			break;
		case NB_BATHROOMS:
			avf.setNbBathroomsSubjectiveValueWeight(value);
			break;
		case NB_BEDROOMS:
			avf.setNbBedroomsSubjectiveValueWeight(value);
			break;
		case NB_SLEEPING:
			avf.setNbSleepingSubjectiveValueWeight(value);
			break;
		case NB_MIN_NIGHT:
			avf.setNbMinNightSubjectiveValueWeight(value);
			break;
		case PRICE_PER_NIGHT:
			avf.setPricePerNightSubjectiveValueWeight(value);
			break;
		default:
			throw new IllegalArgumentException();
		}

		return avf;

	}

	/**
	 * This function allows the user to clone an object ApartmentValueFunction
	 *
	 * @return an object ApartmentValueFunction
	 */
	private ApartmentValueFunction cloneAVF() {

		ApartmentValueFunction avf = new ApartmentValueFunction();

		avf.setFloorAreaValueFunction(this.floorAreaValueFunction);
		avf.setNbBedroomsValueFunction(this.nbBedroomsValueFunction);
		avf.setNbSleepingValueFunction(this.nbSleepingValueFunction);
		avf.setNbBathroomsValueFunction(this.nbBathroomsValueFunction);
		avf.setTerraceValueFunction(this.terraceValueFunction);
		avf.setFloorAreaTerraceValueFunction(this.floorAreaTerraceValueFunction);
		avf.setWifiValueFunction(this.wifiValueFunction);
		avf.setPricePerNightValueFunction(this.pricePerNightValueFunction);
		avf.setNbMinNightValueFunction(this.nbMinNightValueFunction);
		avf.setTeleValueFunction(this.teleValueFunction);

		avf.floorAreaSubjectiveValueWeight = this.floorAreaSubjectiveValueWeight;
		avf.nbBedroomsSubjectiveValueWeight = this.nbBedroomsSubjectiveValueWeight;
		avf.nbSleepingSubjectiveValueWeight = this.nbSleepingSubjectiveValueWeight;
		avf.nbBathroomsSubjectiveValueWeight = this.nbBathroomsSubjectiveValueWeight;
		avf.terraceSubjectiveValueWeight = this.terraceSubjectiveValueWeight;
		avf.floorAreaTerraceSubjectiveValueWeight = this.floorAreaTerraceSubjectiveValueWeight;
		avf.wifiSubjectiveValueWeight = this.wifiSubjectiveValueWeight;
		avf.pricePerNightSubjectiveValueWeight = this.pricePerNightSubjectiveValueWeight;
		avf.nbMinNightSubjectiveValueWeight = this.nbMinNightSubjectiveValueWeight;
		avf.teleSubjectiveValueWeight = this.teleSubjectiveValueWeight;

		return avf;

	}

	/**
	 * Gets the weight of the floor area subjective value
	 * 
	 * @return the weight of the attribute floorAreaSubjectiveValueWeight
	 */
	public double getFloorAreaSubjectiveValueWeight() {
		return this.floorAreaSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the floor Area
	 * 
	 * @return the attribute floorAreaValueFunction
	 */
	public PartialValueFunction<Double> getFloorAreaValueFunction() {
		return this.floorAreaValueFunction;
	}

	/**
	 * Gets the weight of the floor area Terrace subjective value
	 * 
	 * @return the weight of the attribute floorAreaTerraceSubjectiveValueWeight
	 */
	public double getFloorAreaTerraceSubjectiveValueWeight() {
		return this.floorAreaTerraceSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the floor Area Terrace
	 * 
	 * @return the attribute floorAreaTerraceValueFunction
	 */
	public PartialValueFunction<Double> getFloorAreaTerraceValueFunction() {
		return this.floorAreaTerraceValueFunction;
	}

	/**
	 * Gets the weight of the number of bathrooms subjective value
	 * 
	 * @return the weight of the attribute nbBathroomsSubjectiveValueWeight
	 */
	public double getNbBathroomsSubjectiveValueWeight() {
		return this.nbBathroomsSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the number of bathrooms
	 * 
	 * @return the attribute nbBathroomsValueFunction
	 */
	public PartialValueFunction<Double> getNbBathroomsValueFunction() {
		return this.nbBathroomsValueFunction;
	}

	/**
	 * Gets the weight of the number of bedrooms subjective value
	 * 
	 * @return the weight of the attribute nbBedroomsSubjectiveValueWeight
	 */
	public double getNbBedroomsSubjectiveValueWeight() {
		return this.nbBedroomsSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the number of bedrooms
	 * 
	 * @return the attribute nbBedroomsValueFunction
	 */
	public PartialValueFunction<Double> getNbBedroomsValueFunction() {
		return this.nbBedroomsValueFunction;
	}

	/**
	 * Gets the weight of the number of minimum night the user have to stay
	 * subjective value
	 * 
	 * @return the weight of the attribute nbMinNightSubjectiveValueWeight
	 */
	public double getNbMinNightSubjectiveValueWeight() {
		return this.nbMinNightSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the minimum number of nights the user has to stay in
	 * 
	 * @return the attribute nbMinNightValueFunction
	 */
	public PartialValueFunction<Double> getNbMinNightValueFunction() {
		return this.nbMinNightValueFunction;
	}

	/**
	 * Gets the weight of the number of person who can sleep in the apartment
	 * subjective value
	 * 
	 * @return the weight of the attribute nbSleepingValueWeight
	 */
	public double getNbSleepingSubjectiveValueWeight() {
		return this.nbSleepingSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the number of people who can sleep in
	 * 
	 * @return the attribute nbSleepingValueFunction
	 */
	public PartialValueFunction<Double> getNbSleepingValueFunction() {
		return this.nbSleepingValueFunction;
	}

	/**
	 * Gets the weight of the price per night subjective value
	 * 
	 * @return the weight of the attribute pricePerNightSubjectiveValueWeight
	 */
	public double getPricePerNightSubjectiveValueWeight() {
		return this.pricePerNightSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the price per night
	 * 
	 * @return the attribute pricePerNightValueFunction
	 */
	public PartialValueFunction<Double> getPricePerNightValueFunction() {
		return this.pricePerNightValueFunction;
	}

	/**
	 * Gets the weight of the presence of a television subjective value
	 * 
	 * @return the weight of the attribute teleSubjectiveValueWeight
	 */
	public double getTeleSubjectiveValueWeight() {
		return this.teleSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the presence of a television
	 * 
	 * @return the attribute teleValueFunction
	 */
	public PartialValueFunction<Boolean> getTeleValueFunction() {
		return this.teleValueFunction;
	}

	/**
	 * Gets the weight of the presence of a terrace subjective value
	 * 
	 * @return the weight of the attribute terraceSubjectiveValueWeight
	 */
	public double getTerraceSubjectiveValueWeight() {
		return this.terraceSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the presence of a terrace
	 * 
	 * @return the attribute terraceValueFunction
	 */
	public PartialValueFunction<Boolean> getTerraceValueFunction() {
		return this.terraceValueFunction;
	}

	/**
	 * Gets the weight of the presence of wifi subjective value
	 * 
	 * @return the weight of the attribute wifiSubjectiveValueWeight
	 */
	public double getWifiSubjectiveValueWeight() {
		return this.wifiSubjectiveValueWeight;
	}

	/**
	 * Gets the object PartialValueFunction used to compute the subjective value of
	 * the presence of the wifi
	 * 
	 * @return the attribute wifiValueFunction
	 */
	public PartialValueFunction<Boolean> getWifiValueFunction() {
		return this.wifiValueFunction;
	}
}
