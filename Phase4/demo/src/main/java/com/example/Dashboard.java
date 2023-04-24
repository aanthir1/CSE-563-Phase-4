/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */
package com.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Dashboard extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JButton addButton;

    public Dashboard() {
        setTitle("Task Dashboard");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(Color.WHITE);

        // Set up table
        model = new DefaultTableModel();
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
        table = new JTable(model);
        table.setFont(new Font("Helvetica", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.darkGray);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(30);

        // Set up scroll pane
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(780, 500));

        // Add scroll pane to content pane
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create add button
        addButton = new JButton("Add Task");
        addButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.BLACK);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddTask();
            }
        });

        // Add button to content pane
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
