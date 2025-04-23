package model;

import java.util.List;

public class Organizzatore extends Utente {

    public Organizzatore(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        this.ruolo="Organizzatore";
    }

    public Giudice aggiungiGiudice(Utente utente, List<Utente> partecipanti, Hackathon hackathon){

        if(partecipanti.contains(utente)) {

            partecipanti.remove(utente); // elimina l'utente dall'arraylist "partecipanti" poichè diventerà giudice
            Giudice g = new Giudice(utente.getNome(), utente.getCognome(), utente.email, utente.username, utente.password);
            hackathon.listaGiudici.add(g);
            return g;
        }
        else
            throw new IllegalArgumentException("L'utente selezionato è già un giudice");
    }

}
