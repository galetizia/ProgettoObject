package controller;

import gui.OrganizzaHackathon;

import implementazionepostgresdao.HackathonDAO;
import model.Hackathon;
import model.Organizzatore;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controller per la gestione della schermata di organizzazione di un hackathon.
 * Permette a un organizzatore di creare una nuova hackathon o visualizzare l'elenco di quelle esistenti.
 */
public class ControllerOrganizzaHackathon {

    /** GUI associata alla schermata di organizzazione hackathon. */
    private final OrganizzaHackathon schermataOrganizzaHackathon;

    /** Controller principale dell'applicazione. */
    private final MainController mainController;

    /** Organizzatore attualmente loggato. */
    private final Organizzatore organizzatore;

    /** DAO per operazioni su hackathon. */
    HackathonDAO hdao = new HackathonDAO();

    /** Formatter per la validazione e parsing delle date. */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Messaggio base per la stringa ripetuta. */
    private static final String ERROREFORMATO = "Errore di formato";

    /**
     * Costruttore del controller.
     *
     * @param mainController il controller principale
     * @param organizzatore  l'organizzatore loggato
     */
    public ControllerOrganizzaHackathon(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.organizzatore = organizzatore;
        this.schermataOrganizzaHackathon = new OrganizzaHackathon(this);
    }

    /**
     * Restituisce il pannello principale della schermata per organizzare Hackathon.
     *
     * @return il JPanel della schermata OrganizzaHackathon
     */
    public JPanel getOrganizzaHackathon() {return schermataOrganizzaHackathon.getMainPanel();}

    /**
     * Popola una lista grafica con gli Hackathon attualmente attivi.
     * Se non ci sono Hackathon, mostra un messaggio informativo.
     *
     * @param listElenchi  la JList da popolare con i nomi degli Hackathon
     * @param modelLista   il modello della lista usato per gestire gli elementi
     * @param panelElenchi il pannello scrollabile contenente la lista
     */
    public void listeHackathon(JList<String> listElenchi, DefaultListModel<String> modelLista, JScrollPane panelElenchi) {
        List<Hackathon> hackathons = hdao.getHackathons();
        if(hackathons.isEmpty()){
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Nessuna Hackathon attiva!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelLista.clear();
        for (Hackathon h : hackathons) {
            modelLista.addElement(h.getNome()+" (ID:"+h.getID()+")");
        }
        listElenchi.revalidate();
        listElenchi.repaint();
        panelElenchi.setVisible(true);
    }

    /**
     * Crea un nuovo Hackathon con i dati forniti dall'utente nei JTextField,
     * valida il formato delle date e dei numeri, e lo salva nel database.
     * Mostra messaggi di errore in caso di dati non validi.
     *
     * @param titoloTextField     campo testo contenente il titolo dell'Hackathon
     * @param sedeTextField       campo testo contenente la sede dell'Hackathon
     * @param problemaTextField   campo testo contenente la descrizione del problema
     * @param dataInizioTextField campo testo contenente la data di inizio (formato dd/MM/yyyy)
     * @param dataFineTextField   campo testo contenente la data di fine (formato dd/MM/yyyy)
     * @param maxIscrTextField    campo testo contenente il numero massimo di iscritti (intero)
     * @param maxDimTeamTextField campo testo contenente la dimensione massima del team (intero)
     */
    public void creaHackathon(JTextField titoloTextField, JTextField sedeTextField, JTextField problemaTextField, JTextField dataInizioTextField, JTextField dataFineTextField, JTextField maxIscrTextField, JTextField maxDimTeamTextField) {

        String titolo = titoloTextField.getText();
        String sede = sedeTextField.getText();
        String problema = problemaTextField.getText();
        LocalDate dataInizio;
        LocalDate dataFine;
        int maxIscr;
        int maxDimTeam;

        if(titolo.isEmpty() || sede.isEmpty() || problema.isEmpty()) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Inserire tutti i campi!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dataInizio = LocalDate.parse(dataInizioTextField.getText(), formatter);
        } catch (DateTimeParseException datE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Data Inizio deve avere formato DD/MM/yyyy", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            dataFine = LocalDate.parse(dataFineTextField.getText(), formatter);
        } catch (DateTimeParseException datE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Data Fine deve avere formato DD/MM/yyyy", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            maxIscr = Integer.parseInt(maxIscrTextField.getText());
        } catch (NumberFormatException numE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Max Iscritti deve essere un numero intero", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            maxDimTeam = Integer.parseInt(maxDimTeamTextField.getText());
        } catch (NumberFormatException numE) {
            JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Il campo Max Dim. Team deve essere un numero intero", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        Hackathon nuovaHackathon = new Hackathon(titolo, sede,  dataInizio, dataFine, problema, maxIscr, maxDimTeam);
        hdao.caricaHackathonDB(nuovaHackathon, organizzatore);
        JOptionPane.showMessageDialog(schermataOrganizzaHackathon.getMainPanel(), "Nuova Hackathon caricata con successo","Success", JOptionPane.INFORMATION_MESSAGE);
        this.indietro();
    }

    /**
     * Indietro.
     */
    public void indietro() {
        mainController.showSchermataOrganizzatore(organizzatore);
    }
}
