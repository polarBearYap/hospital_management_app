package app.util;

import java.util.List;

import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.StringLengthValidator;

import app.model.Employee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public final class EmployeeFormLauncher extends PersonFormLauncher {

	//Form Input Fields
    private CustomTextField salaryInputField;
    private HBox workTimeInputField;
    private JFXTimePicker startWorkTimePicker;
    private JFXTimePicker endWorkTimePicker;
    private CustomTextField roomNoInputField;
    @SuppressWarnings("unused")
	private List<Integer> roomNoList;

	/**
	 * Initialize input fields
	 */
    public EmployeeFormLauncher() {
    	super();
        salaryInputField = new CustomTextField("Salary");
        workTimeInputField = new HBox();
        startWorkTimePicker = new JFXTimePicker();
        endWorkTimePicker = new JFXTimePicker();
        roomNoInputField = new CustomTextField("Room No.");
    }

    /**
     * Preload employee form.
     * Add JavaFX and JFoenix UI elements into curScene to prompt user's inputs.
     * Utilize built-in JFoenix validators: RegexValidator RequiredFieldValidator StringLengthValidator etc.
     * Utilize custom JFoenix validators: IntRangeValidator
     * Utilize JFXAutoCompletePopup to provide intuitive input for users.
     */
	public void preloadForm(FormPane form, String personType, List<Integer> idList, List<Integer> roomNoList) {

		this.roomNoList = roomNoList;
    	super.preloadForm(form, personType, idList);

    	//Initialize validators
        RegexValidator dateTypeValidator;
        StringLengthValidator LengthValidator;
        IntRangeValidator RangeValidator = new IntRangeValidator("Salary", 500, 100000000);
        UniqueValueValidator roomUniquenessValidator = new UniqueValueValidator(roomNoList, "Room No. must be unique.");

        //Add validators
        dateTypeValidator = new RegexValidator("Salary must be an positive integer!");
        dateTypeValidator.setRegexPattern("^\\d+$");
        LengthValidator = new StringLengthValidator("Input must be less than 10 digits.", 9);
        salaryInputField.addValidators(dateTypeValidator, LengthValidator, RangeValidator);

        dateTypeValidator = new RegexValidator("Room No must be an positive integer!");
        dateTypeValidator.setRegexPattern("^\\d+$");
        LengthValidator = new StringLengthValidator("Input must be less than 4 digits.", 3);
        roomNoInputField.addValidators(dateTypeValidator, LengthValidator, roomUniquenessValidator);

    	//Handling time input field
        startWorkTimePicker.setDefaultColor(Color.valueOf("#1565c0"));
        startWorkTimePicker.setOverLay(false);
        startWorkTimePicker.getStyleClass().add("input-field");
        startWorkTimePicker.setPromptText("Start worktime");

        endWorkTimePicker.setDefaultColor(Color.valueOf("#1565c0"));
        endWorkTimePicker.setOverLay(false);
        endWorkTimePicker.getStyleClass().add("input-field");
        endWorkTimePicker.setPromptText("End worktime");

        workTimeInputField.getChildren().addAll(startWorkTimePicker, endWorkTimePicker);
        workTimeInputField.setAlignment(Pos.BOTTOM_LEFT);
        HBox.setMargin(endWorkTimePicker, new Insets(0, 0, 0, 50));

    	//Add validators
        startWorkTimePicker.getValidators().add(CustomTextField.INPUT_EMPTY_VALIDATOR);
        endWorkTimePicker.getValidators().add(CustomTextField.INPUT_EMPTY_VALIDATOR);
        startWorkTimePicker.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	startWorkTimePicker.validate();
            }
        });
        endWorkTimePicker.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
            	endWorkTimePicker.validate();
            }
        });

        //Add input field into the scene
        form.addNode(workTimeInputField, 50, 80, 0, 80);
    	form.addNode(salaryInputField, 50, 80, 0, 80);
    	form.addNode(roomNoInputField, 50, 80, 0, 80);
    }

	/**
     * Launch employee form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public void addNewEmployee(FormPane form, Employee newEmployee) {

    	super.newPerson(form, newEmployee);

    	newEmployee.setSalary(Integer.parseInt(salaryInputField.getNode().getText()));
    	newEmployee.setWorkTime(startWorkTimePicker.getValue(), endWorkTimePicker.getValue());
    	newEmployee.setRoomNo(Integer.parseInt(roomNoInputField.getNode().getText()));

    	salaryInputField.getNode().setText("");
    	startWorkTimePicker.setValue(null);
    	endWorkTimePicker.setValue(null);
    	roomNoInputField.getNode().setText("");
    }
}
