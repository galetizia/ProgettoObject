package model;
import implementazionepostgresdao.HackathonDAO;

import java.util.ArrayList;

public class Team {
    private int id;
    private Integer hackathonID;
    private final String nome;
    public final ArrayList <Utente> componentiTeam;
    private final int maxDimTeam;
    protected ArrayList<Voto> voti= new ArrayList<>();
    protected double mediaVoti=0;
    private HackathonDAO hdao = new HackathonDAO();

    public Team(int id, String nome, double mediaVoti, Integer hackathonID) {
        this.id = id;
        this.nome = nome;
        this.componentiTeam= new ArrayList<>();
        this.mediaVoti = mediaVoti;
        this.hackathonID = hackathonID;
        this.maxDimTeam = hdao.getMaxDimTeam(hackathonID);
    }

    public void aggiungiMembro(Utente u){
        if(u!=null && !componentiTeam.contains(u) && componentiTeam.size()<maxDimTeam){
            componentiTeam.add(u);
        }
    }

    public void stampaTeam(){
        System.out.println("Team: " + this.nome);
            for(Utente u : componentiTeam){
                System.out.println("Membro: " + u.getUsername());
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

    public Integer getId(){ return this.id; }

}
