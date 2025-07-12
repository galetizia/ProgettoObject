package dao;
import model.*;

public interface IOrganizzatoreDAO {

    Giudice aggiungiGiudice(String username, Hackathon hackathon);

    Utente trovaUtentePerUsername(String username);

    Organizzatore login(String username, String password);
}
