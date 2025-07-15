package controller;

import gui.TeamSchermataUtente;
import implementazionepostgresdao.TeamDAO;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerTeamSchermataUtente {

    private final TeamSchermataUtente teamSchermataUtente;
    private ArrayList<Utente> listacomponenti;

    private final MainController maincontroller;

    public ControllerTeamSchermataUtente(MainController maincontroller, Team team, Utente utente) {
        this.teamSchermataUtente = new TeamSchermataUtente(this, team, utente);
        this.maincontroller = maincontroller;
        this.listacomponenti = team.componentiTeam;
    }

    public void abbandonaTeam(Utente utente) {
        TeamDAO dao = new TeamDAO();
        dao.rimuoviUtenteDalTeam(utente.getUsername());
        utente.setTeamID(null);

        maincontroller.showSchermataUtente(utente);
    }

    public JPanel getTeamSchermataUtente() { return teamSchermataUtente.getMainPanel(); }

    public void showSchermataUtente(Utente utente) {
        maincontroller.showSchermataUtente(utente);
    }

}//Modificato - Fabio (Metodo AbbandonaTeam)
