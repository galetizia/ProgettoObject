package gui;

import controller.ControllerSchermataUtente;
import implementazionepostgresdao.HackathonDAO;
import model.Hackathon;
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
    private JLabel area;
    private JLabel titolo;
    private JLabel sede;
    private JLabel dataInizio;
    private JLabel dataFine;
    private JLabel maxIscritti;
    private JLabel maxDimTeam;
    private JLabel hackText;
    private JButton classificaHackathonButton;
    private boolean hackathonVisibile= false;
    private final HackathonDAO hdao = new HackathonDAO();

    public SchermataUtente(ControllerSchermataUtente controller, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600, 300));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));
        hackText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackText.setVisible(false);
        name.setVisible(false);

        classificaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        classificaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        classificaHackathonButton.addActionListener(ignored ->{
            if(utente.getHackathonID() == (null)) {
                JOptionPane.showMessageDialog(mainPanel, "Non partecipi a nessun Hackathon!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!hdao.isClassificaPubblicata(utente.getHackathonID())){
                JOptionPane.showMessageDialog(mainPanel, "Classifica non ancora pubblicata!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.showSchermataClassifica(utente.getHackathonID(), utente);

        });

        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(ignored -> {

            if (!name.isVisible()) {
                name.setText("Nome: " + utente.getNome());
                surname.setText("Cognome: " + utente.getCognome());
                email.setText("Email: " + utente.getEmail());
                username.setText("Username: " + utente.getUsername());

                name.setVisible(true);
                surname.setVisible(true);
                email.setVisible(true);
                username.setVisible(true);

            } else {
                name.setVisible(false);
                surname.setVisible(false);
                email.setVisible(false);
                username.setVisible(false);
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });

        attualebutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        attualebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        attualebutton.addActionListener(ignored -> {
            if(utente.getHackathonID() != null) {
                Hackathon h = hdao.getHackathonByID(utente.getHackathonID());
                titolo.setText("Titolo: " +h.getNome());
                sede.setText("Sede: " +h.getSede());
                dataInizio.setText("Data Inizio:" +h.getDataInizio().toString());
                dataFine.setText("Data Fine: " +h.getDataFine().toString());
                maxIscritti.setText("Max Iscritti: " +h.getMaxIscritti());
                maxDimTeam.setText("Max Dim. Team: " +h.getMaxDimTeam());

                if(!hackathonVisibile){
                    hackText.setVisible(true);
                    titolo.setVisible(true);
                    sede.setVisible(true);
                    dataInizio.setVisible(true);
                    dataFine.setVisible(true);
                    maxIscritti.setVisible(true);
                    maxDimTeam.setVisible(true);

                    hackathonVisibile = true;
                } else {
                    hackText.setVisible(false);
                    titolo.setVisible(false);
                    sede.setVisible(false);
                    dataInizio.setVisible(false);
                    dataFine.setVisible(false);
                    maxIscritti.setVisible(false);
                    maxDimTeam.setVisible(false);
                    hackathonVisibile = false;
                }
                return;
            }
            JOptionPane.showMessageDialog(mainPanel,"Non partecipi a nessun Hackathon");

        });

        iscrizioneTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iscrizioneTeamButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iscrizioneTeamButton.addActionListener(ignored -> controller.schermataIscrizioneTeam(utente));

        myTeambutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        myTeambutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        myTeambutton.addActionListener(ignored -> controller.schermataTeamUtente(utente));

        problemabutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        problemabutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemabutton.addActionListener(ignored -> controller.mostraProblemaHackathon(utente));

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.addActionListener(ignored -> controller.logout());

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

}//modificato
