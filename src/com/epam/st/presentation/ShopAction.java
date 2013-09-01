package com.epam.st.presentation;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

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
import com.epam.st.util.ProductsJDOMHandler;
import com.epam.st.util.ProductsXMLWriter;
import com.epam.st.util.Synchronizer;
import com.epam.st.util.TemplatesCache;

import static com.epam.st.constant.STConstant.*;
import static com.epam.st.resource.PropertyGetter.*;

public final class ShopAction extends DispatchAction {
	private static final String CATEGORIES_FORWARD = "categories";
	private static final String SUBCATERIES_FORWARD = "subcategories";
	private static final String GOODS_FORWARD = "goods";

	// parameter names for taking values from request and setting values in
	// transformer
	private static final String PRODUCER = "producer";
	private static final String MODEL = "model";
	private static final String DATE_OF_ISSUE = "dateOfIssue";
	private static final String COLOR = "color";
	private static final String PRICE = "price";

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

		// read products.xml and add new good to buffer by applying
		// transformation
		File xml;
		long lastModified;
		String resultingInfo;
		Synchronizer.getReadLock().lock();
		try {
			xml = new File(getProperty(PRODUCTS_XML));
			lastModified = xml.lastModified();
			resultingInfo = transformAndGetResult(transf,
					getProperty(PRODUCTS_XML));
		} finally {
			Synchronizer.getReadLock().unlock();
		}

		// check the result of transformation
		if (validator.isGoodValid()) {
			// Validation passed; list of goods with new good
			writeToXML(resultingInfo, getProperty(PRODUCTS_XML), lastModified,
					transf);
			updateProductsJDOM(shopForm);
			resp.sendRedirect(buildRedirectQuery(shopForm.getCategoryName(),
					shopForm.getSubcategoryName()));
		} else {
			// Validation didn't passed; form with error messages
			resp.getWriter().append(resultingInfo);
		}
	}

	private static String transformAndGetResult(Transformer transf,
			String xmlPath) throws Exception {
		StringWriter stringWriter = null;
		Synchronizer.getReadLock().lock();
		try {
			StreamSource xmlSource = new StreamSource(xmlPath);
			stringWriter = new StringWriter();
			StreamResult output = new StreamResult(stringWriter);
			transf.transform(xmlSource, output);
			return stringWriter.toString();
		} finally {
			Synchronizer.getReadLock().unlock();
			if (stringWriter != null) {
				stringWriter.close();
			}
		}
	}

	private static GoodValidator setParametersInTransf(Transformer transf,
			HttpServletRequest req) {
		GoodValidator validator = new GoodValidator();
		transf.setParameter(VALIDATOR, validator);
		transf.setParameter(CATEGORY_NAME, req.getParameter(CATEGORY_NAME));
		transf.setParameter(SUBCATEGORY_NAME,
				req.getParameter(SUBCATEGORY_NAME));
		transf.setParameter(PRODUCER, req.getParameter(PRODUCER));
		transf.setParameter(MODEL, req.getParameter(MODEL));
		transf.setParameter(DATE_OF_ISSUE, req.getParameter(DATE_OF_ISSUE));
		transf.setParameter(COLOR, req.getParameter(COLOR));
		transf.setParameter(PRICE, req.getParameter(PRICE));
		return validator;
	}

	private static void writeToXML(String information, String xmlPath,
			long lastModified, Transformer transf) throws Exception {
		FileWriter fileWriter = null;
		Synchronizer.getWriteLock().lock();
		try {
			File xml = new File(xmlPath);
			if (lastModified != xml.lastModified()) {
				GoodValidator validator = (GoodValidator) transf
						.getParameter(VALIDATOR);
				validator.reset();
				information = transformAndGetResult(transf, xmlPath);
			}
			fileWriter = new FileWriter(xml);
			fileWriter.append(information);
		} finally {
			Synchronizer.getWriteLock().unlock();
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
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
		ProductsXMLWriter.updateGoodsInXML(productsJDOM);
		updateProductsJDOM(shopForm);
		return mapping.findForward(GOODS_FORWARD);
	}
}
