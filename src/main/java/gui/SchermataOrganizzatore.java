package gui;

import controller.ControllerSchermataOrganizzatore;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata organizzatore.
 * Consente a un organizzatore di visualizzare la propria hackathon, il relativo problema, pubblicare e
 * visualizzare la classifica e organizzare un hackathon.
 * <p>
 * Questa classe interagisce con {@link ControllerSchermataOrganizzatore} per delegare la logica applicativa.
 * </p>
 */
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

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore della schermata organizzatore.
     *
     * @param controller Controller associato alla schermata.
     * @param organizzatore L'oggetto {@link Organizzatore} attualmente loggato.
     */
    public SchermataOrganizzatore(ControllerSchermataOrganizzatore controller, Organizzatore organizzatore) {
        final HackathonDAO hdao = new HackathonDAO();
        mainPanel.setPreferredSize(new Dimension(600, 400));

        hackatt.setVisible(false);
        name.setVisible(false);
        hackatt.setFont(new Font(SEGOEUI, Font.BOLD, 14));

        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        /* Bottone che, se pubblicata, mostra la classifica */
        visualizzaClassificaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        visualizzaClassificaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaClassificaButton.addActionListener(ignored -> controller.getClassifica(organizzatore));

        /* Bottone che porta alla schermata per la gestione dell'hackathon */
        gestioneHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        gestioneHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gestioneHackathonButton.addActionListener(ignored -> controller.getSchermataGestioneHack(organizzatore));

        /* Bottone che chiama il metodo del controller che calcola e pubblica la classifica */
        pubblicaClassificaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        pubblicaClassificaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pubblicaClassificaButton.addActionListener(ignored ->controller.pubblicaClassifica(organizzatore));

        /* Bottone che rende visibili le informazioni personali */
        informazioniPersonaliButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        informazioniPersonaliButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        informazioniPersonaliButton.addActionListener(ignored -> {
            if (!name.isVisible()) {
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
            name.setVisible(false);
            surname.setVisible(false);
            email.setVisible(false);
            username.setVisible(false);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        /* Bottone che rende visibili le informazioni dell'hackathon attuale */
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

        /* Bottone che mostra il problema dell'hackathon */
        problemaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        problemaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        problemaHackathonButton.addActionListener(ignored -> controller.mostraProblemaHackathon(organizzatore));

        /* Bottone che porta alla schermata per organizzare un hackathon*/
        organizzaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        organizzaHackathonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        organizzaHackathonButton.addActionListener(ignored -> controller.schermataOrganizzaHackathon(organizzatore));

        /* Bottone di logout, riporta alla schermata di login */
        logOutButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.addActionListener(ignored -> controller.logout());

    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return Il pannello principale {@link JPanel}.
     */
    public JPanel getMainPanel(){
        return mainPanel;
    }

}

