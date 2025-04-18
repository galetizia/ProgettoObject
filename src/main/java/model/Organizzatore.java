package model;

public class Organizzatore extends Utente {

    public Organizzatore(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        this.ruolo="Organizzatore";
    }

    public Giudice aggiungiGiudice(Utente utente){
        return new Giudice(utente.getNome(), utente.getCognome(), utente.email, utente.username, utente.password);
    }

}
