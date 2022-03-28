package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;

public class CreateAProduct {
	
	SoftAssert softAssertobj;
	public CreateAProduct() {
		softAssertobj = new SoftAssert();
		
	}

	@Test
	public void createOneProduct() {
		
//		{
//	    "name": "MY Magic Cushion",
//	    "description": "The best pillow for amazing programmers.",
//	    "price": "250",
//	    "category_id": "2"
//
//	}
		
		
		
//		SoftAssert softAssert = new SoftAssert();
		
		HashMap<String,String> payload = new HashMap<String,String>();
		
		payload.put("name", "Cushiest Cushion");
		payload.put("description", "The best cushion");
		payload.put("price", "300");
		payload.put("category_id", "2");
		
		Response response =
		
		given()
			   .log().all()
	 	       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type","application/json; charset=UTF-8")
		       .auth().preemptive().basic("demo@techfios.com", "abc123")
//		       .body(new File("src\\main\\java\\data\\createPayload.json"))
		       .body(payload)
		       .log().all().
		when()
				.post("/create.php").
		then()
				.log().all()
				.extract().response();
		
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actual status code:" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 201);
		softAssertobj.assertEquals(actualStatusCode, 201,"Status code not matching");
		
		//validate response header
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actual Header:" + actualHeader );
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		
		String actualResponseBody = response.getBody().asString();
		System.out.println("actual Response Body:" + actualResponseBody );
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String actualProductMessage = jp.get("message");
		System.out.println("actualProductMessage:" + actualProductMessage );
		Assert.assertEquals(actualProductMessage, "Product was created.");
	

	}

}
