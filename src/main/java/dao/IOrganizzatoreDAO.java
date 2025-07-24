package dao;

import model.*;

/**
 * Interfaccia DAO per la gestione delle operazioni relative agli organizzatori.
 * <p>
 * Fornisce i metodi per aggiungere giudici, gestire il login degli organizzatori,
 * recuperare informazioni sugli organizzatori, rimuovere utenti, giudici e team,
 * e gestire lo stato e la classifica delle hackathon.
 * </p>
 */
public interface IOrganizzatoreDAO {

    /**
     * Aggiunge un giudice a una hackathon specifica.
     *
     * @param username Username del giudice da aggiungere.
     * @param idHackathon Identificativo della hackathon a cui associare il giudice.
     * @return true se l'aggiunta è andata a buon fine, false altrimenti.
     */
    boolean aggiungiGiudice(String username, Integer idHackathon);

    /**
     * Effettua il login di un organizzatore usando username e password.
     *
     * @param username Username dell'organizzatore.
     * @param password Password associata all'username.
     * @return L'oggetto {@link Organizzatore} se il login ha successo, null altrimenti.
     */
    Organizzatore login(String username, String password);

    /**
     * Cerca un organizzatore in base al suo username.
     *
     * @param username Username dell'organizzatore da cercare.
     * @return L'oggetto {@link Organizzatore} corrispondente, o null se non trovato.
     */
    Organizzatore findOrganizzatoreByUsername(String username);

    /**
     * Cerca un organizzatore in base alla sua email.
     *
     * @param email Email dell'organizzatore da cercare.
     * @return L'oggetto {@link Organizzatore} corrispondente, o null se non trovato.
     */
    Organizzatore findOrganizzatoreByEmail(String email);

    /**
     * Rimuove un utente dal sistema tramite username.
     *
     * @param username Username dell'utente da rimuovere.
     */
    void removeUtente(String username);

    /**
     * Rimuove un giudice dal sistema tramite username.
     *
     * @param username Username del giudice da rimuovere.
     */
    void removeGiudice(String username);

    /**
     * Rimuove un team dal sistema tramite il suo identificativo.
     *
     * @param id Identificativo del team da rimuovere.
     */
    void removeTeam(Integer id);

    /**
     * Termina una hackathon specifica.
     *
     * @param id Identificativo della hackathon da terminare.
     */
    void terminaHackathon(Integer id);

    /**
     * Imposta la classifica di una hackathon.
     *
     * @param id Identificativo della hackathon per cui impostare la classifica.
     */
    void setClassifica(Integer id);
}
