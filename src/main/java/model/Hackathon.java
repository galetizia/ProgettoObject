package model;
import java.time.LocalDate;

public class Hackathon {

    private final String titolo;
    private final String sede;
    private final String problema;
    private final LocalDate dataInizio;
    private final LocalDate dataFine;
    public LocalDate finePeriodoPrenotazioni;
    private final int maxIscritti;
    private final int maxDimTeam;
    private Integer hackathonID;

    public Hackathon(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, String problema, int maxIscritti, int maxDimTeam) {
        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.maxIscritti = maxIscritti;
        this.maxDimTeam = maxDimTeam;
        this.problema = problema;
        this.finePeriodoPrenotazioni = dataInizio.minusDays(2); //le iscrizioni chiudono due giorni prima
    }

    public int getMaxDimTeam() { return maxDimTeam; }

    public int getMaxIscritti() { return maxIscritti; }

    public String getProblema() { return problema; }

    public String getSede() { return sede; }

    public int getID(){ return this.hackathonID; }

    public void setID(Integer id) {this.hackathonID = id;}

    public String getNome(){ return this.titolo; }

    public LocalDate getDataInizio(){ return this.dataInizio; }

    public LocalDate getDataFine(){ return this.dataFine; }

    public LocalDate getFinePeriodoPrenotazioni(){ return this.finePeriodoPrenotazioni; }


}
