package com.example;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ForgotPassword extends JFrame implements ActionListener {
    private JLabel emailLabel, passwordLabel, confirmPasswordLabel;
    private JTextField emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton submitButton;

    public ForgotPassword() {
        setTitle("Forgot Password");
        setSize(300, 200);
        setLayout(new GridLayout(4, 2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        emailLabel = new JLabel("Email ID:");
        passwordLabel = new JLabel("New Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        submitButton = new JButton("Submit");

        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(submitButton);

        submitButton.addActionListener(this);
        setVisible(true);
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static int sendOTP(String email){
        // Generate a random 6-digit verification code
        int code = (int) ((Math.random() * (999999 - 100000)) + 100000);
        System.out.println(code);
        // Send the verification code to the user's email address
        Email from = new Email("aanthir1@asu.edu");
        String subject = "Verification Code for Net Banking Login";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Your verification code is: " + code);
        Mail mail = new Mail(from, subject, to, content);

        String apiKey = "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";
        SendGrid sg = new SendGrid(apiKey);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return code;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
            } else {
                try {
                    File file = new File("registrations.txt");
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String line;
                    boolean foundEmail = false;
                    StringBuilder newFileContents = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts[1].equals(email)) {
                            parts[2] = hashPassword(password);
                            foundEmail = true;
                        }
                        int code = sendOTP(email);
                        String input = JOptionPane.showInputDialog(this, "A verification code has been sent to your registered email address. Please enter the code below:");

                        // Check if the input code matches the generated code
                        if (input != null && input.equals(Integer.toString(code))) {
                            newFileContents.append(parts[0]).append(",").append(parts[1]).append(",").append(parts[2]).append(",").append(parts[3]).append("\n");
                        }

                        bufferedReader.close();

                        if (!foundEmail) {
                            JOptionPane.showMessageDialog(this, "Email ID not found.");
                        } else {
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write(newFileContents.toString());
                            fileWriter.close();
                            JOptionPane.showMessageDialog(this, "Password updated successfully.");
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error reading/writing to registrations.txt file.");
                } catch (NoSuchAlgorithmException ex2) {
                    JOptionPane.showMessageDialog(this, "Error reading/writing to registrations.txt file.");
                }
            }
        }
    }
}
