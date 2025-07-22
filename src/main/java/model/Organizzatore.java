package model;

/**
 * Rappresenta un organizzatore di un hackathon.
 * <p>
 * L'organizzatore è un tipo di {@link Utente} responsabile della gestione dell'evento,
 * come la creazione di hackathon, gestione dei partecipanti, giudici, e aggiornamenti.
 * </p>
 */
public class Organizzatore extends Utente {

    /**
     * Crea un nuovo organizzatore con i dati specificati.
     *
     * @param nome     il nome dell’organizzatore
     * @param cognome  il cognome dell’organizzatore
     * @param email    l’indirizzo email dell’organizzatore
     * @param username lo username per l’accesso
     * @param password la password associata all’account
     */
    public Organizzatore(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
    }
}
