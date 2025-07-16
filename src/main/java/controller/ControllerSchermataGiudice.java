package controller;

import gui.SchermataGiudice;
import model.*;
import javax.swing.*;

public class ControllerSchermataGiudice {
    private final SchermataGiudice schermataGiudice;

    private final MainController mainController;

    public ControllerSchermataGiudice(MainController mainController, Giudice giudice) {
        this.mainController = mainController;
        schermataGiudice = new SchermataGiudice(this, giudice);
    }
    public JPanel getSchermataGiudice() {
        return schermataGiudice.getMainPanel();
    }

    public void logout(){mainController.logout();}


}
