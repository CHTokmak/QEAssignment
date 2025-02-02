package steps;

import com.thoughtworks.gauge.*;
import io.appium.java_client.MobileElement;
import methods.Methods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class StepImplementation {
    private static Logger logger = Logger.getLogger(String.valueOf(StepImplementation.class));
    protected static Methods methods;
    private static StepImplementation stepImplementation;

    public StepImplementation() {

        methods = new Methods();
    }
    public static StepImplementation getInstance() {
        if (stepImplementation == null) {
            stepImplementation = new StepImplementation();
        }
        return stepImplementation;
    }

    @Step({"<seconds> saniye bekle"})
    public void waitBySecond(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Step({"<key> elementinin görünür olması kontrol edilir"})
    public void controlIsElementVisible(String key) {

        assertTrue(methods.isElementVisible(methods.getBy(key), 30), "Element görünür değil");
        logger.info(key + "elementi sayfa üzerinde görülebilir");
    }
    @Step("<key> elementi varsa tıkla <timeout>")
    public void clickElementIfVisible(String key, long timeout) {

        if (methods.isElementVisible(methods.getBy(key), timeout)) {
            methods.click(methods.getBy(key));
        }
    }
    @Step({"<key> elementinin tıklanabilir olması kontrol edilir"})
    public void controlIsElementClickable(String key) {

        assertTrue(methods.isElementClickable(methods.getBy(key), 30), "Element tıklanabilir değil");
    }
    @Step({"<key> li elementi bul, temizle ve <text> değerini yaz"})
    public void sendKeysByKey(String key, String text) {
        WebElement webElement = methods.findElementByKey(key);
        webElement.clear();
        webElement.sendKeys(text);
    }
    @Step({"<key> li elemente <text> değerini yaz"})
    public void sendKeysNoclearByKey(String key, String text) {
        WebElement webElement = methods.findElementByKey(key);
        webElement.sendKeys(text);
    }
    @Step("<key> elementinin text değeri <expectedText> değerine eşit mi")
    public void getElementText(String key, String expectedText) {

        String actualText = methods.getText(methods.getBy(key)).replace("\r", "").replace("\n", "").trim();
        logger.info("Beklenen text: " + expectedText);
        logger.info("Alınan text: " + actualText);
        assertEquals(expectedText, actualText, "Text değerleri eşit değil");
        logger.info("Text değerleri eşit");
    }
    @Step("<key> elementinin text değerini <mapKey> keyinde tut")
    public void getElementTextAndSave(String key, String mapKey) {

        String text = methods.getText(methods.getBy(key)).trim();
        logger.info(text);
        methods.putValueInTestMap(mapKey, text);
    }
    @Step("<key> elementinin text değeri <mapKey> değerinde tutulan değere eşit mi")
    public void checkElementTextKeyValue(String key, String mapKey) {

        String text = "";
        if (key.endsWith("KeyValue")) {
            text = ((WebElement) methods.getValueInTestMap(key)).getText();
        } else {
            text = methods.getText(methods.getBy(key));
        }
        String expectedValue = methods.getValueInTestMap(mapKey).toString();
        assertEquals(expectedValue, text, "expected: " + expectedValue + " actual: " + text);
        logger.info("Beklenen text: " + expectedValue);
        logger.info("Alınan text: " + text);
    }
    @Step("<key> elementinin text değeri <mapKey> değerinde tutulan değerden farklı mı")
    public void checkElementTextKeyDifferentValue(String key, String mapKey) {

        String text = "";
        if (key.endsWith("KeyValue")) {
            text = ((WebElement) methods.getValueInTestMap(key)).getText();
        } else {
            text = methods.getText(methods.getBy(key));
        }
        String expectedValue = methods.getValueInTestMap(mapKey).toString();
        assertNotEquals(expectedValue, text, "expected: " + expectedValue + " actual: " + text);
        logger.info("Beklenen text: " + expectedValue);
        logger.info("Alınan text: " + text);
    }
    @Step("<key> elementinin değeri <mapKey> değerinde tutulan değerden doğru düşüldü mü sonuc: <key1>")
    public void checkRemainings(String key, String mapKey,String key1) throws ParseException {
        String text = "";
        if (key.endsWith("KeyValue")) {
            text = ((WebElement) methods.getValueInTestMap(key)).getText();
        } else {
            text = methods.getText(methods.getBy(key));
        }
        String guncelBakiye = "";
        if (key.endsWith("KeyValue")) {
            guncelBakiye = ((WebElement) methods.getValueInTestMap(key1)).getText();
        } else {
            guncelBakiye = methods.getText(methods.getBy(key1));
        }
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        double güncelBakiye = format.parse(guncelBakiye).doubleValue();
        double islem = format.parse(text).doubleValue();
        double bakiye = format.parse(methods.getValueInTestMap(mapKey).toString()).doubleValue();
        double sonuc = bakiye - islem;
        assertEquals(sonuc, güncelBakiye, "expected: " + sonuc + " actual: " + guncelBakiye);
    }
    @Step("<key> elementinin değeri <mapKey> değerinde tutulan değere doğru eklendi mi sonuc: <key1>")
    public void checkRemainingsPlus(String key, String mapKey,String key1) throws ParseException {
        String islemdgeri = "";
        if (key.endsWith("KeyValue")) {
            islemdgeri = ((WebElement) methods.getValueInTestMap(key)).getText();
        } else {
            islemdgeri = methods.getText(methods.getBy(key));
        }
        String guncelBakiye = "";
        if (key.endsWith("KeyValue")) {
            guncelBakiye = ((WebElement) methods.getValueInTestMap(key1)).getText();
        } else {
            guncelBakiye = methods.getText(methods.getBy(key1));
        }
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        double güncelBakiye = format.parse(guncelBakiye).doubleValue();
        double islem = format.parse(islemdgeri).doubleValue();
        double bakiye = format.parse(methods.getValueInTestMap(mapKey).toString()).doubleValue();
        double sonuc = bakiye + islem;
        assertEquals(sonuc, güncelBakiye, "expected: " + sonuc + " actual: " + guncelBakiye);
    }
    @Step({"<key> li elementi bul, temizle ve rastgele bir değer yaz"})
    public void sendKeysByKey(String key) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        WebElement webElement = methods.findElementByKey(key);
        webElement.clear();
        webElement.sendKeys(saltStr);
    }



}
