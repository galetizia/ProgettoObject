package gui;

import controller.ControllerSchermataOrganizzatore;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.TeamDAO;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

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
    private boolean infoVisibili = false; //variabile per controllare il pulsante informazioni personali
    private boolean hackathonVisibili = false;
    private OrganizzatoreDAO odao = new OrganizzatoreDAO();

    HackathonDAO hdao = new HackathonDAO();
    TeamDAO tdao = new TeamDAO();

    public SchermataOrganizzatore(ControllerSchermataOrganizzatore controller, Organizzatore organizzatore) {
        mainPanel.setPreferredSize(new Dimension(600, 400));

        hackatt.setVisible(false);
        hackatt.setFont(new Font("Segoe UI", Font.BOLD, 14));

        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        visualizzaClassificaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        visualizzaClassificaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaClassificaButton.addActionListener(e ->{
            if(organizzatore.getHackathonID() == null) {
                JOptionPane.showMessageDialog(mainPanel, "Al momento non sta gestendo alcun Hackathon!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(!hdao.isClassificaPubblicata(organizzatore.getHackathonID())){
                JOptionPane.showMessageDialog(mainPanel, "Classifica non ancora pubblicata!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                controller.showSchermataClassifica(organizzatore.getHackathonID(), organizzatore);
            }
        });

        gestioneHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gestioneHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gestioneHackathonButton.addActionListener(e -> {
            if(organizzatore.getHackathonID() != null) {
                controller.getSchermataGestioneHack(organizzatore);
            }
            else
                JOptionPane.showMessageDialog(mainPanel,"Al momento non sta gestendo alcun Hackathon");
        });

        pubblicaClassificaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pubblicaClassificaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pubblicaClassificaButton.addActionListener(e -> {
            if(organizzatore.getHackathonID() == null) {
                JOptionPane.showMessageDialog(mainPanel, "Al momento non sta gestendo alcun Hackathon!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
           List<Team> teams = tdao.getTeamByHackathon(organizzatore.getHackathonID());

            if(teams.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Nessun Team iscritto!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!hdao.isClassificaPubblicata(organizzatore.getHackathonID())) {
                for(Team team : teams) {
                    odao.setClassifica(organizzatore.getHackathonID());
                    int teamID = team.getId();
                    String nome = team.getNome();

                    List<Double> votiPerTeam = tdao.getVotiPerTeam(teamID);
                    if (votiPerTeam.isEmpty()) { tdao.setVotiPerTeam(teamID, 0.00);continue; }

                    double somma = 0;
                    for(Double voti : votiPerTeam) {
                        somma += voti;
                    }
                    double media = somma / votiPerTeam.size();
                    tdao.setVotiPerTeam(teamID, media);
                }
                JOptionPane.showMessageDialog(mainPanel, "Classifica pubblicata!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(mainPanel, "Classifica già pubblicata!", "Info", JOptionPane.INFORMATION_MESSAGE);

        });

        informazioniPersonaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(e -> {
            if (!infoVisibili) {
                // Primo clic: imposto i testi e rendo visibili
                name.setText("Nome: " + organizzatore.getNome());
                surname.setText("Cognome: " + organizzatore.getCognome());
                email.setText("Email: " + organizzatore.getEmail());
                username.setText("Username: " + organizzatore.getUsername());

                name.setVisible(true);
                surname.setVisible(true);
                email.setVisible(true);
                username.setVisible(true);

                infoVisibili = true; // Ora le info sono visibili
            } else {
                // Se già visibili, li nascondo
                name.setVisible(false);
                surname.setVisible(false);
                email.setVisible(false);
                username.setVisible(false);

                infoVisibili = false; // Ora le info sono nascoste
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });

        hackathonAttualeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttualeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hackathonAttualeButton.addActionListener(e -> {
            if(organizzatore.getHackathonID() != null) {
                Hackathon h = hdao.getHackathonByID(organizzatore.getHackathonID());
                titolo.setText("Titolo: " +h.getNome());
                sede.setText("Sede: " +h.getSede());
                dataInizio.setText("Data Inizio:" +h.getDataInizio().toString());
                dataFine.setText("Data Fine: " +h.getDataFine().toString());
                maxIscritti.setText("Max Iscritti: " +h.getMaxIscritti());
                maxDimTeam.setText("Max Dim. Team: " +h.getMaxDimTeam());

                if(!hackathonVisibili){
                    hackatt.setVisible(true);
                    titolo.setVisible(true);
                    sede.setVisible(true);
                    dataInizio.setVisible(true);
                    dataFine.setVisible(true);
                    maxIscritti.setVisible(true);
                    maxDimTeam.setVisible(true);

                    hackathonVisibili = true;
                } else {
                    hackatt.setVisible(false);
                    titolo.setVisible(false);
                    sede.setVisible(false);
                    dataInizio.setVisible(false);
                    dataFine.setVisible(false);
                    maxIscritti.setVisible(false);
                    maxDimTeam.setVisible(false);
                    hackathonVisibili = false;
                }
                return;
            }
            JOptionPane.showMessageDialog(mainPanel,"Al momento non sta gestendo alcun Hackathon");

        });

        problemaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        problemaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemaHackathonButton.addActionListener(e -> controller.mostraProblemaHackathon(organizzatore));

        organizzaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        organizzaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organizzaHackathonButton.addActionListener(e -> {
            if(organizzatore.getHackathonID() == null){
                controller.schermataOrganizzaHackathon(organizzatore);
                return;
            }
            JOptionPane.showMessageDialog(mainPanel,"È già l'organizzatore di un Hackathon");

        });

        logOutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.addActionListener(e -> controller.logout());

    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

}

