package com.automation.helpers;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkalash on 2/7/17.
 */
public class DriverManager {


    private static DesiredCapabilities capabilities;

    public static AppiumDriver getDriver(String deviceName , String port) throws MalformedURLException {

        AppiumDriver driver = null;

        File apk = new File("/Users/rmahpud/IdeaProjects/WazeAutomationAndroid/src/main/resources/base.apk");

        capabilities = new DesiredCapabilities();
        capabilities.setCapability("newCommandTimeout", 60 * 5);

        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("deviceName","test");
        capabilities.setCapability("platformName","Android");
        //capabilities.setCapability("app", "");
        //capabilities.setCapability("appWaitActivity", "com.waze.install.SignUpWelcomeActivity");
        //capabilities.setCapability("appActivity", "com.waze.SignUpWelcomeActivity");
        capabilities.setCapability("appPackage", "com.waze");
        capabilities.setCapability("browserName","");
        capabilities.setCapability("commandTimeout","6000");
        capabilities.setCapability(MobileCapabilityType.APP,apk.getAbsolutePath());


        if(deviceName.equals("LGG2")) {
            //capabilities.setCapability("platformVersion", "4.4.2");
            capabilities.setCapability("platformVersion", "5.0.2");
//            capabilities.setCapability("appPackage", "com.lge.filemanager");
//            capabilities.setCapability("appActivity", "com.lge.filemanager.MainActivity");
            capabilities.setCapability("appActivity", "com.waze.MainActivity");
            capabilities.setCapability("appPackage", "com.waze");
            capabilities.setCapability("udid", "LGD802988c53e6");
            capabilities.setCapability("bp", "6204");

        } else if(deviceName.equals("SamsungS5")){
            capabilities.setCapability("platformVersion", "6.0.1");//          capabilities.setCapability("appWaitActivity", "HomeScreenActivity");
            //capabilities.setCapability("appActivity", "com.waze.MainActivity");

            capabilities.setCapability("appWaitPackage", "com.google.android.packageinstaller");
            capabilities.setCapability("appWaitActivity", "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");

        }

        Object deviceType = capabilities.getCapability("platformName");

        if(deviceType.equals("Android")) {
            driver = new AndroidDriver(new URL("http://localhost:"+ port +"/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
            driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();

            firstAlertHandle(driver);

        } else {
            driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
        }

        return driver;
    }

    public static void firstAlertHandle(AppiumDriver driver)
    {
//        WebElement okBtn = driver.findElementById("com.android.packageinstaller:id/permission_allow_button");
//        if(okBtn.isDisplayed())
//            okBtn.click();

        WebElement wazeNextBtn = driver.findElementById("com.waze:id/btnNext");
        if(wazeNextBtn.isDisplayed())
            wazeNextBtn.click();


        WebElement wazecrollToBtn = driver.findElementById("com.waze:id/btnScrollToBottom");
        if(wazecrollToBtn.isDisplayed())
            wazecrollToBtn.click();


        WebElement wazeAcceptBtn = driver.findElementById("com.waze:id/AcceptButton");
        if(wazeAcceptBtn.isDisplayed())
            wazeAcceptBtn.click();

    }

    public static void addCapbilities(String capbilityName , String value) throws MalformedURLException {

        capabilities.setCapability(capbilityName,value);
    }

    public static void reloadDriver(AppiumDriver driver, String port) throws MalformedURLException {

        driver.quit();

        Object deviceType = capabilities.getCapability("platformName");

        if(deviceType.equals("Android")) {
            driver = new AndroidDriver(new URL("http://172.0.0.1:"+ port +"/wd/hub"), capabilities);
        } else {
            driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
        }

    }

}
