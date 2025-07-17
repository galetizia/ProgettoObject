package controller;

import gui.GestioneHackathon;
import gui.IscrizioneTeam;
import model.Organizzatore;

import javax.swing.*;

public class ControllerGestioneHackathon {
    private final GestioneHackathon schermataGestioneHackathon;
    private final MainController mainController;
    private final Organizzatore organizzatoreLoggato;

    public ControllerGestioneHackathon(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataGestioneHackathon = new GestioneHackathon(this, organizzatore);
        this.organizzatoreLoggato = organizzatore;
    }

    public JPanel getGestioneHackathon() {
        return schermataGestioneHackathon.getMainPanel();
    }

    public void getSchermataOrganizzatore() {
        mainController.showSchermataOrganizzatore(organizzatoreLoggato);
    }

}
