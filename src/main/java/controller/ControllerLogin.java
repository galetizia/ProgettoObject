package controller;

import gui.Login;
import implementazionepostgresdao.*;
import model.*;
import javax.swing.*;

/**
 * Controller per la gestione della schermata di login.
 * Gestisce l'autenticazione degli utenti (Utente, Giudice, Organizzatore)
 * e la navigazione verso le schermate corrispondenti.
 */
public class ControllerLogin {

    /** Riferimento alla schermata GUI associata al Login. */
    private final Login loginGui;

    /** DAO per le operazioni sugli organizzatori. */
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    /** DAO per le operazioni sugli utenti. */
    UtenteDAO udao = new UtenteDAO();

    /** DAO per le operazioni sui giudici. */
    GiudiceDAO gdao = new GiudiceDAO();

    /** Riferimento al controller principale dell'applicazione. */
    private final MainController maincontroller;

    /**
     * Costruttore del controller.
     * Inizializza la GUI del login e imposta il riferimento al controller principale.
     *
     * @param maincontroller Il controller principale dell'applicazione.
     */
    public ControllerLogin(MainController maincontroller) {
        this.maincontroller = maincontroller;
        this.loginGui = new Login (this);
    }

    /**
     * Restituisce il pannello principale della schermata di login.
     *
     * @return Il pannello della schermata di login.
     */
    public JPanel getLogin() {
        return loginGui.getMainPanel();
    }

    /**
     * Esegue il processo di login in base al ruolo selezionato.
     * Se l'autenticazione ha successo, reindirizza l'utente alla relativa schermata principale.
     * Altrimenti, mostra un messaggio di errore.
     *
     * @param username        Nome utente inserito.
     * @param password        Password inserita.
     * @param isUtente        True se il ruolo selezionato è "Utente".
     * @param isOrganizzatore True se il ruolo selezionato è "Organizzatore".
     * @param isGiudice       True se il ruolo selezionato è "Giudice".
     */
    public void login(String username, String password, boolean isUtente, boolean isOrganizzatore, boolean isGiudice) {
        if(!isUtente && !isOrganizzatore && !isGiudice) {
            JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Inserire un ruolo!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(isUtente){
            Utente u = udao.login(username, password);
            if(u!=null){
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Utente!", "Success", JOptionPane.INFORMATION_MESSAGE);
                maincontroller.showSchermataUtente(u);
                return;
            }
        }

        if(isGiudice) {
            Giudice g = gdao.login(username, password);
            if(g != null) {
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Giudice!", "Success", JOptionPane.INFORMATION_MESSAGE);
                maincontroller.showSchermataGiudice(g);
                return;
            }
        }

        if(isOrganizzatore){
            Organizzatore o = odao.login(username, password);
            if(o!=null){
                JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Login effettuato come Organizzatore!",  "Success", JOptionPane.INFORMATION_MESSAGE);
                maincontroller.showSchermataOrganizzatore(o);
                return;
            }
        }

        JOptionPane.showMessageDialog(loginGui.getMainPanel(), "Credenziali errate.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    /**
     * Mostra la schermata di registrazione (sign in).
     */
    public void showSignIn() {
        maincontroller.showSignIn();
    }

    /**
     * Mostra la schermata per il recupero dello username.
     */
    public void showRecuperoUsername() {
        maincontroller.showRecuperoUsername();
    }

    /**
     * Mostra la schermata per il recupero della password.
     */
    public void showRecuperoPassword() {
        maincontroller.showRecuperoPassword();
    }
}
