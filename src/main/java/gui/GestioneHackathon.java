package gui;

import controller.ControllerGestioneHackathon;
import model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Classe responsabile dell'interfaccia grafica per la gestione di un hackathon da parte di un organizzatore.
 * <p>
 * Permette di:
 * <ul>
 *   <li>Visualizzare gli elenchi di utenti, giudici e team</li>
 *   <li>Rimuovere partecipanti (utenti, giudici, team)</li>
 *   <li>Aggiungere giudici a un hackathon</li>
 *   <li>Terminare un hackathon</li>
 * </ul>
 * Tutte le operazioni sono delegate a {@link controller.ControllerGestioneHackathon}.
 * </p>
 */
public class GestioneHackathon {
    // Componenti dell’interfaccia grafica
    private JLabel area;
    private JList<String> list;
    private JButton elencoUtentiButton;
    private JButton elencoGiudiciButton;
    private JButton elencoTeamsButton;
    private JButton rimozioneUtenteGiudiceTeamButton;
    private JTextField idTextField;
    private JButton confermaButton;
    private JButton indietroButton;
    private JLabel idLabel;
    private JScrollPane panelHackathon;
    private JPanel mainpanel;
    private JCheckBox utenteCheckBox;
    private JCheckBox giudiceCheckBox;
    private JCheckBox teamCheckBox;
    private JButton terminaHackathonButton;
    private JButton aggiungiGiudiceButton;
    private JTextField usernameTextField;
    private JButton confermaGiudiceButton;
    private JLabel username;

    // Stato dell'interfaccia
    private JButton ultimoPulsantePremuto = null;

    // Costanti
    private static final String SEGOEUI = "Segoe UI";

    // Modello della lista visualizzata
    private final DefaultListModel<String> modelList;

    /**
     * Costruttore della schermata di gestione hackathon.
     *
     * @param controller     il controller che gestisce le logiche dell'interfaccia
     * @param organizzatore  l’organizzatore attualmente loggato
     */
    public GestioneHackathon(ControllerGestioneHackathon controller, Organizzatore organizzatore) {
        mainpanel.setPreferredSize(new Dimension(600, 450));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        setVisibilityAggGiudice(false);
        setVisibilityRimozione(false);

        modelList = new DefaultListModel<>();
        list.setModel(modelList);

        /* Bottone che mostra il pannello per l’aggiunta di un nuovo giudice */
        aggiungiGiudiceButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        aggiungiGiudiceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aggiungiGiudiceButton.addActionListener(ignored ->
                ultimoPulsantePremuto = controller.visibilitaAggGiudice(idLabel, list, modelList, panelHackathon,
                        username, ultimoPulsantePremuto, aggiungiGiudiceButton));

        /* Bottone che conferma l’aggiunta di un giudice e aggiorna la lista potenziali */
        confermaGiudiceButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaGiudiceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaGiudiceButton.addActionListener(ignored -> {
            controller.aggiungiGiudice(usernameTextField, organizzatore);
            controller.mostraPotenzialiGiudici(list, modelList, panelHackathon);
        });

        /* Bottone che mostra l’elenco degli utenti dell’hackathon */
        elencoUtentiButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        elencoUtentiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoUtentiButton.addActionListener(ignored ->
                ultimoPulsantePremuto = controller.visibilitaElencoUtenti(username, idLabel, ultimoPulsantePremuto,
                        elencoUtentiButton, list, modelList, organizzatore));

        /* Bottone che mostra l’elenco dei giudici assegnati */
        elencoGiudiciButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        elencoGiudiciButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoGiudiciButton.addActionListener(ignored ->
                ultimoPulsantePremuto = controller.visibilitaElencoGiudici(username, idLabel, ultimoPulsantePremuto,
                        elencoGiudiciButton, list, modelList, organizzatore));

        /* Bottone che mostra l’elenco dei team partecipanti */
        elencoTeamsButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        elencoTeamsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoTeamsButton.addActionListener(ignored ->
                ultimoPulsantePremuto = controller.visibilitaElencoTeam(username, idLabel, ultimoPulsantePremuto,
                        elencoTeamsButton, list, modelList, organizzatore));

        /* Bottone che termina ufficialmente l’hackathon */
        terminaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        terminaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        terminaHackathonButton.addActionListener(ignored -> controller.terminaHackathon());

        /* Bottone che apre il pannello per la rimozione di utente/giudice/team */
        rimozioneUtenteGiudiceTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        rimozioneUtenteGiudiceTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rimozioneUtenteGiudiceTeamButton.addActionListener(ignored ->
                ultimoPulsantePremuto = controller.visibilitaRimozione(idLabel, ultimoPulsantePremuto,
                        rimozioneUtenteGiudiceTeamButton, modelList,
                        utenteCheckBox, giudiceCheckBox, teamCheckBox));

        /* Checkbox che mostra la lista utenti se selezionata */
        utenteCheckBox.addActionListener(ignored -> {
            if (utenteCheckBox.isSelected()) {
                teamCheckBox.setSelected(false);
                giudiceCheckBox.setSelected(false);
                controller.mostraUtenti(list, modelList, panelHackathon, organizzatore);
            } else {
                modelList.clear();
            }
        });

        /* Checkbox che mostra la lista team se selezionata */
        teamCheckBox.addActionListener(ignored -> {
            if (teamCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false);
                giudiceCheckBox.setSelected(false);
                controller.mostraTeams(list, modelList, panelHackathon, organizzatore);
            } else {
                modelList.clear();
            }
        });

        /* Checkbox che mostra la lista giudici se selezionata */
        giudiceCheckBox.addActionListener(ignored -> {
            if (giudiceCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false);
                teamCheckBox.setSelected(false);
                controller.mostraGiudici(list, modelList, panelHackathon, organizzatore);
            } else {
                modelList.clear();
            }
        });

        /* Bottone che conferma la rimozione del partecipante selezionato */
        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored ->
                controller.gestioneRimozioni(utenteCheckBox, giudiceCheckBox, teamCheckBox,
                        idTextField, organizzatore));

        /* Bottone che torna alla schermata principale dell’organizzatore */
        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.getSchermataOrganizzatore());
    }

    /**
     * Metodo interno per impostare la visibilità di più componenti insieme.
     *
     * @param visible    true per renderli visibili, false altrimenti
     * @param components i componenti da modificare
     */
    private void setVisibility(boolean visible, JComponent... components) {
        for (JComponent c : components)
            c.setVisible(visible);
    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return il {@link JPanel} principale
     */
    public JPanel getMainPanel() {
        return mainpanel;
    }

    /**
     * Restituisce il pannello contenente l'elenco.
     *
     * @return lo {@link JScrollPane} con la lista
     */
    public JScrollPane getPanelHackathon() {
        return panelHackathon;
    }

    /**
     * Restituisce la label "username".
     *
     * @return la {@link JLabel} "username"
     */
    public JLabel getUsernameLabel() {
        return username;
    }

    /**
     * Imposta la visibilità dei componenti legati alla rimozione (checkbox, campo ID, conferma).
     *
     * @param visible true per mostrare, false per nascondere
     */
    public void setVisibilityRimozione(boolean visible){
        setVisibility(visible, idLabel, idTextField, confermaButton, utenteCheckBox, giudiceCheckBox, teamCheckBox);
    }

    /**
     * Imposta la visibilità dei componenti legati all’aggiunta di giudici.
     *
     * @param visible true per mostrare, false per nascondere
     */
    public void setVisibilityAggGiudice(boolean visible){
        setVisibility(visible, username, usernameTextField, confermaGiudiceButton);
    }
}
