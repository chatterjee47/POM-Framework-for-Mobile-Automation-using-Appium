package ObjectRepository_Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keywords {
    public static WebDriver driver;
    public WebDriverWait wait;
  
    public Keywords (WebDriver driver, WebDriverWait wait){
    	Keywords.driver = driver;
        this.wait = wait;
    }

    public static void click (By elementLocation) {
        driver.findElement(elementLocation).click();
    }
    
    public static void input (By elementLocation, String text) {
        driver.findElement(elementLocation).sendKeys(text);
    }

    public String readText (By elementLocation) {
        return driver.findElement(elementLocation).getText();
    }
}
