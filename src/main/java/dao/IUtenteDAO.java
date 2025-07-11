package dao;
import model.*;

public interface IUtenteDAO {

    Utente login(String username, String password);
}

