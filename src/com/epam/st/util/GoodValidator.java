package com.epam.st.util;

import static com.epam.st.constant.STConstant.EMPTY_DATE;
import static com.epam.st.constant.STConstant.WRONG_DATE_FORMAT;
import static com.epam.st.constant.STConstant.WRONG_DATE_RANGE;

import java.util.regex.Pattern;

public final class GoodValidator {
	// I return empty string when everything is valid
	public static final String VALID = "";

	private static final String DATE_REGEXP =
			"(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(\\d{4})";
	private static final String CORRECT_DATE_RANGE_REGEXP = 
			"(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(19\\d{2}|2\\d{3})";

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
}
