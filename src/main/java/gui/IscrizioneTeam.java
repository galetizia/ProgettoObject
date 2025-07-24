package gui;

import controller.ControllerIscrizioneTeam;

import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata di iscrizione a un team.
 * Consente di visualizzare le hackathon attive, i team iscritti,
 * iscriversi a un team esistente o crearne uno.
 * <p>
 * Questa classe interagisce con {@link ControllerIscrizioneTeam} per delegare la logica applicativa.
 * </p>
 */
public class IscrizioneTeam {

    /** Tutte le componenti di design */
    private JPanel mainPanel;
    private JButton listaTeamButton;
    private JButton creaTeamButton;
    private JList<String> listElenchi;
    private JButton iscrivitiAdUnTeamButton;
    private JTextField teamIDtextField;
    private JScrollPane panelElenchi;
    private JTextField nomeNuovoTeamTextField;
    private JTextField creaTeamIDTextField;
    private JButton confermaCreaTeamButton;
    private JTextField hackathonIDtextField;
    private JPanel panelIscrizione;
    private JButton indietroButton;
    private JButton hackathonAttiveButton;
    private JLabel area;
    private JButton confermaListaTeamButton;
    private JButton confermaIscrTramiteButton;
    private JLabel hackathonIDLabel;
    private JLabel teamIDLabel;

    /** Modello della lista per la visualizzazione dei dati nella GUI. */
    private final DefaultListModel<String> modelList;

    /** Ultimo pulsante premuto dall’utente, usato per la gestione dello stato della GUI. */
    private JButton ultimoPulsantePremuto = null;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore della schermata iscrizione team per gli utenti.
     *
     * @param controller Controller associato alla schermata.
     */
    public IscrizioneTeam(ControllerIscrizioneTeam controller) {
        mainPanel.setPreferredSize(new Dimension(600,400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        panelIscrizione.setVisible(false);

        hackathonIDLabel.setVisible(false);
        hackathonIDtextField.setVisible(false);
        confermaListaTeamButton.setVisible(false);

        teamIDLabel.setVisible(false);
        teamIDtextField.setVisible(false);
        confermaIscrTramiteButton.setVisible(false);

        /* Inizializza il modello della lista e lo collega alla JList per gestire gli elementi visualizzati dinamicamente. */
        modelList = new DefaultListModel<>();
        listElenchi.setModel(modelList);

        /* Bottone che permette la visualizzazione delle Hackathon attive */
        hackathonAttiveButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaHackathonAttive(listElenchi, modelList, confermaListaTeamButton, confermaIscrTramiteButton, panelIscrizione, hackathonAttiveButton, ultimoPulsantePremuto));

        /* Bottone che permette la visualizzazione dei campi da compilare per visualizzare i team di un hackathon */
        listaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        listaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listaTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaListaTeam(listElenchi, modelList, listaTeamButton, confermaIscrTramiteButton, panelIscrizione, hackathonIDtextField, ultimoPulsantePremuto));

        /* Bottone che chiama il metodo del controller per la visualizzazione dei team di una specifica Hackathon*/
        confermaListaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaListaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaListaTeamButton.addActionListener(ignored ->
            controller.visualizzaTeamHackathon(hackathonIDtextField, listElenchi, modelList));

        /* Bottone che permette la visualizzazione dei campi da compilare per iscriversi a un team di un hackathon */
        iscrivitiAdUnTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        iscrivitiAdUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iscrivitiAdUnTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaIscrivitiTeam(confermaListaTeamButton, iscrivitiAdUnTeamButton, panelIscrizione, teamIDtextField, ultimoPulsantePremuto));

        /* Bottone che chiama un metodo del controller che contiene la logica di iscrizione a un team */
        confermaIscrTramiteButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaIscrTramiteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaIscrTramiteButton.addActionListener(ignored ->
            controller.iscrizioneTeam(teamIDtextField));

        /* Bottone che permette la visualizzazione dei campi da compilare per creare un team in un hackathon */
        creaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        creaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creaTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaCreaTeam(modelList, creaTeamButton, confermaListaTeamButton, confermaIscrTramiteButton, panelIscrizione, ultimoPulsantePremuto));

        /* Bottone che chiama un metodo del controller che contiene la logica di creazione di un team */
        confermaCreaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaCreaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaCreaTeamButton.addActionListener(ignored ->
            controller.creazioneTeam(nomeNuovoTeamTextField, creaTeamIDTextField));

        /* Bottone che porta alla schermata Utente */
        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.showUtente());
    }

    /**
     * Rende visibile il pannello contenente l'elenco (lista) dei team.
     */
    public void setVisiblePanelElenchi() {
        panelElenchi.setVisible(true);
    }

    /**
     * Metodo per mostrare/nascondere un insieme di componenti della GUI.
     *
     * @param visible true per rendere visibili i componenti, false per nasconderli.
     * @param components Lista di componenti Swing da modificare.
     */
    private void setVisibility(boolean visible, JComponent... components) {
        for (JComponent c : components)
            c.setVisible(visible);
    }

    /**
     * Imposta la visibilità dei componenti relativi alla visualizzazione dei team.
     *
     * @param visible true per rendere visibili i componenti, false per nasconderli.
     */
    public void setVisibilityListaTeam(Boolean visible){
        setVisibility(visible, hackathonIDLabel, hackathonIDtextField, confermaListaTeamButton);
    }

    /**
     * Imposta la visibilità dei componenti relativi all'iscrizione a un team.
     *
     * @param visible true per rendere visibili i componenti, false per nasconderli.
     */
    public void setVisibilityIscrivitiTeam(Boolean visible){
        setVisibility(visible, teamIDLabel, teamIDtextField, confermaIscrTramiteButton);
    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return Il {@link JPanel} principale della schermata.
     */
    public JPanel getMainPanel(){
        return mainPanel;
    }
}
