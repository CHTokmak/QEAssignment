package mobile.gaugeProject.selector;

import mobile.gaugeProject.model.ElementInfo;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public class AndroidSelector implements Selector {

  @Override
  public By getElementInfoToBy(ElementInfo elementInfo) {
    By by = null;
    String androidType = elementInfo.getAndroidType();
    String androidValue = elementInfo.getAndroidValue();
    switch (androidType){
      case "css":
        by = MobileBy.cssSelector(androidValue);
        break;
      case "id":
        by = MobileBy.id(androidValue);
        break;
      case "xpath":
        by = MobileBy.xpath(androidValue);
        break;
      case "class":
        by = MobileBy.className(androidValue);
        break;
      case "text":
        by = MobileBy.linkText(androidValue);
        break;
      case "androidUI":
        by = MobileBy.AndroidUIAutomator(androidValue);
        break;
      default:
        throw new NullPointerException(elementInfo.getKey() + " keyine sahip elementin " + "\"" + androidType + "\"" + " android tip değeri bulunamadı.");
    }
    return by;
  }

  @Override
  public int getElementInfoToIndex(ElementInfo elementInfo) {
    return elementInfo.getAndroidIndex();
  }
}
