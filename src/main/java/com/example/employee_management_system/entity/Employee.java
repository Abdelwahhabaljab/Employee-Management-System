package com.example.employee_management_system.entity;

import com.example.employee_management_system.entity.ContactInfo;
import com.example.employee_management_system.entity.EmploymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false)
    private String fullName;

    private String jobTitle;
    private String department;

    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Embedded
    private ContactInfo contactInfo;

    // Getters and setters
}
