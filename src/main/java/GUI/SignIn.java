package GUI;

import model.Organizzatore;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SignIn {
    private JPanel mainPanel;
    private JTextField inpName;
    private JTextField inpSurname;
    private JTextField inpEmail;
    private JTextField inpUsername;
    private JPasswordField inpPassword;
    private JPasswordField inpPassConfirm;
    private JButton registerButton;
    private JLabel name;
    private JLabel surname;
    private JLabel email;
    private JLabel username;
    private JButton backButton;
    private JCheckBox utenteCheckBox;
    private JCheckBox organizzatoreCheckBox;
    private Controller controller;

    public SignIn(Controller controller) {
        this.controller = controller;
        // Logica per la registrazione
        mainPanel.setPreferredSize(new Dimension(600, 400));

        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        utenteCheckBox.addActionListener(e -> {
            if (utenteCheckBox.isSelected()) organizzatoreCheckBox.setSelected(false);
        });

        organizzatoreCheckBox.addActionListener(e -> {
            if (organizzatoreCheckBox.isSelected()) utenteCheckBox.setSelected(false);
        });

        registerButton.addActionListener(e -> {
            String username = inpUsername.getText();
            String email = inpEmail.getText();
            String name = inpName.getText();
            String surname = inpSurname.getText();
            String password = new String(inpPassword.getPassword());
            String confirmPassword = new String(inpPassConfirm.getPassword());

            if (password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty() || email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Compilare tutti i campi");
            } else if (!email.contains("@")) {
                JOptionPane.showMessageDialog(mainPanel, "Formato Email non valido");
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(mainPanel, "Le password non coincidono!");
            } else if (!utenteCheckBox.isSelected() && !organizzatoreCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(mainPanel, "Inserire un ruolo");
            } else {
                boolean success = false;

                if (utenteCheckBox.isSelected()) {
                    Utente utente = new Utente(name, surname, email, username, password);
                    success = controller.registraUtente(utente);
                } else if (organizzatoreCheckBox.isSelected()) {
                    Organizzatore organizzatore = new Organizzatore(name, surname, email, username, password);
                    success = controller.registraOrganizzatore(organizzatore);
                }

                if (!success) {
                    JOptionPane.showMessageDialog(mainPanel, "Username già in uso. Scegli un altro.");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Registrazione completata!");
                    controller.showLogin();
                }
            }
        });

        // Pulsante indietro
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> controller.showLogin());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

