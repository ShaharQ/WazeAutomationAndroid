package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mkalash on 2/7/17.
 */
public class Activity {

    private AppiumDriver driver;
    private WebDriverWait wait;

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

    public void openProcess(String name,String proccessName) throws InterruptedException, IOException {

        try {
            if (System.getProperty("os.name").startsWith("Mac OS X")) {

                File proccessFile = new File("src/main/resources/" + proccessName + ".sh");
                ProcessBuilder pb = new ProcessBuilder(proccessFile.getAbsolutePath());
                pb.redirectErrorStream(true);
                File dir = new File("src/main/resources/");
                pb.directory(dir);
                System.out.println("About to start " + proccessFile.getAbsolutePath());
                Process p = pb.start();


            } else {
//                List cmdAndArgs = Arrays.asList("cmd", "/c", "start", proccessName);
//                File dir = new File("src/main/resources/");
//                ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
//                pb.directory(dir);
//                Process p = pb.start();
                File proccessFile = new File("src/main/resources/" + proccessName + ".bat");
                final ProcessBuilder pb = new ProcessBuilder(proccessFile.getAbsolutePath());
                pb.redirectErrorStream(true);
                File dir = new File("src/main/resources/");
                pb.directory(dir);
                Process p = pb.start();
            }
            System.out.println("The process:" + name + " has started.");
            ATUReports.add("The process:" + name + " has not started.","True." ,"False.",LogAs.PASSED, null);

        } catch (Exception E) {
            System.out.println("The process:" + name + " has not started.");
            ATUReports.add("The process:" + name + " has not started.","True." ,"False.", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
        }

        Thread.sleep(10000);

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
        clickElement(deviceStorage);

        List<WebElement> downloads = driver.findElements(By.id("text1"));
        clickElement(downloads.get(0));


        List<WebElement> downloadsContent = driver.findElements(By.id("text1"));
        clickElement(downloadsContent.get(0));

        List<WebElement> buttons =  driver.findElements(By.className("android.widget.Button"));
        wait.until(ExpectedConditions.visibilityOf(buttons.get(1)));
        clickElement(buttons.get(1));

        //WebElement end = driver.findElement(By.name("סיום"));
        //wait.until(ExpectedConditions.visibilityOf(end));
       // clickElement(end);

        Thread.sleep(2000);

        List<WebElement> waitForDownload=  driver.findElements(By.className("android.widget.TextView"));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(waitForDownload.get(1),"מתקין")));
        List<WebElement> end=  driver.findElements(By.className("android.widget.Button"));
        clickElement(end.get(0));

    }

    public void installApkLgG2() throws MalformedURLException, InterruptedException {


            WebElement allFiles = driver.findElement(By.id("textview_allFiles"));
            clickElement(allFiles);

            List<WebElement> downloads = driver.findElements(By.id("list_item_name"));
            clickElement(downloads.get(0));


            List<WebElement> downloadsContent = driver.findElements(By.id("list_item_name"));
            clickElement(downloadsContent.get(3));

            List<WebElement> buttons =  driver.findElements(By.id("list_item_name"));
            wait.until(ExpectedConditions.visibilityOf(buttons.get(2)));
            clickElement(buttons.get(2));

            List<WebElement> button=  driver.findElements(By.className("android.widget.Button"));
            wait.until(ExpectedConditions.visibilityOf(button.get(1)));
            clickElement(button.get(1));

            Thread.sleep(2000);

            List<WebElement> waitForDownload=  driver.findElements(By.className("android.widget.TextView"));
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(waitForDownload.get(1),"מתקין")));
            List<WebElement> end=  driver.findElements(By.className("android.widget.Button"));
            clickElement(end.get(0));
    }

    public void clickElement(WebElement element) // clicking element
    {
        String text = element.getText();
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.click();
            System.out.println("Clicked on " + text + " element");
            ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                    null);
        } catch (Exception msg) {

            try {
                System.out.println("Clicked failed trying again with JS");
                String id = element.getAttribute("id");
                ( (JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
                ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                        null);

            } catch (Exception e1) {

            }
        }

    }
    public void openNewSession(String Phone) throws InterruptedException, IOException {

        openProcess("appium", "LaunchAppiumServer.bat");

        openProcess("LGG2", "LGG2Node.bat");

        //3.get driver and capabilities
        driver = DriverManager.getDriver("LGG2", "4444");

        //4.install the apk
        installApk("LGG2",driver);

        //5.change the main page
        DriverManager.addCapbilities("appWaitActivity", "com.waze.MainActivity");
        DriverManager.addCapbilities("appWaitPackage", "com.waze");
        DriverManager.addCapbilities("appPackage","com.waze");
        DriverManager.addCapbilities("appActivity","com.waze.FreeMapAppActivity");

        //6. adding the changes to the driver
        DriverManager.reloadDriver(driver , "4444");


    }
}
