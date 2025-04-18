package model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Hackathon {

    public String titolo;
    public String sede;
    public String problema;
    public LocalDate dataInizio;
    public LocalDate dataFine;
    public LocalDate finePeriodoPrenotazioni;
    private final int maxIscritti;
    private final int maxDimTeam;
    private final ArrayList<Team> listaTeam;
    private final ArrayList<Utente> listaUtenti;

    public Hackathon(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, int maxIscritti, int maxDimTeam, String problema) {

        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.listaTeam = new ArrayList<>();
        this.listaUtenti = new ArrayList<>();
        this.maxIscritti = maxIscritti;
        this.maxDimTeam = maxDimTeam;
        this.finePeriodoPrenotazioni = dataInizio.minusDays(2); //le iscrizioni chiudono due giorni prima
        this.problema = problema;

    }
    public void iscriviUtente(Utente u){

        if(u!=null && !listaUtenti.contains(u) && listaUtenti.size()<this.maxIscritti){
            listaUtenti.add(u);
        }
    }

    public void aggiungiTeam(Team team){

           this.listaTeam.add(team);
    }

    public void mostraTeam(){

        System.out.println("Ecco la lista dei team:");

            for(Team t : listaTeam){
                System.out.println(t.nome);
            }
    }

    public int getMaxDimTeam() {
        return maxDimTeam;
    }
    public int getMaxIscritti() {
        return maxIscritti;
    }

    public boolean prenotazioniAperte() {
        return LocalDate.now().isBefore(finePeriodoPrenotazioni);
    }

    public String getProblema() {
        return problema;
    }
}
