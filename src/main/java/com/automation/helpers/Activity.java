package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by mkalash on 2/7/17.
 */
public class Activity {

    public AppiumDriver driver;
    public WebDriverWait wait;

    public Activity(AppiumDriver activity) {

        driver = activity;
        wait = new WebDriverWait(driver,30);
        ATUReports.setWebDriver(driver);
    }


    public void waitForVisibility(WebElement element) {

        try {
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Waiting for element visibiliy failed");
            ATUReports.add("Waiting for element visibility", element.getText(), "Element is visible before timeout.",
                    "Element is not visible after timeout.", LogAs.WARNING
                    , new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            e.printStackTrace();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Waiting for element visibiliy failed");
            ATUReports.add("Waiting for element visibility",element.getText(),"Element is visible before timeout." ,
                    "Element is not found.",LogAs.WARNING
                    , new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Waiting for element visibiliy failed");
            ATUReports.add("Waiting for element visibility",element.getText(),"Element is visible before timeout." ,
                    "Element is not visible after timeout.",LogAs.WARNING
                 , new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        e.printStackTrace();
    }

    }

    public void clickElement(WebElement element , String description) // clicking element
    {

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.click();
            System.out.println("Clicked on " + description + " element");
            ATUReports.add("Clicked on " + description + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                    null);
        } catch (Exception msg) {

            try {
                System.out.println("Clicked failed trying again with JS");
                String id = element.getAttribute("id");
                ( (JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
                ATUReports.add("Clicked on " + description + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                        null);

            } catch (Exception e1) {

            }
        }

    }
    public void installApk(String phoneName,AppiumDriver driver) throws MalformedURLException, InterruptedException {

        if(phoneName.equals("LGG2")) {
            installApkLgG2();
        } else  if(phoneName.equals("SamsungS5")){
            installApkSamsungS5();
        }
    }


    public void installApkSamsungS5() throws InterruptedException {

        WebElement deviceStorage = driver.findElement(By.id("Main_list_item_container"));
        clickElement(deviceStorage , "device");

        List<WebElement> downloads = driver.findElements(By.id("text1"));
        clickElement(downloads.get(0) , "downloads");


        List<WebElement> downloadsContent = driver.findElements(By.id("text1"));
        clickElement(downloadsContent.get(0) , "downloads content");

        List<WebElement> buttons =  driver.findElements(By.className("android.widget.Button"));
        wait.until(ExpectedConditions.visibilityOf(buttons.get(1)));
        clickElement(buttons.get(1) ,"install") ;

        //WebElement end = driver.findElement(By.name("סיום"));
        //wait.until(ExpectedConditions.visibilityOf(end));
        // clickElement(end);

        Thread.sleep(2000);

        List<WebElement> waitForDownload=  driver.findElements(By.className("android.widget.TextView"));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(waitForDownload.get(1),"מתקין")));
        List<WebElement> end=  driver.findElements(By.className("android.widget.Button"));
        clickElement(end.get(0) , "finish");

    }

    public void installApkLgG2() throws MalformedURLException, InterruptedException {


        WebElement allFiles = driver.findElement(By.id("textview_allFiles"));
        clickElement(allFiles , "all files");

        List<WebElement> downloads = driver.findElements(By.id("list_item_name"));
        clickElement(downloads.get(0) , "downloads");


        List<WebElement> downloadsContent = driver.findElements(By.id("list_item_name"));
        clickElement(downloadsContent.get(3) , "downloads content");

        List<WebElement> buttons =  driver.findElements(By.id("list_item_name"));
        wait.until(ExpectedConditions.visibilityOf(buttons.get(2)));
        clickElement(buttons.get(2) , "button");

        List<WebElement> button=  driver.findElements(By.className("android.widget.Button"));
        wait.until(ExpectedConditions.visibilityOf(button.get(1)));
        clickElement(button.get(1) , "install");

        Thread.sleep(2000);

        List<WebElement> waitForDownload=  driver.findElements(By.className("android.widget.TextView"));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(waitForDownload.get(1),"מתקין")));
        List<WebElement> end=  driver.findElements(By.className("android.widget.Button"));
        clickElement(end.get(0) , "finish");
    }

    public void openNewSession(String phone) throws InterruptedException, IOException {

        //4.install the apk
        installApk(phone,driver);

        //5.change the main page
        DriverManager.addCapbilities("appWaitActivity", "com.waze.MainActivity");
        DriverManager.addCapbilities("appWaitPackage", "com.waze");
        DriverManager.addCapbilities("appPackage","com.waze");
        DriverManager.addCapbilities("appActivity","com.waze.FreeMapAppActivity");

        //6. adding the changes to the driver
        DriverManager.reloadDriver(driver , "4444");

    }


    // This function send keys to input, and verify that this keys appear in
    // input
    public void sendKeysToWebElementInput(WebElement web_element, String target_input) {
        try {
            waitForVisibility(web_element);
            web_element.clear();
            web_element.sendKeys(target_input);
            if (web_element.getAttribute("text").equals(target_input)) {
                System.out.println("Target keys sent to WebElement: " + target_input);
                ATUReports.add("Target keys sent.", target_input, target_input, LogAs.PASSED, null);
                Assert.assertTrue(true);
            } else {
                System.out.println("Target keys sent: " + target_input + ", but not appear in the input itself: "
                        + web_element.getAttribute("value"));
                ATUReports.add("Target keys send.", target_input, web_element.getAttribute("value"), LogAs.FAILED,
                        null);
                Assert.assertTrue(false);
            }
        } catch (Exception msg) {
            System.out.println("Fail to sent target keys: " + target_input);
            ATUReports.add("Target keys sent.", "True.", "False", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }
    }

    public void clickBackOnTheDevice() throws AWTException {
        try {
            Object deviceType = driver.getCapabilities().getCapability("platformName");

            if(deviceType.equals("Android")) {
                ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.BACK);
            } else {
                driver.navigate().back();
            }

            System.out.println("press on the back icon.");
            ATUReports.add("press on the back icon.", "True.","True.", LogAs.PASSED, null);
        } catch (Exception e) {
            System.out.println("Fail to press on the back icon.");
            ATUReports.add("Fail to press on the back icon.", "True.", "False", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }

        }

        public void sendKeyboardKeys(int number , String description) {

           try {
            ((AndroidDriver)driver).pressKeyCode(number);
            System.out.println("press on the " + description + " key on the keyboard.");
            ATUReports.add("press on the " + description + " key on the keyboard.", "True.","True.", LogAs.PASSED, null);

           } catch (Exception e) {
               e.printStackTrace();
               System.out.println("Fail to press on the key" + description);
               ATUReports.add("Fail to press on the key" + description, "True.", "False", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
           }
        }

        public void TapOnTheScreenByCoordinates(int x, int y ,String description) {
            try {
                driver.tap(1, x, y, 1);
                System.out.println("Clicked on " + description + " element");
                ATUReports.add("Clicked on " + description + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                        null);
            } catch (Exception msg) {
                System.out.println("Fail to Tap on the screen on the element " + description);
                ATUReports.add("Fail to Tap on the screen on the element " + description, "True.", "False", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            }
        }

}
