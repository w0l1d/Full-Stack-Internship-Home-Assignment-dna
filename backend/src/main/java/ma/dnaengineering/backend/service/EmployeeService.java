package ma.dnaengineering.backend.service;

import ma.dnaengineering.backend.model.Employee;
import ma.dnaengineering.backend.util.CsvParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class EmployeeService {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private double roundToTwoDecimals(double value) {
        return Double.parseDouble(DECIMAL_FORMAT.format(value));
    }

    public List<Employee> processCsvFile(MultipartFile file) throws IOException {
        List<String[]> csvData = CsvParser.parseCsv(file.getInputStream());

        // skip header
        csvData.remove(0);

        return csvData.stream()
                .map(this::mapToEmployee)
                .collect(toList());
    }

    private Employee mapToEmployee(String[] data) {
        return Employee
                .builder()
                .id(Long.parseLong(data[0]))
                .name(data[1])
                .jobTitle(data[2])
                .salary(Double.parseDouble(data[3]))
                .build();
    }

    public Map<String, Double> calculateAverageSalaryByJobTitle(List<Employee> employees) {

        return employees
                .stream()
                .collect(groupingBy(
                        Employee::jobTitle,
                        averagingDouble(Employee::salary)))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> roundToTwoDecimals(entry.getValue())));
    }
}
