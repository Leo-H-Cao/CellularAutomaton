package cellsociety.io;

import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
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
import java.io.InputStream;


public class FileReader {

  private final DocumentBuilder BUILDER;
  private Document dom;
  private HashMap<String, String> data;

  public FileReader(){
    BUILDER = createDocumentBuilder();
    dom = null;
    data = new HashMap<>();
  }

  private void parse(File xmlFile){
    try{
      dom = BUILDER.parse(xmlFile);
    }
    catch (IOException | SAXException e) {
      System.out.print("Error parsing " + xmlFile.getPath());
    }
  }

  private DocumentBuilder createDocumentBuilder(){
    DocumentBuilder documentBuilder = null;
    try{
      documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    return documentBuilder;
  }

  public Map<String, String> getData(String fileName){
    parse(new File (fileName));
    Element rootElement = dom.getDocumentElement();
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
            data.put(nodeName, nodeText);
//            System.out.print(nodeName +" ");
//            System.out.println(nodeText);
        }
      }
    }
    BUILDER.reset();
    return data;
  }

  private void parseChildNode(NodeList childNodes){
    for(int j = 0; j < childNodes.getLength(); j++) {
      Node curChildNode = childNodes.item(j);
      String childNodeName = curChildNode.getNodeName();
      String childNodeText = curChildNode.getTextContent();
      if(childNodeName.equals("Cell")){
        childNodeText = parseCellNode(curChildNode);
      }
      else if(childNodeName.equals("Color")){
        childNodeName = childNodeName + " " + parseColorAttribute(curChildNode);
      }
      if(!childNodeName.equals("#text")) {
        data.put(childNodeName, childNodeText);
//        System.out.print(childNodeName + " ");
//        System.out.println(childNodeText);
      }
    }
  }

  private String parseCellNode(Node cellNode){
    NamedNodeMap attributes = cellNode.getAttributes();
    String nodeAttributes = attributes.getNamedItem("x").getNodeValue() + attributes.getNamedItem("y").getNodeValue()
        +attributes.getNamedItem("type").getNodeValue();
    return nodeAttributes;
  }

  private String parseColorAttribute(Node colorNode){
    NamedNodeMap attributes = colorNode.getAttributes();
    String nodeAttributes = attributes.getNamedItem("type").getNodeValue();
    return nodeAttributes;
  }

  //TODO: validate if xml file is correct for given game?

}
