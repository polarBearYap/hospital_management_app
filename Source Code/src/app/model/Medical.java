package app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.StringLengthValidator;
import com.jfoenix.validation.base.ValidatorBase;

import app.util.CustomTextField;
import app.util.FormPane;
import app.util.IntRangeValidator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public final class Medical {

    private StringProperty name;
    private StringProperty manufacturer;
    private ObjectProperty<DateUtility> expiryDate;
    private IntegerProperty cost;
    private IntegerProperty count;

    // Source: https://rxsaver.retailmenot.com/top-100-drugs-lists/
    private static String[] MEDICINE_SUGGESTION_LIST = {
    		"Adderall","Adderall XR","Albuterol sulfate","Albuterol sulfate HFA",
    		"Amlodipine besylate","Amoxicillin","Atorvastatin calcium","Cialis",
    		"Eliquis","Gabapentin","Hydrocodone/Acetaminophen","Levothyroxine sodium",
    		"Lisinopril","Metformin HCL ER","Metoprolol succinate ER","Norco",
    		"Omeprazole","Percocet","Phentermine Hydrochloride","ProAir HFA",
    		"Sildenafil","Suboxone","Synthroid","Tadalafil","Tamiflu","Tramadol HCL",
    		"Ventolin HFA","Viagra","Xarelto"
    };

    // Source: https://www.pharmaceutical-technology.com/features/top-pharmaceutical-companies/
    private static String[] DRUG_COMPANY_SUGGESTION_LIST = {"Johnson & Johnson","Merck","Novartis","Pfizer","Roche"};

    public Medical() {
		this.name = new SimpleStringProperty("");
		this.manufacturer = new SimpleStringProperty("");
		this.expiryDate = new SimpleObjectProperty<DateUtility>(null);
		this.cost = new SimpleIntegerProperty(0);
		this.count = new SimpleIntegerProperty(0);
    }

    public Medical(String name, String manufacturer, String expiryDate, int cost, int count) {
		this.name = new SimpleStringProperty(name);
		this.manufacturer = new SimpleStringProperty(manufacturer);
		this.expiryDate = new SimpleObjectProperty<DateUtility>(new DateUtility(expiryDate));
		this.cost = new SimpleIntegerProperty(cost);
		this.count = new SimpleIntegerProperty(count);
    }

    //Form Input Fields
	private static final CustomTextField NAME_INPUT_FIELD = new CustomTextField("Medicine Name");
	private static final CustomTextField MANUFACTURER_INPUT_FIELD = new CustomTextField("Manufacturer");
	private static final JFXDatePicker EXPIRY_DATE_INPUT_FIELD = new JFXDatePicker();
	private static final CustomTextField COST_INPUT_FIELD = new CustomTextField("Cost per unit");
	private static final CustomTextField COUNT_INPUT_FIELD = new CustomTextField("Quantity");
	private static final FormPane ADD_FORM = new FormPane(Pos.CENTER_LEFT);

    /**
     * Preload medical form.
     * Add JavaFX and JFoenix UI elements into curScene to prompt user's inputs.
     * Utilize built-in JFoenix validators: RegexValidator RequiredFieldValidator StringLengthValidator, etc.
     * Utilize custom JFoenix validators: IntRangeValidator
     * Utilize JFXAutoCompletePopup to provide intuitive input for users.
     */
	public static void preloadMedicalForm() {

      	//Handling date input field
        EXPIRY_DATE_INPUT_FIELD.setPromptText("Expiry Date");
        EXPIRY_DATE_INPUT_FIELD.setDefaultColor(Color.valueOf("#3f51b5"));
        EXPIRY_DATE_INPUT_FIELD.setOverLay(false);

        //Source: https://stackoverflow.com/a/21498568
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        EXPIRY_DATE_INPUT_FIELD.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate localDate) {
                return localDate == null ? "" : dateFormat.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateFormat);
            }
        });

    	//Add auto-complete pop-up
    	NAME_INPUT_FIELD.addPopupAutoComplete(MEDICINE_SUGGESTION_LIST);
    	MANUFACTURER_INPUT_FIELD.addPopupAutoComplete(DRUG_COMPANY_SUGGESTION_LIST);

    	//Initialize validators
        RegexValidator DataTypeValidator;
        IntRangeValidator RangeValidator;
        StringLengthValidator LengthValidator;

    	//Add validators
        LengthValidator = new StringLengthValidator("Input must be less than 7 digits.", 6);
        RangeValidator = new IntRangeValidator("Cost per unit", 10, 100000);
        DataTypeValidator = new RegexValidator("Cost per unit must be an positive integer!");
        DataTypeValidator.setRegexPattern("^\\d+$");

        COST_INPUT_FIELD.addValidators(DataTypeValidator, LengthValidator, RangeValidator);

        DataTypeValidator = new RegexValidator("Quantity must be an positive integer!");
        DataTypeValidator.setRegexPattern("^\\d+$");
        LengthValidator = new StringLengthValidator("Quantity must be less than 7 digits.", 6);
        RangeValidator = new IntRangeValidator("Quantity", 1, 100000);

        COUNT_INPUT_FIELD.addValidators(DataTypeValidator, LengthValidator, RangeValidator);

        EXPIRY_DATE_INPUT_FIELD.getValidators().addAll(CustomTextField.INPUT_EMPTY_VALIDATOR, new CustomDateValidator());
        EXPIRY_DATE_INPUT_FIELD.focusedProperty().addListener((o, oldVal, newVal) -> {
        	if (!newVal) {
        		EXPIRY_DATE_INPUT_FIELD.validate();
        	}
        });

    	//Add all input fields into the scene
    	ADD_FORM.addNode(NAME_INPUT_FIELD, 30, 80, 0, 80);
    	ADD_FORM.addNode(MANUFACTURER_INPUT_FIELD, 50, 80, 0, 80);
    	ADD_FORM.addNode(COST_INPUT_FIELD, 50, 80, 0, 80);
    	ADD_FORM.addNode(COUNT_INPUT_FIELD, 50, 80, 0, 80);
    	ADD_FORM.addNode(EXPIRY_DATE_INPUT_FIELD, 50, 80, 50, 80);
	}

	/**
     * Launch medical form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public static VBox launchMedicalForm(BorderPane curScene) {

    	curScene.setCenter(ADD_FORM.getVBoxScroll(700));

    	return ADD_FORM.getVBox();
    }

    /**
     * Store the value of the recently added medical using setter.
     * @return newMedical
     */
    public static Medical addNewMedical() {

    	Medical newMedical = new Medical();

    	newMedical.setName(NAME_INPUT_FIELD.getNode().getText());
    	newMedical.setManufacturer(MANUFACTURER_INPUT_FIELD.getNode().getText());
    	newMedical.setExpiryDate(new DateUtility(EXPIRY_DATE_INPUT_FIELD.getValue()));
    	newMedical.setCost(Integer.parseInt(COST_INPUT_FIELD.getNode().getText()));
    	newMedical.setCount(Integer.parseInt(COUNT_INPUT_FIELD.getNode().getText()));

    	NAME_INPUT_FIELD.getNode().setText("");
    	MANUFACTURER_INPUT_FIELD.getNode().setText("");
    	EXPIRY_DATE_INPUT_FIELD.setValue(null);
    	COST_INPUT_FIELD.getNode().setText("");
    	COUNT_INPUT_FIELD.getNode().setText("");

    	return newMedical;
    }

    /**
     * Add the columns and data into the table.
     * Bind dynamic data to the table such that the data is synchronized with any latest change.
     * @param table
     */
    @SuppressWarnings("unchecked")
	public static void showMedicals(TableView<Medical> table) {
    	// name, manufacturer, expiryDate, cost
        TableColumn<Medical, String> nameColumn = new TableColumn<Medical, String>("Name");
        TableColumn<Medical, String> manufacturerColumn = new TableColumn<Medical, String>("Manufacturer");
        TableColumn<Medical, DateUtility> expiryDateColumn = new TableColumn<Medical, DateUtility>("Expiry Date");
        TableColumn<Medical, Integer> costColumn = new TableColumn<Medical, Integer>("Cost per unit (RM)");
        TableColumn<Medical, Integer> countColumn = new TableColumn<Medical, Integer>("Quantity");

        table.getColumns().addAll(nameColumn, manufacturerColumn, expiryDateColumn, costColumn, countColumn);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());
        expiryDateColumn.setCellValueFactory(cellData -> cellData.getValue().expiryDateProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());

		expiryDateColumn.setStyle("-fx-alignment: CENTER");
		costColumn.setStyle("-fx-alignment: CENTER");
		countColumn.setStyle("-fx-alignment: CENTER");

		nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
		manufacturerColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
		expiryDateColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
		costColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.20));
		countColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
    }

	/**
	 * Private class to handle converting and displaying expiry date.
	 */
    private static final class DateUtility {

    	private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	private LocalDate date;

		public DateUtility(LocalDate date) {
    		this.date = date;
    	}

    	public DateUtility(String dateString) {
    		this.date = LocalDate.parse(dateString, DATE_FORMAT);
    	}

    	@Override
    	public String toString() {
    		return date.format(DATE_FORMAT);
    	}
    }

    /**
     * Private class to validate expiry date
     */
    private static final class CustomDateValidator extends ValidatorBase {

    	private static final LocalDate TODAY = LocalDate.now();

    	CustomDateValidator() {
    		super("Today is " + TODAY.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
    	}

    	@Override
    	protected void eval() {
    		JFXDatePicker dateInputField = (JFXDatePicker) srcControl.get();

            LocalDate inputDate = dateInputField.getValue();
            hasErrors.set(false);

            if (inputDate.compareTo(LocalDate.now()) < 0) {
            	hasErrors.set(true);
            }
    	}
	}

    /*
     * Getter and setter for name, manufacturer, expiry date, cost , number of unit
     * Getter is not used in this application, therefore being removed.
     */
    private void setName(String name) {
        this.name.set(name);
    }

    private StringProperty nameProperty() {
        return name;
    }

    private void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    private StringProperty manufacturerProperty() {
        return manufacturer;
    }

    private void setExpiryDate(DateUtility expiryDate) {
        this.expiryDate.set(expiryDate);
    }

    private ObjectProperty<DateUtility> expiryDateProperty() {
        return expiryDate;
    }

    private void setCost(int cost) {
        this.cost.set(cost);
    }

    private IntegerProperty costProperty() {
        return cost;
    }

    private void setCount(int count) {
        this.count.set(count);
    }

    private IntegerProperty countProperty() {
        return count;
    }
}
