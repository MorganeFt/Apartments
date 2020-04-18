package io.github.oliviercailloux.y2018.apartments.apartment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for ApartmentFactory.
 *
 */
class ApartmentFactoryTest {

	//Logger for AparmentFactoryTest class
	private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentFactoryTest.class);

	/**
	 * Test GenerateApartment function 
	 * tests the correspondence between the results sent and those recorded.
	 */
	@Test
	void testGenerateApartmentFromParameters() {
		double floorArea = 456.56; 
		String address = "A Random Address";
		int nbBedrooms = 5;
		int nbSleeping = 9;
		int nbBathrooms = 3;
		boolean terrace = true;
		double floorAreaTerrace = 25.32; 
		String description = "A Random Description for A Random House in A Random Address"; 
		String title = "A Random House"; 
		boolean wifi = true;
		double pricePerNight = 45.95;
		int nbMinNight = 60; 
		boolean tele = false;
		Apartment apart = ApartmentFactory.generateApartment(floorArea, address, nbBedrooms,
				nbSleeping, nbBathrooms, terrace, floorAreaTerrace, description, 
				title, wifi, pricePerNight, nbMinNight, tele);

		assertEquals(floorArea, apart.getFloorArea());
		assertEquals(address, apart.getAddress());
		assertEquals(nbBedrooms, apart.getNbBedrooms());
		assertEquals(nbSleeping, apart.getNbSleeping());
		assertEquals(nbBathrooms, apart.getNbBathrooms());
		assertEquals(terrace, apart.getTerrace());
		assertEquals(floorAreaTerrace, apart.getFloorAreaTerrace());
		assertEquals(description, apart.getDescription());
		assertEquals(title, apart.getTitle());
		assertEquals(wifi, apart.getWifi());
		assertEquals(pricePerNight, apart.getPricePerNight());
		assertEquals(nbMinNight, apart.getNbMinNight());
		assertEquals(tele, apart.getTele());
	}
	
	/**
	 * This function allow us to test if we have an exception thrown when the json path given
	 * in argument of the method is wrong.
	 */
	@Test
	public void generateApartmentFromJsonExceptionTest() {
		assertThrows(IOException.class,
				()-> ApartmentFactory.generateApartmentFromJsonPath("abc"));
	}

}
