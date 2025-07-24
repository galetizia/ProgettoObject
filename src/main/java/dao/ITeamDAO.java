package dao;

import model.*;

import java.util.List;

/**
 * Interfaccia DAO per la gestione delle operazioni relative ai teams.
 * <p>
 * Definisce i metodi per recuperare informazioni sui team, gestire i membri,
 * caricare team e aggiornamenti nel database, gestire voti e stati degli elaborati.
 * </p>
 */
public interface ITeamDAO {

    /**
     * Recupera un team tramite il suo identificativo.
     *
     * @param id Identificativo del team da recuperare.
     * @return L'oggetto {@link Team} corrispondente all'id fornito.
     */
    Team getTeamByID(Integer id);

    /**
     * Restituisce la lista dei membri (utenti) appartenenti a un team.
     *
     * @param id Identificativo del team.
     * @return Lista di oggetti {@link Utente} membri del team.
     */
    List<Utente> membriTeam(Integer id);

    /**
     * Rimuove un utente da un team tramite username.
     *
     * @param username Username dell'utente da rimuovere dal team.
     */
    void rimuoviUtenteDalTeam(String username);

    /**
     * Carica un nuovo team nel database associandolo a un utente.
     *
     * @param team Oggetto {@link Team} da caricare.
     * @param utente Oggetto {@link Utente} associato al team.
     */
    void caricaTeamNelDB(Team team, Utente utente);

    /**
     * Carica un aggiornamento nel database associato a un utente.
     *
     * @param utente Oggetto {@link Utente} che effettua l'aggiornamento.
     * @param aggiornamento Oggetto {@link Aggiornamento} da salvare.
     */
    void caricaAggiornamentoDB(Utente utente, Aggiornamento aggiornamento);

    /**
     * Restituisce la descrizione o il contenuto dell'ultimo aggiornamento di un team.
     *
     * @param id Identificativo del team.
     * @return Stringa che rappresenta l'ultimo aggiornamento.
     */
    String getUltimoAggiornamento(Integer id);

    /**
     * Restituisce la lista dei voti ottenuti da un team.
     *
     * @param id Identificativo del team.
     * @return Lista di valori {@link Double} rappresentanti i voti.
     */
    List<Double> getVotiPerTeam(Integer id);

    /**
     * Imposta la media dei voti per un team.
     *
     * @param id Identificativo del team.
     * @param media Valore medio dei voti da assegnare.
     */
    void setVotiPerTeam(Integer id, Double media);

    /**
     * Verifica se l'ultimo aggiornamento di un team contiene l'elaborato finale.
     *
     * @param id Identificativo del team.
     * @return true se l'ultimo aggiornamento contiene l'elaborato finale, false altrimenti.
     */
    boolean getElaboratoFinaleUltimoAggiornamento(Integer id);
}
