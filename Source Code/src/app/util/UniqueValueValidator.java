package app.util;

import java.util.List;

import com.jfoenix.validation.base.ValidatorBase;

import javafx.scene.control.TextInputControl;

/**
 * Private class to validate the uniqueness of value
 * Reference: https://github.com/jfoenixadmin/JFoenix/blob/fb03a39e3feeb1a645aa98dbf2e8c0cb918b7fec/jfoenix/src/main/java/com/jfoenix/validation/IntegerValidator.java
 */
public final class UniqueValueValidator extends ValidatorBase {

	private List<Integer> listOfValues;

    public UniqueValueValidator(List<Integer> listOfValues, String message) {
        super(message);
        this.listOfValues = listOfValues;
    }

	@Override
	protected void eval() {
		TextInputControl textField = (TextInputControl) srcControl.get();

        String text = textField.getText();
        int intText;

        if (srcControl.get() instanceof TextInputControl) {
            try {
                hasErrors.set(false);

                if (!text.isEmpty()) {

                	intText = Integer.parseUnsignedInt(text);

                	if (listOfValues.indexOf(intText) != -1) {
                		hasErrors.set(true);
                	}
                }
            }
            catch (NumberFormatException e) {}
        }
	}
}