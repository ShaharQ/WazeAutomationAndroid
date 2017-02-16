package com.automation.helpers;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Listeners;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class MapHelper extends Activity{

    @FindBy (id ="mapViewWrapperMapView")
    public WebElement map;
    @FindBy (id ="mainBottomBarMenuButton")
    public WebElement searchButton;
    @FindBy (id ="navBarLine")
    public WebElement navigateBarButton;
    @FindBy (id ="mainBottomBar")
    public WebElement BottomBar;


    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public MapHelper(AppiumDriver driver){
        super(driver);
    }

}
