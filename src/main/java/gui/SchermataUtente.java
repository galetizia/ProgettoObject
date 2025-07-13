package gui;

import controller.ControllerSchermataUtente;
import model.Utente;

import javax.swing.*;
import java.awt.*;

public class SchermataUtente {
    private JPanel mainPanel;
    private JButton iscrizionebutton;
    private JButton attualebutton;
    private JButton teambutton;
    private JButton informazioniPersonaliButton;
    private JButton problemabutton;
    private JButton logoutButton;
    private JButton caricaAggiornamentoButton;
    private JLabel name;
    private JLabel surname;
    private JLabel username;
    private JLabel email;

    public SchermataUtente(ControllerSchermataUtente controller, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600, 400));

        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(e -> {
            name.setText("Nome: "+utente.getNome());
            surname.setText("Cognome: "+utente.getCognome());
            email.setText("Email: "+utente.getEmail());
            username.setText("Username: "+utente.getUsername());
        });

        attualebutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        attualebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        attualebutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        iscrizionebutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iscrizionebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iscrizionebutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        teambutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        teambutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teambutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        problemabutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        problemabutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemabutton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        caricaAggiornamentoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        caricaAggiornamentoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        caricaAggiornamentoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel,"Funzionalità presto in arrivo");
        });

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> controller.logout());

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

}
