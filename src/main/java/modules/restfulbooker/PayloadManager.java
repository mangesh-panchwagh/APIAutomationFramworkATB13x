package modules.restfulbooker;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

//import io.restassured.response.Response;
import pojos.requestPOJO.restfulbooker.Booking;
import pojos.requestPOJO.restfulbooker.BookingDates;
import pojos.responsePOJO.restfulbooker.BookingResponse;

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
	public String createPayloadBokoingAsStringWrongBody() {
		
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

	
	
	
	
}
