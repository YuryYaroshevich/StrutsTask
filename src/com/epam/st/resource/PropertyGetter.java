package com.epam.st.resource;

import java.util.ResourceBundle;

public final class PropertyGetter {	
	private static final ResourceBundle resourceBundle = ResourceBundle
			.getBundle("com.epam.st.resource.appresource");

	private PropertyGetter() {
	}

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}