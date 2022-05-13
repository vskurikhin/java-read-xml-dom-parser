/*
 * This file was last modified at 2022.05.13 10:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EmployeesService.java
 * $Id$
 */

package su.svn.readXML.services;

import su.svn.readXML.model.xml.gen.Employees;

import java.io.ByteArrayInputStream;

public interface EmployeesService {

    Employees parseData(String xmlData, String xmlSchemaFileName);

    Employees parseFile(String xmlFile, String xmlSchemaFileName);
}
