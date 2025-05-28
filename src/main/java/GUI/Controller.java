package GUI;
import model.*;
import javax.swing.*;
import java.util.ArrayList;


public class Controller {
    private JFrame mainFrame;
    private ArrayList<Organizzatore> listaOrganizzatori;
    private ArrayList<Utente> listaUtenti;

    private Organizzatore loggedOrganizzatore=null;
    private Utente loggedUtente=null;

    public Controller(){
        listaOrganizzatori = new ArrayList<>();
        listaUtenti = new ArrayList<>();
        mainFrame = new JFrame("Hackathon");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        showLogin();
    }
    public void showLogin(){
        Login login = new Login(this);
        mainFrame.setContentPane(login.getMainPanel());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void showSignIn() { // Chiude la finestra di login PRIMA di aprirne una nuova
        SignIn signIn = new SignIn(this);
        mainFrame.setContentPane(signIn.getMainPanel());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void showSchermataOrganizzatore(Organizzatore organizzatore) {
        loggedOrganizzatore = organizzatore;
        SchermataOrganizzatore schermata = new SchermataOrganizzatore(this, organizzatore);
        mainFrame.setContentPane(schermata.getMainPanel());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    public void showSchermataUtente(Utente utente) {
        loggedUtente = utente;
        SchermataUtente schermata = new SchermataUtente(this, utente);
        mainFrame.setContentPane(schermata.getMainPanel());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // Getters e accesso centralizzato ai dati
    public ArrayList<Utente> getListaUtenti() {
        return listaUtenti;
    }

    public ArrayList<Organizzatore> getListaOrganizzatori() {
        return listaOrganizzatori;
    }

    public void logout() {
        loggedOrganizzatore = null;
        loggedUtente = null;
        showLogin();
    }

    public boolean registraUtente(Utente utente) {
        boolean existUser = listaUtenti.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(utente.getUsername()));
        boolean sxistInOrg = listaOrganizzatori.stream().anyMatch(org -> org.getUsername().equalsIgnoreCase(utente.getUsername()));

        if (existUser || sxistInOrg) return false;

        listaUtenti.add(utente);
        return true;
    }

    public boolean registraOrganizzatore(Organizzatore organizzatore) {
        boolean existUser = listaUtenti.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(organizzatore.getUsername()));
        boolean sxistInOrg = listaOrganizzatori.stream().anyMatch(org -> org.getUsername().equalsIgnoreCase(organizzatore.getUsername()));

        if (existUser || sxistInOrg) return false;

        listaOrganizzatori.add(organizzatore);
        return true;

    }

}
