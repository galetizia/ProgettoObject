package dao;
import model.*;

import java.util.List;

public interface ITeamDAO {

    Team getTeamByID(Integer id);

    List<Utente> membriTeam(Integer id);

    void rimuoviUtenteDalTeam(String username);//Modificato - Fabio
}
