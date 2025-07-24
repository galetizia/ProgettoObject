package controller;

import gui.IscrizioneTeam;

import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.TeamDAO;
import implementazionepostgresdao.UtenteDAO;
import model.Hackathon;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.List;

/**
 * Controller per la gestione dell'iscrizione ai team in un hackathon.
 * Permette all'utente di creare team, iscriversi a team esistenti, visualizzare hackathon attive
 * e gestire la visibilità delle relative interfacce grafiche.
 */
public class ControllerIscrizioneTeam {

    /** Riferimento alla schermata GUI associata a IscrizioneTeam. */
    private final IscrizioneTeam schermataIscrizioneTeam;

    /** Riferimento al controller principale dell'applicazione. */
    private final MainController mainController;

    /** Utente attualmente autenticato. */
    private final Utente utente;

    /** DAO per le operazioni sui team. */
    private final TeamDAO tdao = new TeamDAO();

    /** DAO per le operazioni sugli hackathon. */
    private final HackathonDAO hdao = new HackathonDAO();

    /** DAO per le operazioni sugli Utenti. */
    private final UtenteDAO udao = new UtenteDAO();

    /** Messaggi costanti per le stringhe ripetute. */
    private static final String ERROR = "Error";
    private static final String ERROREFORMATO = "Errore di formato";
    private static final String ATTENZIONE = "Attenzione";

    /**
     * Costruttore del controller per la schermata di iscrizione team.
     *
     * @param mainController Il controller principale dell'applicazione.
     * @param utente         L'utente attualmente autenticato.
     */
    public ControllerIscrizioneTeam(MainController mainController, Utente utente) {
        this.mainController = mainController;
        this.schermataIscrizioneTeam = new IscrizioneTeam(this);
        this.utente=utente;
    }

