package com.testCases;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pages.BasePage;
import com.pages.ClerkDashboardPage;
import com.pages.LoginPage;
import com.pages.UploadCSVPage;
import com.testUtilities.TestUtilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class TestCreateWorkingClassHero extends BasePage{
	
	TestUtilities utl= new TestUtilities();
	String id1;
	LoginPage login;
	UploadCSVPage upload;
	ClerkDashboardPage clerk;
	String url = prop.getProperty("url");           //"http://localhost:9997";
	
	@Test
	void createHero() throws FileNotFoundException{
		 // Your base URL here
		RestAssured.baseURI=url;

		File file=new File(".\\resources\\create_Hero.json");
		FileReader fR=new FileReader(file);
		JSONTokener jT=new JSONTokener(fR);
		JSONObject requestBody=new JSONObject(jT);
		System.out.println(requestBody);
		
		String payload=requestBody.toString();
		
		Assert.assertNotNull(requestBody);
		
		String nid=requestBody.getString("natid");
		String name=requestBody.getString("name");
		String gender=requestBody.getString("gender"); 
		
		double sal=requestBody.getDouble("salary");
		double tax=requestBody.getDouble("taxPaid");
		int brwn=requestBody.getInt("browniePoints");
		
		
		Assert.assertEquals(utl.validateNatid(nid), true,"natid must be in the format 'natid-number', where number is between 0 to 9999999 (inclusive)");
		Assert.assertEquals(utl.validateName(name), true,"Name must contain letters and spaces with a length between 1 and 100 (inclusive)");
		Assert.assertEquals(utl.getGenderDetails(gender), true,"Gender must be either MALE or FEMALE");
		
		//Validate date of birth
		Assert.assertEquals(utl.validateDate(requestBody.getString("birthDate")), false,"Date of birth should not be greater than today.");
		
		//Validate death date
		//Assert.assertEquals((utl.validateDate(requestBody.getString("deathDate"))==false||requestBody.getString("deathDate")==null), true,"Not a valid death Date format and not a future date.");
		
		Assert.assertEquals(utl.validateSalary(sal), true,"Salary is a decimal and cannot be negative");
		Assert.assertEquals(utl.validateTaxPaid(tax), true,"TaxPaid is a decimal and cannot be negative");
		Assert.assertEquals(utl.validateBrowniePoints(brwn),true,"Brownie points can be nullable but should be numeric.!");

			given()
    		   .auth().basic("clerk", "clerk")
    		   .header("Content-Type", "application/json") // Set content type header
               .body(payload)
    		   
           .when()
                .post("/api/v1/hero")
                   
           .then()
               .statusCode(200)
               .header("Content-Type", equalTo("application/json"))
               .log().all();       
	}
	
	@Test
	void UploadCSVAsClark() throws InterruptedException {
		//initialization();
		login=new LoginPage();
		Assert.assertEquals(login.verifyLoginPageBannerText(), "Working Class Hero System");
		
		clerk =login.loginToWorkingClassHeroSystem(prop.getProperty("clerk_username"),prop.getProperty("clerk_password"));
		Assert.assertEquals(clerk.validatePageHeaderBanner(), "Welcome Home Clark!");
		
		upload= clerk.navigateToUploadCSV();
		Assert.assertEquals(upload.validateUploadCSVBanner(), "Upload CSV file");
		upload.createCSVUpload();
		Assert.assertEquals(upload.verifyBannerDisplay(), true,"File not uploaded!");
		upload.verifyDisplayedBannerText();
		
	}
	
	@Test
	void createWorkingCLassHerosWithVouchers() throws FileNotFoundException {
		RestAssured.baseURI=url;
		
		RestAssured.baseURI=url; 

		File file=new File(".\\resources\\hero_vouchers.json");
		FileReader fR=new FileReader(file);
		JSONTokener jT=new JSONTokener(fR);
		JSONObject requestBody=new JSONObject(jT);
		System.out.println(requestBody);
		String payload=requestBody.toString();
		
        // Define the payload
		String nid=requestBody.getString("natid");
		String name=requestBody.getString("name");
		String gender=requestBody.getString("gender"); 
		String dob=requestBody.getString("birthDate");
		double sal=requestBody.getDouble("salary");
		double tax=requestBody.getDouble("taxPaid");
		int brwn=requestBody.getInt("browniePoints");
		
		Assert.assertEquals(utl.validateNatid(nid), true,"natid must be in the format 'natid-number', where number is between 0 to 9999999 (inclusive)");
		Assert.assertEquals(utl.validateName(name), true,"Name must contain letters and spaces with a length between 1 and 100 (inclusive)");
		Assert.assertEquals(utl.getGenderDetails(gender), true,"Gender must be either MALE or FEMALE");
		
		
		//Validate date of birth
		Assert.assertEquals(utl.validateDate(requestBody.getString("birthDate")), false,"Date of birth should not be greater than today.");
		
		//Validate death date
		//Assert.assertEquals((utl.validateDate(requestBody.getString("deathDate"))==false||requestBody.getString("deathDate")==null), true,"Not a valid death Date format and not a future date.");
		
		Assert.assertEquals(utl.validateSalary(sal), true,"Salary is a decimal and cannot be negative");
		Assert.assertEquals(utl.validateTaxPaid(tax), true,"TaxPaid is a decimal and cannot be negative");

        given()
            .header("Content-Type", "application/json") // Set content type header
            .body(payload) // Attach the JSON payload
        .when()
            .post("/api/v1/hero/vouchers") // Replace with your actual endpoint    
        .then()
            .statusCode(200)
            .header("Content-Type", equalTo("application/json")); // Assert status code is 200 (Created)
    }
	
	
	@AfterMethod
    public void afterMethod(ITestResult result) {
        // Check the name of the test method that just ran
        if (result.getMethod().getMethodName().equals("UploadCSVAsClark")) {
            System.out.println("Running @AfterMethod for '----UploadCSVAsClark----' ");
            // Your @AfterMethod logic here
            driver.quit();
        }
    }
	
	@BeforeMethod
	public void setup(ITestResult results) {
		if(results.getMethod().getMethodName().equals("UploadCSVAsClark")) {
			System.out.println("Running @BeforeMethod for '----UploadCSVAsClark----' ");
            // Your @AfterMethod logic here
            initialization();
		}
	}
	
	
		
	@Test
	public void verifyHerosOwningMoney() throws FileNotFoundException {
		RestAssured.baseURI=url;
		
		File file=new File(".\\resources\\idFile.json");
		FileReader fR=new FileReader(file);
		JSONTokener jT=new JSONTokener(fR);
		JSONObject input_id=new JSONObject(jT);
		System.out.println(input_id);
		
		String id_1=input_id.getString("id");
		  Assert.assertEquals(utl.isValidId(id_1), true,"ID should be a numeric valie"); 
		
		given()
			.contentType("application/json")   
		.when()
			.get("/api/v1/hero/owe-money?natid="+id_1)
		.then()
			.log().all()
			.statusCode(200) // Assert status code
			.header("Content-Type", equalTo("application/json")) // Verify content type
			.body("message.data", equalTo("natid-" + id_1)) // Validate that data matches expected format
			.body("message.status", isOneOf("OWE", "NIL")) // Validate that status is either OWE or NIL
			.body("timestamp", notNullValue()); // Validate that timestamp is present			
	}
	
	@Test
	public void verifyVoucherByPerson() {
		
		RestAssured.baseURI=url;
		
		given()	
		.when()
			.get("/api/v1/voucher/by-person-and-type")
		.then()
			.statusCode(200)
			.log().all()
			.header("Content-Type", equalTo("application/json")) 
            .body("data", notNullValue()) 
            .body("data.size()", greaterThan(0)) 
            .body("data[0].name", notNullValue()) 
            .body("data[0].voucherType", notNullValue()) 
            .body("data[0].count", is(greaterThan(0)));
	}
	
	@Test
	public void byPersonAndType() {
			
		RestAssured.baseURI=url;
		given()
		.when()
			.get("/api/v1/voucher/by-person-and-type")			
		.then()
			.statusCode(200)
			.header("Content-Type", equalTo("application/json")) 
			.body("data[0].name", notNullValue()) 
            .body("data[0].voucherType", notNullValue()) 
            .body("data[0].count", notNullValue()) 
            .body("data[0].count", greaterThanOrEqualTo(0))
			.log().all();
	}	
}
