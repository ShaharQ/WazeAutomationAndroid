package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mkalash on 2/7/17.
 */
public class Utils {

    public DriverManager driverManager;

    public void openProcess(String name,String proccessName) throws InterruptedException {

        try {
            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                String[] args = new String[] {"/bin/bash", "-c", proccessName};
                ProcessBuilder pb = new ProcessBuilder(args);
                File dir = new File("src/main/resources/");
                pb.directory(dir);
                Process p = pb.start();
                //Runtime.getRuntime().exec(new String[]{"open /","-c","src/main/resources/" + proccessName});

            } else {
                List cmdAndArgs = Arrays.asList("cmd", "/c", "start", proccessName);
                File dir = new File("src/main/resources/");
                ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
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

    public void installApk(String phoneName){

        if(phoneName.equals("LGG2")) {
            installApkLgG2();
        } else  if(phoneName.equals("SamsungS5")){
            installApkSamsungS5();
        }
    }


    public void installApkSamsungS5() {


        WebElement deviceStorage = driverManager.getDriver().findElement(By.id("Main_list_item_container"));
        clickElementJS(deviceStorage);

        List<WebElement> downloads = driverManager.getDriver().findElements(By.id("text1"));
        clickElementJS(downloads.get(0));


        List<WebElement> downloadsContent = driverManager.getDriver().findElements(By.id("text1"));
        clickElementJS(downloadsContent.get(0));

        List<WebElement> buttons =  driverManager.getDriver().findElements(By.className("android.widget.Button"));
        driverManager.getWait().until(ExpectedConditions.visibilityOf(buttons.get(1)));
        clickElement(buttons.get(1));

        WebElement end =  driverManager.getDriver().findElement(By.name("סיום"));
        driverManager.getWait().until(ExpectedConditions.visibilityOf(end));
        clickElement(end);

    }

    public void installApkLgG2() {

            WebElement deviceStorage = driverManager.getDriver().findElement(By.id("btnAllFiles"));
            clickElementJS(deviceStorage);

            List<WebElement> downloads = driverManager.getDriver().findElements(By.id("list_item_name"));
            clickElementJS(downloads.get(0));


            List<WebElement> downloadsContent = driverManager.getDriver().findElements(By.id("list_item_name"));
            clickElementJS(downloadsContent.get(3));

            List<WebElement> buttons =  driverManager.getDriver().findElements(By.id("list_item_name"));
            driverManager.getWait().until(ExpectedConditions.visibilityOf(buttons.get(2)));
            clickElement(buttons.get(2));

            WebElement install =  driverManager.getDriver().findElement(By.name("התקן"));
            driverManager.getWait().until(ExpectedConditions.visibilityOf(install));
            clickElement(install);

            WebElement end =  driverManager.getDriver().findElement(By.name("סיום"));
            driverManager.getWait().until(ExpectedConditions.visibilityOf(end));
            clickElement(end);
    }

    public void clickElement(WebElement element) // clicking element
    {
        String text = element.getText();
        try {
            driverManager.getWait().until(ExpectedConditions.visibilityOf(element));
            element.click();
            System.out.println("Clicked on " + element.getText() + " element");
            ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                    null);
        } catch (Exception msg) {

            try {
                System.out.println("Clicked failed trying again with JS");
                String id = element.getAttribute("id");
                ((JavascriptExecutor) driverManager.getDriver()).executeScript("document.getElementById(\"" + id + "\").click();");
                ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                        null);

            } catch (Exception e1) {

            }
        }

    }

    public void clickElementJS(WebElement element) // clicking element
    {
        String text = null;
        try {
            text = element.getText();
            String id = element.getAttribute("id");
            ((JavascriptExecutor) driverManager.getDriver()).executeScript("document.getElementById(\"" + id + "\").click();");
            ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                    null);
        } catch (Exception msg) {

            try {
                System.out.println("Clicked failed trying again with selenium");
                driverManager.getWait().until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                System.out.println("Clicked on " + element.getText() + " element");
                ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                        null);
            } catch (Exception e1) {
                ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked failed:"+msg.getMessage(), LogAs.WARNING,
                        new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));

            }
        }

    }

    public void clickElementWithOutIdJS(WebElement element) // clicking element
    {
        String text = element.getText();
        try {
            ((JavascriptExecutor) driverManager.getDriver()).executeScript("arguments[0].click();", element);
            System.out.println("Clicked on " + text + " element");
            ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED, null);
        } catch (Exception msg) {
            try {
                System.out.println("Clicked failed trying again with selenium");
                driverManager.getWait().until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                System.out.println("Clicked on " + element.getText() + " element");
                ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                        null);
            } catch (Exception e1) {
                ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked failed:" + msg.getMessage(), LogAs.WARNING,
                        new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));

            }
        }
    }

}
