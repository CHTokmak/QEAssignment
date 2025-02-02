package methods;

import driver.Driver;
import helper.StoreHelperElement;
import io.appium.java_client.MobileElement;
import model.ElementInfo;
import model.SelectorInfo;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import selector.Selector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Methods {

    public Logger logger = Logger.getLogger(String.valueOf(getClass()));
    Selector selector;
    WebDriver webDriver;
    long waitElementTimeout;
    long pollingEveryValue;
    FluentWait<WebDriver> wait;
    public Methods()
    {
        this.webDriver = Driver.webDriver;
        this.selector = Driver.selector;
    }
    public FluentWait<WebDriver> setFluentWait(long timeout){

        return new FluentWait<WebDriver>(webDriver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(pollingEveryValue))
                .ignoring(NoSuchElementException.class);
    }

    public WebElement findElement(By by)
    {
        return webDriver.findElement(by);
    }
    public List<WebElement> findElements(By by){

        List<WebElement> webElementList = new ArrayList<>();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by))
                .forEach(element -> webElementList.add((WebElement) element));
        return webElementList;
    }
    public String getText(By by){

        return findElement(by).getText();
    }

    public boolean isElementVisible(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
            logger.info(by.toString() + " true");
            return true;
        }catch (Exception e){
            logger.info(by.toString() + " false");
            return false;
        }
    }
    public void click(By by){

        findElement(by).click();
        logger.info("Elemente tıklandı.");
    }
    public void sendKeys(By by, String text){

        findElement(by).sendKeys(text);
        logger.info("Elemente " + text + " texti yazıldı.");
    }
    public boolean isElementClickable(By by, long timeout){

        FluentWait<WebDriver> fluentWait = setFluentWait(timeout);
        try {
            fluentWait.until(ExpectedConditions.elementToBeClickable(by));
        }catch (Exception e){
            logger.info("false" + " " + e.getMessage());
            return false;
        }
        logger.info("true");
        return true;
    }
    public By getBy(String key){

        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        By by = selectorInfo.getBy();
        logger.info(key + " elementi " + by.toString() + " by değerine sahip");
        return by;
    }

    public ElementInfo getElementInfo(String key){

        return StoreHelperElement.INSTANCE.findElementInfoByKey(key);
    }
    public WebElement findElementByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

         WebElement webElement = null;
        try {
            webElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            Assertions.fail("key = %s by = %s Element not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return webElement;
    }
    public void putValueInTestMap(String key, Object object){

        Driver.TestMap.put(key, object);
    }
    public Object getValueInTestMap(String key){

        return Driver.TestMap.get(key);
    }
}
