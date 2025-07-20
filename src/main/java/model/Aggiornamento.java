package model;

public class Aggiornamento {

    private Integer idAggiornamento;
    private final Integer teamID;
    private final String usernameUtente;
    private String nome;
    private final String documento;
    private boolean isElaboratoFinale = false;


    public Aggiornamento(String nome, String documento, Integer teamID, String usernameUtente) {
        this.nome = nome;
        this.documento = documento;
        this.teamID = teamID;
        this.usernameUtente = usernameUtente;
    }
    public void setNome(String nome) {this.nome = nome;}
    public String getNome() {return this.nome;}

    public String getDocumento() {return this.documento;}

    public void setIdAggiornamento(Integer idAggiornamento) {this.idAggiornamento = idAggiornamento;}

    public Integer getTeamID() {return this.teamID;}

    public String getUsernameUtente() {return this.usernameUtente;}

    public void setElaboratoFinale(boolean isElaboratoFinale) {this.isElaboratoFinale = isElaboratoFinale;}
}
