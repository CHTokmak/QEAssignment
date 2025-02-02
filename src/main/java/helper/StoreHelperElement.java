package mobile.gaugeProject.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mobile.gaugeProject.model.ElementInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.fail;

public enum StoreHelperElement {
  INSTANCE;
  Logger logger = Logger.getLogger(String.valueOf(getClass()));
  private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
  ConcurrentMap<String, Object> elementMapList;

  StoreHelperElement() {
    initMap(getFileList());
  }

  private void initMap(File[] fileList) {
    elementMapList = new ConcurrentHashMap<>();
    Type elementType = new TypeToken<List<ElementInfo>>() {}.getType();
    Gson gson = new Gson();
    List<ElementInfo> elementInfoList = null;
    for (File file : fileList) {
      try {
        elementInfoList = gson
                .fromJson(new FileReader(file), elementType);
        elementInfoList.parallelStream()
                .forEach(elementInfo -> elementMapList.put(elementInfo.getKey(), elementInfo));
      } catch (FileNotFoundException e) {
        logger.log(null,"{} not found", e);
      }
    }
  }

  private File[] getFileList() {
    URI uri = null;
    String jsonPath = "";
    try {
      uri = new URI(this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH).getFile());
      File file = new File(uri.getPath());
      System.out.println(file.getAbsolutePath());
      jsonPath = file.getAbsolutePath();
    } catch (URISyntaxException e) {
      e.printStackTrace();
      // logger.error("File Directory Is Not Found! file name: {}", DEFAULT_DIRECTORY_PATH);
      throw new NullPointerException("File Directory Is Not Found! file name: " + DEFAULT_DIRECTORY_PATH);
    }
    /**File[] fileList = new File(uri.getPath())
     .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));*/
    List<File> list = new ArrayList<>();
    try {
      Files.walk(Paths.get(jsonPath))
              .filter(Files::isRegularFile)
              .forEach(path -> addFileList(path, list));
    } catch (IOException e) {
      e.printStackTrace();
    }
    File[] fileList = list.toArray(new File[0]);
    /** List<Path> result;
     // walk file tree, no more recursive loop
     try (Stream<Path> walk = Files.walk(Paths.get(uri.getPath())) {
     result = walk
     .filter(Files::isReadable)
     .filter(Files::isRegularFile)// read permission
     .filter(p -> !Files.isDirectory(p))     // is a file
     .filter(p -> file.getName().endsWith(".json"))
     .collect(Collectors.toList());
     } catch (IOException e) {
     e.printStackTrace();
     }*/

    logger.info("json uzantılı dosya sayısı: " + fileList.length);
    if (fileList.length == 0){
      throw new NullPointerException("Json uzantılı dosya bulunamadı."
              + " Default Directory Path = " + uri.getPath());
    }
    return fileList;
  }

  private void addFileList(Path path, List<File> list){

    File file = path.toFile();
    if (file.getName().endsWith(".json")){
      list.add(file);
    }
  }

  public void printAllValues() {
    elementMapList.forEach((key, value) -> logger.entering("Key = {} value = {}", key, value));
  }

  public ElementInfo findElementInfoByKey(String key) {

    if(!elementMapList.containsKey(key)){
      fail(key + " adına sahip element bulunamadı. Lütfen kontrol ediniz.");
    }
    return (ElementInfo) elementMapList.get(key);
  }

  public boolean containsKey(String key){return elementMapList.containsKey(key);}

  public void addElementInfoByKey(String key, ElementInfo elementInfo){elementMapList.put(key,elementInfo);}

  public void saveValue(String key, String value) {
    elementMapList.put(key, value);
  }

  public String getValue(String key) {
    return elementMapList.get(key).toString();
  }

}
