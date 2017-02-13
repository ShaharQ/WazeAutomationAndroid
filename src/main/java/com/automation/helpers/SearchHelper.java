package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Listeners;

/**
 * Created by mkalash on 2/12/17.
 */

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class SearchHelper extends Activity{

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    @FindBy(id ="menuSettings")
    public WebElement settingsButton;
    @FindBy(id ="menuSwitchOff")
    public WebElement switchOffButton;
    @FindBy(id ="WazeProfileImage")
    public WebElement profileImage;

    public SearchHelper(AppiumDriver driver){
        super(driver);
    }

    public void verifySearchViewOpen() {

        try {
            waitForVisibility(settingsButton);
            waitForVisibility(switchOffButton);
            waitForVisibility(profileImage);

        } catch(Exception e) {
            e.printStackTrace();
            ATUReports.add("The page can't load" + e.getMessage(), LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            Assert.assertTrue(false);
        }
    }
}
