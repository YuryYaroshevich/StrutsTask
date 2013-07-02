package com.epam.st.presentation;

import static com.epam.st.stconstant.STConstant.PRODUCTS_XML;
import static com.resource.PropertyGetter.getProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

public final class ShopAction extends DispatchAction {
	private static final SAXBuilder SAX_BUILDER = new SAXBuilder();

	private static final String CATEGORIES_FORWARD = "categories";

	public ActionForward categories(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			ShopForm shopForm = (ShopForm) form;
			Document productsJDOM = SAX_BUILDER
					.build(getProperty(PRODUCTS_XML));
			shopForm.setProductsJDOM(productsJDOM);
			return mapping.findForward(CATEGORIES_FORWARD);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
