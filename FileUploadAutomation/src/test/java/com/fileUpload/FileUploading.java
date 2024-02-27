package com.fileUpload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.io.Files;

public class FileUploading {

	public static void main(String[] args) throws Exception {

		MyScreenRecorderUtil.startRecording("main");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setAcceptInsecureCerts(true);

		WebDriver driver = new ChromeDriver(chromeOptions);
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(05));

		driver.get("https://demo.dealsdray.com/");
		driver.manage().window().fullscreen();
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//input[@id='mui-1']"))).click()
				.sendKeys("prexo.mis@dealsdray.com").build().perform();
		action.moveToElement(driver.findElement(By.xpath("//input[@id='mui-2']"))).click()
				.sendKeys("prexo.mis@dealsdray.com").build().perform();
		action.moveToElement(driver.findElement(By.cssSelector("button[type='submit']"))).click().build().perform();
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sidenavHoverShow.css-1s178v5")));
		// click on order from side navbar
		driver.findElement(By.cssSelector(".sidenavHoverShow.css-1s178v5")).click();

		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[normalize-space()='Orders'])[1]")));
		// click on orders from side navbar
		action.moveToElement(driver.findElement(By.xpath("(//span[normalize-space()='Orders'])[1]"))).click().build()
				.perform();
		wt.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Bulk Orders']")));
		driver.findElement(By.xpath("(//button[normalize-space()='Add Bulk Orders'])[1]")).click();

		String filePath = "D:\\Downloads\\demo-data.xlsx";

		// check if the choosefile option is available befor uploading the file.
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@id='mui-7'])")));

		// upload a file
		driver.findElement(By.xpath("(//input[@id='mui-7'])")).sendKeys(filePath);
		driver.findElement(By.xpath("//button[normalize-space()='Import']")).click();

		wt.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Validate Data']")));
		driver.findElement(By.xpath("//button[normalize-space()='Validate Data']")).click();

		// checkl if the browser alert is present.
		wt.until(ExpectedConditions.alertIsPresent());

		// accept the alert
		driver.switchTo().alert().accept();
		driver.manage().window().fullscreen();

		takeScreenshot(driver);
		MyScreenRecorderUtil.stopRecording();
	
		driver.close();
		
				
	}

	public static void takeScreenshot(WebDriver driver) throws Exception {

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName = String.format("screenshot_%s.png", timestamp);

		// Create the full path to the screenshot file within the "screenshots" directory
		File screenshotFile = new File("screenshots", fileName); // Combine path and filename

		// Create the directory structure if it doesn't exist
		File directory = new File("screenshots");
		directory.mkdirs(); // Create all necessary parent directories too

		// Capture the screenshot and save it directly to the desired location
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File tempScreenshot = screenshot.getScreenshotAs(OutputType.FILE);
		Files.copy(tempScreenshot, screenshotFile);

	}

}
