package com.example.employee_management_system.ui.employee;

import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.ui.enabled", havingValue = "true", matchIfMissing = true)

public class EmployeeManagementFrame extends JFrame {

    private JTable employeeTable;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeManagementFrame(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        // Vérifier si l'environnement est headless
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("Cannot initialize UI in a headless environment.");
        }

        setTitle("Employee Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }
    @PostConstruct
    private void initUI() {
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Title
        JLabel titleLabel = new JLabel("Employee Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Employee Table
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Load Employees
        loadEmployeeData();

        // Buttons Panel (Add, Edit, Delete)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners for buttons
        addButton.addActionListener(e -> showEmployeeForm(null)); // null for creating a new employee
        editButton.addActionListener(e -> editEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
    }

    private void loadEmployeeData() {
        List<Employee> employees = employeeRepository.findAll(); // Get all employees from the repository
        String[] columnNames = {"Employee ID", "Full Name", "Job Title", "Department", "Employment Status", "Hire Date"};

        // Data for the table
        Object[][] data = new Object[employees.size()][6];
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            data[i][0] = employee.getEmployeeId();
            data[i][1] = employee.getFullName();
            data[i][2] = employee.getJobTitle();
            data[i][3] = employee.getDepartment();
            data[i][4] = employee.getEmploymentStatus();
            data[i][5] = employee.getHireDate();
        }

        // Create table model
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void showEmployeeForm(Employee employee) {
        EmployeeFormDialog formDialog = new EmployeeFormDialog(this, employee);
        formDialog.setVisible(true);

        if (formDialog.isFormSubmitted()) {
            loadEmployeeData(); // Refresh the table data after submitting the form
        }
    }

    private void editEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            String employeeId = (String) employeeTable.getValueAt(selectedRow, 0);
            Employee employee = employeeRepository.findByEmployeeId(employeeId); // Find employee by ID
            showEmployeeForm(employee);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.");
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            String employeeId = (String) employeeTable.getValueAt(selectedRow, 0);
            Employee employee = employeeRepository.findByEmployeeId(employeeId); // Find employee by ID
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + employee.getFullName() + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                employeeRepository.delete(employee); // Delete employee from repository
                loadEmployeeData(); // Refresh the table data after deletion
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
        }
    }


}
