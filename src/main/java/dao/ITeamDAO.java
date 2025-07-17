package dao;
import model.*;

import java.util.List;

public interface ITeamDAO {

    Team getTeamByID(Integer id);

    List<Utente> membriTeam(Integer id);

    void rimuoviUtenteDalTeam(String username);

    List<Team> getTeamByHackathon(Integer id);

    void caricaTeamNelDB(Team team, Utente utente);

    void caricaAggiornamentoDB(Team team, Aggiornamento aggiornamento);
}
