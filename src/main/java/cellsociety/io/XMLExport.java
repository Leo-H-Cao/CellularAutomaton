package cellsociety.io;

import cellsociety.cell.Cell;
import cellsociety.cell.CellGrid;
import cellsociety.cell.CellType;
import cellsociety.game.Game;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
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
  public static final String ROOT_TAG = "CellSociety";
  public static final String PARSER_EXCEPTION_MSG = "UsersXML: Error trying to instantiate DocumentBuilder ";

  private Element rootElement;
  private Document dom;
  private final File saveDirectory ;

  public XMLExport(File file){
    saveDirectory = file;
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
    writeGameData();
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
          new StreamResult(new FileOutputStream(saveDirectory.getPath())));

    } catch (TransformerException | IOException te) {
      System.out.println(te.getMessage());
    }
  }

  private void writeGameData(){
    Map<String,String> curGameData = Game.getCurrentFile().getGameData();
    for(Entry<String, String> data : curGameData.entrySet()){
      Element element = dom.createElement(data.getKey());
      if(data.getKey().equals("InitialCellState")){
        addInitialState(element);
      }
      else{
        element.appendChild(dom.createTextNode(data.getValue()));
      }
      rootElement.appendChild(element);
    }
  }

  private void addInitialState(Element initialStateParent){
    Cell[][] curCellGrid = CellGrid.getGrid();
    for (Cell[] cells : curCellGrid) {
      for (int j = 0; j < curCellGrid[0].length; j++) {
        Cell curCell = cells[j];
        CellType cellType = curCell.getType();
        if (cellType != CellType.NULL && cellType != CellType.EMPTY && cellType != CellType.DEAD) {
          addCell(curCell, cellType, initialStateParent);
        }
      }
    }
  }

  private void addCell(Cell cell, CellType celltype, Element initialStateParent){
    Element element = dom.createElement("cell");
    String cellTypeString = celltype.toString();
    String cellX = String.valueOf(cell.getX());
    String cellY = String.valueOf(cell.getY());
    element.setAttribute("type",cellTypeString);
    element.setAttribute("x", cellX);
    element.setAttribute("y", cellY);
    initialStateParent.appendChild(element);
  }



}
