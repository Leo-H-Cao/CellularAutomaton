package cellsociety.io;

import cellsociety.cell.Cell;
import cellsociety.cell.CellType;
import cellsociety.game.GameType;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
	private Map<String, String> gameData;
	private ArrayList<Cell> initialState;
	private GameType game_type;

	/**
	 * creates file reader instance
	 */
	public FileReader() {
		BUILDER = createDocumentBuilder();
		gameData = new HashMap<>();
		initialState = new ArrayList<>();
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
		validateFileType(fileName);
		Element rootElement = getRootElement(new File(fileName));
		validateRootTag(rootElement);
		setGameType(rootElement);
		NodeList childNodes = rootElement.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node curNode = childNodes.item(i);
			if (curNode.hasChildNodes()) {
				NodeList curNodeChildren = curNode.getChildNodes();
				parseChildNode(curNodeChildren);
			} else {
				String nodeName = curNode.getNodeName();
				String nodeText = curNode.getTextContent();
				if (!nodeName.equals("#text")) {
					gameData.put(nodeName, nodeText);
				}
			}
		}
	}

	private void validateFileType(String fileName) throws XMLException {
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!fileType.equals("xml")) {
			throw new XMLException(FILE_TYPE_ERROR);
		}
	}

	private void validateRootTag(Element root) throws XMLException {
		if (!root.getNodeName().equals(VALID_ROOT_TAG)) {
			throw new XMLException(ROOT_TAG_ERROR);
		}
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
		String nodeAttributes = attributes.getNamedItem("type").getNodeValue();
		return nodeAttributes;
	}

	public Map<String, String> getGameData() {
		return gameData;
	}

	public ArrayList<Cell> getInitialState() {
		return initialState;
	}

	private void setGameType(Element root) {
		String gameTypeString = root.getAttributes().getNamedItem(GAME_TYPE_ATTRIBUTE).getNodeValue();
		game_type = GameType.valueOf(gameTypeString);
	}

	public GameType getGameType() {
		return game_type;
	}

	public void printMap() {
		System.out.println(gameData);
	}
}
