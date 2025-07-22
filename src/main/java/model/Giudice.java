package model;

/**
 * Rappresenta un giudice associato a un hackathon.
 * <p>
 * Il giudice è un tipo di {@link Utente} che partecipa all'evento con il ruolo di valutatore.
 * Ogni giudice è collegato a uno specifico {@code hackathonID}.
 * </p>
 */
public class Giudice extends Utente {

    /**
     * Crea un nuovo giudice con i dati specificati e lo assegna a un determinato hackathon.
     *
     * @param nome        il nome del giudice
     * @param cognome     il cognome del giudice
     * @param email       l'email del giudice
     * @param username    lo username usato per l'accesso
     * @param password    la password associata all'account
     * @param hackathonID l'ID dell'hackathon a cui il giudice è assegnato
     */
    public Giudice(String nome, String cognome, String email, String username, String password, int hackathonID) {
        super(nome, cognome, email, username, password);
        setHackathonID(hackathonID);
    }
}
