package com.epam.st.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

public final class TemplatesCache {
	private static final Map<String, TemplatesWrapper> cache = new HashMap<String, TemplatesWrapper>();

	private static final TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

	private static final Lock lock = new ReentrantLock();

	private TemplatesCache() {
	}

	public static Transformer getCorrespondTransf(String xsltPath)
			throws TransformerConfigurationException {
		File xsltFile = new File(xsltPath);
		TemplatesWrapper templ = cache.get(xsltPath);
		long lastModified = xsltFile.lastModified();

		if (templ != null) {
			// check if XSLT file was changed
			if (lastModified > templ.lastModified) {
				templ = null;
			}
		} else {
			templ = createNewEntryInCache(lastModified, xsltPath);
		}
		return templ.templates.newTransformer();
	}

	private static TemplatesWrapper createNewEntryInCache(long lastModified,
			String key) throws TransformerConfigurationException {
		TemplatesWrapper templWrapper = cache.get(key);
		// check if other thread has updated entry in a cache already
		if (templWrapper != null && lastModified == templWrapper.lastModified) {
			return templWrapper;
		}
		// if didn't, put new entry in a cache
		lock.lock();
		try {
			Templates templates = transformerFactory
					.newTemplates(new StreamSource(key));
			templWrapper = new TemplatesWrapper(templates, lastModified);
			cache.put(key, templWrapper);
			return templWrapper;
		} finally {
			lock.unlock();
		}
	}

	private static class TemplatesWrapper {
		Templates templates;
		long lastModified;

		TemplatesWrapper(Templates templates, long lastModified) {
			this.templates = templates;
			this.lastModified = lastModified;
		}
	}
}
