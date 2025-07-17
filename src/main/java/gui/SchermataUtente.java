package gui;

import controller.ControllerSchermataUtente;
import implementazionepostgresdao.UtenteDAO;
import model.Utente;

import javax.swing.*;
import java.awt.*;

public class SchermataUtente {
    private JPanel mainPanel;
    private JButton attualebutton;
    private JButton myTeambutton;
    private JButton informazioniPersonaliButton;
    private JButton problemabutton;
    private JButton logoutButton;
    private JLabel name;
    private JLabel surname;
    private JLabel username;
    private JLabel email;
    private JButton iscrizioneTeamButton;
    private JTextArea testoCentrale;
    private JLabel area;
    private boolean infoVisibili = false; //variabile per controllare il pulsante informazioni personali

    public SchermataUtente(ControllerSchermataUtente controller, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600, 400));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(e -> {

            if (!infoVisibili) {
                // Primo clic: imposto i testi e rendo visibili
                name.setText("Nome: " + utente.getNome());
                surname.setText("Cognome: " + utente.getCognome());
                email.setText("Email: " + utente.getEmail());
                username.setText("Username: " + utente.getUsername());

                name.setVisible(true);
                surname.setVisible(true);
                email.setVisible(true);
                username.setVisible(true);

                infoVisibili = true; // Ricorda che ora sono visibili
            } else {
                // Se già visibili, li nascondo
                name.setVisible(false);
                surname.setVisible(false);
                email.setVisible(false);
                username.setVisible(false);

                infoVisibili = false; // Ricorda che ora sono nascosti
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });

        attualebutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        attualebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        attualebutton.addActionListener(e -> {
            controller.hackathonAttuale(utente, testoCentrale);
        });

        iscrizioneTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iscrizioneTeamButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iscrizioneTeamButton.addActionListener(e -> {
            controller.schermataIscrizioneTeam(utente);
        });

        myTeambutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        myTeambutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        myTeambutton.addActionListener(e -> {
                controller.schermataTeamUtente(utente);
        });

        problemabutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        problemabutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemabutton.addActionListener(e -> {
            controller.mostraProblemaHackathon(utente, testoCentrale);
        });

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> controller.logout());

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

}//modificato
