package app.model;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXToggleButton;

import app.util.CustomTextField;
import app.util.FormPane;
import app.util.PersonFormLauncher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public final class Patient extends Person {

    private StringProperty disease;
    private StringProperty admitStatus;

    private final static List<Integer> PATIENT_ID_LIST = new ArrayList<Integer>();

    // Source: https://www.benaroyaresearch.org/what-is-bri/disease-information, https://simple.wikipedia.org/wiki/List_of_diseases
    private final static String[] DISEASE_SUGGESTION_LIST = {
    		"AIDS/HIV","Allergies","Appendicitis","Asthma","Bronchitis","COVID-19","Cancer",
    		"Cholera","Chronic Obstructive Pulmonary Disease (COPD)","Cuts and contusions",
    		"Dengue fever","Diarrhea","Food poisoning","Heart disease","Influenza",
    		"Liver disease","Multiple sclerosis","Paralysis","Pneumonia","Rheumatoid arthritis",
    		"Skin infections","Sprains and broken bones","Stroke","Tuberculosis","Type 1 diabetes"
    };

    public Patient() {
    	super();
		this.disease = new SimpleStringProperty("");
		this.admitStatus = new SimpleStringProperty("");
    }

	public Patient(int id, String name, String sex, int age, String disease, String admitStatus) {
		super(id, name, sex, age);
		PATIENT_ID_LIST.add(id);
		this.disease = new SimpleStringProperty(disease);
		this.admitStatus = new SimpleStringProperty(admitStatus);
	}

	//Form Input Fields
	private static final PersonFormLauncher PARENT_FORM = new PersonFormLauncher();
	private static final FormPane ADD_FORM = new FormPane(Pos.TOP_LEFT);
    private static final CustomTextField DISEASE_INPUT_FIELD = new CustomTextField("Disease");
    private static final HBox ADMIT_STATUS_INPUT_FIELD = new HBox();
    private static final Label ADMIT_STATUS_LABEL = new Label("Admit status:");
    private static final JFXToggleButton ADMIT_STATUS_TOGGLE_BUTTON = new JFXToggleButton();
    private static final String ADMITTED = "Admitted";
    private static final String DISCHARGED = "Discharged";

    /**
     * Preload patient form.
     */
    public static void preloadPatientForm() {

    	PARENT_FORM.preloadForm(ADD_FORM, "Patient", PATIENT_ID_LIST);

    	//Handling sex input field
        ADMIT_STATUS_LABEL.getStyleClass().add("input-field");
        ADMIT_STATUS_LABEL.setPrefWidth(100);

        ADMIT_STATUS_TOGGLE_BUTTON.getStyleClass().add("input-field");

        ADMIT_STATUS_TOGGLE_BUTTON.setOnAction(event -> {
        	if (ADMIT_STATUS_TOGGLE_BUTTON.isSelected()) ADMIT_STATUS_TOGGLE_BUTTON.setText(ADMITTED);
        	else ADMIT_STATUS_TOGGLE_BUTTON.setText(DISCHARGED);
        });

        ADMIT_STATUS_INPUT_FIELD.getChildren().addAll(ADMIT_STATUS_LABEL, ADMIT_STATUS_TOGGLE_BUTTON);
        ADMIT_STATUS_INPUT_FIELD.setAlignment(Pos.CENTER_LEFT);

    	//Add auto-complete pop-up
    	DISEASE_INPUT_FIELD.addPopupAutoComplete(DISEASE_SUGGESTION_LIST);

    	//Add all input fields into the scene
    	ADD_FORM.addNode(DISEASE_INPUT_FIELD, 50, 80, 0, 80);
    	ADD_FORM.addNode(ADMIT_STATUS_INPUT_FIELD, 25, 80, 0, 80);
    }

	/**
     * Launch patient form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public static VBox launchPatientForm(BorderPane curScene) {

    	ADMIT_STATUS_TOGGLE_BUTTON.setText(ADMITTED);
        ADMIT_STATUS_TOGGLE_BUTTON.setSelected(true);
        DISEASE_INPUT_FIELD.getNode().setText("");

    	curScene.setCenter(ADD_FORM.getVBoxScroll(800));

    	return ADD_FORM.getVBox();
    }

    /**
     * Store the value of the recently added patient using setter.
     * @return newPatient
     */
    public static Patient addNewPatient() {

    	Patient newPatient = new Patient();

    	PARENT_FORM.newPerson(ADD_FORM, newPatient);

    	newPatient.setDisease(DISEASE_INPUT_FIELD.getNode().getText());
    	newPatient.setAdmitStatus(ADMIT_STATUS_TOGGLE_BUTTON.getText());

    	DISEASE_INPUT_FIELD.getNode().setText("");
    	ADMIT_STATUS_TOGGLE_BUTTON.setText(ADMITTED);

    	return newPatient;
    }

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param table
     */
    @SuppressWarnings("unchecked")
	public static void showPatients(TableView<Patient> table) {

    	showPeople(table);

        TableColumn<Patient, String> diseaseColumn = new TableColumn<Patient, String>("Disease");
        TableColumn<Patient, String> admitStatusColumn = new TableColumn<Patient, String>("Admit Status");

        table.getColumns().addAll(diseaseColumn, admitStatusColumn);

		diseaseColumn.setCellValueFactory(cellData -> cellData.getValue().diseaseProperty());
		admitStatusColumn.setCellValueFactory(cellData -> cellData.getValue().admitStatusProperty());

		admitStatusColumn.setStyle("-fx-alignment: CENTER");

		diseaseColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
		admitStatusColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
    }

    /*
     * Getter and setter for disease and admit status
     * Getter is not used in this application, therefore being removed.
     */
    public void setDisease(String disease) {
        this.disease.set(disease);
    }

    private StringProperty diseaseProperty() {
        return disease;
    }

    public void setAdmitStatus(String admitStatus) {
        this.admitStatus.set(admitStatus);
    }

    private StringProperty admitStatusProperty() {
        return admitStatus;
    }
}
