package model;

public class Giudice extends Utente {

    public Giudice(String nome, String cognome, String email, String username, String password, int hackathonID) {
        super(nome, cognome, email, username, password);
        setHackathonID(hackathonID);
    }

}
