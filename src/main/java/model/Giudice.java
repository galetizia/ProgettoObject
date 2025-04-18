package model;

public class Giudice extends Utente {

    protected Giudice(String nome, String cognome, String email, int id, String username, String password) {
        super(nome, cognome, email, id, username, password);
        this.ruolo="Giudice";
    }

    public Voto valutaTeam(Giudice giudice, Team team, int punteggio) {
        return new Voto(punteggio, giudice, team);
    }

    public void commentaAggiornamento(Aggiornamento a, String commento) {

        a.commento = commento;
    }



}
