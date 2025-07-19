package controller;
import gui.SchermataGiudice;
import model.*;
import javax.swing.*;


public class MainController {
    private final JFrame mainFrame;

    public MainController(){
        mainFrame = new JFrame("Hackathon");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        showLogin();

    }
    public void setPanel(JPanel panel) {
        mainFrame.setContentPane(panel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showLogin(){
        ControllerLogin loginController = new ControllerLogin(this);
        setPanel(loginController.getLogin());
    }

    public void showSignIn() {
        ControllerSignIn signInController = new ControllerSignIn(this);
        setPanel(signInController.getSignIn());
    }

    public void showRecuperoUsername() {
        ControllerRecuperoUsername recuperoUsernameController = new ControllerRecuperoUsername(this);
        setPanel(recuperoUsernameController.getRecuperoUsername());
    }

    public void showRecuperoPassword() {
        ControllerRecuperoPassword recuperoPasswordController = new ControllerRecuperoPassword(this);
        setPanel(recuperoPasswordController.getRecuperaPassword());
    }

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

    public void showSchermataClassifica(Integer hackathonId){
        ControllerClassifica classificaController = new ControllerClassifica(this, hackathonId);
        setPanel(classificaController.getSchermataClassifica());
    }

    public void logout() {
        showLogin();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainController::new);
    }

} //modificato - Fabio (Aggiunto utente come parametro in showTeamSchermataUtente)
