package model;

public class Organizzatore extends Utente {

    public Organizzatore(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        this.ruolo="Organizzatore";
    }

    public Giudice aggiungiGiudice(Utente u){
        return new Giudice(u.nome, u.cognome, u.email, u.username, u.password);
    }

}
