package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class SeleniumUtil {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Properties prop;
	public static Actions action;

	public static WebDriver setUp(String browserName, String appUrl) {
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
		// implicit wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// maximize browser window
		driver.manage().window().maximize();
		// Define WebDriverWait
		wait = new WebDriverWait(driver, 20);
		// enter the required URL
		driver.get(appUrl);
		action = new Actions(driver);
		return driver;
	}

	public static void typeInput(WebElement element, String input) {
		waitUntilElementIsVisible(element);
		element.clear();
		element.sendKeys(input);
	}

	public static void clickOnElement(WebElement element) {
		waitUntilElementClickable(element);
		element.click();
	}

	public static void waitUntilTextToBeMatched(By element, String text) {
		wait.until(ExpectedConditions.textToBe(element, text));
	}

	public static void waitUntilElementClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitUntilPageTitleMatched(String title) {
		wait.until(ExpectedConditions.titleIs(title));
	}

	public static void waitUntilElementIsVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void loadPropertyFile(String filePath) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(filePath);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getDataFromPropertyFile(String key) {
		return prop.getProperty(key);
	}

	// TODO:
	
	//  Dropdown InnerText Attribute Value PageTitle PageUrl
	 
	public static String getCurrentPageTitle(String title) {
		wait.until(ExpectedConditions.titleIs(title));
		return driver.getTitle();
	}
	
	public static String getCurrentPageTitle() {
		return driver.getTitle();
	}

	public static String getAttributeForElement(WebElement element, String attributeName) {
		waitUntilElementIsVisible(element);
		return element.getAttribute(attributeName);
	}

	public static String getTextForElement(WebElement element) {
		waitUntilElementIsVisible(element);
		return element.getText();
	}

	public static Select getDropdown(WebElement element) {
		waitUntilElementIsVisible(element);
		return new Select(element);
	}

	public static String getTextFromDropdown(Select select) {
		return select.getFirstSelectedOption().getText();
	}

	public static void mouseHoverOverInTheElement(Actions act, WebElement element) {
		act.moveToElement(element).perform();
	}

	public static void rightClick(Actions action, WebElement option) {
		action.moveToElement(option).contextClick().build().perform();
	}

	public static void mouseHoverWithCords(Actions act, WebElement option, int x, int y) {
		act.moveToElement(option, x, y).perform();
	}

	public static void performDranAndDrop(Actions act, WebElement src, WebElement target) {
		waitUntilElementIsVisible(src);
		waitUntilElementIsVisible(target);
		act.dragAndDrop(src, target).build().perform();
	}

	public static void copyTextFromElement(WebElement element) {
		action.doubleClick(element).sendKeys(Keys.chord(Keys.CONTROL, "c")).perform();
	}

	public static void pasteCopiedTextIntoElement(WebElement element) {
		action.moveToElement(element).sendKeys(Keys.chord(Keys.CONTROL, "v")).perform();
	}

	public static void copyAndPasteText(WebElement copyFrom, WebElement copyTo) {
		copyTextFromElement(copyFrom);
		pasteCopiedTextIntoElement(copyTo);
	}

	public static void takeScreenShot(String location) {
		// type cast WebDriver instance into TakesScreenshot interface
		TakesScreenshot ts = (TakesScreenshot) driver;
		// get screenshot using getScreenshotAs() of TakesScreenshot interface
		File file = ts.getScreenshotAs(OutputType.FILE);
		// store above screenshot into required location
		try {
			FileUtils.copyFile(file, new File(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
