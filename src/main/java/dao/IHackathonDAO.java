package dao;

import model.*;

import java.util.List;

public interface IHackathonDAO {

    int getMaxDimTeam(Integer id);
    int getMaxIscritti(Integer id);

    boolean signInUtente(String nome, String cognome, String email, String username, String password);
    boolean signInOrganizzatore(String nome, String cognome, String email, String username, String password);
    List<Hackathon> getHackathons();
    void caricaHackathonDB(Hackathon hackathon, Organizzatore organizzatore);

    Hackathon getHackathonByID(Integer id);
    List<Team> getTeamByHackathon(Integer id);
    Integer getHackathonByTeam(Integer id);
    Integer getIdAggiornamentoByTeam(Integer id);

    List<Utente> getUtenti(Integer id);
    List<Giudice> getGiudici(Integer id);
    List<Utente> getPotenzialiGiudici();

    List<Team> getClassificaTeams(Integer hackathonID);

    boolean isClassificaPubblicata(Integer id);



}