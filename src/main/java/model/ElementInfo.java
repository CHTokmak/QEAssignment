package mobile.gaugeProject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElementInfo {

  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("value")
  @Expose
  private String value;
  @SerializedName("type")
  @Expose
  private String type;
  @SerializedName("index")
  @Expose
  private int index;
  @SerializedName("androidValue")
  @Expose
  private String androidValue;
  @SerializedName("androidType")
  @Expose
  private String androidType;
  @SerializedName("androidIndex")
  @Expose
  private int androidIndex;
  @SerializedName("iosValue")
  @Expose
  private String iosValue;
  @SerializedName("iosType")
  @Expose
  private String iosType;
  @SerializedName("iosIndex")
  @Expose
  private int iosIndex;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getAndroidValue() {
    return androidValue;
  }

  public void setAndroidValue(String androidValue) {
    this.androidValue = androidValue;
  }

  public String getAndroidType() {
    return androidType;
  }

  public void setAndroidType(String androidType) {
    this.androidType = androidType;
  }

  public int getAndroidIndex() {
    return androidIndex;
  }

  public void setAndroidIndex(int androidIndex) {
    this.androidIndex = androidIndex;
  }

  public String getIosValue() {
    return iosValue;
  }

  public void setIosValue(String iosValue) {
    this.iosValue = iosValue;
  }

  public String getIosType() {
    return iosType;
  }

  public void setIosType(String iosType) {
    this.iosType = iosType;
  }

  public int getIosIndex() {
    return iosIndex;
  }

  public void setIosIndex(int iosIndex) {
    this.iosIndex = iosIndex;
  }


  @Override
  public String toString() {
    return "ElementInfo{" +
        "key='" + key + '\'' +
        ", androidValue='" + androidValue + '\'' +
        ", androidType='" + androidType + '\'' +
        ", androidIndex=" + androidIndex +
        ", iosValue='" + iosValue + '\'' +
        ", iosType='" + iosType + '\'' +
        ", iosIndex=" + iosIndex +
        '}';
  }
}
