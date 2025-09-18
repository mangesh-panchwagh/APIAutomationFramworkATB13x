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
		assertActions.verifyStringKey(bookingResponse.getBooking().getLastname(), "Panchwagh");
		assertActions.verifyIntKey(bookingResponse.getBooking().getTotalprice(), 112,"Total price does not match");
		assertActions.verifyBooleanKey(bookingResponse.getBooking().getDepositpaid(),true);
		assertActions.verifyStringKey(bookingResponse.getBooking().getBookingdates().getCheckin(), "2025-02-01");
		assertActions.verifyStringKey(bookingResponse.getBooking().getBookingdates().getCheckout(), "2025-02-05");
		assertActions.verifyStringKey(bookingResponse.getBooking().getAdditionalneeds(),"Breakfast");
		
	}
	
	@Test(groups="reg",priority=2)
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
	
	@Test(groups="reg",priority=3)
	@Owner("Mangesh Panchwagh")
	@Description("TC#3 - Verify that the Booking can be Created, When Payload is CHINESE")
	public void testCreateBookingPOST_POSITIVE_CHINISE() {
		
		requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
		response = RestAssured.given(requestSpecification)
		   					.when().body(payloadManager.createPayloadBookoingAsStringWrongBody())
		   					.log().all().post();
		
		// Extraction Part - 2
		validatableResponse = response.then().log().all();
		validatableResponse.statusCode(200);	
		
		BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
		
		// Validation and verification via the AssertJ, TestNG Part - 3
		
		System.out.println("Booking id is : " + bookingResponse.getBookingid());
		assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
		
	}
	
	@Test(groups="reg",priority=4)
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
	     assertActions.verifyStringKeyNotNull(bookingResponse.getBooking().getLastname());
		
	}
	
	@Test(groups = "reg", priority = 5)
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
	
	@Test(groups = "reg", priority = 6)
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
	
	@Test(groups="reg", priority = 7)
	@Owner("Mangesh Panchwagh")
	@Description("TC#7 - Verify that the Booking can't be Created, Firstname Missing")
	public void testCreateBookingPOST_NEGATIVE_FIRSTNAME_MISSING() {
		
		String payload = payloadManager.createBookingPayloadWithMissingFields("firstname");
		
		response = RestAssured.given(requestSpecification)
					.when().body(payload)
					.log().all().post();
		
		validatableResponse = response.then().log().all();
		validatableResponse.statusCode(404);
		
		//Validation and verification via the AssertJ, TestNG Part - 3
		assertActions.verifyStatusCode(response, 404);
		assertActions.verifyKeyIsMissing(response,"firstname");
		
	}
	
	 @Test(groups = "reg", priority = 8)
	 @Owner("Mangesh Panchwagh")
	 @Description("TC#8 - Verify that the Booking can't be Created,Lastname missing")
	 public void testCreateBookingPOST_NEGATIVE_Lastname_MISSING() {
		 
		 String payload = payloadManager.createBookingPayloadWithMissingFields("lastname");
		 
		 response = RestAssured.given(requestSpecification)
				 	.when().body(payload)
				 	.log().all().post();
		 
		 validatableResponse = response.then().log().all();
		 validatableResponse.statusCode(404);
		 
		//Validation and verification via the AssertJ, TestNG Part - 3
		assertActions.verifyStatusCode(response, 404);
		assertActions.verifyKeyIsMissing(response, "lastname");
	 }

	 @Test(groups="reg",priority = 9)
	 @Owner("Mangesh Panchwagh")
	 @Description("TC#9 - Verify that the Booking can't be Created,Totalprice missing")
	 public void testCreateBookingPOST_NEGATIVE_Totalprice_MISSING() {
		
		 String payload = payloadManager.createBookingPayloadWithMissingFields("totalprice");
		 response = RestAssured.given(requestSpecification)
				 	.when().body(payload)
				 	.log().all().post();
		 
		 validatableResponse = response.then().log().all();
		 validatableResponse.statusCode(404);
		 
		//Validation and verification via the AssertJ, TestNG Part - 3
		 assertActions.verifyStatusCode(response, 404);
		 assertActions.verifyKeyIsMissing(response, "totalprice");
	 }
	 
	 
	 @Test(groups="reg",priority = 10)
	 @Owner("Mangesh Panchwagh")
	 @Description("TC#10 - Verify that the Booking can't be Created,DepositPaid missing")
	 public void testCreateBookingPOST_NEGATIVE_DepositPaid_MISSING() {
		 
		 String payload = payloadManager.createBookingPayloadWithMissingFields("depositpaid");
				 
		 response = RestAssured.given(requestSpecification)
				 		  .when().body(payload)
				 		  .log().all().post();
		 
		 validatableResponse = response.then().log().all();
		 validatableResponse.statusCode(404);
		 
		//Validation and verification via the AssertJ, TestNG Part - 3
		 assertActions.verifyStatusCode(response, 404);
		 assertActions.verifyKeyIsMissing(response, "depositpaid");
	 }
	
	 
	 @Test(groups="reg",priority=11)
	 @Owner("Mangesh panchwagh")
	 @Description("TC#11 - Verify that the Booking can't be Created,BookingDates missing")
	 public void testCreateBookingPOST_NEGATIVE_BookingDates_MISSING() {
		 
		 String payload = payloadManager.createBookingPayloadWithMissingFields("bookingdates");
		 
		 response = RestAssured.given(requestSpecification)
				 	.when().body(payload)
				 	.log().all().post();
		 
		 validatableResponse = response.then().log().all();
		 validatableResponse.statusCode(404);
		 
		//Validation and verification via the AssertJ, TestNG Part - 3
		 assertActions.verifyStatusCode(response, 404);
		 assertActions.verifyKeyIsMissing(response, "bookingdates");
		 
	 }
	 
	 @Test(groups="reg", priority=12)
	 @Owner("Mangesh Panchwagh")
	 @Description("TC#12 - Verify that the Booking can't be Created,AdditionalNeeds missing")
	 public void testCreateBookingPOST_NEGATIVE_AdditionalNeeds_MISSING() {
		 
		 String payload = payloadManager.createBookingPayloadWithMissingFields("additionalneeds");
		 
		 response = RestAssured.given(requestSpecification)
				 	.when().body(payload)
				 	.log().all().post();
		 
		 validatableResponse = response.then().log().all();
		 validatableResponse.statusCode(404);
		 
		//Validation and verification via the AssertJ, TestNG Part - 3
		assertActions.verifyStatusCode(response, 404);
		assertActions.verifyKeyIsMissing(response, payload);
		 
	 }
	
	 @Test(groups="reg",priority=13)
	 @Owner("Mangesh Panchwagh")
	 @Description("TC#13 - Verify that the Booking can't be Created,Invalid firstname")
	 public void testCreateBookingPOST_NEGATIVE_Invalid_Firstname() {
		 
		 String payload = payloadManager.createBookingPayloadWithInvalidField("firstname", 676);
		 
		 response = RestAssured.given(requestSpecification)
				 	.when().body(payload)
				 	.log().all().post();
		 
		 validatableResponse = response.then().log().all();
		 validatableResponse.statusCode(404);
		 
		//Validation and verification via the AssertJ, TestNG Part - 3
		 assertActions.verifyStatusCode(response, 404);
		 
	 }
	 
	 
}
