package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableMap;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import java.util.Arrays;
import java.util.EnumMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The public class LinearAVF enables to compute the subjective values of apartments. This is
 * provided by the creation of an object LinearAVF which contains for each valuable attribute of an
 * apartment : An object of and an associated weight. Each Value function used are restricted to be
 * able to deal with profiles and questions
 */
public class LinearAVF {

  private static final Logger LOGGER = LoggerFactory.getLogger(LinearAVF.class);

  /**
   * The next argument are the objects used to compute the value function of the characteristics of
   * an apartment
   */
  private EnumMap<Criterion, BooleanValueFunction> booleanValueFunctions;

  private EnumMap<Criterion, LinearValueFunction> linearValueFunctions;
  private EnumMap<Criterion, ReversedLinearValueFunction> reversedValueFunctions;

  /**
   * The next argument gives the apartment characteristic subjective value weight in the calculation
   * of the Apartment total subjective value
   */
  private EnumMap<Criterion, Double> weight;

  /**
   * Constructor of the object. By default, all the objects are ConstantValueFunction objects. By
   * default, all the weights have the same value and their sum is 1. The setters functions enable
   * to set those two.
   */
  private LinearAVF() {
    // value function
    this.booleanValueFunctions = new EnumMap<>(Criterion.class);
    this.linearValueFunctions = new EnumMap<>(Criterion.class);
    this.reversedValueFunctions = new EnumMap<>(Criterion.class);
    Arrays.stream(Criterion.values())
        .filter(c -> c.hasBooleanDomain())
        .forEach(c -> this.setInternalValueFunction(c, new BooleanValueFunction(true)));
    Arrays.stream(Criterion.values())
        .filter(c -> c.hasDoubleDomain())
        .forEach(c -> this.setInternalValueFunction(c));
    // weight
    this.weight = new EnumMap<>(Criterion.class);
    Arrays.stream(Criterion.values()).forEach(criterion -> weight.put(criterion, 0.0d));
  }

  /**
   * This function return the subjective value of the Apartment in parameter. For each valuable
   * attribute of this apartment, the subjective value is computed by the associated
   * PartialValueFunction object. The weighted sum of these subjective values is returned by the
   * function. When the PartialValueFunction object of an attribute hasn't been set, the subjective
   * value given to the corresponding attribute will be 0.
   *
   * @param apart an object of type Apartment
   * @return a double : the weighted sum of the apartment attributes subjective values
   */
  public double getSubjectiveValue(Apartment apart) {
    checkNotNull(apart);
    ImmutableMap<Criterion, Double> subjectiveValue =
        new ImmutableMap.Builder<Criterion, Double>()
            .put(
                Criterion.FLOOR_AREA,
                this.linearValueFunctions
                    .get(Criterion.FLOOR_AREA)
                    .getSubjectiveValue(apart.getFloorArea()))
            .put(
                Criterion.NB_BEDROOMS,
                this.linearValueFunctions
                    .get(Criterion.NB_BEDROOMS)
                    .getSubjectiveValue((double) apart.getNbBedrooms()))
            .put(
                Criterion.NB_SLEEPING,
                this.linearValueFunctions
                    .get(Criterion.NB_SLEEPING)
                    .getSubjectiveValue((double) apart.getNbSleeping()))
            .put(
                Criterion.NB_BATHROOMS,
                this.linearValueFunctions
                    .get(Criterion.NB_BATHROOMS)
                    .getSubjectiveValue((double) apart.getNbBathrooms()))
            .put(
                Criterion.TERRACE,
                this.booleanValueFunctions
                    .get(Criterion.TERRACE)
                    .getSubjectiveValue(apart.getTerrace()))
            .put(
                Criterion.FLOOR_AREA_TERRACE,
                this.linearValueFunctions
                    .get(Criterion.FLOOR_AREA_TERRACE)
                    .getSubjectiveValue(apart.getFloorAreaTerrace()))
            .put(
                Criterion.WIFI,
                this.booleanValueFunctions.get(Criterion.WIFI).getSubjectiveValue(apart.getWifi()))
            .put(
                Criterion.PRICE_PER_NIGHT,
                this.reversedValueFunctions
                    .get(Criterion.PRICE_PER_NIGHT)
                    .getSubjectiveValue(apart.getPricePerNight()))
            .put(
                Criterion.NB_MIN_NIGHT,
                this.reversedValueFunctions
                    .get(Criterion.NB_MIN_NIGHT)
                    .getSubjectiveValue((double) apart.getNbMinNight()))
            .put(
                Criterion.TELE,
                this.booleanValueFunctions.get(Criterion.TELE).getSubjectiveValue(apart.getTele()))
            .build();

    // Check that the subjective values ​​do have a value between 0 and 1
    subjectiveValue.forEach(
        (criterion, aDouble) -> {
          LOGGER.debug("The {} subjective value has been set to {}", criterion.name(), aDouble);
          checkState(
              aDouble >= 0 && aDouble <= 1,
              "The subjective value of " + criterion.name() + "must be between 0 and 1");
        });

    double sum =
        Arrays.stream(Criterion.values())
            .map(c -> this.weight.get(c) * subjectiveValue.get(c))
            .reduce(0.0d, Double::sum);
    double division = this.weight.values().stream().reduce(0.0d, Double::sum);
    return sum / division;
  }

