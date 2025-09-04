package asserts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.assertj.core.api.Assertions.*;

import io.restassured.response.Response;

public class AssertActions {

	public void verifyResponseBody(String actual, String expected, String description) {
		
		assertEquals(actual, expected, description);
	}
	
	public void verifyResponseBody(int actual, String expected, String description) {
		
		assertEquals(actual, expected, description);
	}
	
	public void verifyStatusCode(Response response, int expected) {
        assertEquals(response.getStatusCode(),expected);
    }
	
	public void verifyStringKey(Response response, int expected) {
		
		assertEquals(response.getStatusCode(), expected);
	}
	
	public void verifyStringKey(String keyExpect, String keyActual) {
		// AssertJ
		assertThat(keyExpect).isNotNull();
		assertThat(keyExpect).isNotBlank();
		assertThat(keyExpect).isEqualTo(keyActual);
	}
	
	public void verifyStringKeyNotNull(Integer keyExpect){
        // AssertJ
        assertThat(keyExpect).isNotNull();
    }
	
    public void verifyStringKeyNotNull(String keyExpect){
        // AssertJ
        assertThat(keyExpect).isNotNull();
    }
    
    public void verifyTrue(boolean keyExpect){
        // Test NG
        assertTrue(keyExpect);
    }
}
