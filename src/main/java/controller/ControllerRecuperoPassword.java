package controller;

import gui.RecuperaPassword;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;

/**
 * Controller per la schermata di recupero password.
 * Gestisce il recupero della password in base a username ed email
 * per utenti, organizzatori e giudici.
 */
public class ControllerRecuperoPassword {

    /** Riferimento alla GUI della schermata di recupero password. */
    private final RecuperaPassword recuperaPassword;

    /** DAO per gli utenti. */
    UtenteDAO udao = new UtenteDAO();

    /** DAO per gli organizzatori. */
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    /** DAO per i giudici. */
    GiudiceDAO gdao = new GiudiceDAO();

    /** Messaggio base per la stringa ripetuta. */
    private static final String PASSWORD = "Ecco la Password: ";

    /** Controller principale dell'applicazione. */
    private final MainController mainController;

    /**
     * Costruttore del controller per il recupero password.
     *
     * @param mainController il controller principale
     */
    public ControllerRecuperoPassword(MainController mainController) {
        this.mainController = mainController;
        this.recuperaPassword = new RecuperaPassword(this);
    }

    /**
     * Restituisce il pannello principale della schermata di recupero password.
     *
     * @return il {@link JPanel} principale
     */
    public JPanel getRecuperaPassword() {
        return recuperaPassword.getMainPanel();
    }

    /**
     * Recupera la password in base a email e username forniti.
     * Cerca tra utenti, organizzatori e giudici.
     * Se i dati corrispondono, mostra un messaggio con la password,
     * altrimenti mostra un errore.
     *
     * @param email    l'email associata all'account
     * @param username lo username associato all'account
     */
    public void recuperoPassword(String email, String username) {
        boolean success = false;
        Utente u = udao.findUtenteByUsername(username);
        Organizzatore o = odao.findOrganizzatoreByUsername(username);
        Giudice g = gdao.findGiudiceByUsername(username);

        if((u!=null)&&((u.getEmail().equalsIgnoreCase(email)) && (u.getUsername().equalsIgnoreCase(username)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperaPassword(),PASSWORD +u.getPassword(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        if((o!=null)&&((o.getEmail().equalsIgnoreCase(email)) && (o.getUsername().equalsIgnoreCase(username)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperaPassword(),PASSWORD +o.getPassword(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        if((g!=null)&&((g.getEmail().equalsIgnoreCase(email)) && (g.getUsername().equalsIgnoreCase(username)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperaPassword(),PASSWORD +g.getPassword(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        if(!success){
            if(email.isEmpty() || username.isEmpty()){
                JOptionPane.showMessageDialog(getRecuperaPassword(), "Compila tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(getRecuperaPassword(), "Non ci sono utenti/organizzatori/giudici con queste credenziali", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Torna alla schermata di login.
     */
    public void indietro() {
        mainController.logout();
    }
}
