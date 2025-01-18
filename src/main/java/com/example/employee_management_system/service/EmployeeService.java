package com.example.employee_management_system.service;

import com.example.employee_management_system.entity.Employee;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    // Récupérer tous les employés
    List<Employee> findAll();

    // Récupérer un employé par son ID
    Employee findById(Long id);

    // Sauvegarder un nouvel employé
    Employee save(Employee employee);

    // Mettre à jour un employé existant
    Employee update(Long id, Employee updatedEmployee);

    // Supprimer un employé par son ID
    void delete(Long id);

    // Vérifier si un manager peut gérer un employé de son département
    boolean isManagerOfDepartment(Long employeeId, Authentication authentication);
    List<Employee> searchAndFilter(String name, String department, String jobTitle, String employmentStatus, String hireDate);
    Map<String, Object> generateStatistics();

}
