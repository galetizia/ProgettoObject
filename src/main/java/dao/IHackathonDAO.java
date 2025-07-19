package dao;

import model.*;

import java.util.List;

public interface IHackathonDAO {

    Utente findUtenteByUsername(String username);

    Organizzatore findOrganizzatoreByUsername(String username);

    Utente findUtenteByEmail(String email);

    Organizzatore findOrganizzatoreByEmail(String email);

    Giudice findGiudiceByUsername(String username);

    Giudice findGiudiceByEmail(String email);

    int getMaxDimTeam(Integer id);
    int getMaxIscritti(Integer id);

    List<Hackathon> getHackathons();

    Hackathon getHackathonByID(Integer id);

    List<Utente> getUtenti(Integer id);
    List<Giudice> getGiudici(Integer id);

    boolean isClassificaPubblicata(Integer id);



}