package model;

/**
 * Rappresenta un voto assegnato da un giudice a un team in un suo hackathon.
 */
public class Voto {

        private final int valutazione;
        private final Giudice giudice;
        private final Team team;

    /**
     * Instantiates a new Voto.
     *
     * @param valutazione il punteggio assegnato (0-10)
     * @param giudice     il giudice che assegna il voto
     * @param team        il team che riceve il voto
     */
    public Voto(int valutazione, Giudice giudice, Team team) {
            this.valutazione = valutazione;
            this.giudice = giudice;
            this.team = team;
        }

    /**
     * Restituisce la valutazione ricevuta.
     *
     * @return la valutazione
     */
    public int getValutazione() { return valutazione; }

    /**
     * Restituisce il giudice che lo ha assegnato.
     *
     * @return il giudice
     */
    public Giudice getGiudice() { return giudice; }

    /**
     * Restituisce il team che ha ricevuto il voto.
     *
     * @return il team
     */
    public Team getTeam() { return team; }


}