    /**
     * Crea un nuovo team e lo associa all'hackathon selezionato.
     * Verifica che i campi siano corretti, l'hackathon esista e ci sia ancora spazio per nuovi team.
     *
     * @param nomeNuovoTeamTextField Campo di input per il nome del team.
     * @param creaTeamIDTextField    Campo di input per l'ID dell'hackathon.
     */
    public void creazioneTeam(JTextField nomeNuovoTeamTextField, JTextField creaTeamIDTextField) {

        String nome = nomeNuovoTeamTextField.getText();
        String idHackathon = creaTeamIDTextField.getText();

        if(nome.isEmpty() || idHackathon.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserire tutti i campi!", ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id;
        try{
            id = Integer.parseInt(idHackathon);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID deve essere un numero intero valido", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(hdao.getHackathonByID(id) == null) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "ID hackathon non valido, inserire un hackathon esistente!" , ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Team> teams = hdao.getTeamByHackathon(id);

        if(teams.size() >= hdao.getMaxDimTeam(id)) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Raggiunto numero massimo di Team!" , ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        Team t = new Team(nome,id);
        tdao.caricaTeamNelDB(t,utente);
        JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Nuovo Team caricato con successo","Success", JOptionPane.INFORMATION_MESSAGE);
        showUtente();
    }

    /**
     * Mostra tutte le hackathon attive, aggiornando la lista con i relativi dati.
     *
     * @param list      La JList in cui mostrare i dati.
     * @param modelList Il modello della lista da aggiornare.
     */
    public void visualizzaHackathonAttive(JList<String> list, DefaultListModel<String> modelList) {
        List<Hackathon> hackathons = hdao.getHackathons();
        modelList.clear();

        modelList.addElement("------- Elenco Hackathon Attive --------");

        for (Hackathon h : hackathons) {
            List<Team> teams = hdao.getTeamByHackathon(h.getID());
            modelList.addElement(h.getNome()+" (ID: "+h.getID()+") "+"("+teams.size()+"/"+hdao.getMaxIscritti(h.getID())+")");
        }

        list.revalidate();
        list.repaint();
        schermataIscrizioneTeam.setVisiblePanelElenchi();
    }

    /**
     * Gestisce la visibilità del pannello hackathon attive.
     * Mostra o nasconde la lista a seconda dello stato precedente del pulsante.
     *
     * @param list                      La JList delle hackathon.
     * @param modelList                 Il modello della lista.
     * @param confermaListaTeamButton   Bottone per confermare lista team.
     * @param confermaIscrTramiteButton Bottone per confermare iscrizione tramite ID team.
     * @param panelIscrizione           Pannello di creazione team.
     * @param hackathonAttiveButton     Bottone premuto per mostrare hackathon.
     * @param ultimoPulsantePremuto     Ultimo bottone premuto.
     * @return Il bottone attualmente attivo, oppure null se deselezionato.
     */
    public JButton visibilitaHackathonAttive(JList<String> list, DefaultListModel<String> modelList, JButton confermaListaTeamButton, JButton confermaIscrTramiteButton, JPanel panelIscrizione, JButton hackathonAttiveButton, JButton ultimoPulsantePremuto) {

        if(confermaListaTeamButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityListaTeam(false);
        }

        if(confermaIscrTramiteButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityIscrivitiTeam(false);
        }

        if(panelIscrizione.isVisible()) {
            panelIscrizione.setVisible(false);
        }

        if(ultimoPulsantePremuto == hackathonAttiveButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        }else{
            visualizzaHackathonAttive(list, modelList);
            ultimoPulsantePremuto = hackathonAttiveButton;
        }

        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità della lista dei team per un hackathon.
     * Mostra o nasconde la lista a seconda dello stato precedente del pulsante.
     *
     * @param list                      La JList dei team.
     * @param modelList                 Il modello della lista.
     * @param listaTeamButton           Bottone per mostrare la lista team.
     * @param confermaIscrTramiteButton Bottone per confermare iscrizione tramite ID.
     * @param panelIscrizione           Pannello di creazione team.
     * @param hackathonIDTextField      Campo ID hackathon per visualizzare team.
     * @param ultimoPulsantePremuto     Ultimo bottone premuto.
     * @return Il bottone attivo o null se deselezionato.
     */
    public JButton visibilitaListaTeam(JList<String> list, DefaultListModel<String> modelList, JButton listaTeamButton, JButton confermaIscrTramiteButton, JPanel panelIscrizione, JTextField hackathonIDTextField, JButton ultimoPulsantePremuto) {

        if(confermaIscrTramiteButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityIscrivitiTeam(false);
        }

        if(panelIscrizione.isVisible()) {
            panelIscrizione.setVisible(false);
        }

        schermataIscrizioneTeam.setVisibilityListaTeam(!hackathonIDTextField.isVisible());

        if(ultimoPulsantePremuto == listaTeamButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        }else{
            visualizzaHackathonAttive(list, modelList);
            ultimoPulsantePremuto = listaTeamButton;
        }

        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità del pannello di iscrizione a un team tramite ID.
     *
     * @param confermaListaTeamButton Bottone per confermare lista team.
     * @param iscrivitiAdUnTeamButton Bottone per iscriversi ad un team.
     * @param panelIscrizione         Pannello per creare un team.
     * @param teamIDTextField         Campo per inserire l'ID del team.
     * @param ultimoPulsantePremuto   Ultimo bottone premuto.
     * @return Il bottone attivo o null se deselezionato.
     */
    public JButton visibilitaIscrivitiTeam(JButton confermaListaTeamButton, JButton iscrivitiAdUnTeamButton, JPanel panelIscrizione, JTextField teamIDTextField, JButton ultimoPulsantePremuto) {

        if(confermaListaTeamButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityListaTeam(false);
        }

        if(panelIscrizione.isVisible()) {
            panelIscrizione.setVisible(false);
        }

        schermataIscrizioneTeam.setVisibilityIscrivitiTeam(!teamIDTextField.isVisible());

        if(ultimoPulsantePremuto == iscrivitiAdUnTeamButton)
            ultimoPulsantePremuto = null;
        else
            ultimoPulsantePremuto = iscrivitiAdUnTeamButton;


        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità del pannello per la creazione di un team.
     *
     * @param modelList                 Il modello della lista da svuotare.
     * @param creaTeamButton            Bottone premuto per creare un team.
     * @param confermaListaTeamButton   Bottone per confermare lista team.
     * @param confermaIscrTramiteButton Bottone per confermare iscrizione tramite ID team.
     * @param panelIscrizione           Il pannello di iscrizione.
     * @param ultimoPulsantePremuto     Ultimo bottone premuto.
     * @return Il bottone attivo o null se deselezionato.
     */
    public JButton visibilitaCreaTeam(DefaultListModel<String> modelList, JButton creaTeamButton, JButton confermaListaTeamButton, JButton confermaIscrTramiteButton, JPanel panelIscrizione, JButton ultimoPulsantePremuto){

        modelList.clear();

        if(confermaListaTeamButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityListaTeam(false);
        }
        if(confermaIscrTramiteButton.isVisible()) {
            schermataIscrizioneTeam.setVisibilityIscrivitiTeam(false);
        }

        if(ultimoPulsantePremuto == creaTeamButton) {
            ultimoPulsantePremuto = null;
            panelIscrizione.setVisible(false);
        }
        else {
            panelIscrizione.setVisible(true);
            ultimoPulsantePremuto = creaTeamButton;
        }

        return ultimoPulsantePremuto;
    }

    /**
     * Iscrive l'utente corrente a un team esistente dato l'ID.
     * Controlla che il team esista e che ci sia ancora posto disponibile.
     *
     * @param idTeamTextField Campo di input con l'ID del team.
     */
    public void iscrizioneTeam(JTextField idTeamTextField) {

        String idTeamTxt = idTeamTextField.getText();

        if(idTeamTxt.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserisci un ID di un team!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id= Integer.parseInt(idTeamTxt);
            Team t = tdao.getTeamByID(id);
            if(t == null) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Il Team inserito non esiste!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Utente> teams = tdao.membriTeam(id);

            if(teams.size() >= (hdao.getMaxDimTeam(hdao.getHackathonByTeam(id)))) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Team Pieno" , ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                udao.changeIDTeam(t, utente);
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Sei stato aggiunto al Team: "+t.getNome(), "Success", JOptionPane.INFORMATION_MESSAGE);
                showUtente();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID del Team deve essere un numero intero.", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Visualizza i team iscritti ad una determinata hackathon, dato il suo ID.
     *
     * @param idHackathonTextField Campo con ID dell'hackathon.
     * @param list                 Lista da aggiornare con i team.
     * @param modelList            Modello della lista.
     */
    public void visualizzaTeamHackathon(JTextField idHackathonTextField, JList<String> list,DefaultListModel<String> modelList) {
        String idHackathonTxt = idHackathonTextField.getText();
        if(idHackathonTxt.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserisci un ID Hackathon!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int hackathonID = Integer.parseInt(idHackathonTxt);
            List<Team> teams = hdao.getTeamByHackathon(hackathonID);
            modelList.clear();

            if(teams.isEmpty()) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Nessun Team iscritto a quest Hackathon!", ATTENZIONE, JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelList.addElement("---------- Elenco Team -----------");

            for (Team t : teams) {
                List<Utente> membri = tdao.membriTeam(t.getId());
                modelList.addElement(t.getNome()+" (ID: "+t.getId()+") "+"("+membri.size()+"/"+hdao.getMaxDimTeam(hackathonID)+")");
            }

            list.revalidate();
            list.repaint();
            schermataIscrizioneTeam.setVisiblePanelElenchi();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID Hackathon deve essere un numero valido.", ERROREFORMATO, JOptionPane.ERROR_MESSAGE);
        }


    }

    /**
     * Restituisce il pannello principale della schermata di iscrizione team.
     *
     * @return Il JPanel della schermata.
     */
    public JPanel getIscrizioneTeam() {return schermataIscrizioneTeam.getMainPanel();}

    /**
     * Mostra la schermata principale dell'utente.
     */
    public void showUtente() {mainController.showSchermataUtente(utente); }

}
