package model;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Hackathon {

    public final String titolo;
    public String sede;
    protected String problema;
    public LocalDate dataInizio;
    public LocalDate dataFine;
    public LocalDate finePeriodoPrenotazioni;
    private final int maxIscritti;
    private final int maxDimTeam;
    private final ArrayList<Team> listaTeam;
    private final ArrayList<Utente> listaUtenti;

    public Hackathon(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, int maxIscritti, int maxDimTeam) {
        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.listaTeam = new ArrayList<>();
        this.listaUtenti = new ArrayList<>();
        this.maxIscritti = maxIscritti;
        this.maxDimTeam = maxDimTeam;
        this.finePeriodoPrenotazioni = dataInizio.minusDays(2); //le iscrizioni chiudono due giorni prima
    }

    public void iscriviUtente(Utente u){
        if(u!=null && !listaUtenti.contains(u) && listaUtenti.size()<this.maxIscritti){
            this.listaUtenti.add(u);
        }
    }

    public void aggiungiTeam(Team team){ this.listaTeam.add(team); }

    public void mostraTeam(){
        System.out.println("Ecco la lista dei team:");
            for(Team t : listaTeam)
                System.out.println("|" +t.getNome());
    }

    public int getMaxDimTeam() { return maxDimTeam; }

    public int getMaxIscritti() { return maxIscritti; }

    public boolean prenotazioniAperte() { return LocalDate.now().isBefore(finePeriodoPrenotazioni);}

    public String getProblema() { return problema; }

    public void pubblicaClassifica(List<Voto> voti){
        for (Voto v : voti) {
            System.out.println("|Nome Team: " + v.getTeam().getNome());
            System.out.println("Voto: " + v.getValutazione());
        }
    }


}
