package gui;

import controller.ControllerVotiCommenti;
import model.Giudice;

import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata Voti Commenti, che si occupa delle votazioni/commenti dei giudici.
 * Consente a un giudice di commentare o valutare un team, visualizzare gli aggiornamenti dei team.
 * <p>
 * Questa classe interagisce con {@link ControllerVotiCommenti} per delegare la logica applicativa.
 * </p>
 */
public class SchermataVotiCommenti {

    /** Tutte le componenti di design */
    private JButton elencoTeamButton;
    private JButton commentaUnAggiornamentoButton;
    private JButton valutaUnTeamButton;
    private JButton visualizzaAggiornamentoDiUnButton;
    private JTextField idTextField;
    private JButton confermavisualizzButton;
    private JLabel idTextF;
    private JLabel commIDT;
    private JTextField aggTextId;
    private JTextField commentoTextF;
    private JButton confermacommentoButton;
    private JList<String> list;
    private JTextField idTeamField;
    private JTextField votoField;
    private JButton confermavotoButton;
    private JButton indietroButton;
    private JLabel area;
    private JPanel mainPanel;
    private JScrollPane elencoPanel;
    private JLabel commentoF;
    private JLabel teamIdtextField;
    private JLabel votField;

    /** Modello della lista per la visualizzazione dei dati nella GUI. */
    private final DefaultListModel<String> modelList;

    /** Ultimo pulsante premuto dall’utente, usato per la gestione dello stato della GUI. */
    private JButton ultimoPulsantePremuto = null;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "SegoeUI";

    /**
     * Costruttore della schermata giudice per commenti e votazioni.
     *
     * @param controller Controller associato alla schermata.
     * @param giudice L'oggetto {@link Giudice} attualmente loggato.
     */
    public SchermataVotiCommenti(ControllerVotiCommenti controller, Giudice giudice) {
        mainPanel.setPreferredSize(new Dimension(500,350));

        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));
        idTextF.setVisible(false);
        idTextField.setVisible(false);
        aggTextId.setVisible(false);
        commentoTextF.setVisible(false);
        commentoF.setVisible(false);
        commIDT.setVisible(false);
        teamIdtextField.setVisible(false);
        votField.setVisible(false);
        confermavotoButton.setVisible(false);
        confermacommentoButton.setVisible(false);
        confermavisualizzButton.setVisible(false);
        idTeamField.setVisible(false);
        votoField.setVisible(false);

        /* Inizializza il modello della lista e lo collega alla JList per gestire gli elementi visualizzati dinamicamente. */
        modelList = new DefaultListModel<>();
        list.setModel(modelList);

        /* Bottone che permette la visualizzazione dell'elenco di team */
        elencoTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        elencoTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto=controller.visibilitaElencoTeam(aggTextId,teamIdtextField,idTextField,modelList, ultimoPulsantePremuto, elencoTeamButton, list));

        /* Bottone che permette la visualizzazione dei campi da compilare per visualizzare l'ultimo aggiornamento di un team */
        visualizzaAggiornamentoDiUnButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        visualizzaAggiornamentoDiUnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaAggiornamentoDiUnButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaVisualizzaAgg(aggTextId, teamIdtextField, idTextField, modelList, ultimoPulsantePremuto, visualizzaAggiornamentoDiUnButton, list)
        );

        /* Bottone che permette la visualizzazione dell'ultimo aggiornamento di un team, tramite un ID inserito */
        confermavisualizzButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermavisualizzButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermavisualizzButton.addActionListener(ignored -> controller.visualizzaAggiornamento(idTextField, modelList));

        /* Bottone che permette la visualizzazione dei campi da compilare per commentare un aggiornamento */
        commentaUnAggiornamentoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        commentaUnAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        commentaUnAggiornamentoButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaCommento(idTextField, teamIdtextField, aggTextId, modelList, ultimoPulsantePremuto, commentaUnAggiornamentoButton, list)
        );

        /* Bottone di conferma del commento */
        confermacommentoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermacommentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermacommentoButton.addActionListener(ignored ->
            controller.caricaCommento(giudice, aggTextId, commentoTextF));

        /* Bottone che permette la visualizzazione dei campi da compilare per votare un team */
        valutaUnTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        valutaUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        valutaUnTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaValutazione(idTextField, teamIdtextField, commentoTextF, modelList, ultimoPulsantePremuto, valutaUnTeamButton, list));

        /* Bottone per tornare alla schermata principale */
        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.getSchermataGiudice());

        /* Bottone di conferma del voto */
        confermavotoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermavotoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermavotoButton.addActionListener(ignored -> controller.caricaVoto(giudice, idTeamField, votoField));

    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return Il {@link JPanel} principale della schermata.
     */
    public JPanel getMainPanel(){
        return mainPanel;
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
     * Rende visibile il pannello contenente l'elenco (lista) dei team.
     */
    public void setVisiblePanelElenchi() {
        elencoPanel.setVisible(true);
    }

    /**
     * Imposta la visibilità dei componenti relativi al commento di un aggiornamento.
     *
     * @param visible true per rendere visibili i componenti, false per nasconderli.
     */
    public void setVisibilityCommentaAggiornamento(boolean visible){
        setVisibility(visible, commIDT,  aggTextId,  commentoF, commentoTextF, confermacommentoButton);
    }

    /**
     * Imposta la visibilità dei componenti relativi alla valutazione di un team.
     *
     * @param visible true per rendere visibili i componenti, false per nasconderli.
     */
    public void setVisibilityValuta(boolean visible){
        setVisibility(visible, teamIdtextField, idTeamField, votField, votoField, confermavotoButton);
    }

    /**
     * Imposta la visibilità dei componenti relativi alla visualizzazione di un aggiornamento.
     *
     * @param visible true per rendere visibili i componenti, false per nasconderli.
     */
    public void setVisibilityVisualizzaAggiornamento(boolean visible){
        setVisibility(visible, idTextF, idTextField, confermavisualizzButton);
    }
}
