/*
 * This file was last modified at 2022.05.01 22:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DOMParserService.java
 * $Id$
 */

package su.svn.readXML.services;

import su.svn.readXML.model.Bundle;

import java.util.List;

public interface DOMParserService {
    List<Bundle> parseData(String xmlData, String xmlSchemaFileName);

    List<Bundle> parseFile(String xmlFile, String xmlSchemaFileName);
}
