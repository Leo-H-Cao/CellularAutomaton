package cellsociety.io;


import cellsociety.game.GameType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.w3c.dom.Element;

/**
 * Validates file format and required values
 *
 * @author Leo Cao
 */
public class FileValidator {

  public static final String FILE_TYPE_ERROR = "Not an XML file!";
  public static final String ROOT_TAG_ERROR = "Not a game configuration file, root tag should be 'CellSociety'";
  public static final String VALID_ROOT_TAG = "CellSociety";

  private ResourceBundle requiredResourceBundle;
  private HashSet<String> requiredValues;


  public FileValidator(){
    requiredResourceBundle = ResourceBundle.getBundle("xmlrequiredvalues", Locale.ENGLISH);
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

  public boolean checkRequiredValues(GameType game, Map<String, String> gameData){
    String[] requiredValuesArray = requiredResourceBundle.getString(game.toString()).split(" ");
    requiredValues = new HashSet<>(Arrays.asList(requiredValuesArray));
    for(String param : gameData.keySet()){
      requiredValues.remove(param);
    }
    if(requiredValues.size() != 0){
      System.out.println(requiredResourceBundle.getString("FAIL_MESSAGE"));
      return false;
    }
    return true;
  }

}
