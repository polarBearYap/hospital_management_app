package app.model;

import app.util.CustomTextField;
import app.util.FormPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Facility {

    private StringProperty facility;

     /* Source 1: https://www.pantai.com.my/kuala-lumpur/facilities-amenities/centre-of-excellence
        Source 2: https://www.pantai.com.my/ampang/facilities-amenities/general-facilities/rooms-and-services */
    private static final String[] FACILITY_SUGGESTION_LIST = {
    		"Breast Care Centre","Cancer Centre","Eye Centre","Four-Bedded Room",
    		"Haemodialysis centre","Hand & Upper Limb Centre","Heart Centre",
    		"Intensive Care Unit","Isolation Room","Joint Centre",
    		"Physiotherapy & Rehabilitation centre","Radiology & Imaging centre",
    		"Single Room","Two-Bedded Room"
    };

    public Facility() {
    	this.facility = new SimpleStringProperty("");
    }

    public Facility(String facility) {
		this.facility = new SimpleStringProperty(facility);
    }
	//Form Input Fields
    private static final CustomTextField FACILITY_NAME_INPUT_FIELD = new CustomTextField("Facility Name");
    private static final FormPane ADD_FORM = new FormPane(Pos.CENTER_LEFT);

    /**
     * Preload facility form.
     * Add JavaFX and JFoenix UI elements into curScene to prompt user's inputs.
     * Utilize built-in JFoenix validators: RequiredFieldValidator.
     * Utilize JFXAutoCompletePopup to provide intuitive input for users.
     */
    public static void preloadFacilityForm() {
    	//Add auto-complete pop-up
        FACILITY_NAME_INPUT_FIELD.addPopupAutoComplete(FACILITY_SUGGESTION_LIST);

    	//Add all input fields into the scene
        ADD_FORM.addNode(FACILITY_NAME_INPUT_FIELD, 0, 80, 0, 80);
    }

	/**
     * Launch facility form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public static VBox launchFacilityForm(BorderPane curScene) {

    	FACILITY_NAME_INPUT_FIELD.getNode().setText("");

    	curScene.setCenter(ADD_FORM.getVBox());

    	return ADD_FORM.getVBox();
    }

    /**
     * Store the value of the recently added facility using setter.
     * @return newFacility
     */
    public static Facility addNewFacility() {

    	Facility newFacility = new Facility();

    	newFacility.setFacility(FACILITY_NAME_INPUT_FIELD.getNode().getText());

    	FACILITY_NAME_INPUT_FIELD.getNode().setText("");

    	return newFacility;
    }

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param table
     */
    public static void showFacilities(TableView<Facility> table) {

        TableColumn<Facility, String> labColumn = new TableColumn<Facility, String>("Name");

        table.getColumns().add(labColumn);

        labColumn.setCellValueFactory(cellData -> cellData.getValue().facilityProperty());

		labColumn.prefWidthProperty().bind(table.widthProperty().multiply(1.0));
    }

    /*
     * Getter and setter for name
     * Getter is not used in this application, therefore being removed.
     */
    protected void setFacility(String facility) {
        this.facility.set(facility);
    }

    protected StringProperty facilityProperty() {
        return facility;
    }
}
