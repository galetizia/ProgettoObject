package controller;

import gui.SchermataOrganizzatore;
import model.*;

import javax.swing.*;

public class ControllerSchermataOrganizzatore {
    private final SchermataOrganizzatore schermataOrganizzatore;

    private final MainController mainController;

    public ControllerSchermataOrganizzatore(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataOrganizzatore = new SchermataOrganizzatore(this,organizzatore);
    }
    public JPanel getSchermataOrganizzatore() {
        return schermataOrganizzatore.getMainPanel();
    }

    public void logout() {
        mainController.logout();
    }

} //modificato
