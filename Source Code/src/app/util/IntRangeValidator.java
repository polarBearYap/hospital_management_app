package app.util;

import com.jfoenix.validation.base.ValidatorBase;

import javafx.scene.control.TextInputControl;

/*
 * Reference: https://github.com/jfoenixadmin/JFoenix/blob/fb03a39e3feeb1a645aa98dbf2e8c0cb918b7fec/jfoenix/src/main/java/com/jfoenix/validation/IntegerValidator.java
 */
public class IntRangeValidator extends ValidatorBase {

	private int minValue;
	private int maxValue;

    public IntRangeValidator(String message, int minValue, int maxValue) {
        super(message + " must be between "+ minValue +" to " + maxValue + ".");
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Check if input is within the range of minValue - maxValue.
     */
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

                    if (intText < minValue || intText > maxValue) {
                    	hasErrors.set(true);
                    }
                }
            } catch (NumberFormatException e) {
            }
        }
	}
}
