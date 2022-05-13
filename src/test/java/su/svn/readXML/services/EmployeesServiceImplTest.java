package su.svn.readXML.services;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import su.svn.readXML.model.xml.gen.Employees;
import su.svn.readXML.utils.IO;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EmployeesServiceImpl.class})
@DisplayName("Unit tests for class EmployeesServiceImpl ")
class EmployeesServiceImplTest {

    @Autowired
    EmployeesServiceImpl employeesService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void parseFile() {
        Employees employees = employeesService.parseFile(
                IO.Util.getResource("xml/employees.xml"),
                IO.Util.getResource("xsd/employees.xsd")
        );
        List<Employees.Employee> list = employees.getEmployee();
        System.out.println("list = " + list);
    }
}