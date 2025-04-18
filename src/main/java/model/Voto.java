package model;

public class Voto {

        public int valutazione;
        Giudice giudice;
        Team team;

        protected Voto(int valutazione, Giudice giudice, Team team) {
            this.valutazione = valutazione;
            this.giudice = giudice;
            this.team = team;
        }

    public int getValutazione() {
            return valutazione;
    }

    public Giudice getGiudice() {
            return giudice;
    }

    public Team getTeam() {
            return team;
    }
}
