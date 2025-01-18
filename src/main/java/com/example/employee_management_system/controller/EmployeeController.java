package com.example.employee_management_system.controller;

import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Endpoint pour récupérer tous les employés (Accessible par ADMIN et HR)
    @GetMapping("/")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // Endpoint pour récupérer un employé par ID (Accessible par ADMIN, HR et Manager dans leur département)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN') or (hasRole('MANAGER') and @employeeService.isManagerOfDepartment(#id, authentication))")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    // Endpoint pour créer un nouvel employé (Accessible par HR et ADMIN)
    @PostMapping("/")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.save(employee);
        return ResponseEntity.ok(createdEmployee);
    }

    // Endpoint pour mettre à jour les informations d'un employé (Accessible par HR et Managers dans leur département)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN') or (hasRole('MANAGER') and @employeeService.isManagerOfDepartment(#id, authentication))")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.update(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Endpoint pour supprimer un employé (Accessible par HR et ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchAndFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String employmentStatus,
            @RequestParam(required = false) String hireDate) {

        List<Employee> results = employeeService.searchAndFilter(name, department, jobTitle, employmentStatus, hireDate);
        return ResponseEntity.ok(results);
    }
}
