package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;

public class QuestionPriceArea {
  int price;
  int surface;
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
   * Getter for attribute price
   *
   * @return the price : question parameter
   */
  public int getPrice() {
    return this.price;
  }

  /**
   * Getter for attribute surface
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
   * @param response the response of the user
   */
  public void resolve(Profile p, boolean response) {
    checkNotNull(p);
    Criterion firstCriterion;
    Criterion secondCriterion;
    Range<Double> closedRange;

    /**
     * The code is very similar if the boolean response is true or false. The only factor that
     * changes is the criteria. Thus, one seeks to know which criterion is given to which range. So
     * we check this based on the boolean
     */
    if (response) {
      firstCriterion = Criterion.FLOOR_AREA;
      secondCriterion = Criterion.PRICE_PER_NIGHT;
    } else {
      secondCriterion = Criterion.FLOOR_AREA;
      firstCriterion = Criterion.PRICE_PER_NIGHT;
    }

    final Range<Double> firstRange = p.getWeightRange(firstCriterion);
    final Range<Double> secondRange = p.getWeightRange(secondCriterion);

    final double min = firstRange.lowerEndpoint() + p.getMiddleOfRange(firstCriterion) * 0.2;
    if (min >= firstRange.upperEndpoint()) {
      closedRange = Range.closed(firstRange.upperEndpoint(), firstRange.upperEndpoint());
    } else {
      closedRange = Range.closed(min, firstRange.upperEndpoint());
    }
    p.setWeightRange(firstCriterion, closedRange);

    final double max = secondRange.upperEndpoint() - p.getMiddleOfRange(secondCriterion) * 0.1;
    if (max <= secondRange.lowerEndpoint()) {
      closedRange = Range.closed(secondRange.lowerEndpoint(), secondRange.lowerEndpoint());
    } else {
      closedRange = Range.closed(secondRange.lowerEndpoint(), max);
    }
    p.setWeightRange(secondCriterion, closedRange);
  }
}
