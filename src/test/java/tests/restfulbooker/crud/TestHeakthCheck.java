package tests.restfulbooker.crud;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.restassured.RestAssured;

public class TestHeakthCheck extends BaseTest{

	@Test
	@Description("TC#3  - Verify Health")
	public void testGETHealthCheck() {
		
		requestSpecification.basePath(APIConstants.PING_URL);
		
		response = RestAssured.given(requestSpecification).when().get();
		
		validatableResponse = response.then().log().all();
		validatableResponse.statusCode(201);
		
		assertActions.verifyTrue(response.asString().contains("Created"));
		
	}
}
