package su.svn.readXML.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import su.svn.readXML.model.Bundle;
import su.svn.readXML.utils.IO;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DOMParserServiceImpl.class})
@DisplayName("Unit tests for class DOMParserServiceImpl ")
class DOMParserServiceImplTest {

    @Autowired
    DOMParserServiceImpl domParserService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void parseFile() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<Bundle> result = domParserService.parseFile(
                            IO.Util.getResource("xml/employees.xml"),
                            IO.Util.getResource("xsd/employees.xsd")
                    );
                    Assertions.assertNotNull(result);
                });
    }
}