package controller;
import gui.*;
import model.*;
import javax.swing.*;
import java.util.ArrayList;


public class MainController {
    private JFrame mainFrame;
    private ArrayList<Organizzatore> listaOrganizzatori;
    private ArrayList<Utente> listaUtenti;

    private Organizzatore loggedOrganizzatore=null;
    private Utente loggedUtente=null;

    public MainController(){
        listaOrganizzatori = new ArrayList<>();
        listaUtenti = new ArrayList<>();
        mainFrame = new JFrame("Hackathon");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        ControllerLogin loginController = new ControllerLogin(listaUtenti,listaOrganizzatori,this);
        setPanel(loginController.getLogin());
    }

    public void showSignIn() {
        ControllerSignIn signInController = new ControllerSignIn(listaUtenti,listaOrganizzatori,this);
        setPanel(signInController.getSignIn());
    }

    public void showRecuperoUsername() {
        ControllerRecuperoUsername recuperoUsernameController = new ControllerRecuperoUsername(listaUtenti,listaOrganizzatori,this);
        setPanel(recuperoUsernameController.getRecuperoUsername());
    }

    public void showRecuperoPassword() {
        ControllerRecuperoPassword recuperoPasswordController = new ControllerRecuperoPassword(listaUtenti,listaOrganizzatori,this);
        setPanel(recuperoPasswordController.getRecuperaPassword());
    }

    public void showSchermataOrganizzatore(Organizzatore organizzatore) {
        ControllerSchermataOrganizzatore schermataOrganizzatoreController = new ControllerSchermataOrganizzatore(listaUtenti,listaOrganizzatori,this,organizzatore);
        setPanel(schermataOrganizzatoreController.getSchermataOrganizzatore());
    }
    public void showSchermataUtente(Utente utente) {
        ControllerSchermataUtente schermataUtenteController = new ControllerSchermataUtente(listaUtenti,listaOrganizzatori,this,utente);
        setPanel(schermataUtenteController.getSchermataUtente());
    }
    public void showTeamSchermataUtente(Team team){
        ControllerTeamSchermataUtente teamSchermataUtenteController = new ControllerTeamSchermataUtente(team, this);
        //setPanel(teamSchermataUtenteController.getTeamSchermataUtente);
    }

    // Getters e accesso centralizzato ai dati
    public ArrayList<Utente> getListaUtenti() {
        return listaUtenti;
    }

    public ArrayList<Organizzatore> getListaOrganizzatori() {
        return listaOrganizzatori;
    }

    public void logout() {
        showLogin();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainController::new);
    }

}
