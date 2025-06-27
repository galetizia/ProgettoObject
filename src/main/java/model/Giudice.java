package model;

public class Giudice extends Utente {

    public Giudice(String nome, String cognome, String email, String username, String password, int hackathonID) {
        super(nome, cognome, email, username, password, 0 ,hackathonID);
        this.ruolo="Giudice";
    }

    public void valutaTeam(Team team, int punteggio) {
        if(punteggio<=0 || punteggio>10)
            throw new IllegalArgumentException("Il punteggio assegnato dal giudice non è valido, deve essere compreso tra 1 e 10");
        team.voti.add(new Voto(punteggio, this, team));
    }

    public void commentaAggiornamento(Aggiornamento a, String commento) { a.commento = commento; }

    public void pubblicaProblema(String problema, Hackathon hackathon){
        hackathon.problema=problema; //Stabiliamo il problema che i partecipanti dovranno risolvere

    }

}
