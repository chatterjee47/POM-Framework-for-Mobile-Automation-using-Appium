package utils.listeners;

import com.relevantcodes.extentreports.LogStatus;
import SetupAppium.BaseClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.excelutils.ExcelUtil;
import utils.extentreports.ExtentManager;
import utils.extentreports.ExtentTestManager;


public class TestListener extends BaseClass implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    public byte[] saveScreenshotPNG (WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    public static String saveTextLog (String message) {
        return message;
    }

    public static String attachHtml(String html) {
        return html;
    }

    public void onStart(ITestContext iTestContext) {
        System.out.println("Starting Method :" + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", this.driver);
    }
    
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Finishing Method :" + iTestContext.getName());
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test Method Start: " +  getTestMethodName(iTestResult) + " Start");
        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(),"");
    }

    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test Method Success " +  getTestMethodName(iTestResult) + " Succeed");
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        try {
			ExcelUtil.setCellData("PASSED", ExcelUtil.getRowNumber(), ExcelUtil.getColumnNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Test Method Failure " +  getTestMethodName(iTestResult) + " Failed");

        Object testClass = iTestResult.getInstance();
        WebDriver driver = ((BaseClass) testClass).getDriver();

        if (driver instanceof WebDriver) {
            System.out.println("Screenshot captured for Test Case :" + getTestMethodName(iTestResult));
            saveScreenshotPNG(driver);
        }

        //Save a log on allure.
        saveTextLog(getTestMethodName(iTestResult) + " Failed and Screenshot taken!");

        //Take base64Screenshot screenshot for extent reports
        String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)driver).
                getScreenshotAs(OutputType.BASE64);

        //Extentreports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed :",
                ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));

        //Update the test result on excel sheet
        try {
			ExcelUtil.setCellData("FAILED", ExcelUtil.getRowNumber(), ExcelUtil.getColumnNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Test Method Skipped"+  getTestMethodName(iTestResult) + " Skipped");
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        try {
			ExcelUtil.setCellData("SKIPPED", ExcelUtil.getRowNumber(), ExcelUtil.getColumnNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

}
