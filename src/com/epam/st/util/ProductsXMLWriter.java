package com.epam.st.util;

import static com.epam.st.stconstant.STConstant.PRODUCTS_XML;
import static com.epam.st.stconstant.STConstant.SAVE_GOOD_XSLT;
import static com.resource.PropertyGetter.getProperty;

import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.epam.st.product.Good;

public final class ProductsXMLWriter {
	private static final TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

	// parameter names for setting values in transformer
	private static final String CATEGORY_NAME = "categoryName";
	private static final String SUBCATEGORY_NAME = "subcategoryName";
	private static final String PRODUCER = "producer";
	private static final String MODEL = "model";
	private static final String DATE_OF_ISSUE = "dateOfIssue";
	private static final String COLOR = "color";
	private static final String PRICE = "price";
	private static final String NOT_IN_STOCK = "notInStock";

	private ProductsXMLWriter() {
	}

	public static void writeGoodToXML(Good good, String categName,
			String subcategName) throws Exception {
		Templates saveGoodTempl = transformerFactory
				.newTemplates(new StreamSource(getProperty(SAVE_GOOD_XSLT)));

		Transformer transf = saveGoodTempl.newTransformer();
		transf.setParameter(CATEGORY_NAME, categName);
		transf.setParameter(SUBCATEGORY_NAME, subcategName);
		transf.setParameter(PRODUCER, good.getProducer());
		transf.setParameter(MODEL, good.getModel());
		transf.setParameter(DATE_OF_ISSUE, good.getDateOfIssue());
		transf.setParameter(COLOR, good.getColor());
		boolean notInStock = good.isNotInStock();
		if (notInStock) {
			transf.setParameter(NOT_IN_STOCK, notInStock);
		} else {
			transf.setParameter(PRICE, good.getPrice());
		}

		StreamSource xmlSource = new StreamSource(getProperty(PRODUCTS_XML));
		StringWriter stringWriter = new StringWriter();
		StreamResult outputTarget = new StreamResult(stringWriter);
		transf.transform(xmlSource, outputTarget);
		FileWriter fileWriter = new FileWriter(getProperty(PRODUCTS_XML));
		fileWriter.append(stringWriter.toString());
		fileWriter.close();
		stringWriter.close();
	}
}
