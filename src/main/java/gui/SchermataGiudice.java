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
    private JLabel dataInizio;
    private JLabel dataFine;
    private JLabel maxDimTeam;
    private JLabel maxIscritti;
    private JButton logoutButton;
    private JLabel attualehack;
    private JLabel area;
    private JButton problemaHackathonButton;
    private JButton valutaTeamButton;
    private JButton commentaAggiornamentiButton;
    private boolean infoVisibili = false;
    private boolean hackathonVisibile = false;


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
            titolo.setText("Titolo: " +h.getNome());
            sede.setText("Sede: " +h.getSede());
            dataInizio.setText("Data Inizio:" +h.getDataInizio().toString());
            dataFine.setText("Data Fine: " +h.getDataFine().toString());
            maxIscritti.setText("Max Iscritti: " +h.getMaxIscritti());
            maxDimTeam.setText("Max Dim. Team: " +h.getMaxDimTeam());
            if(!hackathonVisibile){
                attualehack.setVisible(true);
                titolo.setVisible(true);
                sede.setVisible(true);
                dataInizio.setVisible(true);
                dataFine.setVisible(true);
                maxIscritti.setVisible(true);
                maxDimTeam.setVisible(true);

                hackathonVisibile = true;
            } else {
                attualehack.setVisible(false);
                titolo.setVisible(false);
                sede.setVisible(false);
                dataInizio.setVisible(false);
                dataFine.setVisible(false);
                maxIscritti.setVisible(false);
                maxDimTeam.setVisible(false);
                hackathonVisibile = false;
            }
            return;
        });

        problemaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        problemaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        problemaHackathonButton.addActionListener(e -> {
            if(giudice.getHackathonID() != null) {
                String problema = h.getProblema();
                String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
                JOptionPane.showMessageDialog(mainpanel, problemaHTML);
            } else {
                JOptionPane.showMessageDialog(mainpanel,"Non partecipi a nessun Hackathon");
            }
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