  /**
   * This function allows the user to clone an object LinearAVF
   *
   * @return an object LinearAVF
   */
  private LinearAVF cloneLinearAVF() {
    LinearAVF avf = new LinearAVF();
    // value function
    Arrays.stream(Criterion.values())
        .forEach(
            c -> {
              if (c.hasBooleanDomain()) {
                avf.setInternalValueFunction(c, this.getInternalBooleanValueFunction(c));
              } else if (c.isNotBooleanCrescent()) {
                avf.setInternalValueFunction(c, this.getInternalLinearValueFunction(c));
              } else if (c.isNotBooleanDecrease()) {
                avf.setInternalValueFunction(c, this.getInternalReversedLinearValueFunction(c));
              }
            });
    // weights
    Arrays.stream(Criterion.values())
        .forEach(criterion -> avf.weight.put(criterion, this.weight.get(criterion)));
    return avf;
  }

  /* Operation used for Weight */

  /**
   * Gives the subjective value weight of a criterion awt
   *
   * @param crit the criterion we want to know the value
   * @return the subjective value weight
   */
  public double getWeight(Criterion crit) {
    return this.getWeightSubjectiveValue(crit);
  }

  public double getWeightSubjectiveValue(Criterion crit) {
    checkArgument(this.weight.containsKey(crit));
    return this.weight.get(crit);
  }

  public void setWeightSubjectiveValue(Criterion criterion, double value) {
    this.weight.put(criterion, value);
  }

  /**
   * Sets the subjective value weight of a criterion
   *
   * @param awt the criterion we want to set
   * @param value the value we want to assign at this criterion
   * @return an object LinearAVF with the modified criterion
   */
  public LinearAVF setWeight(Criterion awt, double value) {
    checkArgument(value >= 0, "The given weight cannot be negative");
    LinearAVF avf = cloneLinearAVF();
    avf.setWeightSubjectiveValue(awt, value);
    return avf;
  }

  private void setInternalValueFunction(Criterion criterion, BooleanValueFunction vf) {
    checkArgument(criterion.hasBooleanDomain());
    this.booleanValueFunctions.put(criterion, vf);
  }

  private void setInternalValueFunction(Criterion criterion) {
    if (criterion.isNotBooleanCrescent()) {
      this.linearValueFunctions.put(criterion, null);
    } else if (criterion.isNotBooleanDecrease()) {
      this.reversedValueFunctions.put(criterion, null);
    } else {
      throw new IllegalArgumentException(
          "Criterion must be IS_NOT_BOOLEAN_CRESCENT or IS_NOT_BOOLEAN_DECREASE");
    }
  }

