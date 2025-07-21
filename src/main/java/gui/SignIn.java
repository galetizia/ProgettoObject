package gui;

import controller.ControllerSignIn;
import model.*;

import javax.swing.*;
import java.awt.*;

public class SignIn {
    private JPanel mainPanel;
    private JTextField inpName;
    private JTextField inpSurname;
    private JTextField inpEmail;
    private JTextField inpUsername;
    private JPasswordField inpPassword;
    private JPasswordField inpPassConfirm;
    private JButton registerButton;
    private JButton backButton;
    private JCheckBox utenteCheckBox;
    private JCheckBox organizzatoreCheckBox;
    private JLabel area;

    public SignIn(ControllerSignIn controller) {

        mainPanel.setPreferredSize(new Dimension(600, 400));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        utenteCheckBox.addActionListener(ignored -> {
            if (utenteCheckBox.isSelected()) organizzatoreCheckBox.setSelected(false);
        });

        organizzatoreCheckBox.addActionListener(ignored -> {
            if (organizzatoreCheckBox.isSelected()) utenteCheckBox.setSelected(false);
        });

        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerButton.addActionListener(ignored -> {
            String username = inpUsername.getText();
            String email = inpEmail.getText();
            String name = inpName.getText();
            String surname = inpSurname.getText();
            String password = new String(inpPassword.getPassword());
            String confirmPassword = new String(inpPassConfirm.getPassword());

            Utente u = new Utente(name,surname,email,username,password);

            controller.signIn(u,confirmPassword,utenteCheckBox.isSelected(), organizzatoreCheckBox.isSelected());
        });

        // Pulsante indietro
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addActionListener(ignored -> controller.showLogin());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

