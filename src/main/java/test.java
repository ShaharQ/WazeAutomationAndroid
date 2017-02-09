/**
 * Created by OmriNissim on 31/01/2017.
 */

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.helpers.DriverManager;
import com.automation.helpers.Utils;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class test {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    DriverManager driverManager;
    Utils utils = new Utils();

    @BeforeTest
    public void prepareAndroidForAppium() throws IOException, InterruptedException {

        //1.open the bat file of the appuim
       // utils.openProcess("appium", "LaunchAppiumServer.bat");

        //2.open the bat file of the one of the phones
      //  utils.openProcess("LGG2", "LGG2Node.bat");

        //3.get driver and capabilities
        driverManager = new DriverManager("LGG2", "4444");

        //4.install the apk
        utils.installApk();

        //5.change the main page
        driverManager.addCapbilities("appPackage","com.waze","4444");
        driverManager.addCapbilities("appActivity","com.waze.FreeMapActivity","4444");

        //File appDir = new File("src/main/resources/");
        //File app = new File(appDir, "waze_4_16_0_0.apk");

    }

    @AfterTest
    public void closeAppuim() {
        driverManager.getDriver().quit();
    }

    @Test
    public void test() throws InterruptedException {

        By Waze = By.id("com.waze:id/mainBottomBarMenuButton");
        driverManager.getDriver().findElement(Waze).click();
        ATUReports.setWebDriver(driverManager.getDriver());
        ATUReports.add("Step Desc", false);

        System.out.println("done");
    }
}



