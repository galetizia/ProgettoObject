package dao;
import model.*;

public interface IUtenteDAO {

    Utente login(String username, String password);

    Utente findUtenteByUsername(String username);

    Utente findUtenteByEmail(String email);

    void changeIDTeam(Team team, Utente utente);

}
