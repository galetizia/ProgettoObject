package controller;

import model.Giudice;
import model.Organizzatore;
import model.Team;
import model.Utente;

import javax.swing.*;

/**
 * Controller principale dell'applicazione.
 * Gestisce il frame principale e la navigazione tra le varie schermate
 * dell'interfaccia utente, in base al ruolo dell'utente (Utente, Organizzatore, Giudice).
 */
public class MainController {
    private final JFrame mainFrame;

    /**
     * Costruttore della classe.
     * Inizializza il frame principale e mostra la schermata di login.
     */
    public MainController(){
        mainFrame = new JFrame("Hackathon");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        showLogin();

    }

    /**
     * Imposta il pannello corrente da visualizzare nel frame principale.
     *
     * @param panel Il pannello da visualizzare.
     */
    public void setPanel(JPanel panel) {
        mainFrame.setContentPane(panel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Mostra la schermata di login.
     */
    public void showLogin(){
        ControllerLogin loginController = new ControllerLogin(this);
        setPanel(loginController.getLogin());
    }

    /**
     * Mostra la schermata di registrazione (sign in).
     */
    public void showSignIn() {
        ControllerSignIn signInController = new ControllerSignIn(this);
        setPanel(signInController.getSignIn());
    }

    /**
     * Mostra la schermata per il recupero dello username.
     */
    public void showRecuperoUsername() {
        ControllerRecuperoUsername recuperoUsernameController = new ControllerRecuperoUsername(this);
        setPanel(recuperoUsernameController.getRecuperoUsername());
    }

    /**
     * Mostra la schermata per il recupero della password.
     */
    public void showRecuperoPassword() {
        ControllerRecuperoPassword recuperoPasswordController = new ControllerRecuperoPassword(this);
        setPanel(recuperoPasswordController.getRecuperaPassword());
    }

    /**
     * Mostra la schermata principale per l'organizzatore.
     *
     * @param organizzatore L'organizzatore autenticato.
     */
    public void showSchermataOrganizzatore(Organizzatore organizzatore) {
        ControllerSchermataOrganizzatore schermataOrganizzatoreController = new ControllerSchermataOrganizzatore(this,organizzatore);
        setPanel(schermataOrganizzatoreController.getSchermataOrganizzatore());
    }

    /**
     * Mostra la schermata principale per l'utente autenticato.
     *
     * @param utente L'utente autenticato.
     */
    public void showSchermataUtente(Utente utente) {
        ControllerSchermataUtente schermataUtenteController = new ControllerSchermataUtente(this,utente);
        setPanel(schermataUtenteController.getSchermataUtente());
    }

    /**
     * Mostra la schermata del team a cui appartiene l'utente autenticato.
     *
     * @param team   Il team dell'utente.
     * @param utente L'utente autenticato.
     */
    public void showTeamSchermataUtente(Team team, Utente utente){
        ControllerTeamSchermataUtente teamSchermataUtenteController = new ControllerTeamSchermataUtente(this, team, utente);
        setPanel(teamSchermataUtenteController.getTeamSchermataUtente());
    }

    /**
     * Mostra la schermata per l'iscrizione a un team.
     *
     * @param utente L'utente autenticato.
     */
    public void showIscrizioneTeamUtente(Utente utente){
        ControllerIscrizioneTeam iscrizioneTeamController = new ControllerIscrizioneTeam(this, utente);
        setPanel(iscrizioneTeamController.getIscrizioneTeam());
    }

    /**
     * Mostra la schermata per l'organizzazione di un nuovo hackathon.
     *
     * @param organizzatore L'organizzatore autenticato.
     */
    public void showOrganizzaHackathon(Organizzatore organizzatore) {
        ControllerOrganizzaHackathon organizzaHackathonController = new ControllerOrganizzaHackathon(this,organizzatore);
        setPanel(organizzaHackathonController.getOrganizzaHackathon());
    }

    /**
     * Mostra la schermata principale per il giudice autenticato.
     *
     * @param giudice Il giudice autenticato.
     */
    public void showSchermataGiudice(Giudice giudice){
        ControllerSchermataGiudice schermataGiudiceCon = new ControllerSchermataGiudice(this, giudice);
        setPanel(schermataGiudiceCon.getSchermataGiudice());
    }

    /**
     * Mostra la schermata per la gestione dell' hackathon.
     *
     * @param organizzatore L'organizzatore autenticato.
     */
    public void showSchermataGestioneHack(Organizzatore organizzatore){
        ControllerGestioneHackathon gestioneHackathonController = new ControllerGestioneHackathon(this,organizzatore);
        setPanel(gestioneHackathonController.getGestioneHackathon());
    }

    /**
     * Mostra la schermata per l'inserimento di voti e commenti da parte del giudice.
     *
     * @param giudice Il giudice autenticato.
     */
    public void showSchermataVotiCommenti(Giudice giudice){
        ControllerVotiCommenti votiCommentiController = new ControllerVotiCommenti(this, giudice);
        setPanel(votiCommentiController.getSchermataVotiCommenti());
    }

    /**
     * Mostra la schermata della classifica per un determinato hackathon (vista utente).
     *
     * @param hackathonId L'ID dell'hackathon.
     * @param utente      L'utente autenticato.
     */
    public void showSchermataClassifica(Integer hackathonId, Utente utente) {
        ControllerClassifica classificaController = new ControllerClassifica(hackathonId, () -> showSchermataUtente(utente));
        setPanel(classificaController.getSchermataClassifica());
    }

    /**
     * Mostra la schermata della classifica per un determinato hackathon (vista giudice).
     *
     * @param hackathonId L'ID dell'hackathon.
     * @param giudice     Il giudice autenticato.
     */
    public void showSchermataClassifica(Integer hackathonId, Giudice giudice) {
        ControllerClassifica classificaController = new ControllerClassifica(hackathonId, () -> showSchermataGiudice(giudice));
        setPanel(classificaController.getSchermataClassifica());
    }

    /**
     * Mostra la schermata della classifica per un determinato hackathon (vista organizzatore).
     *
     * @param hackathonId   L'ID dell'hackathon.
     * @param organizzatore L'organizzatore autenticato.
     */
    public void showSchermataClassifica(Integer hackathonId, Organizzatore organizzatore) {
        ControllerClassifica classificaController = new ControllerClassifica(hackathonId, () -> showSchermataOrganizzatore(organizzatore));
        setPanel(classificaController.getSchermataClassifica());
    }

    /**
     * Esegue il logout e ritorna alla schermata di login.
     */
    public void logout() {
        showLogin();
    }
}
