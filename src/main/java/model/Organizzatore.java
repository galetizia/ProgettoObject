package model;

import java.time.LocalDate;
import java.util.List;

public class Organizzatore extends Utente {

    public Organizzatore(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        this.ruolo="Organizzatore";
    }

    public Giudice aggiungiGiudice(Utente utente, List<Utente> partecipanti, Hackathon hackathon){

        if(partecipanti.contains(utente)) {

            partecipanti.remove(utente); // elimina l'utente dall'arraylist "partecipanti" poichè diventerà giudice
            Giudice g = new Giudice(utente.getNome(), utente.getCognome(), utente.email, utente.username, utente.password, utente.getHackathonID());
            hackathon.listaGiudici.add(g);
            return g;
        }
        else
            throw new IllegalArgumentException("L'utente selezionato è già un giudice");
    }

    public Hackathon creaHackathon(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, int maxIscritti, int maxDimTeam, int hackathonID){
        if(hackathonID>=0 && this.getHackathonID()==0) {
            this.setHackathonID(hackathonID);
            return new Hackathon(titolo, sede, dataInizio, dataFine, maxIscritti, maxDimTeam, hackathonID);
        }
        return null;
    }

}
