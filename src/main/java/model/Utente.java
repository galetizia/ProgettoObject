package model;

public class Utente {

    public final String cognome;
    public final String nome;
    public final String email;
    public String ruolo="Partecipante";
    public String username;
    public String password;

    public Utente(String nome, String cognome, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
    }


    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

}

