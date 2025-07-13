package model;

public class Utente {

    private final String nome;
    private final String cognome;
    private final String email;
    private String username;
    private String password;
    private int hackathonID;
    private int teamID;

    public Utente(String nome, String cognome, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Utente login(String username, String password) {
        if(this.username.equals(username) && this.password.equals(password)) {
            return this;
        }
        return null;
    }
    public boolean registrazioneHackathon(int hackathonID) {
        //TODO controllo nel database se esiste l'hackathon a cui stiamo iscrivendo l'utente
        this.hackathonID = hackathonID;
        return true;
    }


    public String getNome() { return this.nome; }

    public String getCognome() { return this.cognome; }

    public String getEmail() { return this.email; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }

    public int getHackathonID() { return this.hackathonID; }
    public void setHackathonID(int hackathonID) { this.hackathonID = hackathonID; }

    public int getTeamID() { return this.teamID; }
    public void setTeamID(int teamID) { this.teamID = teamID; }
}

