/*
 * This file was last modified at 2022.05.01 18:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DOMParserService.java
 * $Id$
 */

package su.svn.readXML.services;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class DOMParserService {
    public void parseData(String data) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
