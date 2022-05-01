/*
 * This file was last modified at 2022.05.01 19:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DOMParserServiceImpl.java
 * $Id$
 */

package su.svn.readXML.services;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class DOMParserServiceImpl {
    public void parseData(String xmlData, String xmlSchemaFileName) {
        parse(new ByteArrayInputStream(xmlData.getBytes()), xmlSchemaFileName);
    }

    public void parseFile(String xmlFile, String xmlSchemaFileName) {
        try (InputStream is = new FileInputStream(xmlFile)) {
            parse(is, xmlSchemaFileName);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO custom Exception
        }
    }

    private void parse(InputStream is, String schemaFileName) {
        Schema schema = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(language);
            schema = schemaFactory.newSchema(new File(schemaFileName));
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);  // TODO custom Exception
        }
    }
}
