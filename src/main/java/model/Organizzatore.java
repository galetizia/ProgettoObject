package model;

public class Organizzatore extends Utente {

    public Organizzatore(String nome, String cognome, String email, int id, String username, String password) {
        super(nome, cognome, email, id, username, password);
        this.ruolo="Organizzatore";
    }
    public Giudice aggiungiGiudice(Utente u){
        return new Giudice(u.nome, u.cognome, u.email, u.id, u.username, u.password);
    }

}
