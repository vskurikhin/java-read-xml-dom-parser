/*
 * This file was last modified at 2022.05.01 19:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DOMParserServiceImpl.java
 * $Id$
 */

package su.svn.readXML.services;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

@Service
public class DOMParserServiceImpl {
    public void parseData(String xmlData) {
        parse(new ByteArrayInputStream(xmlData.getBytes()));
    }

    public void parseFile(String xmlFile) {
        try (InputStream is = new FileInputStream(xmlFile)) {
            parse(is);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO custom Exception
        }
    }

    private void parse(InputStream is) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);  // TODO custom Exception
        }
    }
}
