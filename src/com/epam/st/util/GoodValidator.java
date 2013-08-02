package com.epam.st.util;

import static com.epam.st.stconstant.STConstant.EMPTY_DATE;
import static com.epam.st.stconstant.STConstant.NOT_IN_STOCK_TO_FALSE;
import static com.epam.st.stconstant.STConstant.NOT_IN_STOCK_TO_TRUE;
import static com.epam.st.stconstant.STConstant.WRONG_DATE_FORMAT;
import static com.epam.st.stconstant.STConstant.WRONG_DATE_RANGE;
import static com.epam.st.stconstant.STConstant.WRONG_PRICE_FORMAT;

import java.util.regex.Pattern;

public final class GoodValidator {
	// I return empty string when everything is valid
	public static final String VALID = "";

	private static final String DATE_REGEXP =
			"(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(\\d{4})";
	private static final String CORRECT_DATE_RANGE_REGEXP = 
			"(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(19\\d{2}|2\\d{3})";
	private static final String NUMBER_REGEXP = "\\d+";

	private GoodValidator() {
	}

	public static String validateDate(String date) {
		if (date.isEmpty()) {
			return EMPTY_DATE;
		} else if (Pattern.matches(DATE_REGEXP, date)) {
			if (!Pattern.matches(CORRECT_DATE_RANGE_REGEXP, date)) {
				return WRONG_DATE_RANGE;
			}
		} else {
			return WRONG_DATE_FORMAT;
		}
		return VALID;
	}

	// Checks price and if it empty, checks notInStock to be true.
	// If price is correct then notInStock should be false.
	public static String validateShopState(String price, boolean notInStock) {
		if (price.isEmpty()) {
			// then notInStock should be true
			if (!notInStock) {
				return NOT_IN_STOCK_TO_TRUE;
			}
		} else if (Pattern.matches(NUMBER_REGEXP, price)) {
			// then notInStock should be false
			if (notInStock) {
				return NOT_IN_STOCK_TO_FALSE;
			}
		} else {
			return WRONG_PRICE_FORMAT;
		}
		return VALID;
	}
}
