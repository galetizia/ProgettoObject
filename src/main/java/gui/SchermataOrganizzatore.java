package gui;

import controller.ControllerSchermataOrganizzatore;
import implementazionepostgresdao.HackathonDAO;
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
    private JLabel dataInizio;
    private JLabel dataFine;
    private JLabel maxIscritti;
    private JLabel maxDimTeam;
    private JLabel area;
    private JLabel hackatt;
    private JButton gestioneHackathonButton;
    private JButton problemaHackathonButton;
    private JButton pubblicaClassificaButton;
    private JButton visualizzaClassificaButton;

    private static final String SEGOEUI = "Segoe UI";

    private final HackathonDAO hdao = new HackathonDAO();

    public SchermataOrganizzatore(ControllerSchermataOrganizzatore controller, Organizzatore organizzatore) {
        mainPanel.setPreferredSize(new Dimension(600, 400));

        hackatt.setVisible(false);
        name.setVisible(false);
        hackatt.setFont(new Font(SEGOEUI, Font.BOLD, 14));

        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        visualizzaClassificaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        visualizzaClassificaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaClassificaButton.addActionListener(ignored -> controller.getClassifica(organizzatore));

        gestioneHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        gestioneHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gestioneHackathonButton.addActionListener(ignored -> controller.getSchermataGestioneHack(organizzatore));

        pubblicaClassificaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        pubblicaClassificaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pubblicaClassificaButton.addActionListener(ignored ->controller.pubblicaClassifica(organizzatore));

        informazioniPersonaliButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(ignored -> {
            if (!name.isVisible()) {
                // Primo clic: imposto i testi e rendo visibili
                name.setText("Nome: " + organizzatore.getNome());
                surname.setText("Cognome: " + organizzatore.getCognome());
                email.setText("Email: " + organizzatore.getEmail());
                username.setText("Username: " + organizzatore.getUsername());

                name.setVisible(true);
                surname.setVisible(true);
                email.setVisible(true);
                username.setVisible(true);
                return;
            }
            // Se già visibili, li nascondo
            name.setVisible(false);
            surname.setVisible(false);
            email.setVisible(false);
            username.setVisible(false);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        hackathonAttualeButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        hackathonAttualeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hackathonAttualeButton.addActionListener(ignored -> {
            if(organizzatore.getHackathonID() != null) {
                Hackathon h = hdao.getHackathonByID(organizzatore.getHackathonID());
                titolo.setText("Titolo: " +h.getNome());
                sede.setText("Sede: " +h.getSede());
                dataInizio.setText("Data Inizio:" +h.getDataInizio().toString());
                dataFine.setText("Data Fine: " +h.getDataFine().toString());
                maxIscritti.setText("Max Iscritti: " +h.getMaxIscritti());
                maxDimTeam.setText("Max Dim. Team: " +h.getMaxDimTeam());

                if(!hackatt.isVisible()){
                    hackatt.setVisible(true);
                    titolo.setVisible(true);
                    sede.setVisible(true);
                    dataInizio.setVisible(true);
                    dataFine.setVisible(true);
                    maxIscritti.setVisible(true);
                    maxDimTeam.setVisible(true);
                    return;
                }
                hackatt.setVisible(false);
                titolo.setVisible(false);
                sede.setVisible(false);
                dataInizio.setVisible(false);
                dataFine.setVisible(false);
                maxIscritti.setVisible(false);
                maxDimTeam.setVisible(false);
                return;
            }
            JOptionPane.showMessageDialog(mainPanel,"Al momento non sta gestendo alcun Hackathon");

        });

        problemaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        problemaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemaHackathonButton.addActionListener(ignored -> controller.mostraProblemaHackathon(organizzatore));

        organizzaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        organizzaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organizzaHackathonButton.addActionListener(ignored -> controller.schermataOrganizzaHackathon(organizzatore));

        logOutButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.addActionListener(ignored -> controller.logout());

    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

}

