package dao;
import model.*;

public interface IUtenteDAO {

    Utente login(String username, String password);

    boolean signIn(String nome, String cognome, String email, String username, String password);

    void changeIDTeam(Team team, Utente utente);

}
