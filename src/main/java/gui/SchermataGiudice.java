package gui;
import controller.ControllerSchermataGiudice;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import javax.swing.*;
import java.awt.*;


public class SchermataGiudice {
    private JPanel mainpanel;
    private JButton infoButton;
    private JLabel name;
    private JLabel surname;
    private JLabel email;
    private JLabel username;
    private JButton hackathonAttualeButton;
    private JLabel titolo;
    private JLabel sede;
    private JLabel dataInizio;
    private JLabel dataFine;
    private JLabel maxDimTeam;
    private JLabel maxIscritti;
    private JButton logoutButton;
    private JLabel attualehack;
    private JLabel area;
    private JButton problemaHackathonButton;
    private JButton votazioniCommentiButton;
    private JButton classificaHackathonButton;

    private static final String SEGOEUI = "Segoe UI";

    HackathonDAO hdao = new HackathonDAO();

    public SchermataGiudice(ControllerSchermataGiudice controller, Giudice giudice) {
        mainpanel.setPreferredSize(new Dimension(600, 400));
        Hackathon h = hdao.getHackathonByID(giudice.getHackathonID());
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));
        name.setVisible(false);
        attualehack.setVisible(false);
        attualehack.setFont(new Font(SEGOEUI, Font.BOLD, 14));

        classificaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        classificaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        classificaHackathonButton.addActionListener(ignored -> controller.getClassifica(giudice));

        infoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        infoButton.addActionListener(ignored -> {
            if (!name.isVisible()) {
                // Primo clic: imposto i testi e rendo visibili
                name.setText("Nome: " + giudice.getNome());
                surname.setText("Cognome: " + giudice.getCognome());
                email.setText("Email: " + giudice.getEmail());
                username.setText("Username: " + giudice.getUsername());

                name.setVisible(true);
                surname.setVisible(true);
                email.setVisible(true);
                username.setVisible(true);
                return;
            }
            name.setVisible(false);
            surname.setVisible(false);
            email.setVisible(false);
            username.setVisible(false);

            mainpanel.revalidate();
            mainpanel.repaint();
        });

        votazioniCommentiButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        votazioniCommentiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        votazioniCommentiButton.addActionListener(ignored -> controller.showSchermataVotiCommenti(giudice));

        hackathonAttualeButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        hackathonAttualeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttualeButton.addActionListener(ignored -> {
            titolo.setText("Titolo: " +h.getNome());
            sede.setText("Sede: " +h.getSede());
            dataInizio.setText("Data Inizio:" +h.getDataInizio().toString());
            dataFine.setText("Data Fine: " +h.getDataFine().toString());
            maxIscritti.setText("Max Iscritti: " +h.getMaxIscritti());
            maxDimTeam.setText("Max Dim. Team: " +h.getMaxDimTeam());
            if(!attualehack.isVisible()) {
                attualehack.setVisible(true);
                titolo.setVisible(true);
                sede.setVisible(true);
                dataInizio.setVisible(true);
                dataFine.setVisible(true);
                maxIscritti.setVisible(true);
                maxDimTeam.setVisible(true);
                return;
            }
            attualehack.setVisible(false);
            titolo.setVisible(false);
            sede.setVisible(false);
            dataInizio.setVisible(false);
            dataFine.setVisible(false);
            maxIscritti.setVisible(false);
            maxDimTeam.setVisible(false);
        });

        problemaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        problemaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        problemaHackathonButton.addActionListener(ignored -> controller.problemaHackathon(giudice,h));

        logoutButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(ignored -> controller.logout());
    }

    public JPanel getMainPanel(){
        return mainpanel;
    }
}
