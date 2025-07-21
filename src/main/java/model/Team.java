package model;

/**
 *  Rappresenta un team iscritto a un hackathon.
 *  Ogni team ha un identificativo, un nome, un ID hackathon associato e una media dei voti ricevuti.
 */
public class Team {
    private int id;
    private Integer hackathonID;
    private final String nome;
    private double mediaVoti=0;

    /**
     * Instantiates a new Team (senza id perchè viene generato automaticamente dal database).
     *
     * @param nome        il nome del team
     * @param hackathonID l'hackathon id associato
     */
    public Team(String nome, Integer hackathonID) {
        this.nome = nome;
        this.hackathonID = hackathonID;
    }

    /**
     * Instantiates a new Team (usato per il recupero da database).
     *
     * @param id          l'id del team
     * @param nome        il nome
     * @param mediaVoti   la media voti
     * @param hackathonID l'hackathon id associato
     */
    public Team(int id, String nome, double mediaVoti, Integer hackathonID) {
        this.id = id;
        this.nome = nome;
        this.mediaVoti = mediaVoti;
        this.hackathonID = hackathonID;
    }

    /**
     * Restituisce il nome del (string).
     *
     * @return il nome sotto forma di Stringa
     */
    public String getNome(){ return this.nome; }

    /**
     * Restituisce l'id del team (integer).
     *
     * @return l'id come tipo integer (scelto integer perchè non sempre lo inseriamo, il Database ci pensarà a generarlo)
     *         e se il team non esiste ritorna null.
     */
    public Integer getId(){ return this.id; }

    /**
     * Imposta l'id del team.
     *
     * @param id l'id del team asseganto dal Database
     */
    public void setId(int id) { this.id = id; }

    /**
     * Restituisce la media voti che sono stati assegnati dai giudici.
     *
     * @return la media dei voti
     */
    public Double getMediaVoti() { return mediaVoti; }

    /**
     * Restituisce l'hackathon id associato al team.
     *
     * @return l'hackathon id
     */
    public Integer getHackathonID() { return hackathonID; }

    /**
     * Imposta hackathon id.
     *
     * @param hackathonID l'hackathon id a cui associare il team
     */
    public void setHackathonID(Integer hackathonID) { this.hackathonID = hackathonID; }

}
