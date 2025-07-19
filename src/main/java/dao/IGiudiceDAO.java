package dao;

import model.Giudice;
import model.Team;
import model.Voto;

import java.util.List;

public interface IGiudiceDAO {

    Giudice login(String username, String password);

    void saveCommento(String commento, Integer id, Giudice giudice);

    boolean haCommentatoAggiornamento(Integer id, Giudice giudice);

    void caricaVoto(Voto voto);

    boolean controlloVotoTeam(Integer teamId, String giudiceID);

    List<Team> getElaboratiFinaliTeam(Integer id);

    boolean isElaboratoFinale(Integer idTeam);
}
