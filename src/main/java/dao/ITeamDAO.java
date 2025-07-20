package dao;
import model.*;

import java.util.List;

public interface ITeamDAO {

    Team getTeamByID(Integer id);

    List<Utente> membriTeam(Integer id);

    void rimuoviUtenteDalTeam(String username);

    void caricaTeamNelDB(Team team, Utente utente);

    void caricaAggiornamentoDB(Utente utente, Aggiornamento aggiornamento, boolean isElaboratoFinale);

    String getUltimoAggiornamento(Integer id);

    Integer getIdAggiornamentoByTeam(Integer id);

    List<Double> getVotiPerTeam(Integer id);

    void setVotiPerTeam(Integer id, Double media);

    Integer getHackathonByTeam(Integer id);

    boolean getElaboratoFinaleUltimoAggiornamento(Integer id);
}
