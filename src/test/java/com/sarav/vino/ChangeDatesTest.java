package com.sarav.vino;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class ChangeDatesTest {

	@Test
	public void testChangeDates() {
		
		//Set the Chrome Driver in project path
		System.setProperty("webdriver.chrome.driver", "/Users/sarav/eclipse-workspace/SeleniumLearning/chromedriver");
		
		//Initialize Webdriver
		WebDriver driver = new ChromeDriver();
		//Implicit time wait for 5 seconds
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//Hit the URL
		driver.get(
				"https://www.expedia.com/Flights-Search?flight-type=on&starDate=06%2F07%2F2018&endDate=07%2F14%2F2018&mode=search&trip=roundtrip&leg1=from%3ASan+Francisco%2C+CA+%28SFO-San+Francisco+Intl.%29%2Cto%3ANew+York+%28NYC-All+Airports%29%2Cdeparture%3A06%2F07%2F2018TANYT&leg2=from%3ANew+York+%28NYC-All+Airports%29%2Cto%3ASan+Francisco%2C+CA+%28SFO-San+Francisco+Intl.%29%2Cdeparture%3A07%2F14%2F2018TANYT&passengers=children%3A0%2Cadults%3A1%2Cseniors%3A0%2Cinfantinlap%3AY");

		// Click the Departure date field
		driver.findElement(By.id("departure-date-1")).click();

		// Change the departure month as August
		while (!driver.findElement(By.xpath("//div[@class = 'datepicker-cal-month']/table/caption")).getText()
				.contains("Aug")) {

			driver.findElement(By.xpath("//div[@id = 'start-date-range-1']/div/div/button[2]")).click();
		}

		// Change the departure date as 15
		changeDates(driver, "15");

		// Click the return date field
		driver.findElement(By.id("return-date-1")).click();

		// Change the return month as October
		while (!driver.findElement(By.xpath("//div[@class = 'datepicker-cal-month']/table/caption")).getText()
				.contains("Oct")) {

			driver.findElement(By.xpath("//div[@id = 'end-date-range-1']/div/div/button[2]")).click();
		}

		// Change the return date as 30
		changeDates(driver, "30");

		// Click the Search button
		driver.findElement(By.id("flight-wizard-search-button")).click();

		// validating Departure Date field after changing dates
		assertEquals(driver.findElement(By.xpath("//input[@id = 'departure-date-1']")).getAttribute("value"),
				"08/15/2018");
		// validating Return Date field after changing dates
		assertEquals(driver.findElement(By.xpath("//input[@id = 'return-date-1']")).getAttribute("value"),
				"10/30/2018");
		// Validating date shown at the Results Page title
		assertEquals(driver.findElement(By.className("title-date-rtv")).getText(), "Wed, Aug 15");
	}

	// Method to change the dates
	public static void changeDates(WebDriver driver, String date) {

		int countDays = driver.findElements(By.xpath("//button[@class = 'datepicker-cal-date']")).size();

		for (int i = 0; i < countDays; i++) {

			String arrivalDay = driver.findElements(By.xpath("//button[@class = 'datepicker-cal-date']")).get(i)
					.getAttribute("data-day");

			if (arrivalDay.equals(date)) {
				driver.findElements(By.xpath("//button[@class = 'datepicker-cal-date']")).get(i).click();
				break;
			}
		}
	}
}