  private void setInternalValueFunction(Criterion criterion, LinearValueFunction vf) {
    checkArgument(criterion.isNotBooleanCrescent());
    this.linearValueFunctions.put(criterion, vf);
  }

  private void setInternalValueFunction(Criterion criterion, ReversedLinearValueFunction vf) {
    checkArgument(criterion.isNotBooleanDecrease());
    this.reversedValueFunctions.put(criterion, vf);
  }

  private BooleanValueFunction getInternalBooleanValueFunction(Criterion criterion) {
    checkArgument(criterion.hasBooleanDomain());
    return this.booleanValueFunctions.get(criterion);
  }

  private LinearValueFunction getInternalLinearValueFunction(Criterion criterion) {
    checkArgument(criterion.isNotBooleanCrescent());
    return this.linearValueFunctions.get(criterion);
  }

  private ReversedLinearValueFunction getInternalReversedLinearValueFunction(Criterion criterion) {
    checkArgument(criterion.isNotBooleanDecrease());
    return this.reversedValueFunctions.get(criterion);
  }

  public static class Builder {
    private LinearAVF toBuild;

    public Builder() {
      toBuild = new LinearAVF();
    }

    public LinearAVF build() {
      checkNotNull(toBuild.getInternalLinearValueFunction(Criterion.FLOOR_AREA_TERRACE));
      checkNotNull(toBuild.getInternalLinearValueFunction(Criterion.FLOOR_AREA));
      checkNotNull(toBuild.getInternalLinearValueFunction(Criterion.NB_BATHROOMS));
      checkNotNull(toBuild.getInternalLinearValueFunction(Criterion.NB_BEDROOMS));
      checkNotNull(toBuild.getInternalReversedLinearValueFunction(Criterion.NB_MIN_NIGHT));
      checkNotNull(toBuild.getInternalLinearValueFunction(Criterion.NB_SLEEPING));
      checkNotNull(toBuild.getInternalReversedLinearValueFunction(Criterion.PRICE_PER_NIGHT));
      checkNotNull(toBuild.getInternalBooleanValueFunction(Criterion.WIFI));
      checkNotNull(toBuild.getInternalBooleanValueFunction(Criterion.TELE));
      checkNotNull(toBuild.getInternalBooleanValueFunction(Criterion.TERRACE));

      for (Criterion c : Criterion.values()) {
        checkNotNull(toBuild.getWeight(c));
      }

      return toBuild;
    }

