package model;

public class Utente {

    private final String nome;
    private final String cognome;
    public final String email;
    public String ruolo = "Partecipante";
    public String username;
    public String password;
    private int hackathonID;

    public Utente(String nome, String cognome, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.hackathonID = 0;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
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
    public void setHackathonID(int hackathonID) {
        this.hackathonID = hackathonID;
    }
}

