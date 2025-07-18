package dao;

import model.Giudice;
import model.Voto;

public interface IGiudiceDAO {

    Giudice login(String username, String password);

    void saveCommento(String commento, Integer id, Giudice giudice);

    boolean haCommentatoAggiornamento(Integer id, Giudice giudice);

    void caricaVoto(Voto voto);

    boolean controlloVotoTeam(Integer team_id);
}
