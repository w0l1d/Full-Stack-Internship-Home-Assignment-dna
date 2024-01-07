package ma.dnaengineering.backend.controller;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.dnaengineering.backend.dto.EmployeeCsvDto;
import ma.dnaengineering.backend.model.Employee;
import ma.dnaengineering.backend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class EmployeeController {


    private final EmployeeService employeeService;

    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<EmployeeCsvDto> uploadCsvFile(@RequestParam("file") MultipartFile file) throws
            IOException {
        log.info("Uploading file: {}", file.getOriginalFilename());
        List<Employee> employees = employeeService.processCsvFile(file);
        List<Employee> savedEmployees = employeeService.saveAll(employees);
        log.info("Saved {} employees", savedEmployees.size());
        Map<String, Double> summary = employeeService
                .calculateAverageSalaryByJobTitle(savedEmployees);

        return ResponseEntity.ok(EmployeeCsvDto
                .builder()
                .employees(savedEmployees)
                .summary(summary)
                .build());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }
}
