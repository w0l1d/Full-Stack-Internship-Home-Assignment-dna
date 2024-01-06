package ma.dnaengineering.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<Employee>> uploadCsvFile(@RequestParam("file") MultipartFile file) throws
            IOException {
        log.info("Uploading file: {}", file.getOriginalFilename());
        List<Employee> employees = employeeService.processCsvFile(file);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/summary")
    public ResponseEntity<Map<String, Double>> getSummary(@RequestBody List<Employee> employees) {
        Map<String, Double> summary = employeeService.calculateAverageSalaryByJobTitle(employees);
        return ResponseEntity.ok(summary);
    }
}
