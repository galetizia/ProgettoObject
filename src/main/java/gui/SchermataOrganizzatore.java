package gui;

import controller.ControllerSchermataOrganizzatore;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.OrganizzatoreDAO;
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
    private JLabel titolo;
    private JLabel sede;
    private JLabel problema;
    private JLabel dataInizio;
    private JLabel dataFine;
    private JLabel maxIscritti;
    private JLabel maxDimTeam;
    private JLabel area;
    private JLabel hackatt;

    OrganizzatoreDAO odao = new OrganizzatoreDAO();
    HackathonDAO hdao = new HackathonDAO();

    public SchermataOrganizzatore(ControllerSchermataOrganizzatore controller, Organizzatore organizzatore) {
        mainPanel.setPreferredSize(new Dimension(600, 400));

        hackatt.setVisible(false);
        hackatt.setFont(new Font("Segoe UI", Font.BOLD, 14));

        area.setFont(new Font("Segoe UI", Font.BOLD, 38));
        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(e -> {
            name.setText("Nome: "+organizzatore.getNome());
            surname.setText("Cognome: "+organizzatore.getCognome());
            email.setText("Email: "+organizzatore.getEmail());
            username.setText("Username: "+organizzatore.getUsername());
        });

        hackathonAttualeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttualeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hackathonAttualeButton.addActionListener(e -> {

            if(organizzatore.getHackathonID() != null) {
                Hackathon h = hdao.getHackathonByID(organizzatore.getHackathonID());
                hackatt.setVisible(true);
                titolo.setText("Titolo: " +h.getNome());
                sede.setText("Sede: " +h.getSede());
                problema.setText("Problema: " +h.getProblema());
                dataInizio.setText("Data Inizio:" +h.getDataInizio().toString());
                dataFine.setText("Data Fine: " +h.getDataFine().toString());
                maxIscritti.setText("Max Iscritti: " +h.getMaxIscritti());
                maxDimTeam.setText("Max Dim. Team: " +h.getMaxDimTeam());

            } else {
                JOptionPane.showMessageDialog(mainPanel,"Al momento non sta organizzando alcun Hackathon");
            }
        });

        organizzaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        organizzaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organizzaHackathonButton.addActionListener(e -> {
            if(organizzatore.getHackathonID() == null)
                controller.schermataOrganizzaHackathon(organizzatore);
            else
                JOptionPane.showMessageDialog(mainPanel,"è già l'organizzatore di un Hackathon");

        });

        logOutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.addActionListener(e -> controller.logout());

    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

}

