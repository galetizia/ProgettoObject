package controller;

import gui.GestioneHackathon;
import implementazionepostgresdao.*;
import model.Giudice;
import model.Organizzatore;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.List;

/**
 * Controller che gestisce la logica di controllo relativa alla schermata di gestione dell'Hackathon
 * per un organizzatore.
 * Coordina operazioni quali visualizzazione e gestione di team, utenti, giudici, aggiunta e rimozione di partecipanti,
 * e la terminazione anticipata dell'Hackathon.
 */
public class ControllerGestioneHackathon {

    /** Riferimento alla schermata GUI associata alla Gestione Hackathon. */
    private final GestioneHackathon schermataGestioneHackathon;

    /** Riferimento al controller principale dell'applicazione. */
    private final MainController mainController;

    /** Organizzatore attualmente autenticato. */
    private final Organizzatore organizzatoreLoggato;

    /** DAO per le operazioni sugli organizzatori. */
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    /** DAO per le operazioni sui team. */
    private final TeamDAO tdao = new TeamDAO();

    /** DAO per le operazioni sugli hackathon. */
    HackathonDAO hdao = new HackathonDAO();

    /** DAO per le operazioni sugli utenti. */
    UtenteDAO udao = new UtenteDAO();

    /** DAO per le operazioni sui giudici. */
    GiudiceDAO gdao = new GiudiceDAO();

    /** Messaggi costanti per le stringhe ripetute. */
    private static final String ERROR = "Error";
    private static final String CONFERMA = "Conferma";
    private static final String INFORMATION = "INFORMATION";
    private static final String ATTENZIONE = "Attenzione";
    private static final String NONTROVATO = " non trovato";

    /**
     * Costruisce un nuovo Controller per la schermata di Gestione Hackathon.
     *
     * @param mainController il controller principale dell'applicazione
     * @param organizzatore  l'organizzatore attualmente loggato
     */
    public ControllerGestioneHackathon(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataGestioneHackathon = new GestioneHackathon(this, organizzatore);
        this.organizzatoreLoggato = organizzatore;
    }

