package com.example.employee_management_system.controller;

import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.service.EmployeeService;
import com.example.employee_management_system.utils.CsvExporter;
import com.example.employee_management_system.utils.PdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final EmployeeService employeeService;

    public ReportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(employeeService.generateStatistics());
    }

    @GetMapping("/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employees.csv";
        response.setHeader(headerKey, headerValue);

        List<Employee> employees = employeeService.findAll();

        PrintWriter writer = response.getWriter();
        CsvExporter.writeEmployeesToCsv(writer, employees);
    }

    @GetMapping("/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employees.pdf";
        response.setHeader(headerKey, headerValue);

        List<Employee> employees = employeeService.findAll();

        PdfExporter.writeEmployeesToPdf(response.getOutputStream(), employees);
    }


}
