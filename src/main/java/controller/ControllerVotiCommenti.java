package controller;

import gui.SchermataVotiCommenti;

import implementazionepostgresdao.GiudiceDAO;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.TeamDAO;
import model.*;

import javax.swing.*;
import java.util.List;

/**
 * Controller per la gestione delle funzionalità di voto e commento da parte di un giudice.
 * Si interfaccia con la GUI {@link SchermataVotiCommenti} e coordina il flusso dati tra
 * i modelli (DAO e oggetti) e l'interfaccia utente.
 */
public class ControllerVotiCommenti {

    /** Riferimento alla schermata GUI associata ai voti e commenti. */
    private final SchermataVotiCommenti votiCommenti;

    /** Riferimento al controller principale dell'applicazione. */
    private final MainController mainController;

    /** Giudice attualmente autenticato. */
    private final Giudice giudice;

    /** DAO per le operazioni sui team. */
    private final TeamDAO tdao = new TeamDAO();

    /** DAO per le operazioni sugli hackathon. */
    private final HackathonDAO hdao = new HackathonDAO();

    /** DAO per le operazioni sui giudici. */
    private final GiudiceDAO gdao = new GiudiceDAO();

    /** Messaggio costante per la stringa ripetuta. */
    private static final String ERROREFORMATO = "Errore di formato";

    /**
     * Costruttore che inizializza il controller.
     *
     * @param mainController il controller principale
     * @param giudice il giudice autenticato
     */
    public ControllerVotiCommenti(MainController mainController, Giudice giudice) {
        this.mainController = mainController;
        this.votiCommenti = new SchermataVotiCommenti(this, giudice);
        this.giudice = giudice;
    }

    /**
     * Carica nella lista i team iscritti all'hackathon associato al giudice.
     *
     * @param giudice il giudice
     * @param listTeams la lista grafica dei team
     * @param modelTeams il modello dati della lista
     */
    public void getTeams(Giudice giudice, JList<String> listTeams, DefaultListModel<String> modelTeams) {
        List<Team> teams = hdao.getTeamByHackathon(giudice.getHackathonID());
        if(teams.isEmpty()) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Nessun Team iscritto!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        modelTeams.clear();

        modelTeams.addElement("---------- Elenco Team -----------");

        for (Team team : teams) {
            modelTeams.addElement(team.getNome()+" (ID: "+team.getId()+")");
        }
        listTeams.revalidate();
        listTeams.repaint();
        votiCommenti.setVisiblePanelElenchi();
    }

    /**
     * Carica nella lista i team che hanno consegnato l'elaborato finale.
     *
     * @param list la lista dei team
     * @param modelList il modello dati della lista
     */
    public void elaboratiFinali(JList<String> list, DefaultListModel<String> modelList) {
        List<Team> teams = gdao.getElaboratiFinaliTeam(giudice.getHackathonID());
        if(teams.isEmpty()) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Nessun Team ha consegnato l'elaborato finale!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        modelList.clear();

        modelList.addElement("Elenco Team con Elaborato Finale");
        for (Team team : teams) {
            modelList.addElement(team.getNome()+" (ID: "+team.getId()+")");
        }
        list.revalidate();
        list.repaint();
        votiCommenti.setVisiblePanelElenchi();
    }


