package model;

public class Aggiornamento {

    private Integer idAggiornamento;
    public String nome;
    public String documento; //deve essere caricato dal team
    public String commento; //deve essere caricato dal giudice

    public Aggiornamento(String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
    }

    public String getNome() {return this.nome;}

    public String getDocumento() {return this.documento;}

    public String getCommento() {return this.commento;}

    public void setIdAggiornamento(Integer idAggiornamento) {this.idAggiornamento = idAggiornamento;}



}