    /**
     * Termina anticipatamente l'Hackathon associato all'organizzatore.
     * Aggiorna lo stato e ritorna alla schermata organizzatore.
     */
    public void terminaHackathon() {
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler terminare l'Hackathon prima della data finale?", CONFERMA, JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            odao.terminaHackathon(organizzatoreLoggato.getHackathonID());
            organizzatoreLoggato.setHackathonID(null);
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Hai terminato l'Hackathon con successo.");
            mainController.showSchermataOrganizzatore(organizzatoreLoggato);
        }
    }

    /**
     * Mostra nella lista grafica i team iscritti all'Hackathon dell'organizzatore.
     * Visualizza un messaggio se non ci sono team.
     *
     * @param list          la JList da popolare con i team
     * @param modelList     il modello della lista da aggiornare
     * @param panel         il pannello che contiene la lista da rendere visibile
     * @param organizzatore l'organizzatore loggato
     */
    public void mostraTeams(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel, Organizzatore organizzatore) {
        List<Team> teams = hdao.getTeamByHackathon(organizzatore.getHackathonID());
        modelList.clear();
        if(teams.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti team",INFORMATION, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelList.addElement("--------------------------- Elenco Team ---------------------------");

        for (Team t : teams) {
            modelList.addElement(t.getNome() +" (ID:"+t.getId()+")");
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);

    }

    /**
     * Mostra nella lista grafica gli utenti iscritti all'Hackathon dell'organizzatore.
     * Visualizza un messaggio se non ci sono utenti.
     *
     * @param list          la JList da popolare con gli utenti
     * @param modelList     il modello della lista da aggiornare
     * @param panel         il pannello che contiene la lista da rendere visibile
     * @param organizzatore l'organizzatore loggato
     */
    public void mostraUtenti(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel, Organizzatore organizzatore){
        List<Utente> users = hdao.getUtenti(organizzatore.getHackathonID());
        modelList.clear();
        if(users.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti utenti",INFORMATION, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelList.addElement("-------------------------- Elenco Utenti --------------------------");
        for (Utente u : users) {
            modelList.addElement(u.getUsername());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
    }

    /**
     * Mostra nella lista grafica i giudici associati all'Hackathon dell'organizzatore.
     * Visualizza un messaggio se non ci sono giudici.
     *
     * @param list          la JList da popolare con i giudici
     * @param modelList     il modello della lista da aggiornare
     * @param panel         il pannello che contiene la lista da rendere visibile
     * @param organizzatore l'organizzatore loggato
     */
    public void mostraGiudici(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel, Organizzatore organizzatore){
        List<Giudice> giudici = hdao.getGiudici(organizzatore.getHackathonID());
        modelList.clear();

        if(giudici.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti giudici",INFORMATION, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelList.addElement("-------------------------- Elenco Giudici -------------------------");
        for (Giudice g : giudici) {
            modelList.addElement(g.getUsername());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
    }

    /**
     * Mostra nella lista grafica i potenziali giudici, ossia utenti che possono essere giudici
     * se non fanno parte di un team.
     * Visualizza un messaggio se non ci sono potenziali giudici e ritorna false.
     *
     * @param list      la JList da popolare con i potenziali giudici
     * @param modelList il modello della lista da aggiornare
     * @param panel     il pannello che contiene la lista da rendere visibile
     * @return true se ci sono potenziali giudici da mostrare, false altrimenti
     */
    public boolean mostraPotenzialiGiudici(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel){
        List<Utente> potenzialiGiudici = hdao.getPotenzialiGiudici();
        modelList.clear();

        if(potenzialiGiudici.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Non sono presenti potenziali giudici da inserire",INFORMATION, JOptionPane.INFORMATION_MESSAGE);
            return false ;
        }
        modelList.addElement("-------------------- Elenco Potenziali Giudici --------------------");
        for (Utente pG : potenzialiGiudici) {
            modelList.addElement(pG.getUsername());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
        return true;
    }

    /**
     * Aggiunge un giudice all'Hackathon in base allo username fornito.
     * Se lo username è vuoto o non valido, mostra un errore.
     *
     * @param usernameTextField campo di testo contenente l'username del giudice da aggiungere
     * @param organizzatore     l'organizzatore corrente
     */
    public void aggiungiGiudice(JTextField usernameTextField, Organizzatore organizzatore) {
        String usern = usernameTextField.getText();
        if(usern.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire username!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(hdao.isClassificaPubblicata(organizzatore.getHackathonID())){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Classifica già pubblicata!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            usernameTextField.setText("");
            return;
        }
        if(!odao.aggiungiGiudice(usern, organizzatore.getHackathonID())){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "L'utente non esiste/è membro di un team!" , ERROR, JOptionPane.ERROR_MESSAGE);
            usernameTextField.setText("");
            return;
        }
        JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Aggiunto giudice!" , "Success", JOptionPane.INFORMATION_MESSAGE);
        usernameTextField.setText("");

    }

    /**
     * Gestisce la rimozione di utenti, giudici o team in base alle checkbox selezionate
     * ed all'ID o username inserito.
     *
     * @param utenteCheckBox  checkbox per la rimozione utenti
     * @param giudiceCheckBox checkbox per la rimozione giudici
     * @param teamCheckBox    checkbox per la rimozione team
     * @param idTextField     campo testo contenente l'ID o username da rimuovere
     * @param organizzatore   l'organizzatore corrente
     */
    public void gestioneRimozioni(JCheckBox utenteCheckBox, JCheckBox giudiceCheckBox,JCheckBox teamCheckBox, JTextField idTextField,Organizzatore organizzatore){
        if(!utenteCheckBox.isSelected() && !giudiceCheckBox.isSelected() && !teamCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire un ruolo!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(utenteCheckBox.isSelected()) rimozioneUtente(idTextField, organizzatore.getHackathonID());
        if(giudiceCheckBox.isSelected()) rimozioneGiudice(idTextField, organizzatore.getHackathonID());
        if(teamCheckBox.isSelected()) rimozioneTeam(idTextField, organizzatore.getHackathonID());
    }

    /**
     * Rimuove un utente dal sistema dopo aver verificato la sua esistenza e l'appartenenza all'hackathon.
     *
     * @param idTextField campo di testo contenente lo username dell'utente da rimuovere
     * @param hackathonID id dell'hackathon corrente
     */
    public void rimozioneUtente(JTextField idTextField, Integer hackathonID) {
        String username = idTextField.getText();
        if(username.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire Username!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        Utente u = udao.findUtenteByUsername(idTextField.getText());

        if(u == null) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Utente "+username+NONTROVATO , ERROR, JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }
        if(!u.getHackathonID().equals(hackathonID)){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "L'utente non è iscritto alla sua Hackathon" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler rimuovere "+username+"?" ,
                CONFERMA, JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            if(!odao.removeUtente(username, organizzatoreLoggato)){
                JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Impossibile rimuovere\n Classifica già pubblicata" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
            }
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Utente rimosso.", "Success", JOptionPane.INFORMATION_MESSAGE);
            idTextField.setText("");


        }
    }

    /**
     * Declassa un giudice ad utente rimuovendolo dal ruolo di giudice.
     *
     * @param idTextField campo di testo contenente lo username del giudice da rimuovere
     * @param hackathonID id dell'hackathon corrente
     */
    public void rimozioneGiudice(JTextField idTextField, Integer hackathonID) {
        String username = idTextField.getText();
        if(username.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire Username!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        Giudice g = gdao.findGiudiceByUsername(username);

        if(g == null) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Giudice "+username+NONTROVATO , ERROR, JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }
        if(!g.getHackathonID().equals(hackathonID)){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Questo giudice non è presente nella sua Hackathon!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler declassare "+username +"?" ,
                CONFERMA, JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            if(!odao.removeGiudice(username, organizzatoreLoggato)){
                JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Impossibile rimuovere\n Classifica già pubblicata" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Giudice declassato ad utente.", "Success", JOptionPane.INFORMATION_MESSAGE);
            idTextField.setText("");
        }
    }


    /**
     * Rimuove un team dall'hackathon dopo aver verificato l'esistenza e l'appartenenza all' hackathon.
     *
     * @param idTextField campo di testo contenente l'ID del team da rimuovere
     * @param hackathonID id dell'hackathon corrente
     */
    public void rimozioneTeam(JTextField idTextField, Integer hackathonID){
        String idTxt = idTextField.getText();

        if(idTxt.isEmpty()){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Inserire ID!" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id;
        try{
            id = Integer.parseInt(idTxt);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "L'ID deve essere un numero intero valido", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }

        Team t = tdao.getTeamByID(id);
        if(t == null) {
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Team "+idTextField.getText()+NONTROVATO , ERROR, JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");
            return;
        }
        if(!t.getHackathonID().equals(hackathonID)){
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Questo Team non è iscritto alla sua Hackathon" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        int conferma = JOptionPane.showConfirmDialog(schermataGestioneHackathon.getMainPanel(), "Sei sicuro di voler rimuovere il Team "+idTextField.getText()+"?" ,
                CONFERMA, JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            if(!odao.removeTeam(id, hackathonID, true)){
                JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Impossibile rimuovere\n Classifica già pubblicata" , ATTENZIONE, JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Team rimosso.", "Success", JOptionPane.INFORMATION_MESSAGE);
            idTextField.setText("");
        }
    }

    /**
     * Restituisce il pannello principale della schermata gestione hackathon.
     *
     * @return il pannello principale (JPanel) della schermata di gestione hackathon
     */
    public JPanel getGestioneHackathon() {
        return schermataGestioneHackathon.getMainPanel();
    }

    /**
     * Mostra la schermata principale dell'organizzatore.
     */
    public void getSchermataOrganizzatore() {
        mainController.showSchermataOrganizzatore(organizzatoreLoggato);
    }

    /**
     * Gestisce la visibilità e l'aggiornamento della sezione per aggiungere giudici.
     *
     * @param idLabel                etichetta identificativa
     * @param list                   lista dei potenziali giudici
     * @param modelList              modello della lista
     * @param panelHackathon         pannello contenente l'hackathon
     * @param username               etichetta dello username
     * @param ultimoPulsantePremuto  riferimento all'ultimo pulsante premuto
     * @param aggiungiGiudiceButton  pulsante per aggiungere un giudice
     * @return il pulsante considerato come ultimo premuto dopo l'operazione
     */
    public JButton visibilitaAggGiudice(JLabel idLabel, JList<String> list, DefaultListModel<String> modelList, JScrollPane panelHackathon, JLabel username, JButton ultimoPulsantePremuto, JButton aggiungiGiudiceButton){
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }
        if(!mostraPotenzialiGiudici(list, modelList, panelHackathon)){
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
            modelList.clear();
            return null;
        }
        boolean check=!username.isVisible();
        schermataGestioneHackathon.setVisibilityAggGiudice(check);

        if (ultimoPulsantePremuto == aggiungiGiudiceButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            ultimoPulsantePremuto = aggiungiGiudiceButton;
        }
        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità e l'aggiornamento della lista dei giudici.
     *
     * @param username              etichetta dello username
     * @param idLabel               etichetta identificativa
     * @param ultimoPulsantePremuto riferimento all'ultimo pulsante premuto
     * @param elencoGiudiciButton   pulsante per mostrare elenco giudici
     * @param list                  lista da aggiornare
     * @param modelList             modello della lista
     * @param organizzatore         organizzatore corrente
     * @return il pulsante considerato come ultimo premuto dopo l'operazione
     */
    public JButton visibilitaElencoGiudici(JLabel username, JLabel idLabel, JButton ultimoPulsantePremuto, JButton elencoGiudiciButton, JList<String> list, DefaultListModel<String> modelList, Organizzatore organizzatore){
        if(username.isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }

        if (ultimoPulsantePremuto == elencoGiudiciButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            mostraGiudici(list, modelList, schermataGestioneHackathon.getPanelHackathon(), organizzatore);
            ultimoPulsantePremuto = elencoGiudiciButton;
        }
        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità e l'aggiornamento della lista degli utenti.
     *
     * @param username              etichetta dello username
     * @param idLabel               etichetta identificativa
     * @param ultimoPulsantePremuto riferimento all'ultimo pulsante premuto
     * @param elencoUtentiButton    pulsante per mostrare elenco utenti
     * @param list                  lista da aggiornare
     * @param modelList             modello della lista
     * @param organizzatore         organizzatore corrente
     * @return il pulsante considerato come ultimo premuto dopo l'operazione
     */
    public JButton visibilitaElencoUtenti(JLabel username, JLabel idLabel, JButton ultimoPulsantePremuto, JButton elencoUtentiButton, JList<String> list, DefaultListModel<String> modelList, Organizzatore organizzatore){
        if(username.isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }
        if (ultimoPulsantePremuto == elencoUtentiButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            mostraUtenti(list, modelList, schermataGestioneHackathon.getPanelHackathon(), organizzatore);
            ultimoPulsantePremuto = elencoUtentiButton;
        }
        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità e l'aggiornamento della lista dei team.
     *
     * @param username              etichetta dello username
     * @param idLabel               etichetta identificativa
     * @param ultimoPulsantePremuto riferimento all'ultimo pulsante premuto
     * @param elencoTeamsButton     pulsante per mostrare elenco team
     * @param list                  lista da aggiornare
     * @param modelList             modello della lista
     * @param organizzatore         organizzatore corrente
     * @return il pulsante considerato come ultimo premuto dopo l'operazione
     */
    public JButton visibilitaElencoTeam(JLabel username, JLabel idLabel, JButton ultimoPulsantePremuto, JButton elencoTeamsButton, JList<String> list, DefaultListModel<String> modelList, Organizzatore organizzatore){
        if(username.isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }
        if(idLabel.isVisible()) {
            schermataGestioneHackathon.setVisibilityRimozione(false);
        }
        if (ultimoPulsantePremuto == elencoTeamsButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            mostraTeams(list, modelList, schermataGestioneHackathon.getPanelHackathon(), organizzatore);
            ultimoPulsantePremuto = elencoTeamsButton;
        }
        return ultimoPulsantePremuto;
    }

    /**
     * Gestisce la visibilità e l'aggiornamento del pannello per la rimozione di utenti, giudici o team.
     *
     * @param idLabel                          etichetta identificativa
     * @param ultimoPulsantePremuto            riferimento all'ultimo pulsante premuto
     * @param rimozioneUtenteGiudiceTeamButton pulsante per la rimozione di utenti, giudici o team
     * @param modelList                        modello della lista
     * @param utenteCheckBox                   checkbox per selezionare gli utenti
     * @param giudiceCheckBox                  checkbox per selezionare i giudici
     * @param teamCheckBox                     checkbox per selezionare i team
     * @return il pulsante considerato come ultimo premuto dopo l'operazione
     */
    public JButton visibilitaRimozione(JLabel idLabel, JButton ultimoPulsantePremuto, JButton rimozioneUtenteGiudiceTeamButton, DefaultListModel<String> modelList, JCheckBox utenteCheckBox, JCheckBox giudiceCheckBox, JCheckBox teamCheckBox){
        if(schermataGestioneHackathon.getUsernameLabel().isVisible()) {
            schermataGestioneHackathon.setVisibilityAggGiudice(false);
        }

        schermataGestioneHackathon.setVisibilityRimozione(!idLabel.isVisible());

        if(!utenteCheckBox.isSelected() && !giudiceCheckBox.isSelected() && !teamCheckBox.isSelected())
            modelList.clear();

        if (ultimoPulsantePremuto == rimozioneUtenteGiudiceTeamButton) {
            modelList.clear();
            ultimoPulsantePremuto = null;
        } else {
            ultimoPulsantePremuto = rimozioneUtenteGiudiceTeamButton;
        }
        schermataGestioneHackathon.getMainPanel().revalidate();
        schermataGestioneHackathon.getMainPanel().repaint();
        return ultimoPulsantePremuto;
    }

}
