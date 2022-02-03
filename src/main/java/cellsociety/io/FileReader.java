package cellsociety.io;

import cellsociety.cell.Cell;
import java.util.ArrayList;
import java.util.HashMap;

import cellsociety.util.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


/**
 * Parses XML file by getting content from parent nodes and child nodes,
 * Also reads in attributes if necessary,
 * Stores game data in hashmap and initial state of cells in a list of cells
 *
 * @author Leo Cao
 */

public class FileReader {

  private final String GAME_TYPE_ATTRIBUTE = "game";
  public static final String FILE_TYPE_ERROR = "Not an XML file!";
  public static final String ROOT_TAG_ERROR = "Not a game configuration file, root tag should be 'CellSociety'";
  public static final String VALID_ROOT_TAG = "CellSociety";

  private final DocumentBuilder BUILDER;
  private HashMap<String, String> gameData;
  private ArrayList<Cell> initialState;
  private Type.GAMETYPE game_type;

  public FileReader(){
    BUILDER = createDocumentBuilder();
    gameData = new HashMap<>();
    initialState = new ArrayList<>();
  }

  private DocumentBuilder createDocumentBuilder(){
    try{
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }


  public void parseFile(String fileName){
    Element rootElement = getRootElement(new File(fileName));
    setGameType(rootElement);

    NodeList childNodes = rootElement.getChildNodes();

    //each node only has one child?
    for(int i = 0; i < childNodes.getLength(); i++) {
      Node curNode = childNodes.item(i);
      if(curNode.hasChildNodes()){
          NodeList curNodeChildren = curNode.getChildNodes();
          parseChildNode(curNodeChildren);
      }
      else{
        String nodeName = curNode.getNodeName();
        String nodeText = curNode.getTextContent();
        if(!nodeName.equals("#text")){
            gameData.put(nodeName, nodeText);
        }
      }
    }
  }

  private Element getRootElement (File xmlFile) throws XMLException {
    try {
      BUILDER.reset();
      Document xmlDocument = BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    }
    catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  private void parseChildNode(NodeList childNodes){
    for(int j = 0; j < childNodes.getLength(); j++) {
      Node curChildNode = childNodes.item(j);
      String childNodeName = curChildNode.getNodeName();
      String childNodeText = curChildNode.getTextContent();
      if(childNodeName.equals("Cell")){
        parseCellNode(curChildNode);
      }
      else if(childNodeName.equals("Color")){
        childNodeName = childNodeName + " " + parseColorAttribute(curChildNode);
      }
      if(!childNodeName.equals("Cell") && !childNodeName.equals("#text")) {
        gameData.put(childNodeName, childNodeText);
      }
    }
  }

  private void parseCellNode(Node cellNode){
    NamedNodeMap attributes = cellNode.getAttributes();
    int initialX = Integer.parseInt(attributes.getNamedItem("x").getNodeValue());
    int initialY = Integer.parseInt(attributes.getNamedItem("y").getNodeValue());
    String cellType = attributes.getNamedItem("type").getNodeValue();
    Cell cell = Cell.newGameCell(initialX, initialY, game_type, Type.CELLTYPE.valueOf(cellType));
    initialState.add(cell);
  }

  private String parseColorAttribute(Node colorNode){
    NamedNodeMap attributes = colorNode.getAttributes();
    String nodeAttributes = attributes.getNamedItem("type").getNodeValue();
    return nodeAttributes;
  }

  public HashMap<String, String> getGameData(){
    return gameData;
  }

  public ArrayList<Cell> getInitialState(){
    return initialState;
  }

  private void setGameType (Element root) {
    String gameTypeString = root.getAttributes().getNamedItem(GAME_TYPE_ATTRIBUTE).getNodeValue();
    game_type = Type.GAMETYPE.valueOf(gameTypeString);
  }

  public Type.GAMETYPE getGameType(){
    return game_type;
  }
}
