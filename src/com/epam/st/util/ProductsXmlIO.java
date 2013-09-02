package com.epam.st.util;

import static com.epam.st.constant.STConstant.VALIDATOR;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

public final class ProductsXmlIO {
	// for updating goods in XML
	private static final XMLOutputter OUTPUTTER = new XMLOutputter();	

	private ProductsXmlIO() {
	}

	public static void updateGoodsInXML(Document productsJDOM, String xmlPath)
			throws Exception {
		Synchronizer.getWriteLock().lock();
		try {
			OUTPUTTER.output(productsJDOM, new FileOutputStream(xmlPath));	
		} finally {
			Synchronizer.getWriteLock().unlock();
		}
	}
	
	
	public static void writeGoodToXML(String information, String xmlPath,
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
	
	public static String transformAndGetResult(Transformer transf,
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
}
