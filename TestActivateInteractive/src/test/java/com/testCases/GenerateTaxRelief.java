package com.testCases;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.BaseTest;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GenerateTaxRelief extends BaseTest{

	
	
	String url="http://localhost:9997";
	
	public GenerateTaxRelief() {
		super();
	}
	

	@Test
	void generateHeroTaxRelief() {

		Response response=given()
			.header("Basic","bk","bk")
			.contentType("application/json")
		   
		.when()
			.post(url+"/api/v1/taxrelief")
		.then()
			.statusCode(200)
			.contentType("application/octet-stream")
			.extract().response();
		
		File downloadedFile = new File("downloads/downloaded_file.txt");
		
		saveBinaryFileAsText(response, downloadedFile);
        
        System.out.println("Binary file downloaded and saved as .txt file: " + downloadedFile.getAbsolutePath());
		
	    // Verify that the file has been downloaded successfully
	    Assert.assertEquals(downloadedFile.exists(), true, "File is not available");      
	}
	
	
	
	private static void saveBinaryFileAsText(Response response, File downloadedFile) {
        try (InputStream inputStream = response.getBody().asInputStream();
             FileOutputStream outputStream = new FileOutputStream(downloadedFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the downloaded binary file as .txt.", e);
        }
    }
}
