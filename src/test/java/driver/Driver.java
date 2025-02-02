package driver;

import com.thoughtworks.gauge.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver {

    // Holds the WebDriver instance
    private WebDriver webDriver;

    // Initialize a webDriver instance of required browser
    // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver
    @BeforeSuite
    public void initializeDriver()
    {
        webDriver = DriverFactory.getDriver();
    }
    @BeforeScenario
    public void initializeBaseUrl()
    {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        webDriver.get("https://catchylabs-webclient.testinium.com/signIn");
    }

    // Close the webDriver instance
    @AfterSuite
    public void closeDriver()
    {
        if(webDriver != null)
        {
            webDriver.quit();
        }
    }

}
