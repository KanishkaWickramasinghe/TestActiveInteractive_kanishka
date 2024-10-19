package com.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.testUtilities.TestUtilities;



public class BasePage {
	public static Properties prop;
	public static WebDriver driver;
	
	public BasePage() {
		{
			try {
				prop=new Properties();
				FileInputStream ip=new FileInputStream("src/main/java/com/config/config.properties");
				prop.load(ip);
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initialization(){
		String browserName=prop.getProperty("browser");
		System.out.println(browserName);
		
		if(browserName.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
		}
		
		driver.manage().timeouts().pageLoadTimeout(TestUtilities.PAGE_LOAD_TIMEOUT,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtilities.IMPLICIT_WAIT,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url")+"/login");
	}
}

	
