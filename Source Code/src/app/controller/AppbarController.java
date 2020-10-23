package app.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public final class AppbarController implements Initializable {

	@FXML
	private AnchorPane appbar;

	@FXML
	private HBox titlebar;

	@FXML
	private HBox iconbar;

	private JFXButton[] jfoenixButtons;

	private double xOffSet = 0;

	private double yOffSet = 0;

	/**
	 * Initialize JFoenix buttons.
	 * Add icons into the buttons.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resource) {

		String[] icons_path = { "app/resources/icons/outline_horizontal_rule_white_24dp.png",
		"app/resources/icons/outline_close_white_24dp.png" };

		jfoenixButtons = new JFXButton[icons_path.length];

		Iterator<String> icons = Arrays.asList(icons_path).iterator();

		for (int idx = 0; idx < jfoenixButtons.length; idx++) {
			jfoenixButtons[idx] = new JFXButton();
			jfoenixButtons[idx].setGraphic(new ImageView(new Image(icons.next())));
			jfoenixButtons[idx].getStyleClass().add("hover-button");
			jfoenixButtons[idx].getStyleClass().add("not-round-buttons");
		}

		iconbar.getChildren().addAll(jfoenixButtons);
		iconbar.setAlignment(Pos.CENTER_RIGHT);

		jfoenixButtons[1].setOnMouseClicked(event -> System.exit(0));
	}

	/**
	 * Allow the user to minimize stage. Allow the user to drag the stage by clicking on the application bar.
	 * Reference: https://docs.oracle.com/javase/8/javafx/sample-apps/index.html
	 * @param primaryStage
	 */
	public void makeStageMinimizableAndDraggable(Stage primaryStage) {

		// Make stage minimizable
		jfoenixButtons[0].setOnMouseClicked(event -> primaryStage.setIconified(true));

		// Make stage draggable
		appbar.setOnMousePressed((event) -> {
			xOffSet = event.getSceneX();
			yOffSet = event.getSceneY();
		});

		appbar.setOnMouseDragged((event) -> {
			primaryStage.setX(event.getScreenX() - xOffSet);
			primaryStage.setY(event.getScreenY() - yOffSet);
			primaryStage.setOpacity(0.8f);
		});

		appbar.setOnDragDone((event) -> {
			primaryStage.setOpacity(1.0f);
		});

		appbar.setOnMouseReleased((event) -> {
			primaryStage.setOpacity(1.0f);
		});
	}
}