package model;
import java.util.ArrayList;

public class Team {
    private final String nome;
    private final ArrayList <Utente> componentiTeam;
    private final int maxDimTeam;

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

    public boolean isPieno(){ return componentiTeam.size()>=maxDimTeam; }

    public boolean isVuoto(){ return componentiTeam.isEmpty(); }

    public Aggiornamento creaAggiornamento(String nome, String documento){
        return new Aggiornamento(nome, documento);
    }

    public void espelliMembro(Utente u){ componentiTeam.remove(u); }

    public String getNome(){ return this.nome; }

}
