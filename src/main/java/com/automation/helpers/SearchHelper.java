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

import java.util.List;

/**
 * Created by mkalash on 2/12/17.
 */

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class SearchHelper extends Activity{

    @FindBy(id ="menuSettings")
    public WebElement settingsButton;
    @FindBy(id ="menuSwitchOff")
    public WebElement switchOffButton;
    @FindBy(id ="WazeProfileImage")
    public WebElement profileImage;
    @FindBy(id ="searchBox")
    public WebElement searchBox;
    @FindBy(id ="btnClearSearch")
    public WebElement exitSearch;
    @FindBy(id ="mainContainer")
    public List<WebElement> searchResults;
    @FindBy(className ="android.widget.TextView")
    public List<WebElement> footerButtons;
    @FindBy(id ="addressPreviewMore")
    public WebElement threePoints;
    @FindBy(id ="addressPreviewGoButton")
    public WebElement previewGoButton;
    @FindBy(id ="fragNavResGoText")
    public WebElement goNewButton;

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public SearchHelper(AppiumDriver driver){super(driver);}

    public void verifySearchViewOpen() {

        try {
            waitForVisibility(settingsButton);
            waitForVisibility(switchOffButton);
            waitForVisibility(profileImage);

            System.out.println("Verify that search box appeared.");
            ATUReports.add("Verify that search box appeared.", "Success.","Success.", LogAs.PASSED, null);

        } catch(Exception e) {
            e.printStackTrace();
            ATUReports.add("The page can't load" + e.getMessage(), LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            Assert.assertTrue(false);
        }
    }

    public void verifyThatWeCanSeeTheResults() {

        try {

            if(searchResults.size() > 0  && searchResults.get(0).isDisplayed()) {
                System.out.println("Verify that we can see the results of the search menu.");
                ATUReports.add("Verify that we can see the results of the search menu.", "Success.","Success.", LogAs.PASSED, null);
            } else {
                System.out.println("Not Verify that we can see the results of the search menu.");
                ATUReports.add("Not Verify that we can see the results of the search menu.", "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            }

        } catch(Exception e) {
            System.out.println("Not Verify that we can see the results of the search menu.");
            ATUReports.add("Not Verify that we can see the results of the search menu.", "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        }
    }

    public void selectTheFirstResult() {

        if(searchResults.get(0).isDisplayed() ) {
            clickElement(searchResults.get(0));
            System.out.println("Click on the first search result.");
            ATUReports.add("Click on the first search result.", "Success.","Success.", LogAs.PASSED, null);
        } else {
            System.out.println("Can't Click on the first search result.");
            ATUReports.add("Can't Click on the first search result.", "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        }

    }

}
