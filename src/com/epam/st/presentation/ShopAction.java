package com.epam.st.presentation;

import static com.epam.st.constant.STConstant.ADD_GOOD_XSLT;
import static com.epam.st.constant.STConstant.CATEGORY_NAME;
import static com.epam.st.constant.STConstant.PRODUCTS_XML;
import static com.epam.st.constant.STConstant.SUBCATEGORY_NAME;
import static com.epam.st.constant.STConstant.VALIDATION_XSLT;
import static com.epam.st.constant.STConstant.VALIDATOR;
import static com.epam.st.resource.PropertyGetter.getProperty;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.epam.st.util.GoodValidator;
import com.epam.st.util.ParameterTransporter;
import com.epam.st.util.ProductsJDOMHandler;
import com.epam.st.util.ProductsXmlIO;
import com.epam.st.util.Synchronizer;
import com.epam.st.util.TemplatesCache;

public final class ShopAction extends DispatchAction {
	private static final String CATEGORIES_FORWARD = "categories";
	private static final String SUBCATERIES_FORWARD = "subcategories";
	private static final String GOODS_FORWARD = "goods";

	private static final String REDIRECT_QUERY_START = "redirect.query.start";

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
		return mapping.findForward(GOODS_FORWARD);
	}

	private static void updateProductsJDOM(ShopForm shopForm) throws Exception {
		Synchronizer.getReadLock().lock();
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document productsJDOM = saxBuilder.build(getProperty(PRODUCTS_XML));
			shopForm.setProductsJDOM(productsJDOM);
		} finally {
			Synchronizer.getReadLock().unlock();
		}
	}

	public void addGood(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ShopForm shopForm = (ShopForm) form;
		StreamSource xmlSource = new StreamSource(getProperty(PRODUCTS_XML));
		StreamResult toPage = new StreamResult(resp.getWriter());
		Transformer transf = TemplatesCache
				.getCorrespondTransf(getProperty(ADD_GOOD_XSLT));
		transf.setParameter(CATEGORY_NAME, shopForm.getCategoryName());
		transf.setParameter(SUBCATEGORY_NAME, shopForm.getSubcategoryName());
		transf.transform(xmlSource, toPage);
	}

	public void saveGood(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ShopForm shopForm = (ShopForm) form;
		Transformer transf = TemplatesCache
				.getCorrespondTransf(getProperty(VALIDATION_XSLT));
		GoodValidator validator = setParametersInTransf(transf, req);

		// apply transformation
		File xml;
		long lastModified;
		String resultingInfo;
		Synchronizer.getReadLock().lock();
		try {
			xml = new File(getProperty(PRODUCTS_XML));
			lastModified = xml.lastModified();
			resultingInfo = ProductsXmlIO.transformAndGetResult(transf,
					getProperty(PRODUCTS_XML));
		} finally {
			Synchronizer.getReadLock().unlock();
		}

		// check the result of transformation
		if (validator.isGoodValid()) {
			// validation passed; list of goods with new good
			ProductsXmlIO.writeGoodToXML(resultingInfo,
					getProperty(PRODUCTS_XML), lastModified, transf);
			updateProductsJDOM(shopForm);
			resp.sendRedirect(buildRedirectQuery(shopForm.getCategoryName(),
					shopForm.getSubcategoryName()));
		} else {
			// validation didn't passed; form with error messages
			resp.getWriter().append(resultingInfo);
		}
	}

	private static GoodValidator setParametersInTransf(Transformer transf,
			HttpServletRequest req) {
		GoodValidator validator = new GoodValidator();
		transf.setParameter(VALIDATOR, validator);
		ParameterTransporter.transportFromRequestToTransformer(req, transf);
		return validator;
	}

	private static String buildRedirectQuery(String categName,
			String subcategName) {
		StringBuilder query = new StringBuilder(
				getProperty(REDIRECT_QUERY_START));
		query.append(CATEGORY_NAME + "=" + categName + "&");
		query.append(SUBCATEGORY_NAME + "=" + subcategName);
		return query.toString();
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
		ProductsXmlIO.updateGoodsInXML(productsJDOM, getProperty(PRODUCTS_XML));
		updateProductsJDOM(shopForm);
		return mapping.findForward(GOODS_FORWARD);
	}
}
