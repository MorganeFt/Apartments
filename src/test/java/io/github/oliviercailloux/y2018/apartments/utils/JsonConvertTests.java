package io.github.oliviercailloux.y2018.apartments.utils;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for JsonConvert
 * @author Etienne CARTIER & Morgane FIOT
 */
public class JsonConvertTests {
	
	/**
	 * Tests apartmentToJson function.
	 * Verifies if the JSON file created by the function corresponds to the expected file.
	 * 
	 * @throws IOException if the JSON file can't be created.
	 */
	@Test
	void apartmentToJsonTest() throws IOException {
		
		Apartment a = new Apartment(1182118.48, "118 rue du père noel 77480", "Grand Igloo");
		String expectedApartment = "{\"address\":\"118 rue du père noel 77480\",\"description\":\"\",\"floorArea\":1182118.48,\"floorAreaTerrace\":0.0,\"nbBathrooms\":0,\"nbBedrooms\":0,\"nbMinNight\":0,\"nbSleeping\":0,\"pricePerNight\":0.0,\"tele\":false,\"terrace\":false,\"title\":\"Grand Igloo\",\"wifi\":false}";
		String jsonPath = "src/test/resources/io/github/oliviercailloux/y2018/apartments/readapartments/jsonfileTest.json";
		
		JsonConvert.apartmentToJson(a, jsonPath);
		
		assertEquals(expectedApartment, JsonConvert.readApartmentFromJson(jsonPath));
		
		assertThrows(IOException.class, () ->
			JsonConvert.apartmentToJson(a, "")
		);
	}
	
	
	/**
	 * Tests getAddressFromJson function.
	 * Verifies if the address extracted by the function corresponds to the expected address.
	 *
	 * @throws FileNotFoundException if the file doesn't exists.
	 * @throws IOException if the file can't be convert into JSON format.
	 */
	@Test
	void getAddressFromJsonTest() throws FileNotFoundException, IOException {
		
		String adressJson = "{\"data\":{\"latitude\":48.91777636365895,\"longitude\":2.4954686289120067,\"formattedAddress\":\"Allée de Turenne, Nonneville, Les Pavillons-sous-Bois, Le Raincy, Seine-Saint-Denis, Île-de-France, France métropolitaine, 93320, France\",\"country\":\"France\",\"city\":\"Les Pavillons-sous-Bois\",\"state\":\"Île-de-France\",\"zipcode\":\"93320\",\"streetName\":\"Allée de Turenne\",\"countryCode\":\"FR\",\"neighbourhood\":\"\",\"provider\":\"openstreetmap\"},\"address\":\"1 Allée de Turenne, 93320 Les Pavillons-sous-Bois\"}";
		
		assertEquals("1 Allée de Turenne, 93320 Les Pavillons-sous-Bois", JsonConvert.getAddressFromJson(adressJson));
		
	}
	
	/**
	 * Tests apartmentsToJson function.
	 * Verifies if the JSON file created by the function corresponds to the expected file.
	 *
	 * @throws IOException if the JSON file can't be created.
	 */
	@Test
	void apartmentsToJsonTest() throws IOException {
		
		ArrayList<Apartment> apartments = new ArrayList<>();
		apartments.add(new Apartment(1182118.48, "118 rue du père noel 77480", "Grand Igloo"));
		apartments.add(new Apartment(1234567.89, "123 rue du soleil", "Maison Test"));
		
		String expectedApartment = "[{\"address\":\"118 rue du père noel 77480\",\"description\":\"\",\"floorArea\":1182118.48,\"floorAreaTerrace\":0.0,\"nbBathrooms\":0,\"nbBedrooms\":0,\"nbMinNight\":0,\"nbSleeping\":0,\"pricePerNight\":0.0,\"tele\":false,\"terrace\":false,\"title\":\"Grand Igloo\",\"wifi\":false},{\"address\":\"123 rue du soleil\",\"description\":\"\",\"floorArea\":1234567.89,\"floorAreaTerrace\":0.0,\"nbBathrooms\":0,\"nbBedrooms\":0,\"nbMinNight\":0,\"nbSleeping\":0,\"pricePerNight\":0.0,\"tele\":false,\"terrace\":false,\"title\":\"Maison Test\",\"wifi\":false}]";
		String jsonPath = "src/test/resources/io/github/oliviercailloux/y2018/apartments/readapartments/jsonListTest.json";
		
		JsonConvert.apartmentsToJson(apartments, jsonPath);
		
		assertEquals(expectedApartment, JsonConvert.readApartmentFromJson(jsonPath));
		
		assertThrows(IOException.class, () ->
			JsonConvert.apartmentsToJson(apartments, "")
		);
	}
	
	/**
	 * Tests jsonToApartment function.
	 * Verifies if the Apartment created by the function corresponds to the expected Apartment.
	 *
	 * @throws FileNotFoundException if the file doesn't exists.
	 * @throws IOException if the file can't be convert into JSON format.
	 */
	@Test
	void jsonToApartmentTest() throws FileNotFoundException, IOException {
		
		Apartment apartmentRef = new Apartment(1182118.48, "118 rue du père noel 77480", "Grand Igloo");
		
		String jsonPath = "src/test/resources/io/github/oliviercailloux/y2018/apartments/readapartments/jsonfileTest.json";
		String jsonToConvert = JsonConvert.readApartmentFromJson(jsonPath);
		
		Apartment apartmentTest = JsonConvert.jsonToApartment(jsonToConvert);
		
		assertEquals(apartmentRef.hashCode(), apartmentTest.hashCode());
	}
	
	/**
	 * Tests jsonToApartments function.
	 * Verifies if the <i>ArrayList</i> of Apartment created by the function corresponds to the expected <i>ArrayList</i>.
	 *
	 * @throws FileNotFoundException if the file doesn't exists.
	 * @throws IOException if the file can't be convert into JSON format.
	 */
	@Test
	void jsonToApartmentsTest() throws FileNotFoundException, IOException {
		
		List<Apartment> apartmentsRef = new ArrayList<>();
		apartmentsRef.add(new Apartment(1182118.48, "118 rue du père noel 77480", "Grand Igloo"));
		apartmentsRef.add(new Apartment(1234567.89, "123 rue du soleil", "Maison Test"));
		
		String jsonPath = "src/test/resources/io/github/oliviercailloux/y2018/apartments/readapartments/jsonListTest.json";
		String jsonToConvert = JsonConvert.readApartmentFromJson(jsonPath);
		
		List<Apartment> apartmentsTest = JsonConvert.jsonToApartments(jsonToConvert);
		
		assertEquals(apartmentsRef.get(0).hashCode(), apartmentsTest.get(0).hashCode());
		assertEquals(apartmentsRef.get(1).hashCode(), apartmentsTest.get(1).hashCode());
	}

}
