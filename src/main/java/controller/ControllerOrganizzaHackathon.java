package controller;

import gui.OrganizzaHackathon;

import model.Organizzatore;

import javax.swing.*;

public class ControllerOrganizzaHackathon {
    private final OrganizzaHackathon schermataOrganizzaHackathon;

    private final MainController mainController;

    private final Organizzatore organizzatore;

    public ControllerOrganizzaHackathon(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.organizzatore = organizzatore;
        this.schermataOrganizzaHackathon = new OrganizzaHackathon(this, organizzatore);

    }

    public JPanel getOrganizzaHackathon() {return schermataOrganizzaHackathon.getMainPanel();}
}
