package com.example.employee_management_system.utils;

import com.example.employee_management_system.entity.Employee;
import java.io.PrintWriter;
import java.util.List;

public class CsvExporter {

    public static void writeEmployeesToCsv(PrintWriter writer, List<Employee> employees) {
        writer.println("ID,Full Name,Department,Job Title,Employment Status,Hire Date");

        for (Employee employee : employees) {
            writer.println(String.format("%d,%s,%s,%s,%s,%s",
                    employee.getId(),
                    employee.getFullName(),
                    employee.getDepartment(),
                    employee.getJobTitle(),
                    employee.getEmploymentStatus(),
                    employee.getHireDate()
            ));
        }
    }
}
