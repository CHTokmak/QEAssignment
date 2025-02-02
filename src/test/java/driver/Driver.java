package driver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.thoughtworks.gauge.*;
import helper.SelectorFactory;
import helper.StoreHelperElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import selector.Selector;
import utils.ReadProperties;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class Driver {


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Driver.class);
    public static WebDriver webDriver;
    public static Selector selector;
    private static Logger logger = Logger.getLogger(String.valueOf(Driver.class));
    public static String TestClassName = "";
    public static String TestCaseName = "";
    public static String testResult = "";
    public static ExtentTest test;
    public String failedStep;
    public static String localTime;
    public static ConcurrentHashMap<String,Object> TestMap;

    public static String osName = FindOS.getOperationSystemName();
    public static ResourceBundle ConfigurationProp = ReadProperties.readProp("Configuration.properties");
    public static final ExtentReports extentReports = new ExtentReports();
    static Map<String, ExtentTest> extentTestMap = new HashMap<>();

    @BeforeSuite
    public void initializeDriver()
    {

    }
    @BeforeSpec
    public void beforeSpec(ExecutionContext executionContext) {

        logger.info("=========================================================================" + "\r\n");
        logger.info("------------------------SPEC-------------------------");
        TestClassName = executionContext.getCurrentSpecification().getName();
        logger.info("SPEC FILE NAME: " + executionContext.getCurrentSpecification().getFileName());
        logger.info("SPEC NAME: " + TestClassName);
    }
    @BeforeScenario
    public void initializeBaseUrl(ExecutionContext executionContext)
    {
        webDriver = DriverFactory.getDriver();
        logger.info("Driver ayağa kaldırıldı.");
        logger.info("SCENARIO TAG: " + executionContext.getCurrentScenario().getTags().toString());
        TestMap = new ConcurrentHashMap<String, Object>();
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        webDriver.get(ConfigurationProp.getString("baseUrl"));
        selector = SelectorFactory
                .createElementHelper();
        try {
            StoreHelperElement.INSTANCE.containsKey("Test");
        } catch (Throwable e) {
            logger.info(e.getMessage());
        }
    }
    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {

        logger.info("═════════  " + executionContext.getCurrentStep().getDynamicText() + "  ═════════");
    }
    @AfterStep
    public void afterStep(ExecutionContext executionContext) throws IOException {

        if (executionContext.getCurrentStep().getIsFailing()) {

            logger.info(executionContext.getCurrentSpecification().getFileName());
            failedStep = executionContext.getCurrentStep().getDynamicText();
        }
        logger.info("══════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("\r\n");
    }
    @AfterScenario
    public void endingScenario(ExecutionContext executionContext)
    {
        if (executionContext.getCurrentScenario().getIsFailing()) {
            String base64Code = captureScreenshot();
            testResult = "TEST BAŞARISIZ";
            test = createExtendSparkReports().createTest(executionContext.getCurrentScenario().getTags().toString() + " " + ConfigurationProp.getString("activeBrowser"))
                    .log(Status.FAIL, failedStep)
                    .addScreenCaptureFromBase64String(base64Code)
            ;
            extentReports.setSystemInfo("Test Info", "Case Name: " + executionContext.getCurrentScenario().getTags().toString() + ", Environment: " + ConfigurationProp.getString("activeBrowser") + ", Test Status: " + testResult);
            createExtendSparkReports().flush();
            extentTestMap.put(executionContext.getCurrentScenario().getTags().toString(),test);
        } else {

            testResult = "TEST BAŞARILI";
            test = createExtendSparkReports().createTest(executionContext.getCurrentScenario().getTags().toString() + " " + ConfigurationProp.getString("activeBrowser"))
                    .log(Status.PASS, "Test Passed");
            extentReports.setSystemInfo("Test Info", "Case Name: " + executionContext.getCurrentScenario().getTags().toString() + ", Environment: " + ConfigurationProp.getString("activeBrowser") + ", Test Status: " + testResult);
            createExtendSparkReports().flush();
            extentTestMap.put(executionContext.getCurrentScenario().getTags().toString(),test);
        }
        logger.info(testResult);
        if(webDriver != null)
        {
            webDriver.quit();
        }
        logger.info("Driver sonlandırıldı");
        logger.info("*************************************************************************" + "\r\n");
    }

    // Close the webDriver instance
    @AfterSuite
    public void closeDriver()
    {
        reportFileHandler();
        webDriver = DriverFactory.getDriver();
        webDriver.get(ConfigurationProp.getString("basereportUrl") + ConfigurationProp.getString("activeBrowser") + " Driver " + TestClassName + " " + localTime + ".html#");
    }

    public synchronized static  ExtentReports createExtendSparkReports()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy :: HH:mm");
        LocalDateTime dateTime = LocalDateTime.now();
        localTime = dateTime.format(formatter);
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("target/extent-report/" + ConfigurationProp.getString("activeBrowser") + " Driver " + TestClassName + " " + localTime + ".html");
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setReportName("CatchyLabs Test Report " + TestClassName);

        extentReports.attachReporter(extentSparkReporter);

        return extentReports;
    }
    public static String captureScreenshot()
    {
        TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
        String baseCode64 = takesScreenshot.getScreenshotAs(OutputType.BASE64);
        logger.info("Ekran Resmi Alındı");
        return baseCode64;
    }
    public static void reportFileHandler()
    {
        String directoryPath = "target/extent-report";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if(!file.getName().equals(ConfigurationProp.getString("activeBrowser") + " Driver " + TestClassName + " " + localTime + ".html"))
                {
                    file.delete();
                }
            }
            for (File file : files) {
                file.renameTo(new File("src/test/resources/extent-report-archive/" + ConfigurationProp.getString("activeBrowser") + " Driver " + TestClassName + " " + localTime + ".html"));
            }
        }
    }



}
