package webdrivermanagerexamples;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utilities.SeleniumUtil;

public class LoginIntoSouceApp {

	public static void main(String[] args) {
		WebDriver driver=SeleniumUtil.setUp("chrome", "https://www.saucedemo.com/");

		SeleniumUtil.typeInput(driver.findElement(By.id("user-name")), "standard_user");
		SeleniumUtil.typeInput(driver.findElement(By.id("password")), "secret_sauce");
		SeleniumUtil.clickOnElement(driver.findElement(By.id("login-button")));
		
		System.out.println(SeleniumUtil.getCurrentPageTitle());
		
	}

}
