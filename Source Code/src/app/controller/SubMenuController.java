package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class SubMenuController implements Initializable {

    @FXML
    private JFXButton backButton;

    @FXML
    private Label heading;

    @FXML
    private JFXButton addButton;

    /**
     * Add icons into the buttons.
     */
	@Override
	public void initialize(URL url, ResourceBundle resource) {

		backButton.setGraphic(new ImageView(new Image("app/resources/icons/outline_arrow_back_ios_white_24dp.png")));
		backButton.setGraphicTextGap(10);

		addButton.setGraphic(new ImageView(new Image("app/resources/icons/outline_add_white_24dp.png")));
		addButton.setGraphicTextGap(10);
	}

	/**
	 * Use MainApp instance (curApp) to access sub-menu launchers.
	 * @param curApp
	 * @param menuOption
	 */
    public void setCurApp(MainApp curApp, int menuOption) {

		switch (menuOption) {
		case 1:
			heading.setText("Staffs");
			break;
		case 2:
			heading.setText("Doctors");
			break;
		case 3:
			heading.setText("Patients");
			break;
		case 4:
			heading.setText("Medicals");
			break;
		case 5:
			heading.setText("Laboratories");
			break;
		case 6:
			heading.setText("Facilities");
			break;
		}

    	addButton.setOnMouseClicked(event->curApp.launchAddMenu(menuOption));
    	backButton.setOnMouseClicked(event->curApp.launchMainMenu());
    }
}
