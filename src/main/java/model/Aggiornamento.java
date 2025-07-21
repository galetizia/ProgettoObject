package model;

public class Aggiornamento {

    private final Integer teamID;
    private final String usernameUtente;
    private String nome;
    private final String documento;
    private boolean isElaboratoFinale;

    public Aggiornamento(String nome, String documento, Integer teamID, String usernameUtente) {
        this.nome = nome;
        this.documento = documento;
        this.teamID = teamID;
        this.usernameUtente = usernameUtente;
        this.isElaboratoFinale = false;
    }
    public void setNome(String nome) {this.nome = nome;}
    public String getNome() {return this.nome;}

    public String getDocumento() {return this.documento;}

    public Integer getTeamID() {return this.teamID;}

    public String getUsernameUtente() {return this.usernameUtente;}

    public void setElaboratoFinale(boolean isElaboratoFinale) {this.isElaboratoFinale = isElaboratoFinale;}
    public boolean getElaboratoFinale() { return isElaboratoFinale; }
}
