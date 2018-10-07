package ObjectRepository_Pages;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends Keywords {

    //*********Constructor*********
    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //*********Web Elements*********
    static String SignUpTab = "//android.widget.Button[@text='SIGN_UP']";
    static String EnterName = "//android.widget.EditText[@text='Name']";
    static String FamilyName = "//android.widget.EditText[@text='Family Name']";
    
   
//    public void verifyLoginUserName (String expectedUserNameMessage) {
//        Assert.assertEquals(readText(By.xpath(errorMessageUsernameXpath)), expectedUserNameMessage);
//    }


    //**********Methods Creation**********
    public static void goToLoginPage (){
        click(By.xpath(SignUpTab));
    }
    
    public static void EnterName (XSSFRow row){
    	input(By.xpath(EnterName),row.getCell(1).toString());
    }
    
    public static void FamilyName (XSSFRow row){
    	input(By.xpath(FamilyName), row.getCell(2).toString());
    }
}
