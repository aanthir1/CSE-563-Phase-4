/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */
package com.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CustomerDashboard extends JFrame {

    public CustomerDashboard() {
        setTitle("Task Dashboard");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(Color.WHITE);

        // Set up table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Task Name");
        model.addColumn("Task Description");
        model.addColumn("Created By");

        //read tasks from its file
        try {
            File file = new File("task.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                model.addRow(parts);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create table with model
        JTable table = new JTable(model);
        table.setFont(new Font("Helvetica", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.darkGray);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(30);

        // Set up scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(780, 500));

        // Add scroll pane to content pane
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CustomerDashboard();
    }
}
