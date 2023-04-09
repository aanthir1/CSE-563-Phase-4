/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */

package com.example;
import java.io.*;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NetBankingLogin extends JFrame implements ActionListener {

    JLabel titleLbl, usernameLbl, passwordLbl, codeLbl;
    JTextField usernameTxt, codeTxt;
    JPasswordField passwordTxt;
    JButton loginBtn, registerBtn;
    int numAttempts = 0;
    int emailCode;

    public NetBankingLogin() {
        // Set window properties
        setTitle("Net Banking Login");
        setSize(400, 300);
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

        // Add UI elements to layout
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLbl);
        panel.add(new JLabel());
        panel.add(usernameLbl);
        panel.add(usernameTxt);
        panel.add(passwordLbl);
        panel.add(passwordTxt);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
            String password = String.valueOf(passwordTxt.getPassword());

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

                    if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
                        // Generate a random 6-digit verification code
                        int code = sendOTP(registeredEmailID);

                        // Prompt the user to enter the verification code
                        String input = JOptionPane.showInputDialog(this, "A verification code has been sent to your registered email address. Please enter the code below:");

                        // Check if the input code matches the generated code
                        if (input != null && input.equals(Integer.toString(code))) {
                            JOptionPane.showMessageDialog(this, "Login successful");
                            new BankHomePage(registeredUsername,registeredEmailID).setVisible(true);
                            dispose(); // Close the login window
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid verification code.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        return;
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
                System.out.println("Error: registrations.txt file not found.");
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
            Response response = sg.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return code;
    }

    public static void main(String[] args) {
        new NetBankingLogin();
    }
}
