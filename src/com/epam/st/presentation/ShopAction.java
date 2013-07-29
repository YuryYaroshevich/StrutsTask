package com.epam.st.presentation;

import static com.epam.st.stconstant.STConstant.PRODUCTS_XML;
import static com.epam.st.stconstant.STConstant.SAVE_GOOD_XSLT;
import static com.resource.PropertyGetter.getProperty;

import java.io.FileWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

public final class ShopAction extends DispatchAction {
	private static final SAXBuilder SAX_BUILDER = new SAXBuilder();

	private static final TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

	private static final String CATEGORIES_FORWARD = "categories";
	private static final String SUBCATERIES_FORWARD = "subcategories";
	private static final String GOODS_FORWARD = "goods";
	private static final String ADD_GOOD_FORWARD = "addGood";

	// keys for getting values from request and setting parameters in
	// transformer
	private static final String CATEG_NAME = "categoryName";
	private static final String SUBCATEG_NAME = "subcategoryName";

	// parameter names for setting values in transformer
	private static final String CATEGORY_NAME = "categoryName";
	private static final String SUBCATEGORY_NAME = "subcategoryName";
	private static final String PRODUCER = "producer";
	private static final String MODEL = "model";
	private static final String DATE_OF_ISSUE = "dateOfIssue";
	private static final String COLOR = "color";
	private static final String PRICE = "price";
	private static final String NOT_IN_STOCK = "notInStock";

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
			saveToken(req);
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

	public ActionForward addGood(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) {
		ShopForm shopForm = (ShopForm) form;
		shopForm.reset(mapping, req);
		return mapping.findForward(ADD_GOOD_FORWARD);
	}

	public ActionForward saveGood(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		StringWriter stringWriter = null;
		FileWriter fileWriter = null;
		ShopForm shopForm = (ShopForm) form;
		try {
			if (isTokenValid(req)) {
				Templates saveGoodTempl = transformerFactory
						.newTemplates(new StreamSource(
								getProperty(SAVE_GOOD_XSLT)));

				Transformer transf = saveGoodTempl.newTransformer();
				transf.setParameter(CATEGORY_NAME, shopForm.getCategoryName());
				transf.setParameter(SUBCATEGORY_NAME,
						shopForm.getSubcategoryName());
				transf.setParameter(PRODUCER, shopForm.getProducer());
				transf.setParameter(MODEL, shopForm.getModel());
				transf.setParameter(DATE_OF_ISSUE, shopForm.getDateOfIssue());
				transf.setParameter(COLOR, shopForm.getColor());
				boolean notInStock = shopForm.isNotInStock();
				if (notInStock) {
					transf.setParameter(PRICE, shopForm.getPrice());
				} else {
					transf.setParameter(NOT_IN_STOCK, "true");
				}
				
				StreamSource xmlSource = new StreamSource(
						getProperty(PRODUCTS_XML));
				stringWriter = new StringWriter();
				StreamResult outputTarget = new StreamResult(stringWriter);
				transf.transform(xmlSource, outputTarget);
				fileWriter = new FileWriter(getProperty(PRODUCTS_XML));
				fileWriter.append(stringWriter.toString());
				fileWriter.close();
				stringWriter.close();
				
				updateProductsJDOM(shopForm);
				resetToken(req);
			}
			return mapping.findForward(GOODS_FORWARD);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
}
