package model;
import java.util.ArrayList;
import java.time.LocalDate;

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
    protected final ArrayList<Giudice> listaGiudici;

    public Hackathon(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, int maxIscritti, int maxDimTeam) {
        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.listaTeam = new ArrayList<>();
        this.listaUtenti = new ArrayList<>();
        this.listaGiudici = new ArrayList<>();
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

    public void pubblicaClassifica(){

        double max=0;
        Team temp = new Team("Temp", this);
        ArrayList<Team> listaClassifica;
        listaClassifica = listaTeam;
        int c=1;

        while(!listaClassifica.isEmpty()) {
            for (Team t : listaClassifica) {

                if (t.mediaVoti >= max) {
                    max = t.mediaVoti;
                    temp = t;
                }
            }
            max=0;
            System.out.println(c +") Team: " + temp.getNome() + " Media voti: " + temp.mediaVoti);
            listaClassifica.remove(temp);
            c++;
        }
    }

    public void stampaGiudici(){

        int c=1;

        System.out.println("Lista dei Giudici:");

        for(Giudice g : listaGiudici){
            System.out.println("|Giudice n." + c + " " +g.getNome() + " " + g.getCognome());

            c++;
        }

    }


}
