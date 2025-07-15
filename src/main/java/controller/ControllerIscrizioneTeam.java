package controller;

import gui.IscrizioneTeam;

import model.Utente;

import javax.swing.*;

public class ControllerIscrizioneTeam {

    private final IscrizioneTeam schermataIscrizioneTeam;

    private final MainController mainController;
    private final Utente u;

    public ControllerIscrizioneTeam(MainController mainController, Utente utente) {
        this.mainController = mainController;
        this.schermataIscrizioneTeam = new IscrizioneTeam(this, utente);
        this.u = utente;
    }

    public JPanel getIscrizioneTeam() {return schermataIscrizioneTeam.getMainPanel();}

    public void showUtente() {mainController.showSchermataUtente(u); }

}
