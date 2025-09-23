package modules.restfulbooker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import pojos.requestPOJO.restfulbooker.Auth;
//import io.restassured.response.Response;
import pojos.requestPOJO.restfulbooker.Booking;
import pojos.requestPOJO.restfulbooker.BookingDates;
import pojos.responsePOJO.restfulbooker.BookingResponse;
import pojos.responsePOJO.restfulbooker.InvalidTokenResponse;
import pojos.responsePOJO.restfulbooker.TokenResponse;

public class PayloadManager {

	// Responsibility of POJO is serialization and de-serialization
	
	Gson gson;
	Faker faker;
	
	// Convert Java Object into JSON String to use as Payload.
	// Serialization
	
	// Valid Booking
	public String createPayloadBookingAsString() {
		
		Booking booking = new Booking();
		booking.setFirstname("Mangesh");
		booking.setLastname("Panchwagh");
		booking.setTotalprice(112);
		booking.setDepositpaid(true);
		
		BookingDates bookingdates = new BookingDates();
		bookingdates.setCheckin("2025-02-01");
		bookingdates.setCheckout("2025-02-05");
		booking.setBookingdates(bookingdates);
		booking.setAdditionalneeds("Breakfast");
		
		System.out.println(booking);
		
		// POJO classes has to be converted to JSON
		// Every class need to be serialized before making request
		gson = new Gson();
		return gson.toJson(booking); // This is the one you send to payload of Rest Assured
		
//		{
//		    "firstname" : "Mangesh",
//		    "lastname" : "Panchwagh",
//		    "totalprice" : 3000,
//		    "depositpaid" : true,
//		    "bookingdates" : {
//		        "checkin" : "2025-07-22",
//		        "checkout" : "2025-07-27"
//		    },
//		    "additionalneeds" : "Breakfast"
//		}
		
	}
	
	// Invalid Booking
	public String createPayloadBookoingAsStringWrongBody() {
		
		Booking booking = new Booking();
		booking.setFirstname("会意; 會意");
        booking.setLastname("会意; 會意");
		booking.setTotalprice(112);
		booking.setDepositpaid(true);
		
		BookingDates bookingdates = new BookingDates();
		bookingdates.setCheckin("5025-02-01");
		bookingdates.setCheckout("5025-02-01");
		booking.setBookingdates(bookingdates);
		booking.setAdditionalneeds("会意; 會意");
		
		System.out.println(booking);
		
		// Java Object to JSON
		gson = new Gson();
		String jsonStringBooking = gson.toJson(booking);
		return  jsonStringBooking;
	}
	
	// Booking with dynamic data
	public String createPayloadBokoingFakerJS() {
//		This option allows you to dynamically generate firstname,
//		lastname and other variables
		
		faker = new Faker();
		Booking booking = new Booking();
		booking.setFirstname(faker.name().firstName());
		booking.setLastname(faker.name().lastName());
		booking.setTotalprice(faker.random().nextInt(1, 1000));
		booking.setDepositpaid(faker.random().nextBoolean());
		
		BookingDates bookingdates = new BookingDates();
		bookingdates.setCheckin("2024-02-01");
	    bookingdates.setCheckout("2024-02-01");
	    booking.setBookingdates(bookingdates);
	    booking.setAdditionalneeds("Breakfast");
		
	    System.out.println(booking);
	    // Java Object to JSON String
	    gson = new Gson();
	    String jsonStringBooking = gson.toJson(booking);
	    System.out.println(jsonStringBooking);
		return  jsonStringBooking;
		
		// method with the dynamic data we use,
        // we will fetch the data from excel file.
        // Apache POI
        // String the value, firstName, lastName, and everything, and then we will verify from the response.
	}
	
    // deserialization ( JSON String to Java Objects)
    // Convert the JSON String to Java Object so that we can verify response Body
    // DeSerialization
	
	public BookingResponse bookingResponseJava(String responseString) {
		
		gson = new Gson();
		BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
		
	}
	
	public Booking getResponseFromJSON(String responseString) {
		gson = new Gson();
		Booking bookingResponse = gson.fromJson(responseString, Booking.class);
		return bookingResponse;
	}
	
