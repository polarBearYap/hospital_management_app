package app.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import app.MainApp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public final class MainMenuController implements Initializable {

    @FXML
    private Label timeDisplay;

    @FXML
    private Label dateDisplay;

    @FXML
    private AnchorPane subBackground;

    @FXML
    private JFXButton staffButton;

    @FXML
    private JFXButton doctorButton;

    @FXML
    private JFXButton patientButton;

    @FXML
    private JFXButton medicalButton;

    @FXML
    private JFXButton labButton;

    @FXML
    private JFXButton facilityButton;

    /**
	 * Display current time and date.
	 * Reference: https://stackoverflow.com/a/52785067
	 */
	@Override
	public void initialize(URL url, ResourceBundle resource) {

	    Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

	        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma");
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy");

	        dateDisplay.setText(LocalDateTime.now().format(dateFormatter));
	        timeDisplay.setText(LocalDateTime.now().format(timeFormatter));

	    }), new KeyFrame(Duration.millis(1000)));

	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
	}

	/**
	 * Use MainApp instance (curApp) to access sub-menu launchers.
	 * @param curApp
	 */
    public void setCurApp(MainApp curApp) {

    	staffButton.setOnMouseClicked(event->curApp.launchSubMenu(1));
    	doctorButton.setOnMouseClicked(event->curApp.launchSubMenu(2));
    	patientButton.setOnMouseClicked(event->curApp.launchSubMenu(3));
    	medicalButton.setOnMouseClicked(event->curApp.launchSubMenu(4));
    	labButton.setOnMouseClicked(event->curApp.launchSubMenu(5));
    	facilityButton.setOnMouseClicked(event->curApp.launchSubMenu(6));
    }
}