    /**
     * Set the weight for the criterion given in parameters.
     *
     * @param crit the criterion concerned
     * @param value the value possible for this weight
     * @return the current instance of Builder
     */
    public Builder setWeight(Criterion crit, double value) {
      this.toBuild = toBuild.setWeight(crit, value);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area.
     *
     * @param floorAreaValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaValueFunction(LinearValueFunction floorAreaValueFunction) {
      toBuild.setInternalValueFunction(Criterion.FLOOR_AREA, floorAreaValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaValueFunction(double lowerValue, double upperValue) {
      toBuild.setInternalValueFunction(
          Criterion.FLOOR_AREA, new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bedrooms.
     *
     * @param nbBedroomsValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBedroomsValueFunction(LinearValueFunction nbBedroomsValueFunction) {
      toBuild.setInternalValueFunction(Criterion.NB_BEDROOMS, nbBedroomsValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bedrooms.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBedroomsValueFunction(double lowerValue, double upperValue) {
      toBuild.setInternalValueFunction(
          Criterion.NB_BEDROOMS, new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the accommodation
     * capacity.
     *
     * @param nbSleepingValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbSleepingValueFunction(LinearValueFunction nbSleepingValueFunction) {
      toBuild.setInternalValueFunction(Criterion.NB_SLEEPING, nbSleepingValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the accommodation
     * capacity.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbSleepingValueFunction(double lowerValue, double upperValue) {
      toBuild.setInternalValueFunction(
          Criterion.NB_SLEEPING, new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area of an
     * existing terrace.
     *
     * @param floorAreaTerraceValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaTerraceValueFunction(
        LinearValueFunction floorAreaTerraceValueFunction) {
      toBuild.setInternalValueFunction(Criterion.FLOOR_AREA_TERRACE, floorAreaTerraceValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area of an
     * existing terrace.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaTerraceValueFunction(double lowerValue, double upperValue) {
      toBuild.setInternalValueFunction(
          Criterion.FLOOR_AREA_TERRACE, new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bathrooms.
     *
     * @param nbBathroomsValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBathroomsValueFunction(LinearValueFunction nbBathroomsValueFunction) {
      toBuild.setInternalValueFunction(Criterion.NB_BATHROOMS, nbBathroomsValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bathrooms.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBathroomsValueFunction(double lowerValue, double upperValue) {
      toBuild.setInternalValueFunction(
          Criterion.NB_BATHROOMS, new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * terrace.
     *
     * @param terraceValueFunction a BooleanValueFunction
     * @return the current instance of Builder
     */
    public Builder setTerraceValueFunction(BooleanValueFunction terraceValueFunction) {
      toBuild.setInternalValueFunction(Criterion.TERRACE, terraceValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * terrace.
     *
     * @param value boolean true iif having a Terrace is a benefit
     * @return the current instance of Builder
     */
    public Builder setTerraceValueFunction(boolean value) {
      toBuild.setInternalValueFunction(Criterion.TERRACE, new BooleanValueFunction(value));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * television.
     *
     * @param teleValueFunction a BooleanValueFunction
     * @return the current instance of Builder
     */
    public Builder setTeleValueFunction(BooleanValueFunction teleValueFunction) {
      toBuild.setInternalValueFunction(Criterion.TELE, teleValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * television.
     *
     * @param value boolean true iif having a TV is a benefit
     * @return the current instance of Builder
     */
    public Builder setTeleValueFunction(boolean value) {
      toBuild.setInternalValueFunction(Criterion.TELE, new BooleanValueFunction(value));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * wireless connection.
     *
     * @param wifiValueFunction a BooleanValueFunction
     * @return the current instance of Builder
     */
    public Builder setWifiValueFunction(BooleanValueFunction wifiValueFunction) {
      toBuild.setInternalValueFunction(Criterion.WIFI, wifiValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * wireless connection.
     *
     * @param value boolean true iif having Wifi is a benefit
     * @return the current instance of Builder
     */
    public Builder setWifiValueFunction(boolean value) {
      toBuild.setInternalValueFunction(Criterion.WIFI, new BooleanValueFunction(value));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the price per night.
     *
     * @param pricePerNightValueFunction a ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setPricePerNightValueFunction(
        ReversedLinearValueFunction pricePerNightValueFunction) {
      toBuild.setInternalValueFunction(Criterion.PRICE_PER_NIGHT, pricePerNightValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the price per night.
     *
     * @param lowerValue the min value for this ReversedLinearValueFunction
     * @param upperValue the max value for this ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setPricePerNightValueFunction(double lowerValue, double upperValue) {
      toBuild.setInternalValueFunction(
          Criterion.PRICE_PER_NIGHT, new ReversedLinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the minimum number
     * of nights.
     *
     * @param nbMinNightValueFunction a ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbMinNightValueFunction(ReversedLinearValueFunction nbMinNightValueFunction) {
      toBuild.setInternalValueFunction(Criterion.NB_MIN_NIGHT, nbMinNightValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the minimum number
     * of nights.
     *
     * @param lowerValue the min value for this ReversedLinearValueFunction
     * @param upperValue the max value for this ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbMinNightValueFunction(double lowerValue, double upperValue) {
      toBuild.setInternalValueFunction(
          Criterion.NB_MIN_NIGHT, new ReversedLinearValueFunction(lowerValue, upperValue));
      return this;
    }
  }
}
