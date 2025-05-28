package gui;

import model.*;
import javax.swing.*;
import java.awt.*;

public class RecuperaPassword {
    private JButton confermaButton;
    private JTextField usernameInput;
    private JButton logoutButton;
    private JTextField emailInput;
    private JPanel mainPanel;

    private Controller controller;

    public RecuperaPassword(Controller controller) {
        this.controller = controller;
        mainPanel.setPreferredSize(new Dimension(400, 300));
        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {
            String email = emailInput.getText();
            String username = usernameInput.getText();

            boolean success = false;
            for(Utente u : controller.getListaUtenti()){
                if((u.getEmail().equalsIgnoreCase(email)) && (u.getUsername().equalsIgnoreCase(username))){
                    success = true;
                    JOptionPane.showMessageDialog(mainPanel,"Password: " +u.getPassword());
                }
            }
            for(Organizzatore o : controller.getListaOrganizzatori()){
                if((o.getEmail().equalsIgnoreCase(email)) && (o.getUsername().equalsIgnoreCase(username))){
                    success = true;
                    JOptionPane.showMessageDialog(mainPanel,"Password: " +o.getPassword());
                }
            }
            if(!success){
                if(email.isEmpty() || username.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, "Compila tutti i campi");
                } else
                    JOptionPane.showMessageDialog(mainPanel, "Non ci sono utenti/organizzatori con questo username/email");
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
