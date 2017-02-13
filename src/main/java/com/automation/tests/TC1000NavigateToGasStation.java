package com.automation.tests; /**
 * Created by OmriNissim on 31/01/2017.
 */

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.helpers.MapHelper;
import com.automation.helpers.SearchHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1000NavigateToGasStation {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    AppiumDriver driver;
    MapHelper mapHelper;
    SearchHelper searchHelper;

    @BeforeClass
        public void setup() throws IOException, InterruptedException {

        mapHelper = PageFactory.initElements(driver,MapHelper.class);
        searchHelper =  PageFactory.initElements(driver,SearchHelper.class);


    }

    @AfterTest
    public void closeAppuim() {

        driver.quit();
    }

    @Test
    public void test() throws InterruptedException, MalformedURLException {

        //1. open new session
        mapHelper.openNewSession("LG");

        //pre com.automation.tests.TC1000NavigateToGasStation: after the app startup all the tooltips and encouragments shold be eliminated
        //2.click anywhere on the screen
        mapHelper.clickElement(mapHelper.map);

        //3.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton);

        //4.verify that the search page opened - (Tap the main menu icon(the magnifying glass icon)
        searchHelper.verifySearchViewOpen();

        //5.Tap the search box




        System.out.println("done");
    }
}



