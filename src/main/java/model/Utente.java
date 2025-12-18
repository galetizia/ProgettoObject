package model;

/**
 * Rappresenta un utente iscritto alla piattaforma.
 * <p>
 * Ogni utente è caratterizzato un nome, cognome, email, username, password
 * e può essere associato a un hackathon e a un team.
 * </p>
 */
public class Utente extends User {
    private Integer teamID;
    /**
     * Instantiates a new Utente con i dati personali e le credenziali.
     *
     * @param nome     il nome dell'utente
     * @param cognome  il cognome dell'utente
     * @param email    l'email dell'utente
     * @param username lo username (univoco)
     * @param password la password
     */
    public Utente(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        setHackathonID(null);
        this.teamID = null;
    }

    /**
     * Restituisce l'ID del team di cui l'utente fa parte.
     *
     * @return l'id del team
     */
    public Integer getTeamID() { return teamID; }

    /**
     * Imposta l'ID del team a cui l'utente è assegnato.
     *
     * @param teamID l'id del team
     */
    public void setTeamID(Integer teamID) { this.teamID = teamID; }

}
