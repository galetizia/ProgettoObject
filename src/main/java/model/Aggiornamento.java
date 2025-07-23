package model;

/**
 * Rappresenta un aggiornamento associato a un team, caricato da un utente.
 * <p>
 * Ogni aggiornamento contiene:
 * - un nome identificativo
 * - un documento (file, link, o altro)
 * - l'ID del team associato
 * - lo username dell'utente che lo ha inviato
 * - un flag che indica se l'aggiornamento rappresenta l'elaborato finale
 * </p>
 */
public class Aggiornamento {

    private final Integer teamID;
    private final String usernameUtente;
    private String nome;
    private final String documento;
    private boolean isElaboratoFinale;

    /**
     * Crea una nuova istanza di {@code Aggiornamento}.
     *
     * @param nome           il nome dell'aggiornamento
     * @param documento      il contenuto del documento (esempio un percorso o link)
     * @param teamID         l'ID del team che ha caricato l'aggiornamento
     * @param usernameUtente lo username dell’utente che ha inviato l’aggiornamento
     */
    public Aggiornamento(String nome, String documento, Integer teamID, String usernameUtente) {
        this.nome = nome;
        this.documento = documento;
        this.teamID = teamID;
        this.usernameUtente = usernameUtente;
        this.isElaboratoFinale = false;
    }

    /**
     * Imposta il nome dell'aggiornamento.
     *
     * @param nome il nuovo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il nome dell'aggiornamento.
     *
     * @return il nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Restituisce il contenuto del documento.
     *
     * @return il documento (es. link o contenuto testuale)
     */
    public String getDocumento() {
        return this.documento;
    }

    /**
     * Restituisce l'ID del team associato all'aggiornamento.
     *
     * @return l'ID del team
     */
    public Integer getTeamID() {
        return this.teamID;
    }

    /**
     * Restituisce lo username dell'utente che ha caricato l'aggiornamento.
     *
     * @return lo username dell’utente
     */
    public String getUsernameUtente() {
        return this.usernameUtente;
    }

    /**
     * Imposta se l'aggiornamento rappresenta l'elaborato finale.
     *
     * @param isElaboratoFinale {@code true} se è l'elaborato finale, {@code false} altrimenti
     */
    public void setElaboratoFinale(boolean isElaboratoFinale) {
        this.isElaboratoFinale = isElaboratoFinale;
    }

    /**
     * Restituisce se l'aggiornamento è stato marcato come elaborato finale.
     *
     * @return {@code true} se è l'elaborato finale, {@code false} altrimenti
     */
    public boolean getElaboratoFinale() {
        return isElaboratoFinale;
    }
}
