/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */
//Bank Home Page for Manager/Admin
package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class BankHomePage extends JFrame implements ActionListener {

    JLabel titleLbl, welcomeLbl;
    JButton viewProfileBtn, openDashboardBtn, contactSupportBtn, logoutBtn;
    public String username, emailID;
    private Timer timer;
    private int counter = 0;

    public BankHomePage(String username, String emailID) {
        this.username = username;
        this.emailID = emailID;
        // Set window properties
        setTitle("Bank Home Page : Customer");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(1000, e -> {
            counter++;
            if (counter >= 120) { // 2 minutes of inactivity
                JOptionPane.showMessageDialog(this, "You have been automatically logged out due to inactivity.", "Session Timeout", JOptionPane.INFORMATION_MESSAGE);
                NetBankingLogin netBankingLogin = new NetBankingLogin();
                setVisible(false);
                netBankingLogin.setVisible(true);
                timer.stop();
            }
            // Update the counter label
            welcomeLbl.setText("Welcome, "+username+" (Time Opened: " + counter + " seconds)");
        });
        // Start the timer
        timer.start();

        // Create UI elements
        titleLbl = new JLabel("Welcome to ABC Bank");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 20));

        welcomeLbl = new JLabel("Welcome, John Smith");

        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(this);

        viewProfileBtn = new JButton("View Your Profile");
        viewProfileBtn.addActionListener(this);

        contactSupportBtn = new JButton("Contact Support??");
        contactSupportBtn.addActionListener(this);

        // Add UI elements to layout
        JPanel panel = new JPanel(new GridLayout(6, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLbl);
        panel.add(welcomeLbl);
        panel.add(viewProfileBtn);
        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(logoutBtn);

        add(btnPanel, BorderLayout.SOUTH);

        openDashboardBtn = new JButton("Open Task Dashboard");
        openDashboardBtn.addActionListener(this);

        JPanel projectBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        projectBtnPanel.add(openDashboardBtn);
        projectBtnPanel.add(contactSupportBtn);
        this.add(projectBtnPanel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openDashboardBtn){
            counter = 0;
            new CustomerDashboard();
        }else if (e.getSource() == logoutBtn) {
            System.out.println("Logout button clicked");
            timer.stop();
            // Close all windows
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                window.dispose();
            }
            NetBankingLogin netBankingLogin = new NetBankingLogin();
            setVisible(false);
            netBankingLogin.setVisible(true);
        } else if (e.getSource() == viewProfileBtn) {
            counter = 0;
            viewProfile();
        } else if (e.getSource() == contactSupportBtn) {
            counter = 0;
            contactSupport();
        }
    }
    private void viewProfile() {
        JFrame profileFrame = new JFrame("User Profile");
        profileFrame.setSize(300, 150);
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLbl = new JLabel("Username:");
        JLabel emailLbl = new JLabel("Email ID:");
        JLabel usernameValueLbl = new JLabel(username);
        JLabel emailValueLbl = new JLabel(emailID);

        panel.add(usernameLbl);
        panel.add(usernameValueLbl);
        panel.add(emailLbl);
        panel.add(emailValueLbl);

        profileFrame.add(panel);
        profileFrame.setVisible(true);
    }

    private void contactSupport() {
        JFrame contactSupportFrm = new JFrame("***Contact Support??***");
        contactSupportFrm.setSize(300, 150);
        contactSupportFrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLbl = new JLabel("Contact: 602-123-4567");
        panel.add(usernameLbl);

        contactSupportFrm.add(panel);
        contactSupportFrm.setVisible(true);
    }
}
