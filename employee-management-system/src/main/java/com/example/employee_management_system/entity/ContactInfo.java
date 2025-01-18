package com.example.employee_management_system.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
public class ContactInfo {
    private String email;
    private String phoneNumber;
    private String address;

    // Getters and setters
}

