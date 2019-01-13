package com.handling.ExtentReports.Version4;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//This Script To Generate Extent Reports, First we need to Download Extent Jars From Extent Reports website and associated to our java project
//http://extentreports.com/community/
//Here I am using version 4 to Generate Extent Reports


//Video URL ::   https://www.youtube.com/watch?v=tovz1q5Unxs

   public class Handling_ExtentReports 
   {
	   public static WebDriver driver;
	   public static String url="http://demo.nopcommerce.com/";
	   public static ExtentHtmlReporter htmlreporter;
	   public static ExtentReports extentreports;
	   public static ExtentTest extenttest;
	   
	   @BeforeTest
	   public void setExtent()
	   {
		 htmlreporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myextentreports.html");
		 
		 htmlreporter.config().setDocumentTitle("AUTOMATION REPORT"); //Title of the Report  
		 htmlreporter.config().setReportName("FUNCTION REPORT"); //Name of the Report 
		 htmlreporter.config().setTheme(Theme.DARK);
		 
		 extentreports = new ExtentReports();
		 extentreports.attachReporter(htmlreporter);
		 extentreports.setSystemInfo("Hostname", "Localhost");
		 extentreports.setSystemInfo("OS", "Windows10");
		 extentreports.setSystemInfo("Platform", "Windows");
		 extentreports.setSystemInfo("BrowserName", "Chrome");
		 extentreports.setSystemInfo("TesterName", "SHAIK PRAVEEN"); 
	   }
	   
	   @BeforeMethod
	   public void setUP()
	   {
		   System.setProperty("webdriver.chrome.driver", "E:\\Praveen_Automation\\Launching_Browsers\\Launching_Browsers_Latest\\New folder\\chromedriver.exe");
		   driver = new ChromeDriver();
		   driver.manage().window().maximize();
		   driver.manage().deleteAllCookies();
		   driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		   driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		   
		   driver.get(url); 
	   }
	  
	   @Test(priority=2)
	   public void verifyPageTitleTest()
	   {
		    extenttest = extentreports.createTest("verifyPageTitleTest"); //It will create new entry in your report
	        String pagetitle = driver.getTitle();
	        System.out.println(pagetitle);
	        Assert.assertEquals(pagetitle, "nopCommerce demo store");      
	   }
	   
	   @Test(priority=1)
	   public void nopCommerceLogoTest()
	   {
		   extenttest = extentreports.createTest("nopCommerceLogoTest");
		   boolean status = driver.findElement(By.xpath("//img[@alt='nopCommerce demo store']")).isDisplayed();
	       Assert.assertTrue(status);
	   }
	   
	   @Test(priority=3)
	   public void nopCommerceLoginTest()
	   {
		   extenttest = extentreports.createTest("nopCommerceLoginTest");
		   extenttest.createNode("LOGIN WITH VALID INPUT"); //These are Sub-Nodes, These two sub nodes will Pass finally The Login Test is Passed
		   Assert.assertTrue(true);
		   
		   extenttest.createNode("LOGIN WITH INVALID INPUT");
		   Assert.assertTrue(true); 
	   }
	  
	   @AfterMethod
	   public void tearDown(ITestResult result) throws IOException 
	   {
	    if (result.getStatus() == ITestResult.FAILURE) {
	     extenttest.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
	     extenttest.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
	     String screenshotPath = Handling_ExtentReports.getScreenshot(driver, result.getName());
	     extenttest.addScreenCaptureFromPath(screenshotPath);// adding screen shot
	    } else if (result.getStatus() == ITestResult.SKIP) {
	     extenttest.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
	    }
	    else if (result.getStatus() == ITestResult.SUCCESS) {
	     extenttest.log(Status.PASS, "Test Case PASSED IS " + result.getName());
	    }
	    driver.quit();
	   }
	   
	   public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException
	   {
		   String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		   TakesScreenshot ts = (TakesScreenshot) driver;
		   File source = ts.getScreenshotAs(OutputType.FILE);
		   
		   // after execution, you could see a folder "FailedTestsScreenshots" under src folder
		   String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		   File finalDestination = new File(destination);
		   FileUtils.copyFile(source, finalDestination);
		   return destination;
		  }
	   
    

	@AfterTest
	   public void endReport()
	   {
		  extentreports.flush(); 
	   }
	   
	   
   }
