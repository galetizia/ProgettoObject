package model;

/**
 * Rappresenta un utente iscritto alla piattaforma.
 * <p>
 * Ogni utente è caratterizzato un nome, cognome, email, username, password
 * e può essere associato a un hackathon e a un team.
 * </p>
 */
public class Utente {

    private final String nome;
    private final String cognome;
    private final String email;
    private final String username;
    private final String password;
    private Integer hackathonID;
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
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.hackathonID = null;
        this.teamID = null;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return il nome
     */
    public String getNome() { return this.nome; }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return il cognome
     */
    public String getCognome() { return this.cognome; }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return l'email
     */
    public String getEmail() { return this.email; }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return lo username
     */
    public String getUsername() { return this.username; }

    /**
     * Restituisce la password dell'utente.
     *
     * @return la password
     */
    public String getPassword() { return this.password; }

    /**
     * Restituisce l'id dell'hackathon a cui l'utente è iscritto.
     *
     * @return l'hackathonID
     */
    public Integer getHackathonID() { return this.hackathonID; }

    /**
     * Imposta l'id dell'hackathon a cui l'utente è iscritto.
     *
     * @param hackathonID l'id dell'hackathon
     */
    public void setHackathonID(Integer hackathonID) { this.hackathonID = hackathonID; }

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
