package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProduct {

	@Test
	public void readAllProduct() {
		
		SoftAssert softAssert = new SoftAssert();
		Response response =
		
		given()
				.log().all()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type","application/json; charset=UTF-8")
//				.auth().preemptive().basic("demo@techfios.com", "abc123")
// we can declare autho as header as key and value
				.header("Authorization","Basic cG9zdG1hbjpwYXNzd29yZA==")
				.log().all().
				
		when()
				.log().all()
				.get("/read.php").
		then()
				.log().all()
				.extract().response();
		int actualStatusCode = response.getStatusCode();
		System.out.println("actual status code:" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		
		//validate response header
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actual Header:" + actualHeader );
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		
		String actualResponseBody = response.getBody().asString();
		System.out.println("actual Response Body:" + actualResponseBody );
		
		JsonPath jp = new JsonPath(actualResponseBody);
		String firstProductId = jp.getString("records[0].id");
		System.out.println("firstProductId:" + firstProductId );
		
	

	}

}
