package model;
import java.util.ArrayList;

public class Team {
    public String nome;
    ArrayList <Utente> componentiTeam;
    final int maxDimTeam;

    public Team(String nome, int maxDimTeam) {
        this.nome = nome;
        this.componentiTeam= new ArrayList<>();
        this.maxDimTeam=maxDimTeam;
    }

    public String getNome(){
        return nome;
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
    public boolean isPieno(){
        return componentiTeam.size()>=maxDimTeam;

    }

    public Aggiornamento creaAggiornamento(String nome, String documento){

        return new Aggiornamento(nome, documento);
    }
}
