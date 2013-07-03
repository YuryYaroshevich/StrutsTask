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
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public final class ShopAction extends DispatchAction {
	private static final SAXBuilder SAX_BUILDER = new SAXBuilder();

	private static final String CATEGORIES_FORWARD = "categories";
	private static final String SUBCATERIES_FORWARD = "subcategories";
	private static final String GOODS_FORWARD = "goods";

	// keys for getting values from request
	private static final String CATEG_NAME = "categName";
	private static final String SUBCATEG_NAME = "subcategName";

	public ActionForward categories(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			ShopForm shopForm = (ShopForm) form;
			updateProductsJDOM(shopForm);
			return mapping.findForward(CATEGORIES_FORWARD);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ActionForward subcategories(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			ShopForm shopForm = (ShopForm) form;
			shopForm.setCategoryName(req.getParameter(CATEG_NAME));
			updateProductsJDOM(shopForm);
			return mapping.findForward(SUBCATERIES_FORWARD);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ActionForward goods(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			ShopForm shopForm = (ShopForm) form;
			shopForm.setSubcategoryName(req.getParameter(SUBCATEG_NAME));
			shopForm.setCategoryName(req.getParameter(CATEG_NAME));
			updateProductsJDOM(shopForm);
			return mapping.findForward(GOODS_FORWARD);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static void updateProductsJDOM(ShopForm shopForm) throws Exception {
		Document productsJDOM = SAX_BUILDER.build(getProperty(PRODUCTS_XML));
		shopForm.setProductsJDOM(productsJDOM);		
	}
}
