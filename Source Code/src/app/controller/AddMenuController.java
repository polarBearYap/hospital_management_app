package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.base.IFXValidatableControl;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public final class AddMenuController implements Initializable {

    @FXML
    private Label heading;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private HBox buttonBar;

    private VBox dropdownPane = new VBox();

	private JFXPopup popup = new JFXPopup(dropdownPane);

    private JFXButton mainMenuNavigation = new JFXButton("Main Menu");

	private JFXButton subMenuNavigation = new JFXButton();

	private MainApp curApp;

	private int menuOption;

	private VBox formPane;

    /**
     * Add icons into the buttons.
     * Initialize a drop down pane to be activated when clicked on the back button.
     * Reference for drop down pane: https://github.com/jfoenixadmin/JFoenix/blob/master/demo/src/main/java/demos/components/PopupDemo.java
     */
	@Override
	public void initialize(URL url, ResourceBundle resource) {

		cancelButton.setGraphic(new ImageView(new Image("app/resources/icons/outline_cancel_white_24dp.png")));
		backButton.setGraphicTextGap(10);

		backButton.setGraphic(new ImageView(new Image("app/resources/icons/outline_arrow_back_ios_white_24dp.png")));
		backButton.setGraphicTextGap(10);

		addButton.setGraphic(new ImageView(new Image("app/resources/icons/outline_add_white_24dp.png")));
		addButton.setGraphicTextGap(10);

		dropdownPane.getStylesheets().add(MainApp.class.getResource("css/AddMenu.css").toExternalForm());
		dropdownPane.getStyleClass().add("pop-pane");

		subMenuNavigation.getStyleClass().add("back-to-menu-button");
		mainMenuNavigation.getStyleClass().add("back-to-menu-button");

		dropdownPane.getChildren().addAll(mainMenuNavigation, subMenuNavigation);

		backButton.setOnMouseClicked((event)-> {
			if (event.getButton() == MouseButton.PRIMARY)
				popup.show(backButton, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
		});
	}

	/**
	 * Use MainApp instance (curApp) to access all available menu launchers.
	 * @param curApp
	 * @param menuOption
	 */
    public void setCurApp(MainApp curApp, int menuOption, VBox formPane) {

    	this.curApp = curApp;
    	this.menuOption = menuOption;
    	this.formPane = formPane;

		setTitle();

    	addButton.setOnMouseClicked(event->{
    		if (!checkInputError()) {
        		curApp.addInputRecords(menuOption);
        		dialogLauncher();
    		}
    	});

		cancelButton.setOnMouseClicked(event->curApp.launchSubMenu(menuOption));

    	mainMenuNavigation.setOnMouseClicked(event-> {
    		curApp.launchMainMenu();
    		popup.hide();
    	});

		subMenuNavigation.setOnMouseClicked(event-> {
			curApp.launchSubMenu(menuOption);
    		popup.hide();
    	});
    }

    /**
     * Set the appropriate heading title and menu title based on menuOption.
     */
    private void setTitle() {
    	String title = "New";

    	switch (menuOption) {
    	case 1:
    		title = "Staff";
    		break;
    	case 2:
    		title = "Doctor";
    		break;
    	case 3:
    		title = "Patient";
    		break;
    	case 4:
    		title = "Medical";
    		break;
    	case 5:
    		title = "Laboratory";
    		break;
    	default:
    		title = "Facility";
    		break;
    	}

    	heading.setText("New " + title);
    	subMenuNavigation.setText(title + " Menu");
    }

    private boolean inputContainError;

    /**
     * Error toggler for checkInputError.
     */
    private void toggleError() {
    	//Once the flag switched to false, it can never switched back to true.
    	if (!inputContainError) inputContainError = !inputContainError;
    }

    /**
     * Check if any input fields contains any error.
     * Return true if at least one input field contains error.
     * @return inputContainError
     */
    private boolean checkInputError() {

    	inputContainError = false;

    	formPane.getChildren().forEach(outerNode->{
    		if (outerNode instanceof HBox) {
    			((HBox) outerNode).getChildren().forEach(innerNode->{
    				if (innerNode instanceof IFXValidatableControl) {
    					((IFXValidatableControl) innerNode).validate();
    					if (((IFXValidatableControl) innerNode).getActiveValidator() != null) toggleError();
    				}
    			});
    		}
    		else {
				((IFXValidatableControl) outerNode).validate();
				if (((IFXValidatableControl) outerNode).getActiveValidator() != null) toggleError();
    		}
    	});

    	return inputContainError;
    }

    /**
     * Display successful message indicating inputed value has been added.
     */
    private void dialogLauncher() {

		Label dialogDesc = new Label("Record successfully added.");
		dialogDesc.setGraphic(new ImageView(new Image("app/resources/icons/outline_check_circle_white_24dp.png")));
		dialogDesc.setGraphicTextGap(10);
		dialogDesc.getStyleClass().add("dialog_desc");

	    JFXButton backButton = new JFXButton("Back to Sub Menu");
		JFXButton stayButton = new JFXButton("Add more record");

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(dialogDesc);
		layout.setActions(backButton, stayButton);

		JFXAlert<String> alert = new JFXAlert<String>(curApp.getPrimaryStage());
		alert.initModality(Modality.NONE);
		alert.setOverlayClose(false);
		alert.setContent(layout);
		alert.getDialogPane().getStylesheets().add(AddMenuController.class.getResource("../css/AddMenu.css").toExternalForm());

		backButton.setOnAction(event -> {
			curApp.launchSubMenu(menuOption);
			alert.hideWithAnimation();
		});
		stayButton.setOnAction(event -> {
			alert.hideWithAnimation();
		});

		alert.showAndWait();
    }
}
