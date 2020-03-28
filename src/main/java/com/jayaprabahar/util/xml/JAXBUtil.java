package com.jayaprabahar.util.xml;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> Project : com.jayaprabahar.util.xml </p>
 * <p> Title : JAXBUtil.java </p>
 * <p> Description: Utility class for JAXB related operations </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public final class JAXBUtil {

	private JAXBUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Unmarshall or Converts XML to Java bean where you need to ignore root element type
	 *  
	 * @param String sourceXml
	 * @param Class[] sourceClasses
	 * @return Object JAXB Object
	 * @throws JAXBException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object unmarshal(String sourceXml, Class[] sourceClass) throws JAXBException {
		if (StringUtils.isNotBlank(sourceXml)) {
			JAXBElement jaxbElement = JAXBContext.newInstance(sourceClass).createUnmarshaller().unmarshal(new StreamSource(new StringReader(sourceXml)), sourceClass[0]);

			if (jaxbElement != null && jaxbElement.getValue() != null) {
				return jaxbElement.getValue();
			}
		}
		return null;
	}

	/**
	 * Converts JAB object to XML String
	 * 
	 * @param Class jaxbClass
	 * @param Object element
	 * @return String Marshalled String
	 * @throws JAXBException 
	 */
	public static String toXml(@SuppressWarnings("rawtypes") Class jaxbClass, Object element) throws JAXBException {
		Marshaller marshaller = JAXBContext.newInstance(jaxbClass).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshaller.marshal(element, baos);
		return baos.toString();
	}
}
