package gui;
import controller.ControllerSchermataGiudice;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import implementazionepostgresdao.GiudiceDAO;
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
    private JLabel problema;
    private JLabel dataInizio;
    private JLabel dataFine;
    private JLabel maxDimTeam;
    private JLabel maxIscritti;
    private JButton logoutButton;
    private JLabel attualehack;
    private JLabel area;
    private boolean infoVisibili = false;


    GiudiceDAO gdao = new GiudiceDAO();
    HackathonDAO hdao = new HackathonDAO();

    public SchermataGiudice(ControllerSchermataGiudice controller, Giudice giudice) {
        mainpanel.setPreferredSize(new Dimension(600, 400));
        Hackathon h = hdao.getHackathonByID(giudice.getHackathonID());
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        attualehack.setVisible(false);
        attualehack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        infoButton.addActionListener(e -> {
            if (!infoVisibili) {
                // Primo clic: imposto i testi e rendo visibili
                name.setText("Nome: " + giudice.getNome());
                surname.setText("Cognome: " + giudice.getCognome());
                email.setText("Email: " + giudice.getEmail());
                username.setText("Username: " + giudice.getUsername());

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

            mainpanel.revalidate();
            mainpanel.repaint();
        });

        hackathonAttualeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttualeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttualeButton.addActionListener(e -> {
            attualehack.setVisible(true);
            titolo.setText("Titolo: " +h.getNome());
            sede.setText("Sede: " +h.getSede());
            problema.setText("Problema: " +h.getProblema());
            dataInizio.setText("Data Inizio:" +h.getDataInizio().toString());
            dataFine.setText("Data Fine: " +h.getDataFine().toString());
            maxIscritti.setText("Max Iscritti: " +h.getMaxIscritti());
            maxDimTeam.setText("Max Dim. Team: " +h.getMaxDimTeam());
        });

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            controller.logout();
        });
    }

    public JPanel getMainPanel(){
        return mainpanel;
    }
}
