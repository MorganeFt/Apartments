package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class QuestionPriceAreaTests {

  /** Verifying the initialization of the object QuestionPriceArea. */
  @Test
  void testInitialization() {
    QuestionPriceArea q = QuestionPriceArea.create(100, 10);
    assertEquals(100, q.getPrice());
    assertEquals(10, q.getSurface());
    assertEquals("Would you pay 100€ more for 10 m2 more?", q.getQuestion());
  }

  /**
   * Test if the resolve method works well. The code in comment needs the class Profile first to be
   * merge to work correctly
   */
  @Test
  void testResolve() {
    QuestionPriceArea q = QuestionPriceArea.create(100, 10);
    assertThrows(NullPointerException.class, () -> q.resolve(new Profile.Builder().build(), true));
    // Profile construction
    Range<Double> surface = Range.closed(100d, 300d);
    Range<Double> price = Range.closed(500d, 1500d);
    Profile.Builder profileBuilder = new Profile.Builder();
    Arrays.stream(Criterion.values())
        .forEach(c -> profileBuilder.setWeightRange(c, Range.closed(0d, 10d)));
    profileBuilder
        .setWeightRange(Criterion.FLOOR_AREA, surface.lowerEndpoint(), surface.upperEndpoint())
        .setWeightRange(Criterion.PRICE_PER_NIGHT, price.lowerEndpoint(), price.upperEndpoint());
    Profile p = profileBuilder.build();
    q.resolve(p, false);
    assertEquals(700d, p.getWeightRange(Criterion.PRICE_PER_NIGHT).lowerEndpoint());
    assertEquals(280d, p.getWeightRange(Criterion.FLOOR_AREA).upperEndpoint());
    q.resolve(p, true);
    assertEquals(138d, p.getWeightRange(Criterion.FLOOR_AREA).lowerEndpoint());
    assertEquals(1390d, p.getWeightRange(Criterion.PRICE_PER_NIGHT).upperEndpoint());
  }
}
