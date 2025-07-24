package gui;

import controller.ControllerTeamSchermataUtente;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata utente dedicata alla gestione del team.
 * Consente all'utente di visualizzare i membri del proprio team, caricare aggiornamenti,
 * abbandonare il team, e visualizzare l'ultimo aggiornamento disponibile.
 * <p>
 * Questa classe interagisce con {@link ControllerTeamSchermataUtente} per delegare la logica applicativa.
 * </p>
 */
public class TeamSchermataUtente {

    /** Tutte le componenti di design*/
    private JPanel mainPanel;
    private JButton membriButton;
    private JButton abbandonaButton;
    private JList<String> listaUtenti;
    private JScrollPane panelUtenti;
    private JButton indietroButton;
    private JLabel teamLabelName;
    private JButton caricaAggiornamentoButton;
    private JTextField nomeTextField;
    private JButton confermaButton;
    private JTextField documentoTextField;
    private JButton visualizzaUltimoAggiornamentoButton;
    private JLabel nome;
    private JLabel documento;
    private JCheckBox elaboratoFinaleCheckBox;
    private final DefaultListModel<String> modelListUtenti;

    /** Ultimo pulsante premuto dall’utente, usato per la gestione dello stato della GUI. */
    private JButton ultimoPulsantePremuto = null;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore che inizializza tutti i componenti della GUI e imposta i listener per i vari pulsanti.
     *
     * @param controller Il controller associato alla schermata.
     * @param team Il team dell’utente attualmente loggato.
     * @param utente L’utente attualmente loggato.
     */
    public TeamSchermataUtente(ControllerTeamSchermataUtente controller, Team team, Utente utente) {

        /* DAO per l'entità hackathon, usato per operazioni collegate */
        final HackathonDAO hdao = new HackathonDAO();

        mainPanel.setPreferredSize(new Dimension(600,400));

        teamLabelName.setFont(new Font(SEGOEUI, Font.BOLD, 38));
        teamLabelName.setText("Team: " + team.getNome());

        modelListUtenti = new DefaultListModel<>();
        listaUtenti.setModel(modelListUtenti);

        nomeTextField.setVisible(false);
        documentoTextField.setVisible(false);
        confermaButton.setVisible(false);
        nome.setVisible(false);
        documento.setVisible(false);
        elaboratoFinaleCheckBox.setVisible(false);

        /*bottone per visualizzare i membri del proprio team*/
        membriButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        membriButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        membriButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visualizzaMembri(team, listaUtenti, modelListUtenti, membriButton, ultimoPulsantePremuto));

        /*bottone per visualizzare i campi da compilare per caricare un aggiornamento*/
        caricaAggiornamentoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        caricaAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        caricaAggiornamentoButton.addActionListener(ignored -> {

            if(hdao.isClassificaPubblicata(utente.getHackathonID())) {
                JOptionPane.showMessageDialog(mainPanel, "Classifica già pubblicata.\nImpossibile inserire nuovi aggiornamenti.", "Nessun aggiornamento", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!nome.isVisible()) {
                nomeTextField.setVisible(true);
                documentoTextField.setVisible(true);
                confermaButton.setVisible(true);
                nome.setVisible(true);
                documento.setVisible(true);
                elaboratoFinaleCheckBox.setVisible(true);
                return;
            }
            nomeTextField.setVisible(false);
            documentoTextField.setVisible(false);
            confermaButton.setVisible(false);
            nome.setVisible(false);
            documento.setVisible(false);
        });
        /*bottone per confermare il caricamento dell'aggiornamento*/
        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> controller.caricaAggiornamento(utente, nomeTextField, documentoTextField, elaboratoFinaleCheckBox));

        /*bottone che permette di abbandonare il team*/
        abbandonaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        abbandonaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        abbandonaButton.addActionListener(ignored -> controller.abbandonaTeam(utente));

        /*bottone che porta alla schermata principale di utente*/
        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.showSchermataUtente(utente));

        /*bottone per visualizzare l'ultimo aggiornamento del proprio team*/
        visualizzaUltimoAggiornamentoButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        visualizzaUltimoAggiornamentoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizzaUltimoAggiornamentoButton.addActionListener(ignored -> controller.visualizzaAggiornamento(utente));
    }

    /**
     * Rende visibile il pannello contenente la lista degli utenti.
     */
    public void setVisiblePanelUtenti() {
        panelUtenti.setVisible(true);
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
