package dao;

import model.Giudice;
import model.Team;
import model.Voto;

import java.util.List;

/**
 * Interfaccia DAO per le operazioni relative ai giudici.
 * Fornisce i metodi per login, gestione commenti, voti e accesso ai team/elaborati.
 */
public interface IGiudiceDAO {

    /**
     * Verifica le credenziali del giudice e ne restituisce l'oggetto se corrette.
     *
     * @param username username del giudice
     * @param password password del giudice
     * @return il giudice autenticato o null se le credenziali non sono valide
     */
    Giudice login(String username, String password);

    /**
     * Recupera un giudice a partire dal suo username.
     *
     * @param username username del giudice
     * @return oggetto Giudice se trovato, altrimenti null
     */
    Giudice findGiudiceByUsername(String username);

    /**
     * Recupera un giudice a partire dalla sua email.
     *
     * @param email email del giudice
     * @return oggetto Giudice se trovato, altrimenti null
     */
    Giudice findGiudiceByEmail(String email);

    /**
     * Salva un commento associato a un aggiornamento.
     *
     * @param commento testo del commento
     * @param id ID dell'aggiornamento
     * @param giudice giudice che ha commentato
     */
    void saveCommento(String commento, Integer id, Giudice giudice);

    /**
     * Verifica se un giudice ha già commentato un aggiornamento specifico.
     *
     * @param id ID dell'aggiornamento
     * @param giudice giudice di riferimento
     * @return true se ha già commentato, false altrimenti
     */
    boolean haCommentatoAggiornamento(Integer id, Giudice giudice);

    /**
     * Salva un voto assegnato da un giudice a un team.
     *
     * @param voto oggetto Voto da salvare
     */
    void caricaVoto(Voto voto);

    /**
     * Verifica se il giudice ha già votato un determinato team.
     *
     * @param teamId ID del team
     * @param giudiceID identificativo del giudice
     * @return true se il voto esiste già, false altrimenti
     */
    boolean controlloVotoTeam(Integer teamId, String giudiceID);

    /**
     * Restituisce la lista dei team che hanno caricato l’elaborato finale
     * per una determinata Hackathon (identificata tramite ID).
     *
     * @param id ID della hackathon
     * @return lista dei team con elaborato finale caricato
     */
    List<Team> getElaboratiFinaliTeam(Integer id);

    /**
     * Verifica se un team ha caricato un elaborato finale.
     *
     * @param idTeam ID del team
     * @return true se è stato caricato, false altrimenti
     */
    boolean isElaboratoFinale(Integer idTeam);
}
