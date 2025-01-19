package com.example.employee_management_system.ui.employee;

import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.entity.EmploymentStatus;
import com.example.employee_management_system.repository.EmployeeRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class EmployeeFormDialog extends JDialog {

    private JTextField employeeIdField;
    private JTextField fullNameField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JComboBox<EmploymentStatus> employmentStatusComboBox;
    private JFormattedTextField hireDateField;
    private JButton saveButton;
    private JButton cancelButton;

    private boolean formSubmitted = false;
    private Employee employee;
    private EmployeeRepository employeeRepository;

    public EmployeeFormDialog(JFrame parent, Employee employee) {
        super(parent, true);
        this.employee = employee;

        setTitle(employee == null ? "Add New Employee" : "Edit Employee");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));

        // Employee ID (Non-editable for editing existing employee)
        formPanel.add(new JLabel("Employee ID:"));
        employeeIdField = new JTextField(20);
        employeeIdField.setEditable(false);
        formPanel.add(employeeIdField);

        // Full Name
        formPanel.add(new JLabel("Full Name:"));
        fullNameField = new JTextField(20);
        formPanel.add(fullNameField);

        // Job Title
        formPanel.add(new JLabel("Job Title:"));
        jobTitleField = new JTextField(20);
        formPanel.add(jobTitleField);

        // Department
        formPanel.add(new JLabel("Department:"));
        departmentField = new JTextField(20);
        formPanel.add(departmentField);

        // Employment Status
        formPanel.add(new JLabel("Employment Status:"));
        employmentStatusComboBox = new JComboBox<>(EmploymentStatus.values());
        formPanel.add(employmentStatusComboBox);

        // Hire Date
        formPanel.add(new JLabel("Hire Date:"));
        hireDateField = new JFormattedTextField(LocalDate.now());
        hireDateField.setColumns(20);
        formPanel.add(hireDateField);

        // Add form panel to dialog
        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel (Save and Cancel)
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        saveButton.addActionListener(this::onSave);
        cancelButton.addActionListener(this::onCancel);

        // Fill the fields if editing an existing employee
        if (employee != null) {
            fillFormFields();
        }
    }

    private void fillFormFields() {
        employeeIdField.setText(employee.getEmployeeId());
        fullNameField.setText(employee.getFullName());
        jobTitleField.setText(employee.getJobTitle());
        departmentField.setText(employee.getDepartment());
        employmentStatusComboBox.setSelectedItem(employee.getEmploymentStatus());
        hireDateField.setValue(employee.getHireDate());
    }

    private void onSave(ActionEvent e) {
        try {
            // Validate inputs
            String employeeId = employeeIdField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String jobTitle = jobTitleField.getText().trim();
            String department = departmentField.getText().trim();
            EmploymentStatus employmentStatus = (EmploymentStatus) employmentStatusComboBox.getSelectedItem();
            LocalDate hireDate = (LocalDate) hireDateField.getValue();

            if (fullName.isEmpty() || jobTitle.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.");
                return;
            }

            if (employee == null) {
                // Create new employee
                employee = new Employee();
                employee.setEmployeeId(employeeId);
            }

            employee.setFullName(fullName);
            employee.setJobTitle(jobTitle);
            employee.setDepartment(department);
            employee.setEmploymentStatus(employmentStatus);
            employee.setHireDate(hireDate);

            // Save employee
            employeeRepository.save(employee);
            formSubmitted = true;

            JOptionPane.showMessageDialog(this, "Employee saved successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void onCancel(ActionEvent e) {
        dispose();
    }

    public boolean isFormSubmitted() {
        return formSubmitted;
    }
}
