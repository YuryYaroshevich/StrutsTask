package com.epam.st.util;

import static com.epam.st.constant.STConstant.CATEGORY_NAME;
import static com.epam.st.constant.STConstant.PRODUCTS_XML;
import static com.epam.st.constant.STConstant.SAVE_GOOD_XSLT;
import static com.epam.st.constant.STConstant.SUBCATEGORY_NAME;
import static com.epam.st.resource.PropertyGetter.getProperty;

import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Document;
import org.jdom2.transform.JDOMSource;

import com.epam.st.product.Good;

public final class ProductsXMLWriter {
	private static final TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

	// parameter names for setting values in transformer
	private static final String PRODUCER = "producer";
	private static final String MODEL = "model";
	private static final String DATE_OF_ISSUE = "dateOfIssue";
	private static final String COLOR = "color";
	private static final String PRICE = "price";

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
		transf.setParameter(PRICE, good.getPrice());
		
		StreamSource source = new StreamSource(getProperty(PRODUCTS_XML));
		write(transf, source);
	}

	public static void updateGoodsInXML(Document productsJDOM) throws Exception {
		Transformer transf = transformerFactory.newTransformer();
		JDOMSource source = new JDOMSource(productsJDOM);
		write(transf, source);
	}

	private static void write(Transformer transf, Source source)
			throws Exception {
		FileWriter fileWriter = null;
		StringWriter stringWriter = null;
		Synchronizer.getWriteLock().lock();
		try {
			stringWriter = new StringWriter();
			StreamResult outputTarget = new StreamResult(stringWriter);
			transf.transform(source, outputTarget);
			fileWriter = new FileWriter(getProperty(PRODUCTS_XML));
			fileWriter.append(stringWriter.toString());
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
			if (stringWriter != null) {
				stringWriter.close();
			}
			Synchronizer.getWriteLock().unlock();
		}
	}
}
