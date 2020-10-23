package app.model;

import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.StringLengthValidator;

import app.util.CustomTextField;
import app.util.FormPane;
import app.util.IntRangeValidator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public final class Lab extends Facility {

    private IntegerProperty cost;

    // Source: https://www.pantai.com.my/ampang/facilities-amenities/diagnostic-services
    private static final String[] LAB_SUGGESTION_LIST = {
    		"Clinical Chemistry Laboratory Unit","Cytopathology Laboratory Unit",
    		"Haematology Laboratory Unit","Histopathology Laboratory Unit",
    		"Immunology & Serology Laboratory Unit","Microbiology Laboratory Unit",
    		"Transfusion Medicine Laboratory Unit"
    };

    public Lab() {
    	super();
		this.cost = new SimpleIntegerProperty(0);
    }

    public Lab(String facility, int cost) {
		super(facility);
		this.cost = new SimpleIntegerProperty(cost);
    }

	//Form Input Fields
	private static final CustomTextField LAB_INPUT_FIELD = new CustomTextField("Laboratory Name");
	private static final CustomTextField COST_INPUT_FIELD = new CustomTextField("Cost");
	private static final FormPane ADD_FORM = new FormPane(Pos.CENTER_LEFT);

    /**
     * Preload lab form.
     * Add JavaFX and JFoenix UI elements into curScene to prompt user's inputs.
     * Utilize built-in JFoenix validators: RegexValidator RequiredFieldValidator StringLengthValidator, etc.
     * Utilize custom JFoenix validators: IntRangeValidator
     * Utilize JFXAutoCompletePopup to provide intuitive input for users.
     */
	public static void preloadLabForm() {
    	//Initialize validators
        RegexValidator DataTypeValidator = new RegexValidator("Cost must be an positive integer!");
        StringLengthValidator LengthValidator = new StringLengthValidator("Input must be less than 10 digits.", 9);
        IntRangeValidator RangeValidator = new IntRangeValidator("Cost", 1000, 100000000);

    	//Add validators
        DataTypeValidator.setRegexPattern("^\\d+$");
        COST_INPUT_FIELD.addValidators(DataTypeValidator, LengthValidator, RangeValidator);

    	//Add auto-complete pop-up
        LAB_INPUT_FIELD.addPopupAutoComplete(LAB_SUGGESTION_LIST);

    	//Add all input fields into the scene
        ADD_FORM.addNode(LAB_INPUT_FIELD, 0, 80, 0, 80);
        ADD_FORM.addNode(COST_INPUT_FIELD, 70, 80, 0, 80);
	}

	/**
     * Launch lab form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public static VBox launchLabForm(BorderPane curScene) {

    	curScene.setCenter(ADD_FORM.getVBox());

    	return ADD_FORM.getVBox();
    }

    /**
     * Store the value of the recently added lab using setter.
     * @return newLab
     */
    public static Lab addNewLab() {

    	Lab newLab = new Lab();

    	newLab.setFacility(LAB_INPUT_FIELD.getNode().getText());
    	newLab.setCost(Integer.parseInt(COST_INPUT_FIELD.getNode().getText()));

    	LAB_INPUT_FIELD.getNode().setText("");
    	COST_INPUT_FIELD.getNode().setText("");

    	return newLab;
    }

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param table
     */
    @SuppressWarnings("unchecked")
	public static void showLabs(TableView<Lab> table) {

        TableColumn<Lab, String> labColumn = new TableColumn<Lab, String>("Name");
        TableColumn<Lab, Integer> costColumn = new TableColumn<Lab, Integer>("Cost (RM)");

        table.getColumns().addAll(labColumn, costColumn);

        labColumn.setCellValueFactory(cellData -> cellData.getValue().facilityProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());

		costColumn.setStyle("-fx-alignment: CENTER");

		labColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.70));
		costColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.30));
    }

    /*
     * Getter and setter for name, cost
     * Getter is not used in this application, therefore being removed.
     */
    private void setCost(int cost) {
        this.cost.set(cost);
    }

    private IntegerProperty costProperty() {
        return cost;
    }
}
