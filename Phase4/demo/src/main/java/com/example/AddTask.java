package com.example;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class AddTask extends JFrame {
    private JLabel nameLabel, descLabel, createdByLbl;
    private JTextField nameTextField, createdByTextField;
    private JTextArea descTextArea;
    private JButton addButton;

    public AddTask() {
        super("Add Task");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        nameLabel = new JLabel("Task Name:");
        descLabel = new JLabel("Task Description:");
        createdByLbl = new JLabel("Created By:");
        nameTextField = new JTextField();
        descTextArea = new JTextArea();
        createdByTextField = new JTextField();
        addButton = new JButton("Add");

        // Set layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the window
        JPanel panel = new JPanel(new GridLayout(4, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(descLabel);
        panel.add(new JScrollPane(descTextArea));
        panel.add(createdByLbl);
        panel.add(createdByTextField);
        add(panel);

        add(addButton);

        // Add action listener to the Add button
        addButton.addActionListener(e -> {
            String taskName = nameTextField.getText();
            String taskDesc = descTextArea.getText();
            String createdBy = createdByTextField.getText();

            if(taskName.equals("")||taskDesc.equals("")||createdBy.equals("")){
                JOptionPane.showMessageDialog(this, "Enter all details", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Write the task to the "task.txt" file
                try {
                    FileWriter writer = new FileWriter("task.txt", true);
                    writer.write(taskName + "," + taskDesc + "," + createdBy + "\n");
                    writer.close();
                    JOptionPane.showMessageDialog(this, "Task added successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to add task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                // Close the window
                dispose();
            }
        });

        // Display the window
        setVisible(true);
    }
}
