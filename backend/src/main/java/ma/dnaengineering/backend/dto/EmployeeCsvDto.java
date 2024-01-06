package ma.dnaengineering.backend.dto;

import lombok.Builder;
import ma.dnaengineering.backend.model.Employee;

import java.util.List;
import java.util.Map;

@Builder
public record EmployeeCsvDto(List<Employee> employees, Map<String, Double> summary) {
}
