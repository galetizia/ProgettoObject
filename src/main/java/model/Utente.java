package model;

public class Utente {

    private final String nome;
    private final String cognome;
    private final String email;
    private final String username;
    private final String password;
    private Integer hackathonID;
    private Integer teamID;

    public Utente(String nome, String cognome, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.hackathonID = null;
        this.teamID = null;
    }

    public String getNome() { return this.nome; }
    public String getCognome() { return this.cognome; }
    public String getEmail() { return this.email; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }

    public Integer getHackathonID() { return this.hackathonID; }
    public void setHackathonID(Integer hackathonID) { this.hackathonID = hackathonID; }

    public Integer getTeamID() { return teamID; }
    public void setTeamID(Integer teamID) { this.teamID = teamID; }
}
