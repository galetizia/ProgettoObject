package controller;

import gui.SignIn;
import implementazionepostgresdao.*;
import model.Utente;

import javax.swing.*;

/**
 * Controller per la gestione della logica di registrazione (Sign-In) degli utenti e degli organizzatori.
 * Si occupa della validazione dei dati, della comunicazione con il database e della navigazione tra schermate.
 */
public class ControllerSignIn {

    /** Riferimento alla GUI della schermata di registrazione. */
    private final SignIn signInGui;

    /** DAO per operazioni relative a utenti e organizzatori nel contesto Hackathon. */
    HackathonDAO hdao = new HackathonDAO();

    /** Controller principale per la navigazione tra schermate. */
    private final MainController mainController;

    /**
     * Costruttore che inizializza il controller e la relativa GUI.
     *
     * @param mainController il controller principale dell'applicazione
     */
    public ControllerSignIn(MainController mainController) {
        this.mainController = mainController;
        this.signInGui = new SignIn(this);
    }

    /**
     * Restituisce il pannello principale della schermata di registrazione.
     *
     * @return il pannello principale {@link JPanel}
     */
    public JPanel getSignIn() {
        return signInGui.getMainPanel();
    }

    /**
     * Esegue il processo di registrazione per un utente o un organizzatore.
     * Valida i dati inseriti (campi obbligatori, formato email, password corrispondenti, ruolo selezionato)
     * e, in caso positivo, chiama il metodo DAO per salvare i dati nel database.
     *
     * @param u               oggetto {@link Utente} contenente i dati inseriti
     * @param confirmPassword stringa della password di conferma
     * @param isUtente        true se l'utente si registra come partecipante
     * @param isOrganizzatore true se l'utente si registra come organizzatore
     */
    public void signIn(Utente u, String confirmPassword, boolean isUtente, boolean isOrganizzatore) {

        String checkEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (u.getPassword().isEmpty() || confirmPassword.isEmpty() || u.getUsername().isEmpty() || u.getEmail().isEmpty() || u.getNome().isEmpty() || u.getCognome().isEmpty()) {
            JOptionPane.showMessageDialog(getSignIn(), "Compilare tutti i campi!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        } else if (!u.getEmail().matches(checkEmail)) {
            JOptionPane.showMessageDialog(getSignIn(), "Formato Email non valido", "Errore di formato", JOptionPane.ERROR_MESSAGE);
        } else if (!u.getPassword().equals(confirmPassword)) {
            JOptionPane.showMessageDialog(getSignIn(), "Le password non coincidono!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isUtente && !isOrganizzatore) {
            JOptionPane.showMessageDialog(getSignIn(), "Inserire un ruolo!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        } else {

            boolean success;
            if (isUtente) {
                success = hdao.signInUtente(u.getNome(),u.getCognome(),u.getEmail(),u.getUsername(),u.getPassword());
            } else{
                success = hdao.signInOrganizzatore(u.getNome(),u.getCognome(),u.getEmail(),u.getUsername(),u.getPassword());
            }

            if (!success) {
                JOptionPane.showMessageDialog(getSignIn(), "Username già in uso. Scegli un altro.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(getSignIn(), "Registrazione completata!", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainController.showLogin();
            }
        }
    }

    /**
     * Torna alla schermata di login dopo la registrazione o su richiesta esplicita.
     */
    public void showLogin(){
        mainController.showLogin();
    }
}
