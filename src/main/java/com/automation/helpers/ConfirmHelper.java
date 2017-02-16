package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by mkalash on 2/14/17.
 */
public class ConfirmHelper extends Activity{

        @FindBy(id = "confirmClose")
        public WebElement noThanksButton;

        //Set Peopert for ATU Reporter Configuration
        {
            System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

        }

        public ConfirmHelper(AppiumDriver driver) {
            super(driver);
        }
}


