/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */
package com.example;

import java.io.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class RegistrationPage extends JFrame implements ActionListener {

    JLabel titleLbl, usernameLbl, emailLbl, passwordLbl, confirmPasswordLbl;
    JTextField usernameTxt, emailTxt;
    JPasswordField passwordTxt, confirmPasswordTxt;
    JButton registerBtn;
    JComboBox<String> accountTypeComboBox;

    public RegistrationPage() {
        // Set window properties
        setTitle("Registration Page");
        setSize(400, 400);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create UI elements
        titleLbl = new JLabel("Registration Form");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 20));

        usernameLbl = new JLabel("Username:");
        usernameTxt = new JTextField();

        emailLbl = new JLabel("Email ID:");
        emailTxt = new JTextField();

        String[] accountTypes = {"Manager/Admin", "Customer", "Employee"};
        accountTypeComboBox = new JComboBox<>(accountTypes);


        passwordLbl = new JLabel("Password:");
        passwordTxt = new JPasswordField();

        confirmPasswordLbl = new JLabel("Confirm Password:");
        confirmPasswordTxt = new JPasswordField();

        registerBtn = new JButton("Register");

        // Add UI elements to layout
        JPanel panel = new JPanel(new GridLayout(10, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLbl);
        panel.add(new JLabel());
        panel.add(usernameLbl);
        panel.add(usernameTxt);
        panel.add(emailLbl);
        panel.add(emailTxt);
        panel.add(new JLabel("Account Type:"));
        panel.add(accountTypeComboBox);
        panel.add(passwordLbl);
        panel.add(passwordTxt);
        panel.add(confirmPasswordLbl);
        panel.add(confirmPasswordTxt);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(registerBtn);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Show the window
        setVisible(true);

        registerBtn.addActionListener(e -> {
            String username = usernameTxt.getText();
            String email = emailTxt.getText();
            String password = String.valueOf(passwordTxt.getPassword());
            String reenteredPassword = String.valueOf(confirmPasswordTxt.getPassword());
            String accountType = (String) accountTypeComboBox.getSelectedItem();

            if (username.length() < 6 || username.length() > 20) {
                JOptionPane.showMessageDialog(this, "Username must be between 6 and 20 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (password.length() < 8 || password.length() > 15) {
                JOptionPane.showMessageDialog(this, "Password must be between 8 and 15 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!String.valueOf(password).equals(String.valueOf(reenteredPassword))) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email address is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                // Read existing registration details from file
                List<String> registrations = new ArrayList<>();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("registrations.txt"));
                    String line = reader.readLine();
                    while (line != null) {
                        registrations.add(line);
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error reading from file.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Check if username or email already exists
                boolean usernameExists = false;
                boolean emailExists = false;
                for (String registration : registrations) {
                    String[] details = registration.split(",");
                    if (details[0].equals(username)) {
                        usernameExists = true;
                        break;
                    }
                    if (details[1].equals(email)) {
                        emailExists = true;
                        break;
                    }
                }

                // Show error message if username or email already exists
                if (usernameExists) {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (emailExists) {
                    JOptionPane.showMessageDialog(this, "Email ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Perform registration logic here
                    JOptionPane.showMessageDialog(this, "Registration successful.");

                    // Store registration details in a list
                    String registrationDetails = username + "," + email + "," + password + "," + accountType;

                    // Write registration details to text file
                    try {
                        FileWriter writer = new FileWriter("registrations.txt", true);
                        PrintWriter printWriter = new PrintWriter(writer);
                        printWriter.println(registrationDetails);
                        printWriter.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error writing to file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    dispose(); // Close the registration window
                }
            }
        });
            panel.add(registerBtn);

            add(panel);
            setVisible(true);
     }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerBtn) {
            String username = usernameTxt.getText();
            String email = emailTxt.getText();
            String password = new String(passwordTxt.getPassword());
            String reenterPassword = new String(confirmPasswordTxt.getPassword());

            // Check if the username and password meet the length requirements
            if (username.length() < 5 || username.length() > 20) {
                JOptionPane.showMessageDialog(this, "Username must be between 5 and 20 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 8 || password.length() > 20) {
                JOptionPane.showMessageDialog(this, "Password must be between 8 and 20 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the two passwords match
            if (!password.equals(reenterPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if email is valid
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Registration successful");
            dispose(); // Close the registration window
        }
    }

    private boolean isValidEmail(String email) {
        // A simple email validation regex pattern
        String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