    /**
     * Carica un voto assegnato da un giudice a un team.
     *
     * @param giudice il giudice
     * @param idTeamField campo di testo contenente l'ID del team
     * @param votoField campo di testo contenente il voto assegnato
     */
    public void caricaVoto(Giudice giudice, JTextField idTeamField, JTextField votoField) {

        String idTeamText = idTeamField.getText();
        String votoText = votoField.getText();

        if(idTeamText.isEmpty() || votoText.isEmpty()){
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Compilare tutti i campi!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idTeam;
        int valutazione;
        try {
            idTeam = Integer.parseInt(idTeamText);
            valutazione = Integer.parseInt(votoText);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Entrambi i campi devono essere un numero valido!", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(valutazione < 0 || valutazione > 10) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Il voto deve essere compreso tra 0 e 10!", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(hdao.isClassificaPubblicata(giudice.getHackathonID())){
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Classifica già pubblicata.\nImpossibile valutare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            idTeamField.setText("");
            votoField.setText("");
            return;
        }
        if (!gdao.isElaboratoFinale(idTeam)) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Questo team non ha caricato l'elaborato finale.\nImpossibile valutare.", "Nessun aggiornamento", JOptionPane.WARNING_MESSAGE);
            idTeamField.setText("");
            votoField.setText("");
            return;
        }

        if(!gdao.controlloVotoTeam(idTeam,giudice.getUsername())) {
            Voto voto = new Voto(valutazione, giudice, tdao.getTeamByID(idTeam));
            gdao.caricaVoto(voto);

            idTeamField.setText("");
            votoField.setText("");
            JOptionPane.showConfirmDialog(votiCommenti.getMainPanel(), "Voto caricato con successo!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Hai già votato questo team!", "Error", JOptionPane.ERROR_MESSAGE);
        idTeamField.setText("");
        votoField.setText("");
    }

    /**
     * Carica un commento su un aggiornamento da parte del giudice.
     *
     * @param giudice il giudice
     * @param aggTextId campo contenente l'ID del team
     * @param commentoTextF campo contenente il testo del commento
     */
    public void caricaCommento(Giudice giudice, JTextField aggTextId, JTextField commentoTextF) {
        String idTxt = aggTextId.getText();
        String commentoTxt = commentoTextF.getText();

        if(idTxt.isEmpty() || commentoTxt.isEmpty()){
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Compilare tutti i campi!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idTxt);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "L'ID deve essere un numero valido!", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer idAgg = hdao.getIdAggiornamentoByTeam(id);
        if (idAgg == null) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Questo team non ha ancora caricato aggiornamenti.\nImpossibile commentare.", "Nessun aggiornamento", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!gdao.haCommentatoAggiornamento(id,giudice)){
            gdao.saveCommento(commentoTxt, id, giudice);
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Commento salvato","Success", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Hai già commentato questo team!", "Error", JOptionPane.ERROR_MESSAGE);

    }

    /**
     * Visualizza l'ultimo aggiornamento pubblicato da un team.
     *
     * @param idTextField campo contenente l'ID del team
     * @param modelList il modello della lista da aggiornare
     */
    public void visualizzaAggiornamento(JTextField idTextField, DefaultListModel<String> modelList) {
        String idTxt = idTextField.getText();
        if(idTxt.isEmpty()){
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Inserire un ID di un team!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int teamId;
        try {
            teamId = Integer.parseInt(idTxt);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "L'ID deve essere un numero intero valido", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        String agg = tdao.getUltimoAggiornamento(teamId);
        if(agg == null){
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Nessun aggiornamento presente", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String aHTML = "<html>" + agg.replaceAll("(.{50})", "$1<br>") + "</html>";
        JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), aHTML, "Aggiornamento", JOptionPane.INFORMATION_MESSAGE);

        modelList.clear();
    }

    /**
     * Gestisce la visibilità della sezione per visualizzare l'elenco dei team.
     *
     * @param aggTextId campo commento aggiornamento
     * @param teamIdtextField etichetta per ID team (valutazione)
     * @param idTextField campo ID team per aggiornamento
     * @param modelList modello lista team
     * @param ultimoPulsantePremuto riferimento all’ultimo bottone cliccato
     * @param elencoTeamButton bottone elenco team
     * @param list lista visuale
     * @return il nuovo bottone selezionato
     */
    public JButton visibilitaElencoTeam(JTextField aggTextId, JLabel teamIdtextField, JTextField idTextField, DefaultListModel<String> modelList, JButton ultimoPulsantePremuto, JButton elencoTeamButton, JList<String> list) {

        if(aggTextId.isVisible()) {
            votiCommenti.setVisibilityCommentaAggiornamento(false);
        }

        if(teamIdtextField.isVisible()) {
            votiCommenti.setVisibilityValuta(false);
        }

        if(idTextField.isVisible()){
            votiCommenti.setVisibilityVisualizzaAggiornamento(false);
        }

        if (ultimoPulsantePremuto == elencoTeamButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            getTeams(giudice, list, modelList);
            ultimoPulsantePremuto = elencoTeamButton;
        }
        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità della sezione per visualizzare aggiornamenti.
     *
     * @param aggTextId campo commento aggiornamento
     * @param teamIdtextField etichetta per ID team (valutazione)
     * @param idTextField campo ID team per aggiornamento
     * @param modelList modello lista team
     * @param ultimoPulsantePremuto riferimento all’ultimo bottone cliccato
     * @param visualizzaAggiornamentoDiUnButton bottone per aggiornamento
     * @param list lista visuale
     * @return il nuovo bottone selezionato
     */
    public JButton visibilitaVisualizzaAgg(JTextField aggTextId, JLabel teamIdtextField, JTextField idTextField, DefaultListModel<String> modelList, JButton ultimoPulsantePremuto, JButton visualizzaAggiornamentoDiUnButton, JList<String> list){
        if(aggTextId.isVisible())
            votiCommenti.setVisibilityCommentaAggiornamento(false);

        if(teamIdtextField.isVisible())
            votiCommenti.setVisibilityValuta(false);

        votiCommenti.setVisibilityVisualizzaAggiornamento(!idTextField.isVisible());


        if (ultimoPulsantePremuto == visualizzaAggiornamentoDiUnButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            getTeams(giudice, list, modelList);
            ultimoPulsantePremuto = visualizzaAggiornamentoDiUnButton;
        }

        votiCommenti.getMainPanel().revalidate();
        votiCommenti.getMainPanel().repaint();
        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità della sezione per commentare un aggiornamento.
     *
     * @param idTextField campo ID per visualizzare aggiornamento
     * @param teamIdtextField etichetta per ID team (valutazione)
     * @param aggTextId campo ID team per commento
     * @param modelList modello lista team
     * @param ultimoPulsantePremuto riferimento all’ultimo bottone cliccato
     * @param commentaUnAggiornamentoButton bottone per commentare
     * @param list lista visuale
     * @return il nuovo bottone selezionato
     */
    public JButton visibilitaCommento(JTextField idTextField, JLabel teamIdtextField, JTextField aggTextId,DefaultListModel<String> modelList, JButton ultimoPulsantePremuto, JButton commentaUnAggiornamentoButton, JList<String> list){

        if(idTextField.isVisible()){
            votiCommenti.setVisibilityVisualizzaAggiornamento(false);
        }

        if(teamIdtextField.isVisible()) {
            votiCommenti.setVisibilityValuta(false);
        }

        votiCommenti.setVisibilityCommentaAggiornamento(!aggTextId.isVisible());

        if (ultimoPulsantePremuto == commentaUnAggiornamentoButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            getTeams(giudice, list, modelList);
            ultimoPulsantePremuto = commentaUnAggiornamentoButton;
        }
        return ultimoPulsantePremuto;

    }

    /**
     * Gestisce la visibilità della sezione per valutare un team.
     *
     * @param idTextField campo ID per visualizzare aggiornamento
     * @param teamIdtextField etichetta per ID team (valutazione)
     * @param commentoTextF campo commento aggiornamento
     * @param modelList modello lista team
     * @param ultimoPulsantePremuto riferimento all’ultimo bottone cliccato
     * @param valutaUnTeamButton bottone per valutare un team
     * @param list lista visuale
     * @return il nuovo bottone selezionato
     */
    public JButton visibilitaValutazione(JTextField idTextField, JLabel teamIdtextField, JTextField commentoTextF,DefaultListModel<String> modelList, JButton ultimoPulsantePremuto, JButton valutaUnTeamButton, JList<String> list){

        if(idTextField.isVisible()) {
            votiCommenti.setVisibilityVisualizzaAggiornamento(false);
        }
        if(commentoTextF.isVisible()) {
            votiCommenti.setVisibilityCommentaAggiornamento(false);
        }

        votiCommenti.setVisibilityValuta(!teamIdtextField.isVisible());

        if (ultimoPulsantePremuto == valutaUnTeamButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            elaboratiFinali(list, modelList);
            ultimoPulsantePremuto = valutaUnTeamButton;
        }
        return ultimoPulsantePremuto;
    }


    /**
     * Restituisce il pannello principale della schermata voti e commenti.
     *
     * @return il pannello principale
     */
    public JPanel getSchermataVotiCommenti() {
        return votiCommenti.getMainPanel();
    }

    /**
     * Mostra la schermata iniziale del giudice nel main controller.
     */
    public void getSchermataGiudice() {mainController.showSchermataGiudice(giudice);}

}
