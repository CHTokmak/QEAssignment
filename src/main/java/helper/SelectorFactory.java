package helper;

import selector.Selector;
import selector.WebSelector;

public class SelectorFactory {

  private SelectorFactory() {

  }

  public static Selector createElementHelper() {
    Selector elementHelper = new WebSelector();

    return elementHelper;
  }
}
