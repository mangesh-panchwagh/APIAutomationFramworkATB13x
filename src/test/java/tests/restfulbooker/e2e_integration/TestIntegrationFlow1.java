package tests.restfulbooker.e2e_integration;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import pojos.requestPOJO.restfulbooker.Booking;
import pojos.responsePOJO.restfulbooker.BookingResponse;

public class TestIntegrationFlow1 extends BaseTest{

	 @Test(groups = "qa", priority = 1)
	    @Owner("Mangesh")
	    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
	    public void testCreateBooking(ITestContext iTestContext){
		 
		// 1. Making request 2. Extracting of the data 3. Verification of data
			// Setup will first and making the request - Part - 1
			// Extraction Part - 2
			// Validation and verification via the AssertJ, TestNG Part - 3
		 
		 	requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
		 	response = RestAssured.given(requestSpecification)
		 				.when().body(payloadManager.createPayloadBookingAsString())
		 				.post();
		 	
		 	validatableResponse = response.then().log().all();
		 	validatableResponse.statusCode(200);
		 	
		 // Extraction Part - 2
		 	BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
		 	
		 // Validation and verification via the AssertJ, TestNG Part - 3
		 assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Mangesh");
		 assertActions.verifyStringKey(bookingResponse.getBooking().getLastname(),"Panchwagh");
		 assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
		 
		 Integer bookingid = bookingResponse.getBookingid();
		 iTestContext.setAttribute("bookingid", bookingid);
	    }

	    @Test(groups = "qa", priority = 2)
	    @Owner("Mangesh")
	    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
	    public void testVerifyBookingId(ITestContext iTestContext){
	        
	    	Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
	    	System.out.println(bookingid);
	    	
	    	String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
	    	System.out.println("Final URL " + basePathGET);
	    	
	    	requestSpecification.basePath(basePathGET);
	    	
	    	response = RestAssured
	    				.given(requestSpecification)
	    			    .when().get();
	   
	    	validatableResponse = response.then().log().all();
	    	validatableResponse.statusCode(200);
	    	
	    	Booking booking = payloadManager.getResponseFromJSON(response.asString());
	    	assertActions.verifyStringKeyNotNull(booking.getFirstname());
	    }

	    @Test(groups = "qa", priority = 3)
	    @Owner("Mangesh")
	    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
	    public void testUpdateBookingByID(ITestContext iTestContext){
	        
	    	Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
	    	String token = getToken();	// called token method from BaseTest
	    	iTestContext.setAttribute("token", token);
	    	
	    	String basePathPUTPATCH  = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
	    	System.out.println("basePathPUTPATCH : " + basePathPUTPATCH);
	    	
	    	requestSpecification.basePath(basePathPUTPATCH);
	    	
	    	response = RestAssured
	    				.given(requestSpecification).cookie("token",token)
	    			    .when().body(payloadManager.createPayloadBookingAsString()).put();
	    	
	    	validatableResponse = response.then().log().all();
	    	// Validatable Assertion
	    	validatableResponse.statusCode(200);
	    	
	    	Booking booking = payloadManager.getResponseFromJSON(response.asString());
	    	
	    	assertActions.verifyStringKeyNotNull(booking.getFirstname());
	    	assertActions.verifyStringKey(booking.getFirstname(), "Mangesh");
	    }

	    @Test(groups = "qa", priority = 4)
	    @Owner("Mangesh")
	    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
	    public void testDeleteBookingById(ITestContext iTestContext){
	    	Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
	        String token = (String)iTestContext.getAttribute("token");

	        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

	        requestSpecification.basePath(basePathDELETE).cookie("token", token);
	        validatableResponse = RestAssured.given().spec(requestSpecification)
	                .when().delete().then().log().all();
	        validatableResponse.statusCode(201);

	    }
}
