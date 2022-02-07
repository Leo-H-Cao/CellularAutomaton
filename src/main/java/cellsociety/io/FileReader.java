package cellsociety.io;

import cellsociety.cell.Cell;
import cellsociety.cell.CellType;
import cellsociety.game.GameType;
import cellsociety.game.NeighborhoodType;
import java.util.Map;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Parses XML file by getting content from parent nodes and child nodes,
 * Also reads in attributes if necessary,
 * Stores game data in hashmap and initial state of cells in a list of cells
 *
 * @author Leo Cao
 */

public class FileReader {

	private final DocumentBuilder BUILDER;
	private final Map<String, String> gameData;
	private final ArrayList<Cell> initialState;
	private GameType game_type;
	private NeighborhoodType neighborhoodType;
	private FileValidator validator;

	/**
	 * creates file reader instance
	 */
	public FileReader() {
		BUILDER = createDocumentBuilder();
		gameData = new HashMap<>();
		initialState = new ArrayList<>();
		validator = new FileValidator();
	}

	/**
	 * creates document builder, which is used to read through nodes of XML
	 *
	 * @return new document builder
	 */
	private DocumentBuilder createDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XMLException(e);
		}
	}

	/**
	 * parses XML file, storing game data into hashmap
	 *
	 * @param fileName: name of file being read
	 */
	public void parseFile(String fileName) {
		validator.validateFileType(fileName);
		Element rootElement = getRootElement(new File(fileName));
		validator.validateRootTag(rootElement);
		setGameType(rootElement);
		NodeList childNodes = rootElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node curNode = childNodes.item(i);
			if (curNode.hasChildNodes()) {
				NodeList curNodeChildren = curNode.getChildNodes();
				parseChildNode(curNodeChildren);
			}
			String nodeName = curNode.getNodeName();
			String nodeText = curNode.getTextContent();
			if (!nodeName.equals("#text")) {
				gameData.put(nodeName, nodeText);
			}
		}
		validator.checkRequiredValues(game_type, gameData);
		setNeighborhoodType();
	}


	private Element getRootElement(File xmlFile) throws XMLException {
		try {
			BUILDER.reset();
			Document xmlDocument = BUILDER.parse(xmlFile);
			return xmlDocument.getDocumentElement();
		} catch (SAXException | IOException e) {
			throw new XMLException(e);
		}
	}

	private void parseChildNode(NodeList childNodes) {
		for (int j = 0; j < childNodes.getLength(); j++) {
			Node curChildNode = childNodes.item(j);
			String childNodeName = curChildNode.getNodeName();
			String childNodeText = curChildNode.getTextContent();
			if (childNodeName.equals("Cell")) {
				parseCellNode(curChildNode);
			} else if (childNodeName.equals("Color")) {
				childNodeName = parseColorAttribute(curChildNode) + "_COLOR";
			}
			if (!(childNodeName.equals("Cell") || childNodeName.equals("#text"))) {
				gameData.put(childNodeName, childNodeText);
			}
		}
	}

	private void parseCellNode(Node cellNode) {
		NamedNodeMap attributes = cellNode.getAttributes();
		int initialX = Integer.parseInt(attributes.getNamedItem("x").getNodeValue());
		int initialY = Integer.parseInt(attributes.getNamedItem("y").getNodeValue());
		String cellType = attributes.getNamedItem("type").getNodeValue();
		Cell cell = Cell.newGameCell(initialX, initialY, game_type, CellType.valueOf(cellType));
		initialState.add(cell);
	}

	private String parseColorAttribute(Node colorNode) {
		NamedNodeMap attributes = colorNode.getAttributes();
		return attributes.getNamedItem("type").getNodeValue();
	}

	public Map<String, String> getGameData() {
		return gameData;
	}

	public ArrayList<Cell> getInitialState() {
		return initialState;
	}

	private void setGameType(Element root) {
		String GAME_TYPE_ATTRIBUTE = "game";
		String gameTypeString = root.getAttributes().getNamedItem(GAME_TYPE_ATTRIBUTE).getNodeValue();
		game_type = GameType.valueOf(gameTypeString);
	}

	private void setNeighborhoodType() {
		String neighborhoodTypeString = gameData.get("NeighborhoodType");
		neighborhoodType = NeighborhoodType.valueOf(neighborhoodTypeString);
	}

	public GameType getGameType() {
		return game_type;
	}

	public NeighborhoodType getNeighborhoodType() {
		return neighborhoodType;
	}

	public void printMap() {
		System.out.println(gameData);
	}
}