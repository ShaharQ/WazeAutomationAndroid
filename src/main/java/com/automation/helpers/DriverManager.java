package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mkalash on 2/7/17.
 */
public class DriverManager {

    private AppiumDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "main_list_item_container")
    public WebElement deviceStorge;

    public DriverManager(String deviceName , String port) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("newCommandTimeout", 60 * 5);
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("deviceName","test");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("app", "");
        capabilities.setCapability("appWaitActivity", "com.waze.MainActivity");
        capabilities.setCapability("appWaitPackage", "com.waze");
        capabilities.setCapability("browserName","");
        capabilities.setCapability("commandTimeout","120");


        if(deviceName.equals("LGG2")) {
            capabilities.setCapability("platformVersion", "4.4.2");
            //capabilities.setCapability("platformVersion", "5.0.2");
            capabilities.setCapability("appPackage", "com.lge.filemanager");
            capabilities.setCapability("appActivity", "com.lge.filemanager.MainActivity");
//            capabilities.setCapability("appActivity", "com.waze.MainActivity");
//            capabilities.setCapability("appPackage", "com.waze");
            capabilities.setCapability("udid", "LGD802988c53e6");
            capabilities.setCapability("bp", "6204");
        } else if(deviceName.equals("SamsungS5")){
            capabilities.setCapability("platformVersion", "6.0.1");
            capabilities.setCapability("appActivity", "com.waze.MainActivity");
            capabilities.setCapability("appPackage", "com.waze");
        }

        Object deviceType = capabilities.getCapability("platformName");

        if(deviceType.equals("Android")) {
            setDriver(new AndroidDriver(new URL("http://172.0.0.1:"+ port +"/wd/hub"), capabilities));
        } else {
            setDriver(new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities));
        }
    }

    public void addCapbilities(String capbilityName , String value, String port) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(capbilityName,value);
        if(capabilities.getCapability("PlatformName").toString() == "Android") {
            setDriver(new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities));
        } else {
            setDriver(new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities));
        }
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void setDriver(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }
}
