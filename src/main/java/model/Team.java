package model;
import implementazionepostgresdao.HackathonDAO;

public class Team {
    private int id;
    private Integer hackathonID;
    private final String nome;
    private final int maxDimTeam;
    private double mediaVoti=0;

    HackathonDAO hdao = new HackathonDAO();

    public Team(String nome, Integer hackathonID) { //creazione nuovo team
        this.nome = nome;
        this.maxDimTeam = hdao.getMaxDimTeam(hackathonID);
        this.hackathonID = hackathonID;
    }

    public Team(int id, String nome, double mediaVoti, Integer hackathonID) {
        this.id = id;
        this.nome = nome;
        this.mediaVoti = mediaVoti;
        this.hackathonID = hackathonID;
        this.maxDimTeam = hdao.getMaxDimTeam(hackathonID);
    }

    public String getNome(){ return this.nome; }

    public Integer getId(){ return this.id; }
    public void setId(int id) { this.id = id; }

    public Double getMediaVoti() { return mediaVoti; }

    public Integer getHackathonID() { return hackathonID; }
    public void setHackathonID(Integer hackathonID) { this.hackathonID = hackathonID; }



}
