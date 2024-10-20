package com.pages;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testUtilities.TestUtilities;

public class UploadCSVPage extends BasePage{
	
	@FindBy(xpath = "//label[@class='form-label']")
	WebElement lbl_uploadCSV;
	
	@FindBy(id = "upload-csv-file")
	WebElement input_uploadCSV;
	
	@FindBy(xpath = "//button[text()='Create']")
	WebElement btn_create;
	
	@FindBy(id = "notification-block")
	WebElement statusBanner;
	
	@FindBy(xpath = "//div[@id='notification-block']/child::div/h3")
	WebElement bannerText;
	
	public UploadCSVPage() {
		PageFactory.initElements(driver, this);
		
	}
	
	public String validateUploadCSVBanner() {
		TestUtilities.waitForDisplayOfElement(lbl_uploadCSV);
		String banner=lbl_uploadCSV.getText();
		return banner;
	}
	
	public void createCSVUpload() throws InterruptedException {
		File file = new File("resources/new4.csv");
		
		String filePath = file.getAbsolutePath();
		input_uploadCSV.sendKeys(filePath);
		driver.manage().timeouts().implicitlyWait(TestUtilities.IMPLICIT_WAIT,TimeUnit.SECONDS);
		TestUtilities.waitForDisplayOfElement(btn_create);
		btn_create.click();
		TestUtilities.waitForDisplayOfElement(statusBanner);
		
	}

	public boolean verifyBannerDisplay() {
		TestUtilities.waitForDisplayOfElement(statusBanner);
		if(statusBanner.isDisplayed()) {
			return true;
		}
		else {
			return false;
		}
		
	}

	public void verifyDisplayedBannerText() {
		TestUtilities.waitForDisplayOfElement(statusBanner);
		String text=bannerText.getText();
		if(text.equals("Unable to create hero!")) {
			System.out.println("Errornous upload of CSV."); 
		}
		else {
			System.out.println("Successful upload of CSV.");
		}
		
	}
	
	
}
