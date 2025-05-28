package gui;

import model.*;
import javax.swing.*;
import java.awt.*;

public class RecuperoUsername {
    private JPanel mainPanel;
    private JButton confermaButton;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton logoutButton;

    private Controller controller;

    public RecuperoUsername(Controller controller) {
        this.controller = controller;

        mainPanel.setPreferredSize(new Dimension(400, 300));
        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {
            String email = emailInput.getText();
            String password = passwordInput.getText();

            boolean success = false;
            for(Utente u : controller.getListaUtenti()){
                if((u.getEmail().equalsIgnoreCase(email)) && (u.getPassword().equalsIgnoreCase(password))){
                    success = true;
                    JOptionPane.showMessageDialog(mainPanel, "Username: " +u.getUsername());
                }
            }
            for(Organizzatore o : controller.getListaOrganizzatori()){
                if((o.getEmail().equalsIgnoreCase(email)) && (o.getPassword().equalsIgnoreCase(password))){
                    success = true;
                    JOptionPane.showMessageDialog(mainPanel,"Username: " +o.getUsername());
                }
            }
            if(!success){
                if(email.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, "Compila tutti i campi");
                } else
                    JOptionPane.showMessageDialog(mainPanel, "Non ci sono utenti/organizzatori con questa email/password");
            }

        });

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> controller.logout());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
