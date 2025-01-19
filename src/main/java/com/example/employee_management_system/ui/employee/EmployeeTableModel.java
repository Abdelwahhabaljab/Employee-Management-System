package com.example.employee_management_system.ui.employee;



import com.example.employee_management_system.entity.Employee;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {

    private List<Employee> employeeList;
    private String[] columnNames = {"Employee ID", "Full Name", "Job Title", "Department", "Status"};

    public EmployeeTableModel() {
        employeeList = List.of();  // Empty list initially
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        fireTableDataChanged();  // Notify table to update
    }

    @Override
    public int getRowCount() {
        return employeeList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employeeList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return employee.getEmployeeId();
            case 1:
                return employee.getFullName();
            case 2:
                return employee.getJobTitle();
            case 3:
                return employee.getDepartment();
            case 4:
                return employee.getEmploymentStatus();
            default:
                return null;
        }
    }

    public Employee getEmployeeAt(int rowIndex) {
        return employeeList.get(rowIndex);
    }
}

