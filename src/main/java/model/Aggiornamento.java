package model;

public class Aggiornamento {

    private Integer idAggiornamento;
    private Integer teamID;
    private String usernameUtente;
    private String nome;
    private String documento; //deve essere caricato dal team

    public String commento; //deve essere caricato dal giudice

    public Aggiornamento(String nome, String documento, Integer teamID, String usernameUtente) {
        this.nome = nome;
        this.documento = documento;
        this.teamID = teamID;
        this.usernameUtente = usernameUtente;
    }
    public void setNome(String nome) {this.nome = nome;}
    public String getNome() {return this.nome;}

    public String getDocumento() {return this.documento;}
    public void setDocumento(String documento) {this.documento = documento;}

    public void setIdAggiornamento(Integer idAggiornamento) {this.idAggiornamento = idAggiornamento;}
    public Integer getIdAggiornamento() {return this.idAggiornamento;}

    public void setTeamID(Integer teamID) {this.teamID = teamID;}
    public Integer getTeamID() {return this.teamID;}

    public void setUsernameUtente(String usernameUtente) {this.usernameUtente = usernameUtente;}
    public String getUsernameUtente() {return this.usernameUtente;}



}
