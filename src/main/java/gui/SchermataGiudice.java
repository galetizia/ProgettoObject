package gui;
import controller.ControllerSchermataGiudice;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata Giudice.
 * Consente a un giudice di visualizzare la propria hackathon, il relativo problema e classifica.
 * <p>
 * Questa classe interagisce con {@link ControllerSchermataGiudice} per delegare la logica applicativa.
 * </p>
 */

public class SchermataGiudice {

    /** Tutte le componenti di design */
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

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore della schermata giudice.
     *
     * @param controller Controller associato alla schermata.
     * @param giudice L'oggetto {@link Giudice} attualmente loggato.
     */
    public SchermataGiudice(ControllerSchermataGiudice controller, Giudice giudice) {

        /* DAO per l'entità hackathon, usato per operazioni collegate */
        final HackathonDAO hdao = new HackathonDAO();

        mainpanel.setPreferredSize(new Dimension(600, 400));
        /* Creiamo un oggetto hackathon, ricercando nel database l'hackathon del giudice*/
        Hackathon h = hdao.getHackathonByID(giudice.getHackathonID());
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));
        name.setVisible(false);
        attualehack.setVisible(false);
        attualehack.setFont(new Font(SEGOEUI, Font.BOLD, 14));

        /* Bottone che, se pubblicata, mostra la classifica */
        classificaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        classificaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        classificaHackathonButton.addActionListener(ignored -> controller.getClassifica(giudice));

        /* Bottone che rende visibili le informazioni personali */
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

        /* Bottone che collega alla schermata voti commenti */
        votazioniCommentiButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        votazioniCommentiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        votazioniCommentiButton.addActionListener(ignored -> controller.showSchermataVotiCommenti(giudice));

        /* Bottone che rende visibili le informazioni dell'hackathon attuale */
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

        /* Bottone che mostra il problema dell'hackathon */
        problemaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        problemaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        problemaHackathonButton.addActionListener(ignored -> controller.problemaHackathon(giudice,h));

        /* Bottone di logout, riporta alla schermata di login */
        logoutButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(ignored -> controller.logout());
    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return Il pannello principale {@link JPanel}.
     */
    public JPanel getMainPanel(){
        return mainpanel;
    }
}
