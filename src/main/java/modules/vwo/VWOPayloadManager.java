package modules.vwo;

import com.google.gson.Gson;

import pojos.requestPOJO.vwo.VWOLoginRequest;
import pojos.responsePOJO.vworesponsePOJO.InvalidLoginResponse;
import pojos.responsePOJO.vworesponsePOJO.LoginResponse;

public class VWOPayloadManager {

	Gson gson;
	
	public String setLoginDataInvalid() {
		
		 // Java Object -> JSON (Serialization)
		VWOLoginRequest loginRequest = new VWOLoginRequest();
		loginRequest.setUsername("contact+aug@thetestingacademy.com");
		loginRequest.setPassword("abc");
		loginRequest.setRemember(false);
		loginRequest.setRecaptchaResponseField("");
		
		System.out.println(loginRequest);
		
		gson = new Gson();
		String jsonPayloadString = gson.toJson(loginRequest);
		
		return jsonPayloadString;
	}
	
	public String setLoginDataValid() {
		
		 // Java Object -> JSON (Serialization)
		VWOLoginRequest loginRequest = new VWOLoginRequest();
		loginRequest.setUsername("contact+aug@thetestingacademy.com");
		loginRequest.setPassword("TtxkgQ!s$rJBk85");
		loginRequest.setRemember(false);
		loginRequest.setRecaptchaResponseField("");
		
		System.out.println(loginRequest);
		
		gson = new Gson();
		String jsonPayloadString = gson.toJson(loginRequest);
		System.out.println("Payload Login to the " + jsonPayloadString);
		return jsonPayloadString;
	}
	
	// DeSer ( JSON String -> Java Object
	public String getLoginDataInvalid(String loginResponseEx) {
		
		gson = new Gson();
		
		InvalidLoginResponse loginResonse  = gson.fromJson(loginResponseEx,InvalidLoginResponse.class);
		return loginResonse.getMessage();
		
	}
	
	// DeSer ( JSON String -> Java Object
		public LoginResponse getLoginDataValid(String loginResponseEx) {
			
			gson = new Gson();
			
			LoginResponse loginResonse  = gson.fromJson(loginResponseEx,LoginResponse.class);
			return loginResonse;
			
		}
	
}
