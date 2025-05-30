package gui;

import controller.ControllerLogin;
import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {
    private JPanel mainPanel;
    private JLabel username;
    private JTextField inpUsername;
    private JLabel password;
    private JPasswordField inpPassword;
    private JButton loginButton;
    private JButton signInButton;
    private JLabel forgotpass;
    private JCheckBox organizzatoreCheck;
    private JCheckBox utenteCheck;
    private JLabel forgotUser;

    public Login(ControllerLogin controller) {

        mainPanel.setPreferredSize(new Dimension(400, 300));

        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        utenteCheck.addActionListener(e -> {
            if (utenteCheck.isSelected()) organizzatoreCheck.setSelected(false);
        });

        organizzatoreCheck.addActionListener(e -> {
            if (organizzatoreCheck.isSelected()) utenteCheck.setSelected(false);
        });

        loginButton.addActionListener(e -> {
            String username = inpUsername.getText();
            String password = new String(inpPassword.getPassword());
            controller.login(username, password, utenteCheck.isSelected(), organizzatoreCheck.isSelected());
        });

        signInButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signInButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signInButton.addActionListener(e -> controller.showSignIn());

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
