/*
 * This file was last modified at 2022.05.13 10:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EmployeesServiceImpl.java
 * $Id$
 */

package su.svn.readXML.services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import su.svn.readXML.model.xml.gen.Employees;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Override
    public Employees parseData(String xmlData, String xmlSchemaFileName) {
        return parse(new ByteArrayInputStream(xmlData.getBytes()), xmlSchemaFileName);
    }

    @Override
    public Employees parseFile(String xmlFile, String xmlSchemaFileName) {
        try (InputStream is = new FileInputStream(xmlFile)) {
            return parse(is, xmlSchemaFileName);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO custom Exception
        }
    }

    private Employees parse(InputStream is, String xmlSchemaFileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(Employees.class);
            return (Employees) context.createUnmarshaller().unmarshal(is);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
