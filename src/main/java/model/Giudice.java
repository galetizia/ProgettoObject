package model;

public class Giudice extends Utente {

    protected Giudice(String nome, String cognome, String email, String username, String password) {
        super(nome, cognome, email, username, password);
        this.ruolo="Giudice";
    }

    public Voto valutaTeam(Team team, int punteggio) {
        if(punteggio<=0 || punteggio>10)
            throw new IllegalArgumentException("Il punteggio assegnato dal giudice non è valido, deve essere compreso tra 1 e 10");
        return new Voto(punteggio, this, team);
    }

    public void commentaAggiornamento(Aggiornamento a, String commento) { a.commento = commento; }

}
