package cellsociety.view;

import cellsociety.util.Type;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class TypeSelector {
	private final Type.CELLTYPE type;

	public TypeSelector(Type.CELLTYPE _type) {
		type = _type;
	}

	public Node getNode() {
		HBox ret = new HBox();

		return ret;
	}
}
