package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;

import java.io.*;

import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mkalash on 2/7/17.
 */
public class Utils {

    public DriverManager driverManager;

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

    private void startAppiumServer()
    {
        CommandLine command = new CommandLine(
                "/Applications/Appium.app/Contents/Resources/node/bin/node");
        command.addArgument(
                "/Applications/Appium.app/Contents/Resources/node_modules/appium/build/lib/main.js",
                false);
        command.addArgument("--address", false);
        command.addArgument("0.0.0.0");
        command.addArgument("--port", false);
        command.addArgument("4723");
        command.addArgument("--full-reset", false);
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        try {
            executor.execute(command, resultHandler);
            Thread.sleep(5000);
            System.out.println("Appium server started.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stopAppiumServer() {
        String[] command = { "/usr/bin/killall", "-KILL", "node" };
        try {
            Runtime.getRuntime().exec(command);
            System.out.println("Appium server stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
