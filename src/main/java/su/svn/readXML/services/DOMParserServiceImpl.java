/*
 * This file was last modified at 2022.05.01 21:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DOMParserServiceImpl.java
 * $Id$
 */

package su.svn.readXML.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import java.util.Arrays;
import java.util.stream.Collectors;

import static su.svn.readXML.Main.SPACES;

@Slf4j
@Service
public class DOMParserServiceImpl {
    public static final String prefixSymbol = "|   ";

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

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            Element root = document.getDocumentElement();
            log.info("XML root: [[{}]]", root.getNodeName());

            NodeList nList = document.getElementsByTagName("employee");

            visitChildNodes(nList, 1);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);  // TODO custom Exception
        }
    }

    //This function is called recursively
    private static void visitChildNodes(NodeList nList, int level) {

        final String prefixSeparator = prefixSymbol.repeat(level);

        for (int j = 0; j < nList.getLength(); j++) {
            Node node = nList.item(j);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String content = Arrays.stream(node.getTextContent().split(SPACES))
                        .filter(s -> s != null && s.length() > 0)
                        .collect(Collectors.joining(" || "));
                log.info("{}Node name: [[{}]] Value: [[{}]]", prefixSeparator, node.getNodeName(), content);
                //Check all attributes
                if (node.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node tempNode = nodeMap.item(i);
                        log.info(
                                "{}Attr name: [[{}]] Value: [[{}]]",
                                prefixSeparator,
                                tempNode.getNodeName(),
                                tempNode.getNodeValue()
                        );
                    }
                }
                if (node.hasChildNodes()) {
                    //We got more childs; Let's visit them as well
                    visitChildNodes(node.getChildNodes(), level + 1);
                }
            }
        }
    }
}
