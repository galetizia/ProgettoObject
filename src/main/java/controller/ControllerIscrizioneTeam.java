package controller;

import gui.IscrizioneTeam;
import implementazionepostgresdao.TeamDAO;
import model.Utente;

import javax.swing.*;

public class ControllerIscrizioneTeam {

    private final IscrizioneTeam schermataIscrizioneTeam;

    private final MainController mainController;

    public ControllerIscrizioneTeam(MainController mainController, Utente utente) {
        this.schermataIscrizioneTeam = new IscrizioneTeam(this, utente);
        this.mainController = mainController;
    }

    public JPanel getIscrizioneTeam() {return schermataIscrizioneTeam.gerMainPanel();}

}
