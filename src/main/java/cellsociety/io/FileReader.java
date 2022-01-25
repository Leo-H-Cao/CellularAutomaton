package cellsociety.io;

import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

  public FileReader(){
    BUILDER = createDocumentBuilder();
    dom = null;
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

    HashMap<String,String> data = new HashMap<>();
    NodeList childNodes = rootElement.getChildNodes();

    //TODO: read in attributes as well
    for(int i = 0; i < childNodes.getLength(); i++) {
      Node curNode = childNodes.item(i);
      String nodeName = curNode.getNodeName();
      String nodeText = curNode.getTextContent();
      data.put(nodeName, nodeText);
    }
    BUILDER.reset();
    return data;
  }

  //TODO: validate if xml file is correct for given game?

}
