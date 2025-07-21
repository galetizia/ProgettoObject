package dao;
import model.*;


public interface IOrganizzatoreDAO {

    boolean aggiungiGiudice(String username, Integer idHackathon);

    Organizzatore login(String username, String password);

    Organizzatore findOrganizzatoreByUsername(String username);
    Organizzatore findOrganizzatoreByEmail(String email);

    void removeUtente(String username);

    void removeGiudice(String username);

    void removeTeam(Integer id);

    void terminaHackathon(Integer id);

    void setClassifica(Integer id);
}
