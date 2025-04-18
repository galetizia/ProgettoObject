package model;

public class Giudice extends Utente {

    protected Giudice(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        this.ruolo="Giudice";
    }

    public Voto valutaTeam(Team team, int punteggio) {
        return new Voto(punteggio, this, team);
    }

    public void commentaAggiornamento(Aggiornamento a, String commento) {
        a.commento = commento;
    }

}
