package com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.BaseTest;

public class LoginPage extends BaseTest{
	
	
	@FindBy(xpath="//h1")
	WebElement pageHeader;
	
	@FindBy(id="username-in")
	WebElement txt_username;
	
	@FindBy(id="password-in")
	WebElement txt_pasword;
	
	@FindBy(xpath = "//input[@type='submit']")
	WebElement btn_submit;
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	
	
	public String verifyLoginPageBannerText() {
		String banner=pageHeader.getText();
		return banner;
	}
	
	public ClerkDashboardPage loginToWorkingClassHeroSystem(String username,String password) {
		txt_username.sendKeys(username);
		txt_pasword.sendKeys(password);
		btn_submit.click();
		
		return new ClerkDashboardPage();
	}
}
