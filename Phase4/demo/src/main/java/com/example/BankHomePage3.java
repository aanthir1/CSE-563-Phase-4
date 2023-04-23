/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */
package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankHomePage3 extends JFrame implements ActionListener {

    JLabel titleLbl, welcomeLbl;
    JButton addProjBtn, editProjBtn, delProjBtn, addTaskBtn, editTaskBtn, delTaskBtn;
    JButton viewProfileBtn, openDashboardBtn, contactSupportBtn, logoutBtn;
    public String username, emailID;
    private Timer timer;
    private int counter = 0;

    public BankHomePage3(String username, String emailID) {
        this.username = username;
        this.emailID = emailID;
        // Set window properties
        setTitle("Bank Home Page : Manager/Admin");
        setSize(600, 400);
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

        addProjBtn = new JButton("Add Project");
        addProjBtn.addActionListener(this);

        editProjBtn = new JButton("Edit Project");
        editProjBtn.addActionListener(this);

        delProjBtn = new JButton("Delete Project");
        delProjBtn.addActionListener(this);

        addTaskBtn = new JButton("Add Task");
        addTaskBtn.addActionListener(this);

        editTaskBtn = new JButton("Edit Task");
        editTaskBtn.addActionListener(this);

        delTaskBtn = new JButton("Delete Task");
        delTaskBtn.addActionListener(this);

        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(this);

        viewProfileBtn = new JButton("View User Profile");
        viewProfileBtn.addActionListener(this);

        contactSupportBtn = new JButton("Contact Support??");
        contactSupportBtn.addActionListener(this);

        // Add UI elements to layout
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLbl);
        panel.add(welcomeLbl);
        panel.add(addProjBtn);
        panel.add(editProjBtn);
        panel.add(delProjBtn);
        panel.add(addTaskBtn);
        panel.add(editTaskBtn);
        panel.add(delTaskBtn);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(logoutBtn);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        openDashboardBtn = new JButton("Open Dashboard");
        openDashboardBtn.addActionListener(this);

        JPanel projectBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        projectBtnPanel.add(viewProfileBtn);
        projectBtnPanel.add(openDashboardBtn);
        projectBtnPanel.add(contactSupportBtn);
        add(projectBtnPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openDashboardBtn){
            counter = 0;
            Dashboard dashboard = new Dashboard();
        }else if (e.getSource() == addProjBtn) {
            counter = 0;
            System.out.println("Add Project Functionality");
        } else if (e.getSource() == editProjBtn) {
            counter = 0;
            System.out.println("Edit Project Functionality");
        } else if(e.getSource() == delProjBtn){
            counter = 0;
            System.out.println("Delete Project Functionality");
        }else if (e.getSource() == addTaskBtn) {
            counter = 0;
            System.out.println("Add Task Functionality");
        } else if (e.getSource() == editTaskBtn) {
            counter = 0;
            System.out.println("Edit Task Functionality");
        } else if(e.getSource() == delTaskBtn){
            counter = 0;
            System.out.println("Delete Task Functionality");
        }else if (e.getSource() == logoutBtn) {
            System.out.println("Logout button clicked");
            timer.stop();
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
