package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadOneProduct {
	
	SoftAssert softAssertobj;
	public ReadOneProduct() {
		softAssertobj = new SoftAssert();
		
	}

	@Test
	public void readOneProduct() {
		
//		SoftAssert softAssert = new SoftAssert();
		Response response =
		
		given()
			   .log().all()
	 	       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type","application/json")
		       .auth().preemptive().basic("demo@techfios.com", "abc123")
		       .queryParam("id", "3760")
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
		softAssertobj.assertEquals(productId, "3760","Product ID does not match");
		
		String productName = jp.get("name");
		System.out.println("productName:" + productName );
		softAssertobj.assertEquals(productName, "MY Magic Cushion","Product name does not match");
		
		String productPrice = jp.get("price");
		System.out.println("productPrice:" + productPrice );
		softAssertobj.assertEquals(productPrice, "250","Product Price does not match");
	

	}

}
