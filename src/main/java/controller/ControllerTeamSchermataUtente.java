package controller;

import gui.Login;
import gui.TeamSchermataUtente;
import model.Organizzatore;
import model.Team;
import model.Utente;

import java.util.ArrayList;

public class ControllerTeamSchermataUtente {

    private final TeamSchermataUtente teamSchermataUtenteGui;
    private ArrayList<Utente> listacomponenti;
    private final MainController maincontroller;

    public ControllerTeamSchermataUtente(Team team, MainController maincontroller) {
        this.teamSchermataUtenteGui = new TeamSchermataUtente();
        this.maincontroller = maincontroller;
        this.listacomponenti = team.componentiTeam;
    }
}
