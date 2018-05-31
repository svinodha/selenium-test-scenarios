package com.sarav.vino;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class FlightPricesSortingTest {
	
	@Test
	public static void testSortedFlightPrice() {
		
		//Set the chrome driver in the project path
		System.setProperty("webdriver.chrome.driver", "/Users/sarav/eclipse-workspace/SeleniumLearning/chromedriver");
		
		//Initialize the web driver
		WebDriver driver = new ChromeDriver();
		
		//Implicit wait for 5 secs
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//Hit the URL
		driver.get(
				"https://www.expedia.com/Flights-Search?flight-type=on&starDate=06%2F07%2F2018&endDate=07%2F14%2F2018&mode=search&trip=roundtrip&leg1=from%3ASan+Francisco%2C+CA+%28SFO-San+Francisco+Intl.%29%2Cto%3ANew+York+%28NYC-All+Airports%29%2Cdeparture%3A06%2F07%2F2018TANYT&leg2=from%3ANew+York+%28NYC-All+Airports%29%2Cto%3ASan+Francisco%2C+CA+%28SFO-San+Francisco+Intl.%29%2Cdeparture%3A07%2F14%2F2018TANYT&passengers=children%3A0%2Cadults%3A1%2Cseniors%3A0%2Cinfantinlap%3AY");
		
		//Select class for Static dropdown
		Select sortDropdown = new Select(driver.findElement(By.xpath("//select[@name = 'sort']")));
		
		//Select the Lowest price option in the dropdown
		sortDropdown.selectByValue("price:asc");
		
		//Get the list of flights
		List<WebElement> flightSearchResults = driver.findElement(By.xpath("//ul[@id = 'flightModuleList']"))
				.findElements(By.tagName("li"));

		List<Integer> flightPrices = new ArrayList<>();
		for (WebElement flightSearchResultEntry : flightSearchResults) {
			WebElement priceElement;
			try {
				priceElement = flightSearchResultEntry
						.findElement(By.xpath(".//span[@data-test-id = 'listing-price-dollars']"));
			} catch (NoSuchElementException e) {
				//Skip the ad rows in between flight search results
				continue;
			}
			flightPrices.add(Integer.parseInt(priceElement.getText().replaceAll("[$]", "")));
		}
		System.out.println("Flight prices: " + flightPrices);
		//Validates if the prices are in ascending order
		for(int i = 0; i < flightPrices.size() - 1; i++) {
			assertTrue((flightPrices.get(i) <= flightPrices.get(i+1)), "Prices are not in ascending order");
		}
	}
}
