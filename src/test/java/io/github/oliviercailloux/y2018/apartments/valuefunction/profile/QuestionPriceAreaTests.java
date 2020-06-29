package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;
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
    QuestionPriceArea question = QuestionPriceArea.create(15, 5);
    Profile profile = ProfileManager.getInstance().getProfile(ProfileType.STUDENT);
    Profile profile1 = question.resolve(profile, false);
    assertEquals(0.1234d, profile1.getWeightRange(Criterion.FLOOR_AREA).upperEndpoint(), 0.0001);
    assertEquals(0.0d, profile1.getWeightRange(Criterion.FLOOR_AREA).lowerEndpoint());
    Profile profile2 = question.resolve(profile, true);
    assertEquals(Range.closed(8.1d, 15.0d), profile2.getWeightRange(Criterion.FLOOR_AREA));
    assertEquals(15.0d, profile2.getWeightRange(Criterion.FLOOR_AREA).upperEndpoint(), 0.0001);
    assertEquals(8.1d, profile2.getWeightRange(Criterion.FLOOR_AREA).lowerEndpoint(), 0.0001);
  }

  /**
   * Creation of a Student profile. We ask the question: are you ready to pay x € for y m² more? We
   * assume that the answer is positive. We adapt the linear AVF We consider an apartment a and an
   * apartment b x € more expensive and with y m² more. We check that b is preferred to a with the
   * new avf.
   */
  @Test
  void testSubjectiveValue() {
    QuestionPriceArea question = QuestionPriceArea.create(10, 20);
    Profile profile = ProfileManager.getInstance().getProfile(ProfileType.STUDENT);
    Profile profile1 = question.resolve(profile, true);
    Apartment a =
        new Apartment.Builder()
            .setAddress("118 rue du père noel 77480")
            .setTitle("Grand Igloo")
            .setTerrace(false)
            .setWifi(false)
            .setTele(false)
            .setPricePerNight(20)
            .setFloorArea(50.0d)
            .build();
    Apartment b =
        new Apartment.Builder()
            .setAddress("118 rue du père noel 77480")
            .setTitle("Grand Igloo")
            .setTerrace(false)
            .setWifi(false)
            .setTele(false)
            .setPricePerNight(20 + 10)
            .setFloorArea(50.0d + 20.0d)
            .build();
    assertTrue(
        profile1.getLinearAVF().getSubjectiveValue(a)
            < profile1.getLinearAVF().getSubjectiveValue(b));
  }
}
