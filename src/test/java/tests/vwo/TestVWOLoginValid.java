package tests.vwo;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.APIConstants;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import pojos.responsePOJO.vworesponsePOJO.LoginResponse;

public class TestVWOLoginValid extends BaseTest {

	@Test
	public void test_VWO_Login_Positive(){

		requestSpecification.baseUri(APIConstants.APP_VWO_URL);
		response = RestAssured.given(requestSpecification)
				   .when().body(vwoPayloadManager.setLoginDataValid()).log().all()
				   .post();
		LoginResponse loginResponse = vwoPayloadManager.getLoginDataValid(response.asString());
		
		// Validation and verification via the AssertJ, TestNG Part - 3\
		assertActions.verifyStatusCode(response, 200);
		
		System.out.println(response.asString());
		
		System.out.println(loginResponse.getAccountId());
		assertActions.verifyStringKeyNotNull(loginResponse.getAccountId());
		assertActions.verifyStringKey(loginResponse.getName(),"Aman");
		
	}
}
