package cellsociety.io;


import cellsociety.game.GameType;
import org.w3c.dom.Element;

import java.util.*;

/**
 * Validates file format and required values
 *
 * @author Leo Cao
 */
public class FileValidator {

  public static final String FILE_TYPE_ERROR = "Not an XML file!";
  public static final String ROOT_TAG_ERROR = "Not a game configuration file, root tag should be 'CellSociety'";
  public static final String VALID_ROOT_TAG = "CellSociety";
  public static final String NEIGHBORHOOD_TYPE = "NeighborhoodType";

  private final ResourceBundle requiredResourceBundle;
  private HashSet<String> requiredValues;
  private final ResourceBundle defaults;


  public FileValidator(){
    requiredResourceBundle = ResourceBundle.getBundle("xmlrequiredvalues", Locale.ENGLISH);
    defaults =  ResourceBundle.getBundle("DEFAULTS", Locale.ENGLISH);
  }
  public void validateFileType(String fileName) throws XMLException {
    String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
    if (!fileType.equals("xml")) {
      throw new XMLException(FILE_TYPE_ERROR);
    }
  }

  public void validateRootTag(Element root) throws XMLException {
    if (!root.getNodeName().equals(VALID_ROOT_TAG)) {
      throw new XMLException(ROOT_TAG_ERROR);
    }
  }

  public void checkRequiredValues(GameType game, Map<String, String> gameData){
    String[] requiredValuesArray = requiredResourceBundle.getString(game.toString()).split(" ");
    requiredValues = new HashSet<>(Arrays.asList(requiredValuesArray));
    for(String param : gameData.keySet()){
      requiredValues.remove(param);
    }
    if(requiredValues.size() != 0){
      System.out.println(requiredResourceBundle.getString("FAIL_MESSAGE"));
      setDefaultValues(game, gameData);
    }
  }

  private void setDefaultValues(GameType game, Map<String, String> gameData){
    for(String missing: requiredValues){
      if(missing.equals(NEIGHBORHOOD_TYPE)){
        missing = game.toString()+NEIGHBORHOOD_TYPE;
        gameData.put(NEIGHBORHOOD_TYPE, defaults.getString(missing));
      }
      else{
        gameData.put(missing, defaults.getString(missing));
      }
    }
  }

}
