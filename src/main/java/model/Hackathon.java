package model;

import java.time.LocalDate;

/**
 * Rappresenta un hackathon, con informazioni su titolo, sede, date, problema proposto,
 * limiti di iscrizione e dimensione dei team.
 * <p>
 * Contiene anche la logica per determinare la fine del periodo di prenotazioni,
 * che avviene due giorni prima della data di inizio.
 * </p>
 */
public class Hackathon {

    private final String titolo;
    private final String sede;
    private final String problema;
    private final LocalDate dataInizio;
    private final LocalDate dataFine;

    /**
     * Data di chiusura del periodo di prenotazione, calcolata automaticamente come
     * due giorni prima della {@code dataInizio}.
     */
    public LocalDate finePeriodoPrenotazioni;

    private final int maxIscritti;
    private final int maxDimTeam;
    private Integer hackathonID;

    /**
     * Crea una nuova istanza di {@code Hackathon}.
     *
     * @param titolo      il titolo dell'hackathon
     * @param sede        la sede dell'hackathon
     * @param dataInizio  la data di inizio dell'hackathon
     * @param dataFine    la data di fine dell'hackathon
     * @param problema    il problema che i partecipanti dovranno affrontare
     * @param maxIscritti il numero massimo di partecipanti
     * @param maxDimTeam  la dimensione massima di un team
     */
    public Hackathon(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, String problema, int maxIscritti, int maxDimTeam) {
        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.maxIscritti = maxIscritti;
        this.maxDimTeam = maxDimTeam;
        this.problema = problema;
        this.finePeriodoPrenotazioni = dataInizio.minusDays(2); // le iscrizioni chiudono due giorni prima
    }

    /**
     * Restituisce la dimensione massima di un team.
     *
     * @return la dimensione massima del team
     */
    public int getMaxDimTeam() {
        return maxDimTeam;
    }

    /**
     * Restituisce il numero massimo di iscritti ammessi.
     *
     * @return il numero massimo di iscritti
     */
    public int getMaxIscritti() {
        return maxIscritti;
    }

    /**
     * Restituisce la descrizione del problema da risolvere.
     *
     * @return il problema
     */
    public String getProblema() {
        return problema;
    }

    /**
     * Restituisce la sede dell’hackathon.
     *
     * @return la sede
     */
    public String getSede() {
        return sede;
    }

    /**
     * Restituisce l’ID dell’hackathon.
     *
     * @return l’ID univoco dell’hackathon
     */
    public int getID() {
        return this.hackathonID;
    }

    /**
     * Imposta l’ID dell’hackathon.
     *
     * @param id il nuovo ID dell’hackathon
     */
    public void setID(Integer id) {
        this.hackathonID = id;
    }

    /**
     * Restituisce il titolo dell’hackathon.
     *
     * @return il titolo
     */
    public String getNome() {
        return this.titolo;
    }

    /**
     * Restituisce la data di inizio dell’hackathon.
     *
     * @return la data di inizio
     */
    public LocalDate getDataInizio() {
        return this.dataInizio;
    }

    /**
     * Restituisce la data di fine dell’hackathon.
     *
     * @return la data di fine
     */
    public LocalDate getDataFine() {
        return this.dataFine;
    }

    /**
     * Restituisce la data di fine del periodo di prenotazione.
     * Le prenotazioni si chiudono due giorni prima dell’inizio dell’evento.
     *
     * @return la data di fine prenotazioni
     */
    public LocalDate getFinePeriodoPrenotazioni() {
        return this.finePeriodoPrenotazioni;
    }
}
