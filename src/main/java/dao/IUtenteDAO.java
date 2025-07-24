package dao;

import model.*;

/**
 * Interfaccia DAO per la gestione delle operazioni relative agli utenti.
 * <p>
 * Definisce i metodi per il login, la ricerca di utenti tramite username o email,
 * e la modifica dell'associazione di un utente a un team.
 * </p>
 */
public interface IUtenteDAO {

    /**
     * Effettua il login di un utente utilizzando username e password.
     *
     * @param username Username dell'utente.
     * @param password Password associata all'username.
     * @return L'oggetto {@link Utente} se il login ha successo, null altrimenti.
     */
    Utente login(String username, String password);

    /**
     * Cerca un utente tramite il suo username.
     *
     * @param username Username dell'utente da cercare.
     * @return L'oggetto {@link Utente} corrispondente, o null se non trovato.
     */
    Utente findUtenteByUsername(String username);

    /**
     * Cerca un utente tramite la sua email.
     *
     * @param email Email dell'utente da cercare.
     * @return L'oggetto {@link Utente} corrispondente, o null se non trovato.
     */
    Utente findUtenteByEmail(String email);

    /**
     * Modifica l'associazione di un utente a un team specifico.
     *
     * @param team Oggetto {@link Team} a cui associare l'utente.
     * @param utente Oggetto {@link Utente} da aggiornare.
     */
    void changeIDTeam(Team team, Utente utente);
}
