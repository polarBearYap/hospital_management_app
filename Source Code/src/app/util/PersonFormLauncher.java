package app.util;

import java.util.List;

import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.StringLengthValidator;

import app.model.Person;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PersonFormLauncher {

	//Form input fields
	private CustomTextField idInputField;
	private CustomTextField nameInputField;
	private static final String MALE = "Male";
	private static final String FEMALE = "Female";
	private HBox sexInputField;
	private Label sexInputLabel;
	private JFXToggleButton sexToggleButton;
	private CustomTextField ageInputField;
	private List<Integer> idList;

	/**
	 * Initialize input fields
	 */
	public PersonFormLauncher() {
		sexInputField = new HBox();
		sexInputLabel = new Label("Sex:");
		sexToggleButton = new JFXToggleButton();
		sexToggleButton.setSelected(true);
		sexToggleButton.setText(MALE);
		ageInputField = new CustomTextField("Age");
	}

	/**
     * Preload person form.
     * Add JavaFX and JFoenix UI elements into curScene to prompt user's inputs.
     * Utilize built-in JFoenix validators: RegexValidator RequiredFieldValidator StringLengthValidator, etc.
     * Utilize custom JFoenix validators: IntRangeValidator
     * Utilize JFXAutoCompletePopup to provide intuitive input for users.
	 * @param form
	 * @param personType
	 * @param idList
	 */
	public void preloadForm(FormPane form, String personType, List<Integer> idList) {

		this.idList = idList;

    	//Initialize Input Fields
    	idInputField = new CustomTextField(personType + " Id");
    	nameInputField = new CustomTextField(personType + " Name");

    	//Initialize validators
        RegexValidator dateTypeValidator;
        StringLengthValidator LengthValidator;
        IntRangeValidator RangeValidator = new IntRangeValidator("Age", 1, 150);
        UniqueValueValidator idUniquenessValidator = new UniqueValueValidator(idList, "Id must be unique.");

        //Add validators
        dateTypeValidator = new RegexValidator("Id must be an positive integer!");
        dateTypeValidator.setRegexPattern("^\\d+$");
        LengthValidator = new StringLengthValidator("Input must be less than 4 digits.", 3);
        idInputField.addValidators(dateTypeValidator, LengthValidator, idUniquenessValidator);

        dateTypeValidator = new RegexValidator("Age must be an positive integer!");
        dateTypeValidator.setRegexPattern("^\\d+$");
        ageInputField.addValidators(dateTypeValidator, LengthValidator, RangeValidator);

        //Handling sex input field
        sexInputLabel.getStyleClass().add("input-field");
        sexToggleButton.getStyleClass().add("input-field");

        sexToggleButton.setOnAction(event -> {
        	if (sexToggleButton.isSelected()) sexToggleButton.setText(MALE);
        	else sexToggleButton.setText(FEMALE);
        });

        sexInputField.getChildren().addAll(sexInputLabel, sexToggleButton);
        sexInputField.setAlignment(Pos.CENTER_LEFT);

    	//Add all input fields into the scene
    	form.addNode(idInputField, 35, 80, 0, 80);
    	form.addNode(nameInputField, 50, 80, 0, 80);
    	form.addNode(sexInputField, 20, 80, 0, 80);
    	form.addNode(ageInputField, 10, 80, 0, 80);
	}

	/**
     * Launch person form.
     * @param mainScene (BorderPane)
	 * @return current formScene
	 */
    public void newPerson(FormPane form, Person newPerson) {

    	int id = Integer.parseInt(idInputField.getNode().getText());
    	newPerson.setId(id);
    	idList.add(id);
    	newPerson.setName(nameInputField.getNode().getText());
    	newPerson.setSex(sexToggleButton.getText());
    	newPerson.setAge(Integer.parseInt(ageInputField.getNode().getText()));

    	idInputField.getNode().setText("");
    	nameInputField.getNode().setText("");
    	sexToggleButton.setSelected(true);
    	sexToggleButton.setText(MALE);
    	ageInputField.getNode().setText("");
    }
}
