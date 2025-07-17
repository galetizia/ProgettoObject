package dao;
import model.*;

import java.util.List;

public interface IOrganizzatoreDAO {

    Giudice aggiungiGiudice(String username, Hackathon hackathon);

    Organizzatore login(String username, String password);

    boolean signIn(String nome, String cognome, String email, String username, String password);

    String getHackathonTitleByID(Integer id);

    void removeUtente(String username);

    void removeGiudice(String username);

    void removeTeam(Integer id);
}
