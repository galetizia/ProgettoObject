package dao;

import model.*;

public interface IHackathonDAO {

    Utente findUtenteByUsername(String username);

    Organizzatore findOrganizzatoreByUsername(String username);

    Utente findUtenteByEmail(String email);

    Organizzatore findOrganizzatoreByEmail(String email);

    int getMaxDimTeam(Integer ID);
}