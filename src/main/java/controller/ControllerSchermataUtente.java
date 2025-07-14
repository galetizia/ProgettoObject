package controller;

import gui.SchermataUtente;
import implementazionepostgresdao.TeamDAO;
import model.*;

import javax.swing.*;

public class ControllerSchermataUtente {
    private final SchermataUtente schermataUtente;
    TeamDAO tdao = new TeamDAO();

    private final MainController mainController;

    public ControllerSchermataUtente(MainController mainController, Utente utente) {
        this.mainController = mainController;
        this.schermataUtente = new SchermataUtente(this,utente);
    }
    public JPanel getSchermataUtente() {
        return schermataUtente.getMainPanel();
    }

    public void schermataTeamUtente(Utente utente){
        if(utente.getTeamID() != null) {
            Team team = tdao.getTeamByID(utente.getTeamID());
            mainController.showTeamSchermataUtente(team, utente);
        }
    }

    public void logout() {
        mainController.logout();
    }


} //modificato - Fabio (Parametro Utente)
