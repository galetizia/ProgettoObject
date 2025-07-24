package controller;

import gui.RecuperoUsername;
import implementazionepostgresdao.*;
import implementazionepostgresdao.UtenteDAO;
import model.*;

import javax.swing.*;

/**
 * Controller per la schermata di recupero dello username.
 * Gestisce la logica per recuperare lo username in base a email e password
 * inserite dall'utente, dall'organizzatore o dal giudice.
 */
public class ControllerRecuperoUsername {

    /** Riferimento alla GUI per il recupero dello username. */
    private final RecuperoUsername recuperoUsername;

    /** DAO per gli utenti. */
    UtenteDAO udao = new UtenteDAO();

    /** DAO per gli organizzatori. */
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    /** DAO per i giudici. */
    GiudiceDAO gdao = new GiudiceDAO();

    /** Messaggio base per la stringa ripetuta. */
    private static final String USERNAME = "Ecco lo Username: ";

    /** Controller principale dell'applicazione. */
    private final MainController mainController;

    /**
     * Costruttore del controller per la schermata di recupero username.
     *
     * @param mainController il controller principale dell'applicazione
     */
    public ControllerRecuperoUsername(MainController mainController) {
        this.mainController = mainController;
        this.recuperoUsername = new RecuperoUsername(this);
    }

    /**
     * Restituisce il pannello principale della schermata di recupero username.
     *
     * @return il {@link JPanel} principale della schermata
     */
    public JPanel getRecuperoUsername() {
        return recuperoUsername.getMainPanel();
    }

    /**
     * Recupera lo username in base all'email e alla password fornite.
     * Cerca tra utenti, organizzatori e giudici.
     * Mostra un messaggio con lo username se la combinazione è corretta,
     * altrimenti mostra un errore.
     *
     * @param email    l'email inserita
     * @param password la password inserita
     */
    public void recuperoUsername(String email, String password) {

        boolean success = false;
        Utente u = udao.findUtenteByEmail(email);
        Organizzatore o = odao.findOrganizzatoreByEmail(email);
        Giudice g = gdao.findGiudiceByEmail(email);

        if((u!=null)&&((u.getEmail().equalsIgnoreCase(email)) && (u.getPassword().equalsIgnoreCase(password)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperoUsername(), USERNAME +u.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        if((o!=null)&&((o.getEmail().equalsIgnoreCase(email)) && (o.getPassword().equalsIgnoreCase(password)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperoUsername(),USERNAME +o.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        if((g!=null)&&((g.getEmail().equalsIgnoreCase(email)) && (g.getPassword().equalsIgnoreCase(password)))){
            success = true;
            JOptionPane.showMessageDialog(getRecuperoUsername(),USERNAME +g.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        if(!success){
            if(email.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Compila tutti i campi!", "Error", JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(getRecuperoUsername(), "Non ci sono utenti/organizzatori/giudici con queste credenziali", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Torna alla schermata di login.
     */
    public void indietro(){
        mainController.logout();
    }

}
