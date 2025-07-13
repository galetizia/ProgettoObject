package controller;

import gui.Login;
import gui.TeamSchermataUtente;
import model.Organizzatore;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerTeamSchermataUtente {

    private final TeamSchermataUtente teamSchermataUtente;
    private ArrayList<Utente> listacomponenti;

    private final MainController maincontroller;

    public ControllerTeamSchermataUtente(Team team, MainController maincontroller) {
        this.teamSchermataUtente = new TeamSchermataUtente(this, team);
        this.maincontroller = maincontroller;
        this.listacomponenti = team.componentiTeam;
    }
    public JPanel getTeamSchermataUtente() { return teamSchermataUtente.getMainPanel(); }

}
