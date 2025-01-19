package com.example.employee_management_system;

import com.example.employee_management_system.ui.employee.EmployeeManagementFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class EmployeeManagementSystemApplication implements CommandLineRunner {

	@Autowired(required = false)
	private EmployeeManagementFrame employeeManagementFrame;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (!GraphicsEnvironment.isHeadless()) {
			employeeManagementFrame.setVisible(true);
		} else {
			System.out.println("Application running in headless mode. UI not initialized.");
		}
	}
}
