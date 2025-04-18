package model;

public class Aggiornamento {

    public String nome;
    public String documento; //deve essere caricato dal team
    public String commento; //deve essere caricato dal giudice

    protected Aggiornamento(String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
    }

}
