package gui;

import controller.ControllerLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {
    private JPanel mainPanel;
    private JTextField inpUsername;
    private JPasswordField inpPassword;
    private JButton loginButton;
    private JButton signInButton;
    private JLabel forgotpass;
    private JCheckBox organizzatoreCheck;
    private JCheckBox utenteCheck;
    private JLabel forgotUser;
    private JCheckBox giudiceCheckBox;
    private JLabel area;

    private static final String SEGOEUI = "Segoe UI";

    public Login(ControllerLogin controller) {

        mainPanel.setPreferredSize(new Dimension(400, 300));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));
        loginButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        utenteCheck.addActionListener(ignored -> {
            if (utenteCheck.isSelected()) {
                organizzatoreCheck.setSelected(false); giudiceCheckBox.setSelected(false);}
        });

        organizzatoreCheck.addActionListener(ignored -> {
            if (organizzatoreCheck.isSelected()) {
                utenteCheck.setSelected(false); giudiceCheckBox.setSelected(false);}
        });

        giudiceCheckBox.addActionListener(ignored -> {
            if (giudiceCheckBox.isSelected()) {
                utenteCheck.setSelected(false); organizzatoreCheck.setSelected(false);}
        });

        loginButton.addActionListener(ignored -> {
            String username = inpUsername.getText();
            String password = new String(inpPassword.getPassword());
            controller.login(username, password, utenteCheck.isSelected(), organizzatoreCheck.isSelected(), giudiceCheckBox.isSelected());
        });

        signInButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        signInButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signInButton.addActionListener(ignored -> controller.showSignIn());

        // Simula link cliccabile
        forgotUser.setText("<html><a href=''>Ho dimenticato il mio username</a></html>");
        forgotUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.showRecuperoUsername();
            }
        });
        forgotpass.setText("<html><a href=''>Ho dimenticato la mia password</a></html>");
        forgotpass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotpass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.showRecuperoPassword();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
