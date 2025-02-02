package selector;

import model.ElementInfo;
import org.openqa.selenium.By;

public class WebSelector implements Selector {

  @Override
  public By getElementInfoToBy(ElementInfo elementInfo) {
    By by = null;
    String elementType = elementInfo.getType();
    String elementValue = elementInfo.getValue();
    switch (elementType){
      case "css":
        by = By.cssSelector(elementValue);
        break;
      case "id":
        by = By.id(elementValue);
        break;
      case "xpath":
        by = By.xpath(elementValue);
        break;
      case "class":
        by = By.className(elementValue);
        break;
      case "text":
        by = By.linkText(elementValue);
        break;
      default:
        throw new NullPointerException(elementInfo.getKey() + " keyine sahip elementin " + "\"" + elementType + "\"" + " değeri bulunamadı.");
    }
    return by;
  }

  @Override
  public int getElementInfoToIndex(ElementInfo elementInfo) {
    return elementInfo.getIndex();
  }
}
