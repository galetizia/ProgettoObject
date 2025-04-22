package model;

import java.util.List;

public class Organizzatore extends Utente {

    public Organizzatore(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        this.ruolo="Organizzatore";
    }

    public Giudice aggiungiGiudice(Utente utente, List<Utente> partecipanti){
        partecipanti.remove(utente); // elimina l'utente dall'arraylist "partecipanti" poichè diventerà giudice
        return new Giudice(utente.getNome(), utente.getCognome(), utente.email, utente.username, utente.password);
    }

}
