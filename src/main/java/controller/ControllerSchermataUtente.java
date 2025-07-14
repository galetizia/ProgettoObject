package controller;

import gui.SchermataUtente;
import implementazionepostgresdao.TeamDAO;
import model.*;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ControllerSchermataUtente {
    private final SchermataUtente schermataUtente;
    private ArrayList<Utente> utenti;
    private ArrayList<Organizzatore> organizzatori;
    TeamDAO tdao = new TeamDAO();

    private final MainController mainController;

    public ControllerSchermataUtente(ArrayList<Utente> utenti, ArrayList<Organizzatore> organizzatori, MainController mainController, Utente utente) {
        this.utenti = utenti;
        this.organizzatori = organizzatori;
        this.mainController = mainController;
        this.schermataUtente = new SchermataUtente(this,utente);
    }
    public JPanel getSchermataUtente() {
        return schermataUtente.getMainPanel();
    }
    public void schermataUtente() {}

    public void schermataTeamUtente(Utente utente){
        if(utente.getTeamID() != null) {
            Team team = tdao.getTeamByID(utente.getTeamID());
            mainController.showTeamSchermataUtente(team);
        }
    }

    public void logout() {
        mainController.logout();
    }


}
