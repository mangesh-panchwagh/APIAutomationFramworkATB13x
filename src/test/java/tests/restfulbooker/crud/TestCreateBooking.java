package tests.restfulbooker.crud;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojos.responsePOJO.restfulbooker.BookingResponse;

public class TestCreateBooking extends BaseTest {

	@Test(groups="reg",priority=1)
	@Owner("Mangesh Panchwagh")
	@Description("TC#1 - Verify that the Booking can be created")
	public void testCreateBookingPOST_Positive() {
		
		// 1. Making request 2. Extracting of the data 3. Verification of data
		// Setup will first and making the request - Part - 1
		// Extraction Part - 2
		// Validation and verification via the AssertJ, TestNG Part - 3
		
		requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
		response = RestAssured.given(requestSpecification)
		   					.when().body(payloadManager.createPayloadBookingAsString())
		   					.log().all().post();
		
		// Extraction Part - 2
		BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
		
		// Validation and verification via the AssertJ, TestNG Part - 3
		assertActions.verifyStatusCode(response, 200);
		System.out.println("Booking id is : " + bookingResponse.getBookingid());
		assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
		assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Mangesh");
		
	}
	
	@Test(groups="reg",priority=1)
	@Owner("Mangesh Panchwagh")
	@Description("TC#2 - Verify that the Booking can't be created, When payload is null")
	public void testCreateBookingPOST_Negative() {
		
		requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
		response = RestAssured.given(requestSpecification)
		   					.when().body("{}")
		   					.log().all().post();
		
		validatableResponse = response.then().log().all();
		validatableResponse.statusCode(500);	
	}
	
	@Test(groups="reg",priority=1)
	@Owner("Mangesh Panchwagh")
	@Description("TC#3 - Verify that the Booking can be Created, When Payload is CHINESE")
	public void testCreateBookingPOST_POSITIVE_CHINISE() {
		
		requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
		response = RestAssured.given(requestSpecification)
		   					.when().body(payloadManager.createPayloadBookingAsString())
		   					.log().all().post();
		
		// Extraction Part - 2
		validatableResponse = response.then().log().all();
		validatableResponse.statusCode(200);	
		
		BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
		
		// Validation and verification via the AssertJ, TestNG Part - 3
		
		System.out.println("Booking id is : " + bookingResponse.getBookingid());
		assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
		
	}
	
	@Test(groups="reg",priority=1)
	@Owner("Mangesh Panchwagh")
	@Description("TC#4 - Verify that the Booking can be Created, When Payload is RANDOM")
	public void testCreateBookingPOST_POSITIVE_FAKER_RANDOM_DATA() {
		
		requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
		response = RestAssured.given(requestSpecification)
		   					.when().body(payloadManager.createPayloadBokoingFakerJS())
		   					.log().all().post();
		 System.out.println(response.asString());
		 
		 validatableResponse = response.then().log().all();
	     validatableResponse.statusCode(200);
	     
	     BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
	     
	     assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
	     assertActions.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());
		
	}
	
	@Test(groups = "reg", priority = 1)
    @Owner("Amit Sharma")
    @Description("TC#5 - Verify that the Booking can be Created, When Payload is RANDOM")
    public void testCreateBookingPOST_NEGATIVE_WRONG() {
        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        requestSpecification.contentType(ContentType.HTML);
        response = RestAssured.given(requestSpecification)
        				.when().body(payloadManager.createPayloadBokoingFakerJS())
        				.log().all().post();
        System.out.println(response.asString());

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(500);

         }
	
	@Test(groups = "reg", priority = 1)
    @Owner("Amit Sharma")
    @Description("TC#6 - Verify that the Booking can be Created, URL is wrong")
    public void testCreateBookingPOST_NEGATIVE_BASE_URL() {
        // Setup and Making a Request.
        requestSpecification.baseUri(APIConstants.APP_VWO_URL);
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        requestSpecification.contentType(ContentType.HTML);
        
        response = RestAssured.given(requestSpecification)
        			.when()
        			.body(payloadManager.createPayloadBokoingFakerJS())
        			.log().all().post();
        
        System.out.println(response.asString());

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);
    }
	
	
}
