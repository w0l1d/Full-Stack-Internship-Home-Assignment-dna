package ma.dnaengineering.backend.service;

import lombok.RequiredArgsConstructor;
import ma.dnaengineering.backend.model.Employee;
import ma.dnaengineering.backend.repository.EmployeeRepository;
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
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

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

    public List<Employee> saveAll(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    private Employee mapToEmployee(String[] data) {
        if (data.length != 4)
            throw new IllegalArgumentException("Invalid data: expected 4 columns but got " + data.length + " columns");
        return Employee
                .builder()
                .id(Long.parseLong(data[0].trim()))
                .name(data[1].trim())
                .jobTitle(data[2].trim())
                .salary(Double.parseDouble(data[3].trim()))
                .build();
    }

    public Map<String, Double> calculateAverageSalaryByJobTitle(List<Employee> employees) {

        return employees
                .stream()
                .collect(groupingBy(
                        Employee::getJobTitle,
                        averagingDouble(Employee::getSalary)))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> roundToTwoDecimals(entry.getValue())));
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
