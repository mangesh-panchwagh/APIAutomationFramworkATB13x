package tests.restfulbooker.crud;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class TestCreateToken extends BaseTest{

	@Test(groups="reg",priority=1)
	@Owner("Mangesh Panchwagh")
	@Description("TC#1 - Verify that post request to the create token basically creates a 16-digit token.")
	public void test_verifyTokenPOST() {
		
        // Automatically have the base URL set and the header set.
        // This URL as well as the header of application JSON is automatically
        // set when you start using extends from BaseTest.
		
		requestSpecification.basePath(APIConstants.AUTH_URL);
		
		response = RestAssured.given(requestSpecification)
					.when().body(payloadManager.setAuthPayload()).post();
		
		// Extraction (JSON String response to Java Object)
		String token = payloadManager.getTokenFromJson(response.asString());
		
		System.out.println("Generated Token : " + token);
		// Validatoin of request
		assertActions.verifyStringKeyNotNull(token);
	}
	
	@Test(groups="reg",priority=2)
	@Owner("Mangesh Panchwagh")
	@Description("TC#2 - Create Invalid Token and Verify")
	public void test_verifyTokenPOST_Negative() {
		
        // Automatically have the base URL set and the header set.
        // This URL as well as the header of application JSON is automatically
        // set when you start using extends from BaseTest.
		
		requestSpecification.basePath(APIConstants.AUTH_URL);
		
		response = RestAssured.given(requestSpecification)
					.when().body("{}").post();
		
		// Extraction (JSON String response to Java Object)
		String invalid_reason = payloadManager.getInvalidResponse(response.asString());
		
		System.out.println("Generated Token : " + invalid_reason);
		// Validatoin of request
		assertActions.verifyStringKey(invalid_reason,"Bad credentials");
	}
}
