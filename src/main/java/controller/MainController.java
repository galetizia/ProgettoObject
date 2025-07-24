package controller;

import model.Giudice;
import model.Organizzatore;
import model.Team;
import model.Utente;

import javax.swing.*;

/**
 * Controller principale dell'applicazione.
 * Gestisce il frame principale e la navigazione tra le diverse schermate
 * dell'interfaccia utente in base al ruolo dell'utente (Utente, Organizzatore, Giudice).
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
    public void showSchermataUtente(Utente utente) {
        ControllerSchermataUtente schermataUtenteController = new ControllerSchermataUtente(this,utente);
        setPanel(schermataUtenteController.getSchermataUtente());
    }
    public void showTeamSchermataUtente(Team team, Utente utente){
        ControllerTeamSchermataUtente teamSchermataUtenteController = new ControllerTeamSchermataUtente(this, team, utente);
        setPanel(teamSchermataUtenteController.getTeamSchermataUtente());
    }

    public void showIscrizioneTeamUtente(Utente utente){
        ControllerIscrizioneTeam iscrizioneTeamController = new ControllerIscrizioneTeam(this, utente);
        setPanel(iscrizioneTeamController.getIscrizioneTeam());
    }

    public void showOrganizzaHackathon(Organizzatore organizzatore) {
        ControllerOrganizzaHackathon organizzaHackathonController = new ControllerOrganizzaHackathon(this,organizzatore);
        setPanel(organizzaHackathonController.getOrganizzaHackathon());
    }

    public void showSchermataGiudice(Giudice giudice){
        ControllerSchermataGiudice schermataGiudiceCon = new ControllerSchermataGiudice(this, giudice);
        setPanel(schermataGiudiceCon.getSchermataGiudice());
    }

    public void showSchermataGestioneHack(Organizzatore organizzatore){
        ControllerGestioneHackathon gestioneHackathonController = new ControllerGestioneHackathon(this,organizzatore);
        setPanel(gestioneHackathonController.getGestioneHackathon());
    }

    public void showSchermataVotiCommenti(Giudice giudice){
        ControllerVotiCommenti votiCommentiController = new ControllerVotiCommenti(this, giudice);
        setPanel(votiCommentiController.getSchermataVotiCommenti());
    }

    public void showSchermataClassifica(Integer hackathonId, Utente utente) {
        ControllerClassifica classificaController = new ControllerClassifica(hackathonId, () -> showSchermataUtente(utente));
        setPanel(classificaController.getSchermataClassifica());
    }
    public void showSchermataClassifica(Integer hackathonId, Giudice giudice) {
        ControllerClassifica classificaController = new ControllerClassifica(hackathonId, () -> showSchermataGiudice(giudice));
        setPanel(classificaController.getSchermataClassifica());
    }
    public void showSchermataClassifica(Integer hackathonId, Organizzatore organizzatore) {
        ControllerClassifica classificaController = new ControllerClassifica(hackathonId, () -> showSchermataOrganizzatore(organizzatore));
        setPanel(classificaController.getSchermataClassifica());
    }

    public void logout() {
        showLogin();
    }
}
