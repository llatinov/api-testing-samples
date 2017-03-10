package com.automationrhapsody.httpclient.utils;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public final class JaxbUtils {

    private static final Map<Class, JAXBContext> JAXB_CONTEXT = new HashMap<>();

    private JaxbUtils() {
        // Utils class
    }

    public static <T> T read(InputStream input, Class<T> clazz) {
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.parse(input);
            final String xmlRootElementName = clazz.getAnnotation(XmlRootElement.class).name();
            final Node node = document.getElementsByTagName(xmlRootElementName).item(0);

            final JAXBContext context = getContext(clazz);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final Object unmarshalled = unmarshaller.unmarshal(node);
            return unmarshalled instanceof JAXBElement
                ? clazz.cast(((JAXBElement) unmarshalled).getValue()) : clazz.cast(unmarshalled);
        } catch (Exception e) {
            Log.logError(e.getMessage());
        }
        return null;
    }

    public static <T> String toXmlString(T entity) {
        try {
            final StringWriter stringWriter = new StringWriter();
            final JAXBContext context = getContext(entity.getClass());
            final Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(entity, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            Log.logError(e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    private static JAXBContext getContext(Class clazz) throws JAXBException {
        JAXBContext context = JAXB_CONTEXT.get(clazz);
        if (context == null) {
            context = JAXBContext.newInstance(clazz);
            JAXB_CONTEXT.put(clazz, context);
        }
        return context;
    }
}
