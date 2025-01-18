package com.example.employee_management_system.ui.main;

import com.example.employee_management_system.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String role) {
        setTitle("Employee Management System - Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI(role);
    }

    private void initUI(String role) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JLabel welcomeLabel = new JLabel("Welcome, " + role, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel roleSpecificPanel = new JPanel();
        mainPanel.add(roleSpecificPanel, BorderLayout.CENTER);

        // Display UI components based on the role
        if ("ADMIN".equals(role)) {
            roleSpecificPanel.add(new JLabel("Admin Dashboard"));
            roleSpecificPanel.add(new JButton("Manage Users"));
            roleSpecificPanel.add(new JButton("System Settings"));
        } else if ("HR".equals(role)) {
            roleSpecificPanel.add(new JLabel("HR Dashboard"));
            roleSpecificPanel.add(new JButton("View Employees"));
            roleSpecificPanel.add(new JButton("Manage Employee Records"));
        } else if ("MANAGER".equals(role)) {
            roleSpecificPanel.add(new JLabel("Manager Dashboard"));
            roleSpecificPanel.add(new JButton("View Team"));
            roleSpecificPanel.add(new JButton("Update Employee Details"));
        } else {
            roleSpecificPanel.add(new JLabel("Unknown Role"));
        }

        // Footer with logout button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true); // Return to login screen
        });
        footerPanel.add(logoutButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }
}
