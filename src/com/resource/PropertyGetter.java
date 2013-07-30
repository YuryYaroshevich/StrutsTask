package com.resource;

import java.util.ResourceBundle;

public final class PropertyGetter {
	public static enum BundleType {
		RESOURCE_BUNDLE
	}
	
	private static final ResourceBundle resourceBundle = ResourceBundle
			.getBundle("com.resource.appresource");

	private PropertyGetter() {
	}

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
	
	/*public static String getProperty(String key, BundleType bundleType) {
		switch (bundleType) {
		case ERROR_MSG_BUNDLE:
			return errorMessageBundle.getString(key);
		default:
			return resourceBundle.getString(key);
		}
	}*/
}