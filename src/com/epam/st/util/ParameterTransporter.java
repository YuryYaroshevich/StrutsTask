package com.epam.st.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Transformer;

public final class ParameterTransporter {
	private ParameterTransporter() {
	}

	@SuppressWarnings("unchecked")
	public static void transportFromRequestToTransformer(
			HttpServletRequest req, Transformer transf) {
		Enumeration<String> paramNames = req.getParameterNames();
		String paramName = null;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			transf.setParameter(paramName, req.getParameter(paramName));
		}
	}
}
