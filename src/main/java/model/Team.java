package model;
import java.util.ArrayList;

public class Team {
    private final String nome;
    private final ArrayList <Utente> componentiTeam;
    private final int maxDimTeam;
    protected ArrayList<Voto> voti= new ArrayList<>();
    protected double mediaVoti=0;

    public Team(String nome, Hackathon hackathon) {
        this.nome = nome;
        this.componentiTeam= new ArrayList<>();
        this.maxDimTeam=hackathon.getMaxDimTeam();
    }

    public void aggiungiMembro(Utente u){
        if(u!=null && !componentiTeam.contains(u) && componentiTeam.size()<maxDimTeam){
            componentiTeam.add(u);
        }
    }

    public void stampaTeam(){
        System.out.println("Team: " + this.nome);
            for(Utente u : componentiTeam){
                System.out.println("Membro: " + u.username);
            }
    }

    public void stampaVoti(){
        for(Voto v : voti){
            mediaVoti = mediaVoti + v.getValutazione();

        }
        mediaVoti /= voti.size();
        System.out.println("Nome Team: " +this.getNome());
        System.out.println("Media voti ricevuti: " + mediaVoti);
    }



    public boolean isPieno(){ return componentiTeam.size()>=maxDimTeam; }

    public boolean isVuoto(){ return componentiTeam.isEmpty(); }

    public Aggiornamento creaAggiornamento(String nome, String documento){
        return new Aggiornamento(nome, documento);
    }

    public void espelliMembro(Utente u){ componentiTeam.remove(u); }

    public String getNome(){ return this.nome; }

}
