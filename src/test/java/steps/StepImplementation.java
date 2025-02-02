package steps;

import com.thoughtworks.gauge.*;
import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class StepImplementation {
    @Step({"<seconds> saniye bekle", "Wait <second> seconds"})
    public void waitBySecond(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
