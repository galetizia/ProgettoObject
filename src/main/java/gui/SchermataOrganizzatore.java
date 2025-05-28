package gui;

import model.*;
import javax.swing.*;
import java.awt.*;

public class SchermataOrganizzatore {
    private JPanel mainPanel;
    private JButton organizzaHackathonButton;
    private JButton hackathonAttualeButton;
    private JButton informazioniPersonaliButton;
    private JLabel name;
    private JLabel surname;
    private JLabel email;
    private JLabel username;
    private JButton logOutButton;

    private Controller controller;

    public SchermataOrganizzatore(Controller controller, Organizzatore organizzatore) {
        this.controller = controller;
        mainPanel.setPreferredSize(new Dimension(600, 400));


        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(e -> {
            name.setText("Nome: "+organizzatore.getNome());
            surname.setText("Cognome: "+organizzatore.getCognome());
            email.setText("Email: "+organizzatore.email);
            username.setText("Username: "+organizzatore.username);
        });

        hackathonAttualeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttualeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hackathonAttualeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        organizzaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        organizzaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organizzaHackathonButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });
        logOutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.addActionListener(e -> controller.logout());

    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

}

