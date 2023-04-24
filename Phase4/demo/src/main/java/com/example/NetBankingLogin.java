/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */

package com.example;
import java.io.*;
import java.awt.image.BufferedImage;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NetBankingLogin extends JFrame implements ActionListener {

    private JLabel titleLbl, usernameLbl, passwordLbl, captchaLbl;
    private JTextField usernameTxt, captchaTxt;
    private JPasswordField passwordTxt;
    private JButton loginBtn, registerBtn, forgotPasswordBtn;
    private int numAttempts = 0;
    private Random random = new Random();
    private String captchaText;

    public NetBankingLogin() {
        // Set window properties
        setTitle("Net Banking Login");
        setSize(400, 350);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create UI elements
        titleLbl = new JLabel("Net Banking Login");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 20));

        usernameLbl = new JLabel("Username:");
        usernameTxt = new JTextField();

        passwordLbl = new JLabel("Password:");
        passwordTxt = new JPasswordField();

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);

        registerBtn = new JButton("Register New User");
        registerBtn.addActionListener(this);

        forgotPasswordBtn = new JButton("Forgot Password");
        forgotPasswordBtn.addActionListener(this);

        // Add UI elements to layout
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLbl);
        panel.add(new JLabel());
        panel.add(usernameLbl);
        panel.add(usernameTxt);
        panel.add(passwordLbl);
        panel.add(passwordTxt);


        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(forgotPasswordBtn);
        btnPanel.add(registerBtn);
        btnPanel.add(loginBtn);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Show the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerBtn) {
            new RegistrationPage();
        }else if (e.getSource() == loginBtn) {
            String username = usernameTxt.getText();
            String password = new String(passwordTxt.getPassword());
            // Read the registrations.txt file and search for the username and password
            try {
                File file = new File("registrations.txt");
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    String registeredUsername = parts[0];
                    String registeredPassword = parts[2];
                    String registeredEmailID = parts[1];
                    String accountType = parts[3];
                    String enteredHash;
                    try {
                        enteredHash = hashPassword(password);
                        if (username.equals(registeredUsername) && enteredHash.equals(registeredPassword)) {
                            // Generate a random 6-digit verification code
                            int code = sendOTP(registeredEmailID);

                            // Prompt the user to enter the verification code
                            String input = JOptionPane.showInputDialog(this, "A verification code has been sent to your registered email address. Please enter the code below:");

                            // Check if the input code matches the generated code
                            if (input != null && input.equals(Integer.toString(code))) {
                                JOptionPane.showMessageDialog(this, "Login successful");
                                if (accountType.equals("Manager/Admin")) {
                                    new BankHomePage3(registeredUsername, registeredEmailID).setVisible(true);
                                } else if (accountType.equals("Employee")) {
                                    new BankHomePage2(registeredUsername, registeredEmailID).setVisible(true);
                                } else {
                                    new BankHomePage(registeredUsername, registeredEmailID).setVisible(true);
                                }
                                dispose(); // Close the login window
                                return;
                            } else {
                                JOptionPane.showMessageDialog(this, "Invalid verification code.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (NoSuchAlgorithmException ne) {
                        // handle exception
                    }
                }

                // If the loop completes without finding a matching username and password, display an error message
                numAttempts++; // Increment counter variable
                if (numAttempts >= 3) { // Maximum number of attempts reached
                    JOptionPane.showMessageDialog(this, "You have exceeded the maximum number of login attempts.", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose(); // Close the login window
                } else { // Display error message for invalid login attempt
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No registered user.");
            }
        } else if (e.getSource() == forgotPasswordBtn) {
            String email = JOptionPane.showInputDialog(this, "Please enter your registered email address:");
            if (email != null) {
                try {
                    File file = new File("registrations.txt");
                    RandomAccessFile raf = new RandomAccessFile(file, "rw");

                    boolean emailFound = false;

                    while (raf.getFilePointer() < raf.length()) {
                        String line = raf.readLine();
                        String[] parts = line.split(",");
                        String registeredEmail = parts[1];
                        String registeredUsername = parts[0];

                        if (email.equals(registeredEmail)) {
                            emailFound = true;

                            // Generate a random 6-digit verification code
                            int code = sendOTP(email);

                            // Prompt the user to enter the verification code
                            String input = JOptionPane.showInputDialog(this, "A verification code has been sent to your registered email address. Please enter the code below:");

                            // Check if the input code matches the generated code
                            if (input != null && input.equals(Integer.toString(code))) {
                                // Prompt the user to enter a new password
                                JPasswordField passwordField = new JPasswordField();
                                JPasswordField confirmPasswordField = new JPasswordField();
                                Object[] message = {"Enter new password:", passwordField, "Confirm new password:", confirmPasswordField};
                                int option = JOptionPane.showConfirmDialog(null, message, "New Password", JOptionPane.OK_CANCEL_OPTION);

                                // Check if the user clicked OK and entered a valid password
                                if (option == JOptionPane.OK_OPTION) {
                                    char[] newPassword = passwordField.getPassword();
                                    char[] confirmedPassword = confirmPasswordField.getPassword();
                                    if (newPassword.length >= 6) {
                                        if (Arrays.equals(newPassword, confirmedPassword)) {
                                            if (!hashPassword(String.valueOf(newPassword)).equals(parts[2])) {
                                                // Update the password for the user with the matching email address
                                                String updatedLine = parts[0] + "," + parts[1] + "," + hashPassword(String.valueOf(newPassword)) + "," + parts[3];

                                                // Write the updated line to the file
                                                raf.seek(raf.getFilePointer() - line.length() - 2); // Move the file pointer back to the start of the line
                                                raf.writeBytes(updatedLine + System.lineSeparator());

                                                JOptionPane.showMessageDialog(this, "Password updated successfully.");
                                                return;
                                            } else {
                                                JOptionPane.showMessageDialog(this, "New password cannot be the same as old password.", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Invalid verification code.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                    // If the loop completes without finding a matching email address and emailFound is still false, display an error message
                    if (!emailFound) {
                        JOptionPane.showMessageDialog(this, "The email address you entered is not registered.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    raf.close();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "No registered user.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NoSuchAlgorithmException ne) {
                    ne.printStackTrace();
                }
            }
        }
    }


    public int sendOTP(String email){
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
    public String hashPassword(String password) throws NoSuchAlgorithmException {
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


    public static void main(String[] args) {
        new NetBankingLogin();
    }
}
