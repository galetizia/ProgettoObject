package gui;

import controller.ControllerOrganizzaHackathon;

import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata di organizzazione di un Hackathon.
 * Consente all'organizzatore di creare una nuova hackathon o visualizzare quelle già attive.
 * <p>
 * Questa classe interagisce con {@link ControllerOrganizzaHackathon} per delegare la logica applicativa.
 * </p>
 */
public class OrganizzaHackathon {

    /** Tutte le componenti di design */
    private JPanel mainPanel;
    private JPanel panelIscrizione;
    private JTextField titoloTextField;
    private JTextField sedeTextField;
    private JButton confermaButton;
    private JScrollPane panelElenchi;
    private JList<String> listElenchi;
    private JButton organizzaNuovaHackathonButton;
    private JButton indietroButton;
    private JButton hackathonAttiveButton;
    private JTextField problemaTextField;
    private JTextField dataInizioTextField;
    private JTextField dataFineTextField;
    private JTextField maxIscrTextField;
    private JTextField maxDimTeamTextField;
    private JLabel area;

    /** Modello della lista per la visualizzazione dei dati nella GUI. */
    private final DefaultListModel<String> modelLista;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore della schermata di organizzazione hackathon.
     *
     * @param controller Controller associato alla logica di creazione e gestione hackathon.
     */
    public OrganizzaHackathon(ControllerOrganizzaHackathon controller) {
        mainPanel.setPreferredSize(new Dimension(600,400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        panelIscrizione.setVisible(false); // Nasconde inizialmente il pannello di creazione hackathon

        /* Inizializza il modello della lista e lo collega alla JList per gestire gli elementi visualizzati dinamicamente. */
        modelLista = new DefaultListModel<>();
        listElenchi.setModel(modelLista);

        /* Bottone che permette la visualizzazione delle Hackathon attive */
        hackathonAttiveButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(ignored ->
                controller.listeHackathon(listElenchi, modelLista, panelElenchi));

        /* Bottone che chiama il metodo del controller per creare una nuova Hackathon con i dati inseriti nei campi */
        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored ->
                controller.creaHackathon(titoloTextField, sedeTextField, problemaTextField, dataInizioTextField, dataFineTextField, maxIscrTextField, maxDimTeamTextField));

        /* Bottone che rende visibile il pannello per l'inserimento delle informazioni della nuova Hackathon */
        organizzaNuovaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        organizzaNuovaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        organizzaNuovaHackathonButton.addActionListener(ignored ->
                panelIscrizione.setVisible(true));

        /* Bottone per tornare alla schermata precedente */
        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored ->
                controller.indietro());
    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return il pannello principale
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
