package app.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

public class FormPane {

	private VBox pane;
	private static final int PREF_HEIGHT = 370;
	private static final int PREF_WIDTH = 600;

	/**
	 * Public constructor of FormPane.
	 * @param alignmentPos
	 */
	public FormPane(Pos alignmentPos) {

    	pane = new VBox();
    	pane.setPrefHeight(PREF_HEIGHT);
    	pane.setPrefWidth(PREF_WIDTH);
    	pane.setAlignment(alignmentPos);
	}

	/**
	 * Public constructor of FormPane.
	 * @param alignmentPos
	 * @param height
	 */
	public FormPane(Pos alignmentPos, int height) {

    	pane = new VBox();
    	pane.setPrefHeight(PREF_HEIGHT);
    	pane.setPrefWidth(height);
    	pane.setAlignment(alignmentPos);
	}

	/**
	 * Wrapper function for adding nodes into pane.
	 * @param node
	 * @param marginT
	 * @param marginR
	 * @param marginB
	 * @param marginL
	 */
	public void addNode(Node node, double marginT, double marginR, double marginB, double marginL) {
		addNode_(node, marginT, marginR, marginB, marginL);
	}

	/**
	 * Wrapper function for adding nodes into pane.
	 * @param node
	 * @param marginT
	 * @param marginR
	 * @param marginB
	 * @param marginL
	 */
	public void addNode(CustomTextField node, double marginT, double marginR, double marginB, double marginL) {
		addNode_(node.getNode(), marginT, marginR, marginB, marginL);
	}

	/**
	 * Wrapper function for adding nodes into pane.
	 * @param node
	 * @param marginT
	 * @param marginR
	 * @param marginB
	 * @param marginL
	 */
	private void addNode_(Node node, double marginT, double marginR, double marginB, double marginL) {
		pane.getChildren().add(node);
		VBox.setMargin(node, new Insets(marginT, marginR, marginB, marginL));
	}

	/**
	 * Getter for pane.
	 * @return pane
	 */
	public VBox getVBox() {
		return pane;
	}

	/**
	 * Getter for scrollPane.
	 * @param preHeight
	 * @return scrollPane
	 */
	public ScrollPane getVBoxScroll(int preHeight) {

        ScrollPane scrollPane = new ScrollPane();
    	scrollPane.setContent(pane);
    	scrollPane.setPrefHeight(preHeight);
    	scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    	scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);

    	return scrollPane;
	}
}
