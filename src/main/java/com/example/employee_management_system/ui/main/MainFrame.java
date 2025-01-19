package com.example.employee_management_system.ui.main;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private String role;

    public MainFrame() {
        setTitle("Employee Management System - Main");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setRole(String role) {
        this.role = role != null ? role.toUpperCase() : "UNKNOWN";
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Header
        JLabel welcomeLabel = new JLabel("Welcome, " + role, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "You have been logged out.");
            System.exit(0);
        });
        footerPanel.add(logoutButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }
}
