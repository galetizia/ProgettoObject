package controller;

import gui.SchermataOrganizzatore;
import model.*;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerSchermataOrganizzatore {
    private final SchermataOrganizzatore schermataOrganizzatore;
    private ArrayList<Organizzatore> organizzatori;
    private ArrayList<Utente> utenti;

    private final MainController mainController;

    public ControllerSchermataOrganizzatore(ArrayList<Utente> utenti, ArrayList<Organizzatore> organizzatori, MainController mainController, Organizzatore organizzatore) {
        this.utenti = utenti;
        this.organizzatori = organizzatori;
        this.mainController = mainController;
        this.schermataOrganizzatore = new SchermataOrganizzatore(this,organizzatore);
    }
    public JPanel getSchermataOrganizzatore() {
        return schermataOrganizzatore.getMainPanel();
    }
    public void schermataOrganizzatore() {}

    public void logout() {
        mainController.logout();
    }
}
