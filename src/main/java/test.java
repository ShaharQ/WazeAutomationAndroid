/**
 * Created by OmriNissim on 31/01/2017.
 */


import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import javax.jws.WebResult;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by OmriNissim on 26/01/2017.
 */
/**
 * Created by OmriNissim on 26/01/2017.
 */
public class test {

    public AndroidDriver driver;
    Process p , p1;

    @BeforeTest
    public void prepareAndroidForAppium() throws IOException, InterruptedException {

        List cmdAndArgs = Arrays.asList("cmd", "/c","start","LaunchAppiumServer.bat");
        File dir = new File("C:\\Users\\OmriNissim\\Downloads\\");
        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(dir);
        p = pb.start();
        Thread.sleep(5000);

        List cmdAndArgs1 = Arrays.asList("cmd", "/c","start", "LGG2Node.bat");
        File dir1 = new File("C:\\Users\\OmriNissim\\Downloads\\");
        ProcessBuilder pb1= new ProcessBuilder(cmdAndArgs1);
        pb1.directory(dir1);
        p1 = pb1.start();
        Thread.sleep(15000);

        File appDir = new File("/Users/OmriNissim/Downloads/");
        File app = new File(appDir, "waze_4_16_0_0.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("newCommandTimeout", 60 * 5);
        capabilities.setCapability("automationName", "Appium");

        //mandatory capabilities
        capabilities.setCapability("deviceName","");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("platformVersion","4.4.2");
        //other caps
        capabilities.setCapability("app", "");
        capabilities.setCapability("udid", "LGD802988c53e6");//"LGD8551e0f603d"   "1e4f0c86" "15a6d608ec84785001dcc11f91b052e70d8126c9"/*"Needed if testing on IOS on a specific device. This will be the UDID"*/;
        capabilities.setCapability("bp", "6204");
        capabilities.setCapability("appPackage", "com.waze");
        capabilities.setCapability("appActivity", "com.waze.FreeMapAppActivity");
        capabilities.setCapability("appWaitActivity", "com.waze.MainActivity");
        capabilities.setCapability("appWaitPackage", "com.waze");
        driver =  new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);


    }

    @AfterTest
    public void closeAppuim(){
        driver.quit();
    }

    @Test
    public void test() throws InterruptedException {

        By Waze = By.id("com.waze:id/mainBottomBarMenuButton");
        driver.findElement(Waze).click();
        p.destroy();
        p1.destroy();
        System.out.println("done");
    }
}



