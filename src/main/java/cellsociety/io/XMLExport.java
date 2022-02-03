package cellsociety.io;

import cellsociety.cell.Cell;
import cellsociety.cell.CellGrid;
import cellsociety.cell.CellType;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Converts current cell state to XML file and exports as a local file
 *
 * @author Leo Cao
 */
public class XMLExport {

  public static final String XML_FILE_NAME = "data/test.xml";
  public static final String ROOT_TAG = "CellSociety";
  public static final String PARSER_EXCEPTION_MSG = "UsersXML: Error trying to instantiate DocumentBuilder ";

  private Element rootElement;
  private Document dom;

  public XMLExport(){
    createDom();
  }

  private void createDom(){
    try{
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      dom = db.newDocument();
      rootElement = dom.createElement(ROOT_TAG);
    } catch (ParserConfigurationException pce) {
      System.out.println(PARSER_EXCEPTION_MSG+ pce);
    }

  }

  public void saveToXML(){
    Cell[][] curCellGrid = CellGrid.getGrid();
    for(int i = 0; i < curCellGrid.length; i++){
      for(int j = 0; j < curCellGrid[0].length; j++){
        Cell curCell = curCellGrid[i][j];
        CellType cellType = curCell.getType();
        if(cellType != cellType.NULL && cellType != cellType.EMPTY && cellType != cellType.DEAD){
          addCell(curCell, cellType);
        }
      }
    }
    dom.appendChild(rootElement);
    sendToFile();
  }

  private void sendToFile(){
    try {
      Transformer tr = TransformerFactory.newInstance().newTransformer();
      tr.setOutputProperty(OutputKeys.INDENT, "yes");
      tr.setOutputProperty(OutputKeys.METHOD, "xml");

      // send DOM to file
      tr.transform(new DOMSource(dom),
          new StreamResult(new FileOutputStream(XML_FILE_NAME)));

    } catch (TransformerException te) {
      System.out.println(te.getMessage());
    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }

  private void addCell(Cell cell, CellType celltype){
    Element element = dom.createElement("cell");
    String cellTypeString = celltype.toString();
    String cellX = String.valueOf(cell.getX());
    String cellY = String.valueOf(cell.getY());
    element.setAttribute("type",cellTypeString);
    element.setAttribute("x", cellX);
    element.setAttribute("y", cellY);
    rootElement.appendChild(element);
  }

}
