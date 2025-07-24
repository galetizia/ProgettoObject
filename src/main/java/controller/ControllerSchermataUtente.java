package controller;

import gui.SchermataUtente;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;

/**
 * Controller per la gestione della {@link SchermataUtente}, ovvero la schermata principale
 * mostrata agli utenti registrati. Permette la navigazione verso schermate correlate
 * (team, hackathon, classifica) e gestisce interazioni specifiche come la visualizzazione
 * del problema assegnato o l'iscrizione a un team.
 */
public class ControllerSchermataUtente {

    /** Interfaccia grafica associata alla schermata utente. */
    private final SchermataUtente schermataUtente;

    /** DAO per la gestione dei team nel database. */
    TeamDAO tdao = new TeamDAO();

    /** DAO per la gestione degli hackathon nel database. */
    HackathonDAO hdao = new HackathonDAO();

    /** Controller principale dell'applicazione. */
    private final MainController mainController;

    /**
     * Costruttore. Inizializza la schermata utente e i riferimenti ai controller.
     *
     * @param mainController il controller principale per la navigazione
     * @param utente         l'utente loggato
     */
    public ControllerSchermataUtente(MainController mainController, Utente utente) {
        this.mainController = mainController;
        this.schermataUtente = new SchermataUtente(this,utente);
    }

    /**
     * Restituisce il pannello principale della schermata utente.
     *
     * @return il {@link JPanel} principale
     */
    public JPanel getSchermataUtente() {
        return schermataUtente.getMainPanel();
    }

    /**
     * Mostra la schermata del team a cui l'utente è iscritto, se presente.
     * Altrimenti, mostra un messaggio di warning.
     *
     * @param utente l'utente corrente
     */
    public void schermataTeamUtente(Utente utente){
        if(utente.getTeamID() == null) JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Non fai ancora parte di un team", "Attenzione", JOptionPane.WARNING_MESSAGE);
        else {
            Team team = tdao.getTeamByID(utente.getTeamID());
            mainController.showTeamSchermataUtente(team, utente);
        }
    }

    /**
     * Visualizza la traccia assegnata all'Hackathon al quale l'utente è iscritto.
     * Se l'utente non è iscritto a nessun hackathon, mostra un messaggio di errore.
     *
     * @param utente l'utente corrente
     */
    public void mostraProblemaHackathon(Utente utente){

        if(utente.getHackathonID() != null) {
            Hackathon h = hdao.getHackathonByID(utente.getHackathonID());
            String problema = h.getProblema();
            String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(schermataUtente.getMainPanel(), problemaHTML);
        } else {
            JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Non partecipi a nessun Hackathon", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Porta alla schermata per l'iscrizione o creazione di un team.
     * Se l'utente ha già un team, mostra un messaggio di warning.
     *
     * @param utente l'utente corrente
     */
    public void schermataIscrizioneTeam(Utente utente){
        if(utente.getTeamID() != null) JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Fai già parte di un team!", "Attenzione", JOptionPane.WARNING_MESSAGE);

        else mainController.showIscrizioneTeamUtente(utente);

    }

    /**
     * Mostra la schermata della classifica relativa a un determinato hackathon.
     *
     * @param hackathonID l'ID dell'hackathon
     * @param utente      l'utente corrente
     */
    public void showSchermataClassifica(Integer hackathonID, Utente utente) {
        mainController.showSchermataClassifica(hackathonID, utente);
    }

    /**
     * Esegue il logout dell'utente e torna alla schermata iniziale.
     */
    public void logout() {
        mainController.logout();
    }


}
