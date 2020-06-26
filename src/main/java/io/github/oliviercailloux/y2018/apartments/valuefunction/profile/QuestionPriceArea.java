package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;

public class QuestionPriceArea {
  private int price;
  private int surface;
  private final String question = "Would you pay %d€ more for %d m2 more?";

  /**
   * Instantiates a new question price/area.
   *
   * @param price the price shown to the user according to his/her profile
   * @param surface the surface shown to the user according to his/her profile
   */
  private QuestionPriceArea(int price, int surface) {
    this.price = price;
    this.surface = surface;
  }

  /**
   * Creates the object question.
   *
   * @param price the price
   * @param surface the surface
   * @return the question price area
   */
  public static QuestionPriceArea create(int price, int surface) {
    return new QuestionPriceArea(price, surface);
  }

  /**
   * Getter for attribute price Price unit: euro (€)
   *
   * @return the price: question parameter
   */
  public int getPrice() {
    return this.price;
  }

  /**
   * Getter for attribute surface Surface unit: square meter
   *
   * @return the surface : question parameter
   */
  public int getSurface() {
    return this.surface;
  }

  /**
   * Build the question according to parameters emitted during class creation
   *
   * @return the question constructed
   */
  public String getQuestion() {
    return String.format(this.question, this.getPrice(), this.getSurface());
  }

  /**
   * Adapt the interval of weight contained in a profile depending on his response
   *
   * @param p the profile we need to modify
   * @param response the response of the user to the question
   */
  public void resolve(Profile p, boolean response) {
    checkNotNull(p);
    Criterion firstCriterion;
    Criterion secondCriterion;
    Range<Double> closedRange;

    double surfaceR;
    double priceR;
    double rate;

    Range<Double> surfaceRange;
    Range<Double> priceRange;

    /** In this part we compute the different rates of increasment the question is proposing. */
    surfaceRange = p.getLinearAVF().getFloorAreaValueFunction().getInterval();
    surfaceR =
        this.surface
            / (surfaceRange.upperEndpoint().doubleValue()
                - surfaceRange.lowerEndpoint().doubleValue());

    priceRange = p.getLinearAVF().getPricePerNightValueFunction().getInterval();
    priceR =
        this.price
            / (priceRange.upperEndpoint().doubleValue() - priceRange.lowerEndpoint().doubleValue());

    /**
     * The code is very similar if the boolean response is true or false. The only factor that
     * changes is the criteria. Thus, one seeks to know which criterion is given to which range. So
     * we check this based on the boolean. This also allow us to compute the rate we will be using
     * to adapt the non-prioritized range.
     */
    if (response) {
      rate = priceR / surfaceR;
      firstCriterion = Criterion.FLOOR_AREA;
      secondCriterion = Criterion.PRICE_PER_NIGHT;
    } else {
      rate = surfaceR / priceR;
      secondCriterion = Criterion.FLOOR_AREA;
      firstCriterion = Criterion.PRICE_PER_NIGHT;
    }

    final Range<Double> firstRange = p.getWeightRange(firstCriterion);
    final Range<Double> secondRange = p.getWeightRange(secondCriterion);

    final double min = firstRange.lowerEndpoint() + p.getMiddleOfRange(firstCriterion) * 0.2;
    closedRange =
        Range.closed(Math.min(min, firstRange.upperEndpoint()), firstRange.upperEndpoint());
    p.setWeightRange(firstCriterion, closedRange);

    final double max = secondRange.upperEndpoint() - p.getMiddleOfRange(secondCriterion) * 0.1;
    closedRange =
        Range.closed(secondRange.lowerEndpoint(), Math.max(max, secondRange.lowerEndpoint()));
    p.setWeightRange(secondCriterion, closedRange);
  }
}
