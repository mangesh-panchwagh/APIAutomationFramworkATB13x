package base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import asserts.AssertActions;
import endpoints.APIConstants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import modules.restfulbooker.PayloadManager;

public class BaseTest {

	// This is called as common to all test cases
	// commonToAll Testcase
	// Base URL, Content type - JSON - common
	
	public RequestSpecification requestSpecification;
	public Response response;
	public ValidatableResponse validatableResponse;
	
	public AssertActions assertActions;
	public PayloadManager payloadManager;
	public JsonPath jsonpath;	// used for extraction
	
	// Before test execution we want to set header and baseURL
	
	//@BeforeTest
	@BeforeMethod
	public void setup() {
		
		System.out.println("Starting of the Test");
		payloadManager = new PayloadManager();
		assertActions = new AssertActions();
		
//		requestSpecification = RestAssured.given();
//		requestSpecification.baseUri(APIConstants.BASE_URL);
//		requestSpecification.contentType(ContentType.JSON).log().all();
		
	     requestSpecification = new RequestSpecBuilder()
	                .setBaseUri(APIConstants.BASE_URL)
	                .addHeader("Content-Type", "application/json")
	                .build().log().all();
		
	}
	
	@AfterTest
	public void trardown() {
		System.out.println("Finished the Test!");
	}
	
}
