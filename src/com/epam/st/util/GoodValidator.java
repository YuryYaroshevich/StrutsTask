package com.epam.st.util;

import static com.epam.st.constant.STConstant.*;
import static com.epam.st.resource.PropertyGetter.getProperty;

import java.util.regex.Pattern;

public final class GoodValidator {
	private boolean isGoodValid;

	// I return empty string when everything is valid
	private static final String VALID = "";

	private static final String MODEL_REGEXP = "^[a-zA-Z]{2}\\d{3}$";
	private static final String DATE_REGEXP = "^(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
	private static final String CORRECT_DATE_RANGE_REGEXP = "^(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(19\\d{2}|2\\d{3})$";
	private static final String PRICE_REGEXP = "^\\d+$|^not in stock$";

	public GoodValidator() {
		setGoodValid(true);
	}

	public void setGoodValid(boolean isValid) {
		this.isGoodValid = isValid;
	}

	public void reset() {
		setGoodValid(true);
	}

	public String validateProducer(String producer) {
		if (producer.isEmpty()) {
			setGoodValid(false);
			return getProperty(EMPTY_PRODUCER);
		}
		return VALID;
	}

	public String validateModel(String model) {
		if (model.isEmpty()) {
			setGoodValid(false);
			return getProperty(EMPTY_MODEL);
		} else if (!Pattern.matches(MODEL_REGEXP, model)) {
			setGoodValid(false);
			return getProperty(WRONG_MODEL_FORMAT);
		}
		return VALID;
	}

	public String validateDate(String date) {
		if (date.isEmpty()) {
			setGoodValid(false);
			return getProperty(EMPTY_DATE);
		} else if (Pattern.matches(DATE_REGEXP, date)) {
			if (!Pattern.matches(CORRECT_DATE_RANGE_REGEXP, date)) {
				setGoodValid(false);
				return getProperty(WRONG_DATE_RANGE);
			}
		} else {
			setGoodValid(false);
			return getProperty(WRONG_DATE_FORMAT);
		}
		return VALID;
	}

	public String validateColor(String color) {
		if (color.isEmpty()) {
			setGoodValid(false);
			return getProperty(EMPTY_COLOR);
		}
		return VALID;
	}

	// Checks price and if it empty, checks notInStock to be true.
	// If price is correct then notInStock should be false.
	public String validatePrice(String price) {
		if (price.isEmpty()) {
			setGoodValid(false);
			return getProperty(PRICE_IS_EMPTY);
		} else if (!Pattern.matches(PRICE_REGEXP, price)) {
			setGoodValid(false);
			return getProperty(WRONG_PRICE_FORMAT);
		}
		return VALID;
	}

	public boolean isGoodValid() {
		return isGoodValid;
	}
}
