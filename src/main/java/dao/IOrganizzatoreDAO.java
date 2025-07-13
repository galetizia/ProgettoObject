package dao;
import model.*;

public interface IOrganizzatoreDAO {

    Giudice aggiungiGiudice(String username, Hackathon hackathon);

    Utente trovaUtentePerUsername(String username);

    Organizzatore login(String username, String password);

    boolean signIn(String nome, String cognome, String email, String username, String password);
}
