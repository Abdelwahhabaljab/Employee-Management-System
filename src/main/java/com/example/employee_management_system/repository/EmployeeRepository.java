package com.example.employee_management_system.repository;

import com.example.employee_management_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    // Recherche par nom
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    // Recherche par département
    List<Employee> findByDepartment(String department);

    // Recherche par job title
    List<Employee> findByJobTitle(String jobTitle);
    @Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
    List<Object[]> countEmployeesByDepartment();

    @Query("SELECT e.employmentStatus, COUNT(e) FROM Employee e GROUP BY e.employmentStatus")
    List<Object[]> countEmployeesByStatus();

    Employee findByEmployeeId(String employeeId);
}
