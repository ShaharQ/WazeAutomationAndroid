package com.automation.tests; /**
 * Created by OmriNissim on 31/01/2017.
 */

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.helpers.*;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1000NavigateFromSearch {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    AppiumDriver driver;
    MapHelper mapHelper = new MapHelper(driver);
    SearchHelper searchHelper = new SearchHelper(driver);

    @BeforeTest
        public void setup() throws IOException, InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer.bat");
        Utils.openProcess("LGG2", "LGG2Node.bat");
        driver = DriverManager.getDriver("LGG2", "4444");
        mapHelper = new MapHelper(driver);
        searchHelper = new SearchHelper(driver);
        mapHelper = PageFactory.initElements(driver,MapHelper.class);
        searchHelper =  PageFactory.initElements(driver,SearchHelper.class);

    }

    @AfterTest
    public void closeAppium() {

        driver.quit();
    }

    @Test
    public void test() throws InterruptedException, IOException, AWTException {

        //1. open new session
        mapHelper.openNewSession("LG");

        //pre com.automation.tests.TC1000NavigateFromSearch: after the app startup all the tooltips and encouragments shold be eliminated
        //2.click anywhere on the screen
        mapHelper.clickElement(mapHelper.map);

        //3.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton);

        //4.verify that the search page opened - (Tap the main menu icon(the magnifying glass icon)
        searchHelper.verifySearchViewOpen();

        //5.write hike in the edit box
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"hike");

        //6.Tap the close icon in the search box
        searchHelper.clickElement(searchHelper.exitSearch);

        //7.Enter the string 'hike' and tap enter
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"hike" + Keys.ENTER);

        //8.Search results should appear after a few seconds
        searchHelper.verifyThatWeCanSeeTheResults();

        //9.Tap 'Google'
        searchHelper.clickElement(searchHelper.footerButtons.get(2));

        //10.Tap 'Places'
        searchHelper.clickElement(searchHelper.footerButtons.get(1));

        //11.Tap 'Search Results'
        searchHelper.clickElement(searchHelper.footerButtons.get(0));

        //12.Search the first results
        searchHelper.selectTheFirstResult();

        //13.Tap the 'more options' icon (the grey rectangle with the three white dots)
        searchHelper.clickElement(searchHelper.threePoints);

        //14.Tap back(the Android action button)
        searchHelper.clickBackOnTheDevice();

        //15.Tap 'GO'
        searchHelper.clickElement(searchHelper.previewGoButton);

        //16.Tap 'GO now'
        searchHelper.clickElement(searchHelper.goNewButton);

        //17.Tap the navigation list bar(where the route directions are)
        mapHelper.clickElement(mapHelper.navigateBarButton);

        //18.Tap 'Reports Ahead
        DirectionsHelper directionsHelper = new DirectionsHelper(driver);
        directionsHelper.clickElement(directionsHelper.reportAhead);

        //19.Tap 'Next Turns'
        directionsHelper.clickElement(directionsHelper.nextTurns);

        //20.Tap back(the Android action button)
        directionsHelper.clickBackOnTheDevice();

        //21.Open the ETA popup by tapping the blue eta arrow
        mapHelper.clickElement(mapHelper.BottomBar);

        //22.Tap 'stop'
        ETAPopupHelper etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton);

        //23.Tap 'No thanks'
        ConfirmHelper confirmHelper = new ConfirmHelper(driver);
        confirmHelper.clickElement(confirmHelper.noThanksButton);

        System.out.println("done");

    }
}



