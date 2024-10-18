package com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.BaseTest;
import com.testUtilities.TestUtilities;

public class ClerkDashboardPage extends BaseTest{

	@FindBy(xpath ="//div[@class='col-md']/span")
	WebElement lbl_header;
	
	@FindBy(xpath = "//button[text()='Add a hero']")
	WebElement btn_addHero;
	
	@FindBy(xpath="//a[text()='Upload a csv file']")
	WebElement link_UploadCSV;
	
	
	public ClerkDashboardPage() {
		PageFactory.initElements(driver, this);
	}
	
	public String validatePageHeaderBanner() {
		String banner=lbl_header.getText();
		return banner;
	}
	
	public UploadCSVPage navigateToUploadCSV() {
		btn_addHero.click();
		TestUtilities.waitForDisplayOfElement(link_UploadCSV);
		link_UploadCSV.click();
		return new UploadCSVPage();
	}
}
