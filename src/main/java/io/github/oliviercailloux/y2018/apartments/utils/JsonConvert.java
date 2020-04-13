package io.github.oliviercailloux.y2018.apartments.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

import javax.json.bind.JsonbBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

import javax.json.bind.Jsonb;


public abstract class JsonConvert {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(JsonConvert.class);
	
	private final static String apartmentSaveJson = "Apartment_Json.json";
	
	public static void ApartmentToJson(Apartment a) throws IOException {
		ApartmentToJson(a, apartmentSaveJson);
	}
	
	public static void ApartmentToJson(Apartment a, String jsonPath) throws IOException {
		
		File jsonFile = new File(jsonPath);
		Jsonb jsonb = JsonbBuilder.create();
		
		
		try (Writer jsonFilePath = new BufferedWriter(new FileWriter(jsonFile))) {
			jsonFilePath.write(jsonb.toJson(a));
			jsonFilePath.close();
			
			LOGGER.info("Apartment has been converted with success");
		}		
		
	}
	
	public static String ReadApartmentFromJson(String jsonPath) throws FileNotFoundException, IOException {
		
		File jsonFile = new File(jsonPath);
		
		try (BufferedReader jsonFilePath = new BufferedReader(new FileReader(jsonFile))) {
			
			String jsonLine = jsonFilePath.readLine();
			String jsonRead = jsonLine;
			
			while (true) {
				jsonLine = jsonFilePath.readLine();
				if (jsonLine == null) {
					break;
				}
				jsonRead = jsonRead + "\n" + jsonLine;
			}
			
			jsonFilePath.close();
			LOGGER.info("Apartment has been red with success");
		
			return jsonRead;
		}	
	}
	
	public static String getAddressFromJson(String jsonString) {
        Jsonb jsonb = JsonbBuilder.create();
        LOGGER.info("Create Json builder");

        final LinkedHashMap<String, Object> result = jsonb.fromJson(jsonString, LinkedHashMap.class);
        LOGGER.info("Get address");

        return result.get("address").toString();
    }

}
