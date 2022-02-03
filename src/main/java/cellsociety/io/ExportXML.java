package cellsociety.io;

import cellsociety.cell.Cell;
import cellsociety.cell.CellGrid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExportXML {

  public static final String TYPE_ATTR_TEXT = "type";
  public static final String X_ATTR_TEXT = "x";
  public static final String Y_ATTR_TEXT = "y";

  public void saveToXML(){
    Document dom;
    Element element;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    Cell[][] curCellGrid = CellGrid.getGrid();

    try{
      DocumentBuilder db = dbf.newDocumentBuilder();
      dom = db.newDocument();

      Element rootElement = dom.createElement("CellSociety");

      element = dom.createElement("cell");
      Cell cell  = curCellGrid[0][0];
      String cellType = cell.getType().toString();
      String cellX = String.valueOf(cell.getX());
      String cellY = String.valueOf(cell.getY());
      element.setAttribute(TYPE_ATTR_TEXT,cellType);
      element.setAttribute(X_ATTR_TEXT, cellX);
      element.setAttribute(Y_ATTR_TEXT, cellY);
      rootElement.appendChild(element);

    }
    catch (ParserConfigurationException pce) {
      System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
    }
  }

}
