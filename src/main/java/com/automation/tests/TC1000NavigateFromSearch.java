package com.automation.tests; /**
 * Created by OmriNissim on 31/01/2017.
 */

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.helpers.DriverManager;
import com.automation.helpers.MapHelper;
import com.automation.helpers.SearchHelper;
import com.automation.helpers.Utils;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1000NavigateToGasStation {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    AppiumDriver driver;
    MapHelper mapHelper;
    SearchHelper searchHelper;

    @BeforeTest
        public void setup() throws IOException, InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer.bat");
        Utils.openProcess("LGG2", "LGG2Node.bat");
        driver = DriverManager.getDriver("LGG2", "4444");
        mapHelper = PageFactory.initElements(driver,MapHelper.class);
        searchHelper =  PageFactory.initElements(driver,SearchHelper.class);

    }

    @AfterTest
    public void closeAppium() {

        driver.quit();
    }

    @Test
    public void test() throws InterruptedException, IOException {

        //1. open new session
        mapHelper.openNewSession("LG");

        //pre com.automation.tests.TC1000NavigateToGasStation: after the app startup all the tooltips and encouragments shold be eliminated
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

        System.out.println("done");
    }
}



