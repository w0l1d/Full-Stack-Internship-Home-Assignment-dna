package ma.dnaengineering.backend.service;

import ma.dnaengineering.backend.model.Employee;
import ma.dnaengineering.backend.util.CsvParser;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;

@SpringBootTest
class EmployeeServiceTest {


    @Autowired
    private EmployeeService employeeService;

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testProcessCsvFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv", "text/csv", "1,John,Doe,5000.0".getBytes());
        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"id", "name", "jobTitle", "salary"});
        csvData.add(new String[]{"1", "John", "Doe", "5000.0"});


        try (MockedStatic<CsvParser> utilities = Mockito.mockStatic(CsvParser.class)) {
            utilities.when(() -> CsvParser.parseCsv(any())).thenReturn(csvData);

            List<Employee> employees = employeeService.processCsvFile(file);

            assertEquals(1, employees.size());
            assertAll(
                    () -> assertEquals(1, employees.size()),
                    () -> assertEquals(1L, employees.get(0).id()),
                    () -> assertEquals("John", employees.get(0).name()),
                    () -> assertEquals("Doe", employees.get(0).jobTitle()),
                    () -> assertEquals(5000.0, employees.get(0).salary())
            );
        }


    }

    @Test
    void testCalculateAverageSalaryByJobTitle() {
        List<Employee> employees =
                List.of(Employee.builder().id(1L).name("John").jobTitle("Engineer").salary(5000.0).build(),
                        Employee.builder().id(2L).name("Jane").jobTitle("Engineer").salary(6000.0).build(),
                        Employee.builder().id(3L).name("Doe").jobTitle("Manager").salary(7000.0).build());

        var averageSalaries = employeeService
                .calculateAverageSalaryByJobTitle(employees);

        assertEquals(2, averageSalaries.size());
        assertEquals(5500.0, averageSalaries.get("Engineer"));
        assertEquals(7000.0, averageSalaries.get("Manager"));
    }

    @Test
    void testProcessCsvFile_NullFile() {
        assertThrows(NullPointerException.class, () -> employeeService.processCsvFile(null));
    }

    @Test
        //empty.csv is an empty file with no content and no header
    void testProcessCsvFile_EmptyFile() {
        MockMultipartFile file = new MockMultipartFile("file", "empty.csv", "text/csv", "".getBytes());
        assertThrows(IndexOutOfBoundsException.class, () -> employeeService.processCsvFile(file));
    }

    @Test
    void testProcessCsvFile_MalformedData_OneNonCommaSeparatedColumn() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "malformed.csv",
                "text/csv",
                """
                        id,name,jobTitle,salary
                        1 John Doe 5000.0
                        """.getBytes());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> employeeService.processCsvFile(file));

        assertEquals("Invalid data: expected 4 columns but got 1 columns", exception.getMessage());
    }

    @Test
    void testProcessCsvFile_MalformedData_AdditionalColumn() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "malformed.csv",
                "text/csv",
                """
                        id,name,jobTitle,salary
                        1,John,Doe,5000.0
                        2,John,Doe,5000.0,5000.0
                        """.getBytes());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> employeeService.processCsvFile(file));
        assertEquals("Invalid data: expected 4 columns but got 5 columns", exception.getMessage());
    }

    @Test
    void testProcessCsvFile_MalformedData_MissingColumn() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "malformed.csv",
                "text/csv",
                """
                        id,name,jobTitle,salary
                        1,John,Doe
                        """.getBytes());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> employeeService.processCsvFile(file));
        assertEquals("Invalid data: expected 4 columns but got 3 columns", exception.getMessage());
    }

    @Test
    void testProcessCsvFile_MalformedData_EmptyColumn() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "malformed.csv",
                "text/csv",
                """
                        id,name,jobTitle,salary
                        1,John,Doe,
                        """.getBytes());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> employeeService.processCsvFile(file));
        assertEquals("Invalid data: expected 4 columns but got 3 columns", exception.getMessage());
    }

    @Test
        // invalid number format
    void testProcessCsvFile_MalformedData_InvalidNumberFormat() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "malformed.csv",
                "text/csv",
                """
                        id,name,jobTitle,salary
                        1,John,Doe,5000.0
                        2,John,Doe,5000.0a
                        """.getBytes());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> employeeService.processCsvFile(file));
        assertEquals("For input string: \"5000.0a\"", exception.getMessage());
    }

}