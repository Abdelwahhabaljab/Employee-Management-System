package com.example.employee_management_system.utils;

import com.example.employee_management_system.entity.Employee;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.OutputStream;
import java.util.List;

public class PdfExporter {

    public static void writeEmployeesToPdf(OutputStream outputStream, List<Employee> employees) {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Employee Report"));

        Table table = new Table(5);
        table.addCell("ID");
        table.addCell("Full Name");
        table.addCell("Department");
        table.addCell("Job Title");
        table.addCell("Employment Status");

        for (Employee employee : employees) {
            table.addCell(String.valueOf(employee.getId()));
            table.addCell(employee.getFullName());
            table.addCell(employee.getDepartment());
            table.addCell(employee.getJobTitle());
            table.addCell(String.valueOf(employee.getEmploymentStatus()));
        }

        document.add(table);
        document.close();
    }
}
