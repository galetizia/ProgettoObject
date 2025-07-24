package dao;

import model.*;

import java.util.List;

/**
 * Interfaccia DAO per la gestione delle operazioni relative alle Hackathon.
 * Include metodi per gestione utenti, organizzatori, team, giudici, e classifiche.
 */
public interface IHackathonDAO {

    /**
     * Restituisce la dimensione massima dei team per una specifica Hackathon.
     *
     * @param id ID della Hackathon
     * @return numero massimo di membri per team
     */
    int getMaxDimTeam(Integer id);

    /**
     * Restituisce il numero massimo di iscritti per una Hackathon.
     *
     * @param id ID della Hackathon
     * @return numero massimo di partecipanti
     */
    int getMaxIscritti(Integer id);

    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param nome nome dell’utente
     * @param cognome cognome dell’utente
     * @param email email dell’utente
     * @param username username scelto
     * @param password password dell’account
     * @return true se la registrazione è andata a buon fine, false altrimenti
     */
    boolean signInUtente(String nome, String cognome, String email, String username, String password);

    /**
     * Registra un nuovo organizzatore nel sistema.
     *
     * @param nome nome dell’organizzatore
     * @param cognome cognome dell’organizzatore
     * @param email email dell’organizzatore
     * @param username username scelto
     * @param password password dell’account
     * @return true se la registrazione è andata a buon fine, false altrimenti
     */
    boolean signInOrganizzatore(String nome, String cognome, String email, String username, String password);

    /**
     * Restituisce la lista di tutte le Hackathon presenti nel sistema.
     *
     * @return lista delle Hackathon
     */
    List<Hackathon> getHackathons();

    /**
     * Salva una nuova Hackathon nel database, associandola a un organizzatore.
     *
     * @param hackathon oggetto Hackathon da salvare
     * @param organizzatore organizzatore che la crea
     */
    void caricaHackathonDB(Hackathon hackathon, Organizzatore organizzatore);

    /**
     * Recupera una Hackathon tramite il suo ID.
     *
     * @param id ID della Hackathon
     * @return oggetto Hackathon corrispondente
     */
    Hackathon getHackathonByID(Integer id);

    /**
     * Restituisce tutti i team iscritti a una Hackathon.
     *
     * @param id ID della Hackathon
     * @return lista dei team
     */
    List<Team> getTeamByHackathon(Integer id);

    /**
     * Recupera l’ID della Hackathon a cui è iscritto un team.
     *
     * @param id ID del team
     * @return ID della Hackathon
     */
    Integer getHackathonByTeam(Integer id);

    /**
     * Restituisce l’ID dell’ultimo aggiornamento inviato da un team.
     *
     * @param id ID del team
     * @return ID dell’aggiornamento
     */
    Integer getIdAggiornamentoByTeam(Integer id);

    /**
     * Restituisce la lista di utenti iscritti a una Hackathon.
     *
     * @param id ID della Hackathon
     * @return lista di utenti
     */
    List<Utente> getUtenti(Integer id);

    /**
     * Restituisce la lista di giudici assegnati a una Hackathon.
     *
     * @param id ID della Hackathon
     * @return lista dei giudici
     */
    List<Giudice> getGiudici(Integer id);

    /**
     * Restituisce la lista degli utenti che possono essere assegnati come giudici.
     * (Ad esempio, utenti iscritti ma non ancora assegnati.)
     *
     * @return lista dei potenziali giudici
     */
    List<Utente> getPotenzialiGiudici();

    /**
     * Restituisce la classifica dei team per una Hackathon.
     *
     * @param hackathonID ID della Hackathon
     * @return lista ordinata dei team secondo i voti
     */
    List<Team> getClassificaTeams(Integer hackathonID);

    /**
     * Verifica se la classifica è stata pubblicata per una specifica Hackathon.
     *
     * @param id ID della Hackathon
     * @return true se la classifica è pubblica, false altrimenti
     */
    boolean isClassificaPubblicata(Integer id);
}