	public String createBookingPayloadWithMissingFields(String... fieldsToOmit) {
		
		Set<String> omitFields  = new HashSet<>(Arrays.asList(fieldsToOmit));
		Booking booking = new Booking();
		
		if(!omitFields.contains("firstname")) {
			booking.setFirstname("Mangesh");
		}
		
		if(!omitFields.contains("lastname")) {
			booking.setLastname("Panchwagh");
		}
		
		if(!omitFields.contains("totalprice")) {
			booking.setTotalprice(112);
		}
		
		if(!omitFields.contains("depositpaid")) {
			booking.setDepositpaid(true);
		}
		
		if(!omitFields.contains("bookingdates")) {
			BookingDates bookingDates = new BookingDates(); 
			bookingDates.setCheckin("2025-02-01");
			bookingDates.setCheckout("2025-02-05");
			booking.setBookingdates(bookingDates);	
		}
		
		if(!omitFields.contains("additionalneeds")) {
			booking.setAdditionalneeds("Breakfast");
		}
		
		Gson gson = new Gson();
		return gson.toJson(booking);
	}

	public String createBookingPayloadWithInvalidField(String fieldName, Object invalidValue) {
		
		Map<String,Object> booking = new HashMap<>();
		
		// Default Valid Values
		booking.put("firstname", "Mangesh");
		booking.put("lastname", "Panchwagh");
		booking.put("totalprice",112);
		booking.put("depositpaid", true);
		
		Map<String,Object> bookingdates = new HashMap<>();
		bookingdates.put("checkin", "2025-02-01");
		bookingdates.put("checkout", "2025-02-05");
		booking.put("bookingdates", bookingdates);
		
		booking.put("additionalneeds", "Breakfast");
		
		// Override the invalid field directly
		if("checkin".equalsIgnoreCase(fieldName) || "checkout".equalsIgnoreCase(fieldName)) {
			bookingdates.put(fieldName.toLowerCase(), String.valueOf(invalidValue));
		}else {
			booking.put(fieldName.toLowerCase(), invalidValue);
		}
		
		Gson gson = new Gson();
		return gson.toJson(booking);
	}

	public String createPayloadBookingAsEntireWrongPayload() {
		
		Map<String, Object> booking = new HashMap<>();
		
		booking.put("firstname", 12345);			  // Integer instead of String
		booking.put("lastname", "true");			  // Boolean instead of String
		booking.put("totalprice", "one hundred");	  //String instead of int
		booking.put("depositpaid", "yes");            // String instead of boolean
		
		Map<String, Object> bookingdates = new LinkedHashMap<String, Object>();
		bookingdates.put("checkin", "02-30-2024");		// Invalid Date string
		bookingdates.put("checkout", 20240205);			// Integer instead of String
		
		booking.put("bookingdates", bookingdates);
		booking.put("additionalneeds", 458);			// Integer instead of String

		Gson gson = new Gson();
		return gson.toJson(booking);
		
	}
	
	// Serialization or deserialization of an object is not present.
    // So we need to create.
	
	// We convert the JSON string to the Java object for auth.
    // {
    //    "username" : "admin",
    //    "password" : "password123"
    //}
	
	public String setAuthPayload() {
		Auth auth = new Auth();
		auth.setUsername("admin");
		auth.setPassword("password123");
		
		gson = new Gson();
		String jsonPayloadString = gson.toJson(auth);
		System.out.println("Payload set to the -> " + jsonPayloadString);
		
		return jsonPayloadString;
	}
	
	// DeSer ( JSON String -> Java Object
	public String getTokenFromJson(String tokenResponse) {
		
		gson = new Gson();
		TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
		
		return tokenResponse1.getToken();
	}
	
	// DeSer ( JSON String -> Java Object
	
	public String getInvalidResponse(String invalidTokenResponse) {
		
		gson = new Gson();
		InvalidTokenResponse tokenResponse1 = gson.fromJson(invalidTokenResponse, InvalidTokenResponse.class);
		return tokenResponse1.getReason();
	}

	
	
}
