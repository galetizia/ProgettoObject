package GUI;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Login {
    private JPanel mainPanel;
    private JLabel username;
    private JTextField inpUsername;
    private JLabel password;
    private JPasswordField inpPassword;
    private JButton loginButton;
    private JButton signInButton;
    private JLabel forgotLabel;
    private JCheckBox organizzatoreCheck;
    private JCheckBox utenteCheck;

    private Controller controller;
    public Login(Controller controller) {
        this.controller = controller;

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

            if(!organizzatoreCheck.isSelected() && !utenteCheck.isSelected()) {
                JOptionPane.showMessageDialog(mainPanel, "Inserire un ruolo");
                return;
            }
            // Cerca tra gli organizzatori
            if(organizzatoreCheck.isSelected()) {
                for (Organizzatore org : controller.getListaOrganizzatori()) {
                    if (org.username.equals(username) && org.password.equals(password)) {
                        JOptionPane.showMessageDialog(mainPanel, "Login effettuato come Organizzatore!");
                        controller.showSchermataOrganizzatore(org);
                        return; // esce dal metodo
                    }
                }
            }
            // Cerca tra gli utenti
            if(utenteCheck.isSelected()) {
                for (Utente utente : controller.getListaUtenti()) {
                    if (utente.username.equals(username) && utente.password.equals(password)) {
                        JOptionPane.showMessageDialog(mainPanel, "Login effettuato come Utente!");
                        controller.showSchermataUtente(utente);
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(mainPanel, "Credenziali errate.");
        });

        signInButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signInButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signInButton.addActionListener(e -> controller.showSignIn());

        // Simula link cliccabile
        forgotLabel.setText("<html><a href=''>Ho dimenticato la mia password/username?</a></html>");
        forgotLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(mainPanel, "Recupero password in arrivo!");
            } //idem qua dobbiamo poi fare una cosa a parte
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
