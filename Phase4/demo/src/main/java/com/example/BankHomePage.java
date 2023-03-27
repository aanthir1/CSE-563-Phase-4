/*
 * Authors:
 * Akanksha Reddy Anthireddygari
 * Girija Rani Nimmagadda
 */
package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankHomePage extends JFrame implements ActionListener {

    JLabel titleLbl, welcomeLbl;
    JButton viewAccountsBtn, transferMoneyBtn, logoutBtn;

    public BankHomePage() {
        // Set window properties
        setTitle("Bank Home Page");
        setSize(400, 250);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create UI elements
        titleLbl = new JLabel("Welcome to ABC Bank");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 20));

        welcomeLbl = new JLabel("Welcome, John Smith");

        viewAccountsBtn = new JButton("View Accounts");
        viewAccountsBtn.addActionListener(this);

        transferMoneyBtn = new JButton("Transfer Money");
        transferMoneyBtn.addActionListener(this);

        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(this);

        // Add UI elements to layout
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLbl);
        panel.add(welcomeLbl);
        panel.add(viewAccountsBtn);
        panel.add(transferMoneyBtn);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(logoutBtn);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Show the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAccountsBtn) {
            System.out.println("View Accounts button clicked");
        } else if (e.getSource() == transferMoneyBtn) {
            System.out.println("Transfer Money button clicked");
        } else if (e.getSource() == logoutBtn) {
            System.out.println("Logout button clicked");
            NetBankingLogin netBankingLogin = new NetBankingLogin();
            setVisible(false);
            netBankingLogin.setVisible(true);
        }
    }

}