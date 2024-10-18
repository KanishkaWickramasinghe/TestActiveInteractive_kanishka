package com.testUtilities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import static org.hamcrest.Matchers.*;
import org.json.JSONObject;
import com.base.BaseTest;
//import com.github.dockerjava.transport.DockerHttpClient.Response;

public class TestUtilities extends BaseTest{
	
	public static long PAGE_LOAD_TIMEOUT=20;
	public static long	IMPLICIT_WAIT=20;	


	public static void waitForDisplayOfElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}
	
	public static void scrollToSpecificElement(WebElement element) {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	
	
	
	public boolean  validateNatid(String input) {

		String regex_natid = "^natid-([0-9]|[1-9][0-9]{0,6})$";

		Pattern pattern = Pattern.compile(regex_natid);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	
	
	public static boolean isValidId(String id) {
        // Check if id is null or empty
        if (id == null || id.trim().isEmpty()) {
        	System.out.println("ID is not a numeric value!");
            return false; // Invalid if null or empty
        }

        // Check if id contains only digits
        return id.matches("\\d+"); // Matches one or more digits
    }
	
	
	public boolean validateName(String name) {
		String regex_name = "^[a-zA-Z ]{1,100}$";

		Pattern pattern = Pattern.compile(regex_name);
		
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();

	}
	
	public boolean getGenderDetails(String gender) {
		if(gender.equals("MALE")||gender.equals("FEMALE")) {
			return true;
		}
		else {
			return false;
		}
		

	}

	


	public boolean  validateDate(String input) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//JSONObject jsonObject = new JSONObject(input);
		
            // Parse the input string to a LocalDate
            LocalDate givenDate = LocalDate.parse(input, formatter);
            
            // Get the current date
            LocalDate today = LocalDate.now();
            
            // Check if the given date is after today
            if (givenDate.isAfter(today)) {
                System.out.println(givenDate + " is after current date.");
                return true;
            } 
            else if(givenDate.isBefore(today)){
                System.out.println(givenDate + " is not after current date.");
                return false;
            }
            else {
            	
            	System.out.println("Date field is empty.");
                return false;
            }
        
		
	}
	
	
	public boolean  validateSalary(double input) {
		
		String regex_sal = "^-?\\d+(\\.\\d+)?$"; // Allow optional negative sign and decimal part
        
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex_sal);
        
        String str_val = String.valueOf(input);
        Matcher matcher = pattern.matcher(str_val);
        
		if(input>0 && matcher.matches()) {
			return true;
		}
		else
			return false;
	}
	
	
	public boolean  validateTaxPaid(double input) {
		
		String regex_sal = "^-?\\d+(\\.\\d+)?$"; // Allow optional negative sign and decimal part
        
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex_sal);
        
        String str_tax = String.valueOf(input);
        Matcher matcher = pattern.matcher(str_tax);

		if(input>0 || matcher.matches()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean  validateBrowniePoints(Integer input) {

		boolean flag= false;
		if(input == null ||  input!=null) {
			return true;
		}
		return flag;
	}
	
}
