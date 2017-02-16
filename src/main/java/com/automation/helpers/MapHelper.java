package com.automation.helpers;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class MapHelper extends Activity{

    public MapHelper(WebDriver driver)
    {
        super((AppiumDriver) driver);
        PageFactory.initElements(driver,this);
    }

//    @FindBy (id ="mapViewWrapperMapView")
//    public WebElement map;

//    @FindBy (id ="mainBottomBarMenuButton")
//    public WebElement searchButton;

    @FindBy(id = "com.waze:id/mainBottomBarMenuButton")
    public WebElement searchButton;

    @FindBy (id ="com.waze:id/mapViewWrapperMapView")
    public WebElement map;


    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");
    }

//    public MapHelper(AppiumDriver driver){
//        super(driver);
//    }

}
