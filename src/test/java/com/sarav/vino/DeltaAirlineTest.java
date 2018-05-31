package com.sarav.vino;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class DeltaAirlineTest {
	@Test
	public static void testDeltaAirlines() {
		
		//Set the Chrome driver in the project path
		System.setProperty("webdriver.chrome.driver", "/Users/sarav/eclipse-workspace/SeleniumLearning/chromedriver");
		
		//Initialize the Webdriver
		WebDriver driver = new ChromeDriver();
		
		//Implicit wait time for 5 secs
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//Hit the URL
		driver.get(
				"https://www.expedia.com/Flights-Search?flight-type=on&starDate=06%2F07%2F2018&endDate=07%2F14%2F2018&mode=search&trip=roundtrip&leg1=from%3ASan+Francisco%2C+CA+%28SFO-San+Francisco+Intl.%29%2Cto%3ANew+York+%28NYC-All+Airports%29%2Cdeparture%3A06%2F07%2F2018TANYT&leg2=from%3ANew+York+%28NYC-All+Airports%29%2Cto%3ASan+Francisco%2C+CA+%28SFO-San+Francisco+Intl.%29%2Cdeparture%3A07%2F14%2F2018TANYT&passengers=children%3A0%2Cadults%3A1%2Cseniors%3A0%2Cinfantinlap%3AY");
		
		//Select Delta airlines
		driver.findElement(By.id("airlineRowContainer_DL")).click();
		
		//Get the list of flights
		List<WebElement> flightSearchResults = driver.findElement(By.xpath("//ul[@id = 'flightModuleList']")).findElements(By.tagName("li"));
		
		//Check that the list of flights has only flights from Delta airlines
		for(WebElement flightSearchResultEntry : flightSearchResults) {
			
			WebElement airlineName;
			try {
				airlineName = flightSearchResultEntry.findElement(By.xpath(".//span[@data-test-id = 'airline-name']"));
			} catch (NoSuchElementException e) {
				//Skip the ad rows in between flight search results
				continue;
			}
			//Validating each airline name against "Delta"
			assertEquals(airlineName.getText(), "Delta");
		
		}
			
		driver.switchTo().defaultContent();
		
	}

}
