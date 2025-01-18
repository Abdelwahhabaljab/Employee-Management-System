package com.example.employee_management_system.service;
import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.employee_management_system.util.SecurityUtils.getCurrentUsername;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuditLogService auditLogService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AuditLogService auditLogService) {
        this.employeeRepository = employeeRepository;
        this.auditLogService = auditLogService;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id, Employee updatedEmployee) {
        Employee existingEmployee = findById(id);
        // Préparer les détails des modifications pour l'audit
        StringBuilder details = new StringBuilder("Updated fields: ");
        if (!existingEmployee.getFullName().equals(updatedEmployee.getFullName())) {
            details.append("FullName, ");
        }
        if (!existingEmployee.getJobTitle().equals(updatedEmployee.getJobTitle())) {
            details.append("JobTitle, ");
        }
        if (!existingEmployee.getDepartment().equals(updatedEmployee.getDepartment())) {
            details.append("Department, ");
        }
        if (!existingEmployee.getHireDate().equals(updatedEmployee.getHireDate())) {
            details.append("HireDate, ");
        }
        if (!existingEmployee.getEmploymentStatus().equals(updatedEmployee.getEmploymentStatus())) {
            details.append("EmploymentStatus, ");
        }
        if (!existingEmployee.getContactInfo().equals(updatedEmployee.getContactInfo())) {
            details.append("ContactInformation, ");
        }

        // Supprimer la virgule finale et l'espace, si nécessaire
        if (details.toString().endsWith(", ")) {
            details = new StringBuilder(details.substring(0, details.length() - 2));
        }

        existingEmployee.setFullName(updatedEmployee.getFullName());
        existingEmployee.setJobTitle(updatedEmployee.getJobTitle());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setHireDate(updatedEmployee.getHireDate());
        existingEmployee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
        existingEmployee.setContactInfo(updatedEmployee.getContactInfo());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void delete(Long id) {
        Employee existingEmployee = findById(id);
        employeeRepository.delete(existingEmployee);
        // Ajouter une entrée dans le log d'audit
        String performedBy = getCurrentUsername(); // Récupérer l'utilisateur actuel
        auditLogService.logChange(id, "DELETE", "Employee record deleted", performedBy);
    }

    @Override
    public boolean isManagerOfDepartment(Long employeeId, Authentication authentication) {
        Employee employee = findById(employeeId);
        User loggedInUser = (User) authentication.getPrincipal();
        String managerDepartment = getManagerDepartment(loggedInUser);
        return employee.getDepartment().equals(managerDepartment);
    }

    private String getManagerDepartment(User loggedInUser) {
        return "IT"; // Retourner le département réel du manager
    }

    @Override
    public List<Employee> searchAndFilter(String name, String department, String jobTitle, String employmentStatus, String hireDate) {
        Specification<Employee> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + name.toLowerCase() + "%"));
        }

        if (department != null && !department.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("department"), department));
        }

        if (jobTitle != null && !jobTitle.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("jobTitle"), jobTitle));
        }

        if (employmentStatus != null && !employmentStatus.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("employmentStatus"), employmentStatus));
        }

        if (hireDate != null && !hireDate.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("hireDate"), hireDate));
        }

        return employeeRepository.findAll(spec);
    }

    @Override
    public Map<String, Object> generateStatistics() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalEmployees", employeeRepository.count());
        stats.put("employeesByDepartment", employeeRepository.countEmployeesByDepartment());
        stats.put("employeesByStatus", employeeRepository.countEmployeesByStatus());

        return stats;
    }

}
