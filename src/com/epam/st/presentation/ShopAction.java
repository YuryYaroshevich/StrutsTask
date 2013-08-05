package com.epam.st.presentation;

import static com.epam.st.constant.STConstant.*;
import static com.epam.st.resource.PropertyGetter.getProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.epam.st.util.ProductsJDOMHandler;
import com.epam.st.util.ProductsXMLWriter;
import com.epam.st.util.Synchronizer;

public final class ShopAction extends DispatchAction {
	private static final SAXBuilder SAX_BUILDER = new SAXBuilder();

	private static final String CATEGORIES_FORWARD = "categories";
	private static final String SUBCATERIES_FORWARD = "subcategories";
	private static final String GOODS_FORWARD = "goods";
	private static final String ADD_GOOD_FORWARD = "addGood";

	public ActionForward categories(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ShopForm shopForm = (ShopForm) form;
		updateProductsJDOM(shopForm);
		return mapping.findForward(CATEGORIES_FORWARD);
	}

	public ActionForward subcategories(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ShopForm shopForm = (ShopForm) form;
		updateProductsJDOM(shopForm);
		String categName = req.getParameter(CATEGORY_NAME);
		shopForm.setCategoryName(categName);
		int categId = ProductsJDOMHandler.getCategoryListIndex(categName,
				shopForm.getProductsJDOM());
		shopForm.setCategoryId(categId);
		return mapping.findForward(SUBCATERIES_FORWARD);
	}

	public ActionForward goods(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ShopForm shopForm = (ShopForm) form;
		String categName = req.getParameter(CATEGORY_NAME);
		String subcategName = req.getParameter(SUBCATEGORY_NAME);
		shopForm.setSubcategoryName(subcategName);
		updateProductsJDOM(shopForm);
		int categId = ProductsJDOMHandler.getCategoryListIndex(categName,
				shopForm.getProductsJDOM());
		shopForm.setCategoryId(categId);
		int subcategId = ProductsJDOMHandler.getSubcategoryListIndex(categId,
				subcategName, shopForm.getProductsJDOM());
		shopForm.setSubcategoryId(subcategId);

		saveToken(req);
		return mapping.findForward(GOODS_FORWARD);
	}

	private static void updateProductsJDOM(ShopForm shopForm) throws Exception {
		Synchronizer.getReadLock().lock();
		try {
			Document productsJDOM = SAX_BUILDER.build(getProperty(PRODUCTS_XML));
			shopForm.setProductsJDOM(productsJDOM);
		} finally {
			Synchronizer.getReadLock().unlock();
		}
	}

	public ActionForward addGood(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) {
		return mapping.findForward(ADD_GOOD_FORWARD);
	}

	public ActionForward saveGood(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ShopForm shopForm = (ShopForm) form;
		if (isTokenValid(req)) {
			ActionMessages errors = shopForm.validate(mapping, req);
			if (!errors.isEmpty()) {
				saveErrors(req, errors);
				return mapping.findForward(ADD_GOOD_FORWARD);
			}
			resetToken(req);
			ProductsXMLWriter.writeGoodToXML(shopForm.getGood(),
					shopForm.getCategoryName(), shopForm.getSubcategoryName());
			updateProductsJDOM(shopForm);
		}
		return mapping.findForward(GOODS_FORWARD);

	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) {
		return mapping.findForward(GOODS_FORWARD);
	}

	public ActionForward updateGoods(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ShopForm shopForm = (ShopForm) form;
		Document productsJDOM = shopForm.getProductsJDOM();
		int categId = shopForm.getCategoryId();
		int subcategId = shopForm.getSubcategoryId();
		ProductsJDOMHandler.setCorrespondShopState(productsJDOM, categId,
				subcategId);
		ProductsXMLWriter.updateGoodsInXML(productsJDOM);
		updateProductsJDOM(shopForm);
		return mapping.findForward(GOODS_FORWARD);
	}
}
