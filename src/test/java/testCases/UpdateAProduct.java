package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;

public class UpdateAProduct {
	
	SoftAssert softAssertobj;
	public UpdateAProduct() {
		softAssertobj = new SoftAssert();
		
	}

	@Test(priority = 1)
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
		       .body(new File("src\\main\\java\\data\\UpdatePayload.json"))
//		       .body(payload)
		       .log().all().
		when()
				.put("/update.php").
		then()
				.log().all()
				.extract().response();
		
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actual status code:" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		softAssertobj.assertEquals(actualStatusCode, 200,"Status code not matching");
		
		//validate response header
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actual Header:" + actualHeader );
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		
		String actualResponseBody = response.getBody().asString();
		System.out.println("actual Response Body:" + actualResponseBody );
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String actualProductMessage = jp.get("message");
		System.out.println("actualProductMessage:" + actualProductMessage );
		Assert.assertEquals(actualProductMessage, "Product was updated.");
	

	}
	
	@Test(priority = 2)
	public void readOneProduct() {
		
//		SoftAssert softAssert = new SoftAssert();
		Response response =
		
		given()
			   .log().all()
	 	       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type","application/json")
		       .auth().preemptive().basic("demo@techfios.com", "abc123")
		       .queryParam("id", "3762")
		       .log().all().
		when()
				.log().all()
				.get("/read_one.php").
		then()
				.log().all()
				.extract().response();
		int actualStatusCode = response.getStatusCode();
		System.out.println("actual status code:" + actualStatusCode);
//		Assert.assertEquals(actualStatusCode, 200);
		softAssertobj.assertEquals(actualStatusCode, 200,"Status code not matching");
		
		//validate response header
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actual Header:" + actualHeader );
		softAssertobj.assertEquals(actualHeader, "application/json", "Header does not match");
		
		String actualResponseBody = response.getBody().asString();
		System.out.println("actual Response Body:" + actualResponseBody );
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String productId = jp.get("id");
		System.out.println("productId:" + productId );
		softAssertobj.assertEquals(productId, "3763","Product ID does not match");
		
		String productName = jp.get("name");
		System.out.println("productName:" + productName );
		softAssertobj.assertEquals(productName, "JC's Cushiest Cushion","Product name does not match");
		
		String productPrice = jp.get("price");
		System.out.println("productPrice:" + productPrice );
		softAssertobj.assertEquals(productPrice, "900","Product Price does not match");
	

	}

}
