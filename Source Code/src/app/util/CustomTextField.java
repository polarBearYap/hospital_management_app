package app.util;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

public final class CustomTextField {

	private JFXTextField inputField;
	private JFXAutoCompletePopup<String> inputAutoComplete;
	public final static RequiredFieldValidator INPUT_EMPTY_VALIDATOR = new RequiredFieldValidator("Input cannot be empty.");

	/**
	 * Create a custom text field to reduce code redundancy.
	 * @param promptText
	 */
	public CustomTextField(String promptText) {

		inputField = new JFXTextField();
		inputField.setLabelFloat(true);
		inputField.setPromptText(promptText);
		inputField.getStyleClass().add("input-field");
		inputField.getValidators().add(INPUT_EMPTY_VALIDATOR);
		inputField.focusedProperty().addListener((o, oldVal, newVal) -> {
			inputField.validate();
        });
	}

	/**
	 * Add validators.
	 * @param validators
	 */
    public void addValidators(ValidatorBase... validators) {
    	inputField.getValidators().addAll(validators);
    }

    /**
     * Wrapper function to initialize pop up auto complete pane in the inputField.
     * Reference: https://stackoverflow.com/a/50688757
     * @param suggestionList
     */
    public void addPopupAutoComplete(String[] suggestionList) {

        inputAutoComplete = new JFXAutoCompletePopup<>();
        inputAutoComplete.getSuggestions().addAll(suggestionList);

        inputAutoComplete.setSelectionHandler(event -> inputField.setText(event.getObject()));

        inputField.textProperty().addListener(observable -> {

        	inputAutoComplete.filter(string -> string.toLowerCase().contains(inputField.getText().toLowerCase()));

        	if (inputAutoComplete.getFilteredSuggestions().isEmpty()) {
            	inputAutoComplete.hide();
            }
            else {
            	try {
            		inputAutoComplete.show(inputField);
            	} catch (java.lang.IllegalStateException e) {}
            }
        });
    }

    /**
     * Getter for inputField.
     * @return inputField
     */
    public JFXTextField getNode() {
    	return inputField;
    }
}
